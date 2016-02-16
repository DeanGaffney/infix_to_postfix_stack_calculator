import java.awt.GraphicsConfiguration;
import java.util.Stack;

/**
 * @file        CalcEngineStackApi.java
 * @author      Dean Gaffney 20067423
 * @assignment  Calculator engine using the java stack class.
 * @brief       Creating a calculator engine with the standard java stack class.
 *
 * @notes       
 * 				
 */

public class CalcEngineStackApi {

	Stack calcStack = new Stack();
	Stack <Double> postStack = new Stack<Double>();

	//give each operator a level of precedence.
	final int ADD_MINUS_PRECEDENCE = 1;
	final int MULTIPLY_DIVIDE_PRECEDENCE = 2;
	final int POWER_OF = 3;
	final int BRACKET_PRECEDENCE = 4;
	final int DECIMAL_POINT = 5;

	int operand;
	String displayValue;
	char operator;
	String expression; // create a string with the full expression
	String postfixExpression;

	public CalcEngineStackApi(){
		operator = ' ';
		displayValue = "";
		operand = 0;
		expression= "";
		postfixExpression ="";
	}

	//Method adds any command (i.e number or operator) to a string expression.
	public void addToExpression(char character){
		expression += character;
		System.out.println(expression);
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String newChar){
		displayValue = newChar;
	}

	private double plus(double number1,double number2){
		double result = number1+number2;
		System.out.println("Result form plus method:" + result);
		return result;
	}

	private double minus(double number1,double number2){
		double result = number2 - number1;
		System.out.println("Result from minus method is : "+ result);
		return result;
	}

	private double multiply(double number1, double number2){
		double result = number1 * number2;
		System.out.println("Result from multiply method is: "+ result);
		return result;
	}

	private double divide(double number1,double number2){
		double result = number2 / number1;
		System.out.println("Result from divide method is: "+result);
		return result;
	}
	//return to this method
	private double powerTo(double number1,double number2){
		double result = (Math.pow(number2, number1));
		return result;
	}

	private boolean isOperator(char testChar){
		boolean isOperator = false;
		switch(testChar){
		case '+':
		case '-':
		case '*':
		case '/':
		case '^':
			isOperator = true;
			break;
		default:
			isOperator = false;
		}
		return isOperator;
	}

	/*
	 * Method takes in the expression when a decimal point occurs.
	 * It takes the number before the decimal point,the decimal point,
	 * and all numbers after the point up until an operator is encountered.
	 * This can then be considered our decimal point number and can be returned.
	 */
	private double parseNumber(char decimalChar,int decimalIndex,String expression){
		String strParsedNumber = "";
		if(isOperator(expression.charAt(decimalIndex-1))){
			strParsedNumber += expression.charAt(decimalIndex);
			decimalIndex++;
		}
		else{
			strParsedNumber += expression.charAt(decimalIndex - 1);
		}

		while((decimalIndex < expression.length()) && (!isOperator(expression.charAt(decimalIndex)))){
			strParsedNumber += expression.charAt(decimalIndex);
			decimalIndex++;
		}
		System.out.println("Postfix decimal number is : "+ strParsedNumber);
		return Double.parseDouble(strParsedNumber);
	}


	// do infix to postfix transition and calculate
	public void equals(){
		convertToPostfix(expression);
		setDisplayValue(calculatePostFix(postfixExpression));
	}

