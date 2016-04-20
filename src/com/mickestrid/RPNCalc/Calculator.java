package com.mickestrid.RPNCalc;

			/*********************************************************************
			 ********************************************************************* 
			 **  					@author IfMiaelStrid					   	**
			 **																   	**
			 ** This is the RPN calculator version 1.0. In this version 	   	**
			 ** there's a bit things to be done:							    **		
			 **																	**
			 **		*TODO negative values is yet to be implemented. 			**
			 **		 only takes positive integers	 							**
			 **																	**
			 **		*TODO Future implementation of more mathematic operators.	**
			 **																	**
			 **		*TODO Some optimization										**
			 **																	**		
			 *********************************************************************
			 *********************************************************************/


/*****************************************************************************************************
 *****************************************************************************************************
 ** This program uses the following algorithm from wikipedia.										**
 ** It's pretty straight forward.																	**	
 ** 																								**
 ** While there are input tokens left																**
 ** Read the next token from input.																	**
 ** If the token is a value																			**
 ** Push it onto the stack.																			**
 ** Otherwise, the token is an operator (operator here includes both operators and functions).	 	**
 ** It is already known that the operator takes n arguments.										**
 ** If there are fewer than n values on the stack													**
 ** (Error) The user has not input sufficient values in the expression.								**	
 ** Else, Pop the top n values from the stack.														**					
 ** Evaluate the operator, with the values as arguments.											**
 ** Push the returned results, if any, back onto the stack.											**
 ** If there is only one value in the stack															**
 ** That value is the result of the calculation.													**
 ** Otherwise, there are more values in the stack													**
 ** (Error) The user input has too many values.														**
 *****************************************************************************************************
 *****************************************************************************************************
*/

import java.util.Stack;

public class Calculator {

	private static String[] operators;
	private static String[] endValues;

	public static String calc(String[] RPNexp){
	
		Stack<String> stack = new Stack<String>();
		String[] operators2 = {};
		String out;
			for(String token : RPNexp){
				if(isNumeric(token)){
					stack.push(token);
				}
				else if(InfixToRPN.operatorCompare(token)||!stack.isEmpty()){
					String op1;
					String op2;

					switch(InfixToRPN.numberOperator(token)){
						case 1:
							op1 = stack.pop();
							out = evaluateOneVar(op1,token);
							stack.push(out);
							break;
						case 2:
							op1 = stack.pop();
							op2 = stack.pop();
							out = evaluateTwoVar(op1,op2,token);
							stack.push(out);
							break;
					}
				}
			}
			String RPNexpOUT = stack.pop();
		return RPNexpOUT;
	}

	private static String evaluateTwoVar(String operator1, String operator2, String token) {
		double Doperator1 = Double.parseDouble(operator1);
		double Doperator2 = Double.parseDouble(operator2);
		double sum = 0;
		
		switch(token){
		case "+":
			sum = Doperator1 + Doperator2;
			break;
		case "-":
			sum = Doperator1 - Doperator2;
			break;
		case "/":
			sum = Doperator2 / Doperator1;
			break;
		case "*":
			sum = Doperator2 * Doperator1;
			break;
		case "^":
			sum = Math.pow(Doperator2 , Doperator1);
			break;
		case "%":
			sum = Doperator1 % Doperator2;
			break;				
		default: System.out.println(token + " i  unsuported");
			break;
		}

		return Double.toString(sum);
	}
	
	private static String evaluateOneVar(String operator1, String token) {
		double Doperator1 = Double.parseDouble(operator1);
		double sum = 0;
		switch(token){
			case "c":
				sum = Math.cos(Doperator1);
			break;
			case "s":
				sum = Math.sin(Doperator1);
			break;
			case "t":
				sum = Math.tan(Doperator1);
			break;
			case "e":
				sum = Math.exp(Doperator1);
			break;
			case "q":
				sum = Math.sqrt(Doperator1);
			break;
			case "l":
				sum = Math.log(Doperator1);
			break;
		}
		return Double.toString(sum);
	}
	
	
	public static boolean isNumeric(String str)  
		{  
			 try  
			 {  
			   double d = Double.parseDouble(str);  
			 }  
			 catch(NumberFormatException nfe)  
			 {  
			   return false;  
			 }  
			 return true;  
		}
}