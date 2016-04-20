package com.mickestrid.RPNCalc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;



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



				  /*,,,,,,,,,,,,,,,,,.,,,...,,.,....,,,,,,,,,,,,,,,
					,,,,,,,,,,,,,,,,..,,....,......,.,,,,,,,,,,,,,,
					,,,,,,,,,,,,,,,,,,.......;####+,..,,,,,,,,,,,,,
					,,,,,,,,,,,,,,,,,,......+#@@####;..,,,,,,,,,,,,
					,,,,,,,,,,,,,,,,.......##########,..,,,,,,,,,,,
					,,,,,,,,,,,,,.,........#@#########,.,,,,,,,,,,,
					,,,,,,,,,,,,,..........##++#++####:.,,,,,,,,,,,
					,,,,,,,,,,,,..........,#';:''##+##,.,,,,,,,,,,,
					,,,,,,,,,,,,..........:#;;:,,'';'#,.,,,,,,,,,,,
					,,,,,,,,,,,...........;#;::,,::;'+..,,,,,,,,,,,
					,,,,,,,,,,,...........;+;:::,::;';..,,,,,,,,,,,
					,,,,,,,,,,............::;;'::::;+...,,,,,,,,,,,
					,,,,,,,,,,............;;:;:;;'+';.,.,,,,,,,,,,,
					,,,,,,,,,,............:;::::;'';,...,,,,,,,,,,,
					,,,,,,,,,,.............;::;:;:;;...,,,,,,,,,,,,
					,,,,,,,,,,............:;:;;;;;;,...,,,,,,,,,,,,
					,,,,,,,,,,...........@;';;;:;;'....,,,,,,,,,,,,
					,,,,,,,,,,......:+@##@,;;;''''.....,,,,,,,,,,,,
					,,,,,,,,,,..+@#######@,.;;;;'#:....,,,,,,,,,,,,
					,,,,,,,,,,.###########,..;+:,@#@,.,.,,,,,,,,,,,
					,,,,,,,,,..@##########,.;#',:##@#@:,..,,,,,,,,,
					,,,,,,,,.,'@##########.,::;:'######@'.,,,,,,,,,
					,,,,,,,,.,############,:::,::'#####@#,,,,,,,,,,
					,,,,,,,,.#############,:;':,:'#####@@',,,,,,,,,
					,,,,,,,,##############,,#;#:,;######@#,,,,,,,,,
					,,,,,,,'##############,.#;+:,;########,,,,,,,,,
					,,,,,,,@@@############,.###:,:######@@,,,,,,,,,
					,,,,,,@@@@############,.###;,:;####@@##,,,,,,,,
					,,,,,,@@@@############:.###@:,:;###@#@',,,,,,,,
					,,,,,@@#@@############;.####+:,+##@#@#+,,,,,,,,
					,,,,@@@@@#############'.###@;:.###@##@@,,,,,,,,
					,,,:@@@@@@@@########+:::;'##..'#######@,,,,,,,,
					,,,@#@@@@@@#@#####':::,,;;;#'.######@#@,,,,,,,,
					,,,@@@@@@##@#####:,,,,::;;;#########@##@,,,,,,,
					,,+@@@@##########,,,,::,;;'#########@#@@,,,,,,,
					,,@@@@@#########+::,::::;''###########@@:,,,,,,
					,,@@@@@@@#######:,@#';''+++##@@######@##+,,,,,,
					,,@@@@@@@@@###@#,.@#@#@.#@@####@####@@#@@,,,,,,
					,,;@@@@@@@@@####:@####@.#@@####@@###@@@@@,,,,,,
					,,,'++##@@@@@@@@#@@###@.@#@####@@@@@@@@@@,,,,,,
					,,,,,,,,:;'@@@@#######@.##@####@@@@@@@@@',,,,,:
					,,,,,,,,,,,@@@@@###@##@.##@@###@@@+@#@@@,,,,,,:
					,,,,,,,,,,,@@@@#####@@+.##@@###@@@,+@@@',,,,,,:
					,,,,,,,,,,'@@@@#######,.#@@@@@@@@@,,'+:,,,,,,,:
					,,,,,,,,,,@@@@@####@#@..@@@#@#@@@@,,,,,,,,,,,,:
					,,,,,,,,,,@@@@@#####@@..@#@#@@@@@@:,,,,,,,,,,,:
					,,,,,,,,,,@@@@@###@@@+,.##@#@@@@@@',,,,,,,,,:::*/


public class InfixToRPN {

	private String infix;
	
	//Declare assoc integer
	private static final int L_TO_R = 0;
	private static final int R_TO_L = 1;
	
	//Declare operator hashmap
	private static final Map<String, int[]> OP = new HashMap<String, int[]>();
	
