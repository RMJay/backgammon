package test;

import java.util.Scanner;

public class ScannerTest {

	static Scanner _keyboard = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		String input = _keyboard.nextLine();
		System.out.println(input);

	}

}
