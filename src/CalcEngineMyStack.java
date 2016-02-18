import java.awt.GraphicsConfiguration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @file        CalcEngineMyStack.java
 * @author      Dean Gaffney 20067423
 * @assignment  Calculator engine using the my own stack class.
 * @brief       Creating a calculator engine with the my own implementation of the stack class.
 *
 * @notes       
 * 				
 */

public class CalcEngineMyStack {
	//Stack calcStack = new Stack();
	//Stack <Double> postStack = new Stack<Double>();
	MyStack calcStack = new MyStack();
	MyStack postStack = new MyStack();
	//give each operator a level of precedence.
	final int ADD_MINUS_PRECEDENCE = 1;
	final int MULTIPLY_DIVIDE_PRECEDENCE = 2;
	final int POWER_OF = 3;
	final int BRACKET_PRECEDENCE = 4;

	int operand;
	String displayValue;
	char operator;
	String expression; // create a string with the full expression
	String postfixExpression;

	public CalcEngineMyStack(){
		operator = ' ';
		displayValue = "";
		operand = 0;
		expression= "";
		postfixExpression ="";
	}

	//Method adds any command (i.e number or operator) to a string expression.
	public void addToExpression(char character){
		if(isOperator(character)){
			expression+= character+" "; // adds spaces after a number for regex use.
		}
		else{
			expression += character;
			System.out.println(expression);
		}
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


	private ArrayList<Double> extractNumbers(String expression){
		ArrayList<Double> extractedGroups = new ArrayList<Double>();
		String extractedString = expression;
		Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?"); // data/decimal/data
		Matcher matcher = pattern.matcher(extractedString);
		while(matcher.find()){
			System.out.println(Double.valueOf(matcher.group()));
			extractedGroups.add(Double.valueOf(matcher.group()));
		}
		System.out.println("This is the extracted groups" + extractedGroups);
		return extractedGroups;
	}

	/*
	 * This method calculates a postfix expression by means of a stack and an array
	 * and the extracted numbers method.
	 */
	private String expressionAnalysis(ArrayList<Double> extractedNums,String postExpression){
		int indexCounter = 0; // used to get number from array for stack.
		int cycle = 1;
		for(int i = 0; i <postExpression.length(); i++){
			char currentChar = postExpression.charAt(i); //get char
			switch(currentChar){
			case ' ':
				if(postStack.size() == extractedNums.size()){ //avoid adding out of bounds nums
					break;
				}
				else{
					if(indexCounter == extractedNums.size()){ //avoid going out of bounds.
						break;
					}
					postStack.push(extractedNums.get(indexCounter));
					System.out.println("This is the post stack :" + postStack);
					indexCounter++;
					break;
				}
				//if it's a space do increment.
			case '+': //push the result of adding the two popped
				postStack.push(plus((double)postStack.pop(),(double)postStack.pop()));
				System.out.println("This is the post stack :" + postStack);
				break;
			case '-': //push result of subtracting the two popped.
				postStack.push(minus((double)postStack.pop(),(double)postStack.pop()));
				System.out.println("This is the post stack :" + postStack);
				break;
			case '*'://push result of multiplying the two popped.
				postStack.push(multiply((double)postStack.pop(),(double)postStack.pop()));
				System.out.println("This is the post stack :" + postStack);
				break;
			case '/'://push result of dividing two popped.
				postStack.push(divide((double)postStack.pop(),(double)postStack.pop()));
				System.out.println("This is the post stack :" + postStack);
				break;
			case '^'://push result of power of two popped.
				postStack.push((double)(powerTo((double)postStack.pop(), (double)postStack.pop())));
				System.out.println("This is the post stack :" + postStack);
				break;
			default:
				if(cycle ==1){
					postStack.push(extractedNums.get(indexCounter));
					// this will push the first number from our array.
					System.out.println("This is the post stack :" + postStack);
					indexCounter++;
					cycle ++;
					break;
				}
				if(postStack.size() == extractedNums.size()){
					System.out.println("This is the post stack :" + postStack);
					break; // if the stack has all required numbers break.
				}
				else
					break;
			}
		}
		return Double.toString((double) postStack.pop());
	}



	// do infix to postfix transition and calculate
	public void equals(){
		convertToPostfix(expression);
		setDisplayValue(expressionAnalysis(extractNumbers(expression),postfixExpression));
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
				hasPrecedenceOver(currentChar,1); //check precedence
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
				System.out.println("After decimal point:" + postfixExpression); //add decimal point to expression
				break;
			default:
				postfixExpression += currentChar; 	//this will be an operand.
			}
		}

		while(!calcStack.isEmpty()){
			postfixExpression += " " +calcStack.pop(); //populates the postfix expression.
			System.out.println(postfixExpression);
		}
		System.out.println("Postfix Expression is: " + postfixExpression);
	}

	//method clears the calculator screen and resets any calculations.
	public void clear()
	{
		displayValue = "Cleared.";
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

	public String getVersion()
	{
		return("Ver. 1.0");
	}
}


