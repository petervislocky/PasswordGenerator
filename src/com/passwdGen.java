package com;

import java.security.SecureRandom;
import java.util.Scanner;

public class passwdGen {
	
	private final String upperLettersKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String lowerLettersKey = "abcdefghijklmnopqrstuvwxyz";
	private final String numsKey = "1234567890";
	private final String symbolsKey = "!@#$%^&*?";
	
	private Scanner input;
	
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
				boolean copyToClip = true;
				
				System.out.println("Password Generator\nBe aware, improper input for any of the prompts asking what characters you want included will default to yes.\nNone of the inputs are case sensitive.");
				
				UserInputHandler UIHand = new UserInputHandler(input);
				int length = UIHand.returnLength();
				
				//the following is a test of the Password settings class
				PasswordSettings settings = new PasswordSettings();
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
				//end of test, this part will be in a settings menu and wont prompt the user every time once implemented properly
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
					runagain = UIHand.runAgain();
				}
			}
	}
	/*
	public void menu() {
		System.out.print("Choose an option by inputting the corresponding number\n1 Password Generator \n2Settings \n3 Useful Information \n4 Instructions for Use \n5 Exit");
		int menuChoice = input.nextInt();
		input.nextLine();
		switch (menuChoice) {
		case 1: 
			
		}
	}
	*/
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

