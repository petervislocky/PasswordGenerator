package com;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class passwdGen {
	
	private final String upperLettersKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final String lowerLettersKey = "abcdefghijklmnopqrstuvwxyz";
	private final String numsKey = "1234567890";
	private final String symbolsKey = "!@#$%^&*?";
	
	private Scanner input;
	
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
				System.out.println("Password Generator");
				
				int length = returnLength();
				System.out.print("Upper case letters included (y/n): ");
				String upperCase_Included = input.nextLine();
					
				System.out.print("Lower case letters included (y/n): ");
				String lowerCase_Included = input.nextLine();
					
				System.out.print("Numbers included (y/n): ");
				String nums_Included = input.nextLine();
					
				System.out.print("Symbols included (y/n): ");
				String symbols_Included = input.nextLine();
				if (upperCase_Included.equalsIgnoreCase("y")) {
					upperLetters = true;
				}
				else if (upperCase_Included.equalsIgnoreCase("n")) {
					upperLetters = false;
				} else {
					System.out.println("Invalid input given for upper case letters. Defualting to yes");
					upperLetters = true;
				}
					
				if (lowerCase_Included.equalsIgnoreCase("y")) {
					lowerLetters = true;
				}
				else if (lowerCase_Included.equalsIgnoreCase("n")) {
					lowerLetters = false;
				} else {
					System.out.println("Invalid input given for lower case letters. Defualting to yes");
					lowerLetters = true;
				}
					
				if (nums_Included.equalsIgnoreCase("y")) {
					nums = true;
				}
				else if (nums_Included.equalsIgnoreCase("n")) {
					nums = false;
				} else {
					System.out.println("Invalid input given for numbers. Defualting to yes");
					nums = false;
				}
					
				if (symbols_Included.equalsIgnoreCase("y")) {
					symbols = true;
				}
				else if (symbols_Included.equalsIgnoreCase("n")) {
					symbols = false;
				} else {
					System.out.println("Invalid input given for symbols. Defualting to yes");
					symbols = true;
				}				
				
				String password = generator(length, upperLetters, lowerLetters, nums, symbols);
				if (password.isEmpty()) {
					runagain = runAgain();
				} else {
					System.out.println("Generated Password: " + password);
					runagain = runAgain();
				}
			}
	}
	
	public String generator(int length, boolean upperLetters, boolean lowerLetters, boolean nums, boolean symbols) {
		
		Random random = new Random();
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
	
	public boolean runAgain() {
		boolean RAloop = true;
		boolean runAgain_returnValue = true;
		while (RAloop) {
			System.out.print("Run again? (y/n): ");
			String again = input.nextLine();
			if (again.equalsIgnoreCase("y")) {
				RAloop = false;
				runAgain_returnValue = true;
			}
			else if (again.equalsIgnoreCase("n")) {
				RAloop = false;
				runAgain_returnValue = false;
			} else {
				System.out.println("Invalid input");
				
			}
		}
		return runAgain_returnValue;
	}
	
	public int returnLength() {
		int length = 0;
		boolean lengthLoop = true;
		while (lengthLoop) {
				System.out.print("Desired length (Min 1, Max 99): ");
				try {
					length = input.nextInt();
					input.nextLine();
					if (length > 99) {
						System.out.println("Maximum length is 99");
					}
					else if (length < 1) {
						System.out.println("Minimum length is 1");
					} else {
						lengthLoop = false;
						return length;
					}
					
				} 
				catch (InputMismatchException e) {
					System.out.println("Invalid input. You either entered invalid characters or a number too large\nRemember the minimum length is 1, maximum is 99");
					input.next();
				}
		}
		return length;
	}
}

