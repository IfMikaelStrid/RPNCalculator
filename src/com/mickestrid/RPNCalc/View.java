package com.mickestrid.RPNCalc;

import java.util.Scanner;

public class View {
	private static Scanner scanner;	
	public static void main(String[] args) {	
	    scanner = new Scanner(System.in);
        String userinput = scanner.nextLine(); 
		String output = InfixToRPN.parseToRPN(userinput);
		System.out.print(output);				
	}
}
