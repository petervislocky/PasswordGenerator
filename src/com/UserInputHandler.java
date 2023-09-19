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
	/*Decreased repetitive code with this method, serves as a barebones prompt for the different character selections.
	 * @param prompt	The insert where you can add the different character prompts.
	 * @return		    Returns users choice
	 */
	public boolean getCharChoice(String prompt) {
		boolean result;
		System.out.print(prompt + "(y/n): ");
		String userChoice = input.nextLine();
		if (userChoice.equalsIgnoreCase("y")) {
			result = true;
		}
		else if (userChoice.equalsIgnoreCase("n")) {
			result = false;
		} else {
			result = true;
			System.out.println("Invalid input. Defaulting to yes.");
		}
		return result;
	}
	
	/*copies the password generated to clipboard
	 * @param passwd	String to be copied to clipboard
	 */
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
