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
	 * TODO further encapsulate mainloop()
	 * TODO create a menu method listing all different options for user and calling a different method depending on what the user selects
	 * TODO Implement a settings method to the menu that allows the user to choose what characters they want and then save the results to a .properties file. Then have the passwdGenerator() method pull its parameters from the .properties file.
	 */
	
	public passwdGen(Scanner scanner) {
		this.input = scanner;
		mainloop();
	}
	public void mainloop() {
		boolean runagain = true;
		boolean upperLetters = true;
		boolean lowerLetters = true;
		boolean nums = true;
		boolean symbols = true;
		
			while (runagain) {
				//copyToclip is just for the while loop at the bottom of this method that copies to clipboard
				boolean copyToClip = true;
				
				System.out.println("Password Generator\nBe aware, improper input for any of the prompts about including different characters, will default to yes.\nNone of the inputs are case sensitive.");
				
				UserInputHandler UIHand = new UserInputHandler(input);
				int length = UIHand.returnLength();
				System.out.print("Upper case letters included (y/n): ");
				String upperCase_Included = input.nextLine();
				
				if (upperCase_Included.equalsIgnoreCase("y")) {
					upperLetters = true;
				}
				else if (upperCase_Included.equalsIgnoreCase("n")) {
					upperLetters = false;
				} else {
					System.out.println("Invalid input given for upper case letters. Defaulting to yes");
					upperLetters = true;
				}
					
				System.out.print("Lower case letters included (y/n): ");
				String lowerCase_Included = input.nextLine();
				
				if (lowerCase_Included.equalsIgnoreCase("y")) {
					lowerLetters = true;
				}
				else if (lowerCase_Included.equalsIgnoreCase("n")) {
					lowerLetters = false;
				} else {
					System.out.println("Invalid input given for lower case letters. Defaulting to yes");
					lowerLetters = true;
				}
					
				System.out.print("Numbers included (y/n): ");
				String nums_Included = input.nextLine();
				
				if (nums_Included.equalsIgnoreCase("y")) {
					nums = true;
				}
				else if (nums_Included.equalsIgnoreCase("n")) {
					nums = false;
				} else {
					System.out.println("Invalid input given for numbers. Defaulting to yes");
					nums = false;
				}
					
				System.out.print("Symbols included (y/n): ");
				String symbols_Included = input.nextLine();	
					
				if (symbols_Included.equalsIgnoreCase("y")) {
					symbols = true;
				}
				else if (symbols_Included.equalsIgnoreCase("n")) {
					symbols = false;
				} else {
					System.out.println("Invalid input given for symbols. Defaulting to yes");
					symbols = true;
				}				
				
				String password = generator(length, upperLetters, lowerLetters, nums, symbols);
				if (password.isEmpty()) {
					runagain = UIHand.runAgain();
				} else {
					System.out.println("Generated Password: " + password);

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
	
	/*generates the password using the SecureRandom class and the StringBuilder class. Randomly selects an index from the character strings defined up top.
	 *@param length 	  The desired length of the password
	 *@param upperLetters Whether to include upper case letters
	 *@param lowerLetters Whether to include lower case letters
	 *@param nums 		  Whether to include numbers
	 *@param symbols	  Whether to include symbols
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