	static {
		//Hash map of operator given priority, associativity and number of operators taken by operator
		//First four repressents the four basic arithmetic calculation methods: + - * /
		OP.put("+", new int[] { 10, L_TO_R, 2 });
		OP.put("-", new int[] { 10, L_TO_R, 2 });
		OP.put("*", new int[] { 15, L_TO_R, 2 });
		OP.put("/", new int[] { 16, L_TO_R, 2 });
		//The modulus operator
		OP.put("%", new int[] { 15, L_TO_R, 2 });
		//The "power of" -operator
		OP.put("^", new int[] { 20, R_TO_L, 2 });
		//c == Cosine operator
		OP.put("c", new int[] { 20,  R_TO_L, 1 });
		//s == sine operator
		OP.put("s", new int[] { 20, R_TO_L, 1 });
		//t == tan operator
		OP.put("t", new int[] { 20, R_TO_L, 1 });
		//e == power of an exponential base: e^x
		OP.put("e", new int[] { 20, R_TO_L, 1 });
		//q == square root
		OP.put("q", new int[] { 20, R_TO_L, 1 });
		//l == logarithm function
		OP.put("l", new int[] { 20, R_TO_L, 1 });
		
	   /***********************************************************************************************
	    *********************************************************************************************** 
	    **  																					     **
	    **  all these functions are converted to their "token state" in the stringParse() function:  **
	    ** 																						     **
	    ***********************************************************************************************
	    ***********************************************************************************************/
	}
	
	//Declare our infix as infix in this class
	public InfixToRPN(String infix) {
		this.infix = infix;
	}
	
	//Compare "token" of OP to next token
	static boolean operatorCompare(String token){
		return OP.containsKey(token);
	}
	
	//Get number of input the selected operator uses
	static int numberOperator(String nOfOperators){
		return OP.get(nOfOperators)[2];
	}
	
	//Compare top token in operator stack to current token
	private static final int comparePrecedence(String char1, String char2) {
		if (!operatorCompare(char1) || !operatorCompare(char2)) {
			throw new IllegalArgumentException("The character " + char1 + " or " + char2 + " is illegal");
		}
		return OP.get(char1)[0] - OP.get(char2)[0];
	}
	
	//If token is operator: get assoc order. Else: token is illegal.
	private static boolean isAssociative(String token, int type) {
		if (!operatorCompare(token)) {
			throw new IllegalArgumentException("The character " + token + " is illegal");
		}
		//OP.get(token)[1]: check if operator is left or right associative.
		if (OP.get(token)[1] == type) {
			return true;
		}
		return false;
	}
	
	//The stringParse function replaces our input functions to tokens
	private static String[] stringParse(String infix) {
		String[] inputToArr = infix.split("(?<=[\\(\\)\\+\\-*\\/])|(?=[\\(\\)\\+\\-*\\/])");
		//look through the array to replace functions to tokens
		for(int i = 0 ; inputToArr.length > i; i++){
			inputToArr[i] = inputToArr[i].replaceAll("cos", "c").replaceAll("sin", "s").replaceAll("tan", "t").replaceAll("sqrt", "q").replaceAll("log", "l").replaceAll("exp", "e");
		}
		return inputToArr;
	}
	
	//parse to RPN 
	public static String parseToRPN(String infix) {
		//Use infix expression is parsed for operators: ( ) + * / - ^
		String[] input = stringParse(infix);
		
		//Get one stack for operators and one array list for output
		ArrayList<String> outputQue = new ArrayList<String>();
		Stack<String> operatorStack = new Stack<String>();		
		
		for (String token : input) {
			//If token is operator
			if(operatorCompare(token)){
				//Check if there's more operators in stack 
				while (!operatorStack.empty() && operatorCompare(operatorStack.peek())) {
					//Compare previous token precedence to current precedence: if precedence of current is lower than previous. Pop to output que
					if ((isAssociative(token, L_TO_R) && comparePrecedence(token, operatorStack.peek()) <= 0)
					 || (isAssociative(token, R_TO_L) && comparePrecedence(token, operatorStack.peek()) < 0)) {
						//Add operators to output
						outputQue.add(operatorStack.pop());
						continue;
					}
					break;
				}
				//if previous operator is higher priority: push to operator stack
				operatorStack.push(token);
			}
			else if (token.equals("(")) {operatorStack.push(token);}
			//Push tokens while parenthesis is not closed
			else if (token.equals(")")) {
					while (!operatorStack.empty() && !operatorStack.peek().equals("(")) {
						outputQue.add(operatorStack.pop());
					}
					operatorStack.pop();
				}
			//else == is number
			else{
				outputQue.add(token);
				}
			}
		while (!operatorStack.empty()) {
			outputQue.add(operatorStack.pop());
		}
		
		
		//send to calculator for calculation:
		String[] output = new String[outputQue.size()];
		//return to main for output:
		/*for(String token : outputQue){
			System.out.print(token + " ");
		}*/
		return Calculator.calc(outputQue.toArray(output));
	}	
}
