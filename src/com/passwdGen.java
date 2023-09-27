package com;

import java.security.SecureRandom;
import java.util.Scanner;

public class passwdGen {
	
	private final String upperLettersKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String lowerLettersKey = "abcdefghijklmnopqrstuvwxyz";
	private final String numsKey = "1234567890";
	private final String symbolsKey = "!@#$%^&*?";
	
	private Scanner input;
	private PasswordSettings settings = new PasswordSettings();
	
	/* 
	 * Author: 
	 * Peter Vislocky
	 */
	
	/*
	 * The main class of the program, handles all aspects of generating the password. Mainloop() method handles the main user interaction of the program
	 * TODO create a menu method listing all different options for user and calling a different method depending on what the user selects
	 * TODO add in multi-language support, look for a translator API
	 */
	
	public passwdGen(Scanner scanner) {
		this.input = scanner;
		mainloop();
	}
	public void mainloop() {
		boolean runagain = true;
		boolean upperLetters;
		boolean lowerLetters;
		boolean nums;
		boolean symbols;
		
			while (runagain) {
				System.out.println("Password Generator\nBe aware, improper input for any of the prompts asking what characters you want included will default to yes.\nNone of the inputs are case sensitive.");
				//all code between here and the runAgain() method call is to be deleted and replaced with the menu method call when I'm done writing that.
				boolean copyToClip = true;
				UserInputHandler UIHand = new UserInputHandler(input);
				
				int length = UIHand.returnLength();
				
				upperLetters = UIHand.getCharChoice("Upper case letters included? ");
				lowerLetters = UIHand.getCharChoice("Lower letters included? ");
				nums = UIHand.getCharChoice("Numbers included? ");
				symbols = UIHand.getCharChoice("Symbols included? ");
				settings.setProperty("upperLetters", String.valueOf(upperLetters));
				settings.setProperty("lowerLetters", String.valueOf(lowerLetters));
				settings.setProperty("nums", String.valueOf(nums));
				settings.setProperty("symbols", String.valueOf(symbols));
				boolean upperIncluded = settings.getProperty("upperLetters");
				boolean lowerIncluded = settings.getProperty("lowerLetters");
				boolean numsIncluded = settings.getProperty("nums");
				boolean symbolsIncluded = settings.getProperty("symbols");
				settings.saveProperties();
				
				String password = generator(length, upperIncluded, lowerIncluded, numsIncluded, symbolsIncluded);
				if (password.isEmpty()) {
					runagain = UIHand.runAgain();
				} else {
					System.out.println("\u001B[92mGenerated Password: " + password + "\u001B[0m");

					while(copyToClip) {
						System.out.print("Copy password to clipboard? (y/n): ");
						String copy = input.nextLine();
						if (copy.equalsIgnoreCase("y")){
							UIHand.copyToClipboard(password);
							break;
						}
						else if (copy.equalsIgnoreCase("n")) {
							break;
						} else {
							System.out.println("Invalid input. Correct input: (y/n)");
							continue;
						}
					}
					//stop deleting here
					runagain = UIHand.runAgain();
				}
			}
	}
	//untested work in progress, not in use at the moment
	public void menu() {
		boolean upperLetters;
		boolean lowerLetters;
		boolean nums;
		boolean symbols;
		boolean genLoop = true;
		boolean settingsLoop = true;
		
		UserInputHandler UIHand = new UserInputHandler(input);
		
		System.out.print("Choose an option by inputting the corresponding number\n1 Password Generator \n2Settings \n3 Useful Information \n4 Instructions for Use \n5 Exit");
		int menuChoice = input.nextInt();
		input.nextLine();
		switch (menuChoice) {
		case 1:
			while (genLoop) {
				boolean copyToClip = true;
				int length = UIHand.returnLength();
				System.out.println("Generating Password ");
				boolean upperIncluded = settings.getProperty("upperLetters");
				boolean lowerIncluded = settings.getProperty("lowerLetters");
				boolean numsIncluded = settings.getProperty("nums");
				boolean symbolsIncluded = settings.getProperty("symbols");
				String password = generator(length, upperIncluded, lowerIncluded, numsIncluded, symbolsIncluded);
				if (password.isEmpty()) {
					genLoop = UIHand.runAgain();
				} else {
					System.out.println("\u001B[92mGenerated Password: " + password + "\u001B[0m");
				}
				while(copyToClip) {
					System.out.print("Copy password to clipboard? (y/n): ");
					String copy = input.nextLine();
					if (copy.equalsIgnoreCase("y")){
						UIHand.copyToClipboard(password);
						break;
					}
					else if (copy.equalsIgnoreCase("n")) {
						break;
					} else {
						System.out.println("Invalid input. Correct input: (y/n)");
						continue;
					}
				}
				genLoop = UIHand.runAgain();
			}
			break;
		case 2:
			System.out.println("Settings\nChoose which characters to include, once you are done it will automatically save.");
			while(settingsLoop) {
				boolean confSettingsLoop = true;
				upperLetters = UIHand.getCharChoice("Upper case letters included? ");
				lowerLetters = UIHand.getCharChoice("Lower letters included? ");
				nums = UIHand.getCharChoice("Numbers included? ");
				symbols = UIHand.getCharChoice("Symbols included? ");
				settings.setProperty("upperLetters", String.valueOf(upperLetters));
				settings.setProperty("lowerLetters", String.valueOf(lowerLetters));
				settings.setProperty("nums", String.valueOf(nums));
				settings.setProperty("symbols", String.valueOf(symbols));
				while(confSettingsLoop) {
					System.out.println("Confirm settings? (y/n) ");
					String confirmSettings = input.nextLine();
					if (confirmSettings.equalsIgnoreCase("y")) {
						settingsLoop = false;
						confSettingsLoop = false;
						menu();
					}
					else if (confirmSettings.equalsIgnoreCase("n")) {
						settingsLoop = true;
						confSettingsLoop = false;
					} else {
						System.out.println("Invalid input. Correct input: (y/n)");
						confSettingsLoop = true;
						
					}
				}
			}
		}
	}
	
	/*generates the password using the SecureRandom class and the StringBuilder class. Randomly selects an index from the character strings defined up top.
	 *@param length 	  The desired length of the password
	 *@param upperLetters Whether to include upper case letters
	 *@param lowerLetters Whether to include lower case letters
	 *@param nums 		  Whether to include numbers
	 *@param symbols	  Whether to include symbols
	 *@return 			  Randomly generated string
	 */
	public String generator(int length, boolean upperLetters, boolean lowerLetters, boolean nums, boolean symbols) {
		
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder();
		
		if (upperLetters) {
			password.append(this.upperLettersKey);
		}
		if (lowerLetters) {
			password.append(this.lowerLettersKey);
		}
		if (nums) {
			password.append(this.numsKey);
		}
		if (symbols) {
			password.append(this.symbolsKey);
		}
		
		StringBuilder generatedPassword = new StringBuilder();
		try {
			for (int i = 0; i < length; i++) {
				int index = random.nextInt(password.length());
				char randomChar = password.charAt(index);
				generatedPassword.append(randomChar);
			}
		}
		catch (IllegalArgumentException e) {
			System.out.println("Invalid input, must select desired characters. ");
		}
		
		return generatedPassword.toString();
	}
}

