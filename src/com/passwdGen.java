package com;

import java.security.SecureRandom;
import java.util.InputMismatchException;
import java.util.Scanner;

public class passwdGen {
	
	/* 
	 * Author: 
	 * Peter Vislocky
	 */
	
	//The main class of the program, handles all aspects of generating the password. menu() method handles the main user interaction of the program
	
	private final String upperLettersKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String lowerLettersKey = "abcdefghijklmnopqrstuvwxyz";
	private final String numsKey = "1234567890";
	private final String symbolsKey = "!@#$%^&*?";
	
	private Scanner input;
	private PasswordSettings settings = new PasswordSettings();
	
	
	public passwdGen(Scanner scanner) {
		this.input = scanner;
		menu();
	}
	 
	//Main method of the class, is the main loop/"home page" of the program. Displays the menu and handles method calls for all options in the menu.
	public void menu() {
		boolean upperLetters;
		boolean lowerLetters;
		boolean nums;
		boolean symbols;
		boolean menuLoop = true;
		
		while (menuLoop) {
			boolean genLoop = true;
			boolean settingsLoop = true;
			boolean menuInputLoop = true;
			int menuChoice = 0;
			UserInputHandler UIHand = new UserInputHandler(input);
			while (menuInputLoop) {
				try {
					System.out.println("Choose an option by inputting the corresponding number (1-4)\n1 Password Generator \n2 Settings \n3 Instructions for Use \n4 Exit");
					menuChoice = input.nextInt();
					menuInputLoop = false;
				}
				catch (InputMismatchException e) {
					System.out.println("Invalid menu choice. Try again.");
				}
				input.nextLine();
			}
			switch (menuChoice) {
			case 1:
				System.out.println("\u001B[92mPassword Generator\u001B[0m");
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
				System.out.println("\u001B[92mSettings\u001B[0m\nChoose which characters to include, once you are done it will automatically save.");
				displayCurrentSettings();
				while(settingsLoop) {
					boolean confSettingsLoop = true;
					upperLetters = UIHand.getCharChoice("Upper case letters included? ");
					lowerLetters = UIHand.getCharChoice("Lower letters included? ");
					nums = UIHand.getCharChoice("Numbers included? ");
					symbols = UIHand.getCharChoice("Symbols included? ");
					if (!upperLetters && !lowerLetters && !nums && !symbols) {
						System.out.println("At least one set of characters must be included. Try again.");
						continue;
					}
					settings.setProperty("upperLetters", String.valueOf(upperLetters));
					settings.setProperty("lowerLetters", String.valueOf(lowerLetters));
					settings.setProperty("nums", String.valueOf(nums));
					settings.setProperty("symbols", String.valueOf(symbols));
					settings.saveProperties();
					while(confSettingsLoop) {
						System.out.print("Confirm settings? (y/n): ");
						String confirmSettings = input.nextLine();
						if (confirmSettings.equalsIgnoreCase("y")) {
							settingsLoop = false;
							confSettingsLoop = false;
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
				System.out.println("Exiting");
				menuLoop = false;
				break;
			default:
				System.out.println("Invalid input. Valid input: 1-4");
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
			System.out.printf("Invalid input, must select desired characters.\nError Code: %s in method generator()\n", e);
		}
		
		return generatedPassword.toString();
	}
	
	public void printInstructions() {
		boolean returnMenuLoop = true;
		System.out.println("\u001B[92mIntructions for use\u001B[0m\n*To adjust what type of characters are in the generated password, naviagate to Settings in the menu and adjust there.");
		System.out.println("*Alternatively you can navigate to your home directory and find the .properties file in Home/PasswordGenConfig/passwordSettings.properties, and adjust the values there.");
		System.out.println("*Each time you go to generate a password you will be prompted to enter the desired length, the bounds are 1 and 99");
		System.out.println("*Everytime you generate a password you will be prompted if you want to copy to clipboard, entering y will copy it to the clipboard for you.");
		System.out.println("*The program will give you a message letting you know if the password succesfully copied to the clipboard or not.");
		System.out.println("*The first time running this program a directory will be automatically created to store the .properties file for the settings, the program will alert you when it does so");
		System.out.println("*Settings DO save so, if you set them and exit the program they will still be applied next time you run the program");
		System.out.println("*Valid inputs will always be listed when you are prompted for input, they will always either be a number (with the upper and lower bounds listed) or an option of y or n");
		while (returnMenuLoop) {
			System.out.print("Press Enter to return to menu ");
			String returnMenu = input.nextLine();
			if (returnMenu.isEmpty()) {
				System.out.println("Returning to menu");
				break;
			} else {
				System.out.println("You pressed a key other than enter.");
				continue;
			}
		}
	}
	public void displayCurrentSettings() {
		boolean displayCurrentUpperCase = settings.getProperty("upperLetters");
		boolean displayCurrentLowerCase = settings.getProperty("lowerLetters");
		boolean displayCurrentNums = settings.getProperty("nums");
		boolean displayCurrentSymbols = settings.getProperty("symbols");
		System.out.println("Current Settings:");
		System.out.printf("*Uppercase letters included = %b%n*Lowercase letters included = %b%n*Numbers included = %b%n*Symbols included %b%n", displayCurrentUpperCase, displayCurrentLowerCase, displayCurrentNums, displayCurrentSymbols);
	}
}

