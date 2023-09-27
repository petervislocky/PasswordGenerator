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
		
		System.out.print("Choose an option by inputting the corresponding number\n1 Password Generator \n2Settings \n3 Instructions for Use \n4 Exit");
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
			break;
		case 3:
			printInstructions();
			break;
		case 4:
			//put exit code here
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
	public void printInstructions() {
		boolean returnMenuLoop = true;
		System.out.println("Intructions for use\nTo adjust what type of characters are in the generated password, naviagate to Settings in the menu and adjust there.");
		System.out.println("Alternatively you can navigate to your home directory and find the .properties file in Home/PasswordGenConfig/passwordSettings.properties");
		System.out.println("Each time you go to generate a password you will be prompted to enter the desired length, the bounds are 1 and 99");
		System.out.println("Everytime you generate a password you will be prompted if you want to copy to clipboard, entering y will copy it to the clipboard for you.");
		System.out.println("The program will give you a message letting you know if the password succesfully copied to the clipboard or not.");
		System.out.println("The first time running this program a directory will be automatically created to store the .properties file for the settings, the program will alert you when it does so");
		while (returnMenuLoop) {
			System.out.print(" Press Enter to return to menu ");
			String returnMenu = input.nextLine();
			if (returnMenu.isEmpty()) {
				System.out.println("Returning to menu");
				menu();
				break;
			} else {
				System.out.println("You pressed a key other than enter.");
				continue;
			}
		}
	}
}

