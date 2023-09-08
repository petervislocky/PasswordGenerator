package com;

import java.util.Scanner;

public class passwdGenMain {
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		scanner.useDelimiter("\\R");
		new passwdGen(scanner);
		scanner.close();
	}

}