	//this method calculates the result of a given postfix sum.
	private String calculatePostFix(String postExpression) {
		int decimalController = 0;
		int flowController = 0;
		for(int i = 0; i < postExpression.length();i++){
			char currentChar = postExpression.charAt(i);
			switch(currentChar){
			case '+':
				postStack.push(plus((double)postStack.pop(),(double)postStack.pop()));
				break;
			case '-':
				postStack.push(minus((double)postStack.pop(),(double)postStack.pop()));
				break;
			case '*':
				postStack.push(multiply((double)postStack.pop(),(double)postStack.pop()));
				break;
			case '/':
				postStack.push(divide((double)postStack.pop(),(double)postStack.pop()));
				break;
			case '^':
				postStack.push((double)(powerTo((double)postStack.pop(), (double)postStack.pop())));
				break;
			case '.':
				postStack.push(parseNumber(currentChar,i,expression));
				System.out.println("Stack is now: "+ postStack);
				++decimalController;
				break;
			default:
				if(flowController == decimalController-1){ // this will avoid adding the post decimal
					flowController++; 						// numbers to the stack and skip them.
					break;
				}
				else{
						String tempString = "";
						tempString+=currentChar;
						System.out.println("This will be pushed to stack: "+tempString);
						postStack.push(Double.parseDouble(tempString)); //this will mean its a number so push onto stack.
						System.out.printf("Stack is now " + postStack); // format this to 3 decimal places
					}
			}
		}
		return Double.toString(postStack.pop());
	}

	private void hasPrecedenceOver(char thisChar,int precedence1){
		while(!calcStack.isEmpty()){
			char topChar = (char) calcStack.pop();
			if(topChar == '('){
				calcStack.push(topChar);
				break;
			}else{
				int precedence2 = 0; //default value
				switch(topChar){
				case '+':
				case '-':
					precedence2 = ADD_MINUS_PRECEDENCE; //i.e. 1
					break;
				case '/':
				case '*':
					precedence2 = MULTIPLY_DIVIDE_PRECEDENCE; //i.e 2
					break;
				case '^':
					precedence2 = POWER_OF; //i.e 3
					break;
				}
				if(precedence1 > precedence2){
					calcStack.push(topChar);
					break;
				}else{
					postfixExpression += topChar;
				}
			}
		}
		calcStack.push(thisChar); // if there is nothing on the stack just push element on.
	}

	// this will pop everything off the stack until the parenthesis match each other.
	private void getEndBracket(char character){
		while(!calcStack.isEmpty()){
			char topChar = (char) calcStack.pop();
			if(topChar == '(') break;
			else
				postfixExpression += topChar;
		}
	}

	//this methods converts a given infix sum to postfix.
	private void convertToPostfix(String expression){
		for(int i = 0; i < expression.length();i++){
			char currentChar = expression.charAt(i); //get the current character

			switch(currentChar){ // switch on character for different operators
			case '(':
				calcStack.push(currentChar);  //push onto the stack
				break;
			case ')':
				getEndBracket(currentChar); // see if there is a matching bracket to end sum.
				break;
			case '+':
			case '-':
				hasPrecedenceOver(currentChar,1);
				break;
			case '/':
			case '*':
				hasPrecedenceOver(currentChar, MULTIPLY_DIVIDE_PRECEDENCE);
				break;
			case '^':
				hasPrecedenceOver(currentChar, POWER_OF);
				break;
			case '.':
				postfixExpression += '.';
				System.out.println("After decimal point:" + postfixExpression);
				break;
			default:
				postfixExpression += currentChar; 	//this will be an operand.
			}
		}

		while(!calcStack.isEmpty()){
			postfixExpression += calcStack.pop();
			System.out.println(postfixExpression);
		}
		System.out.println("Postfix Expression is: " + postfixExpression);
	}

	//method clears the calculator screen and resets any calculations.
	public void clear()
	{
		displayValue = "";
		expression = "";	
		postfixExpression = "";// reset expressions to nothing.
		operand = 0;
		calcStack.removeAllElements(); // reset stacks on clear
		postStack.removeAllElements(); 
	} 

	public String getTitle()
	{
		return("My Calculator");
	}

	public String getAuthor()
	{
		return("Dean Gaffney");
	}

	/**
	 * Return the version number of this engine. This string is displayed as 
	 * it is, so it should say something like "Version 1.1".
	 */
	public String getVersion()
	{
		return("Ver. 1.0");
	}



}
