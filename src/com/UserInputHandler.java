package com;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserInputHandler {
	
	private Scanner input;
	
	public UserInputHandler(Scanner scanner) {
		this.input = scanner;
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
	//This method is for the user to set the length of the password, the value returned is then fed into the generator method in the mainloop() method.
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
					System.out.printf("Invalid input. You either entered invalid characters or a number too large\nRemember the minimum length is 1, maximum is 99\nError code: %s%n", e);
					input.next();
				}
		}
		return length;
	}
	
	public boolean upperLetters() {
		boolean upperLetters;
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
		return upperLetters;
	}
	
	public boolean lowerLetters() {
		boolean lowerLetters;
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
		return lowerLetters;
	}
	
	public boolean nums() {
		boolean nums;
		
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
		return nums;
	}
	
	public boolean symbols() {
		boolean symbols;
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
		return symbols;
	}
	
	//copies the password generated to clipboard
	public void copyToClipboard(String passwd) {
		try {
			StringSelection selection = new StringSelection(passwd);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
			System.out.println("Password succesfully copied to clipboard.");
		}
		catch (Exception e) {
			System.out.printf("Copy to clipboard failed. Error code %s%n", e);
		}
	}
}
