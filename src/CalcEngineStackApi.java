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

	Stack <Character>calcStack = new Stack<Character>();
	Stack<Character> calculationStack = new Stack<Character>();

	//give each operator a level of precedence.
	final int ADD_MINUS_PRECEDENCE = 1;
	final int MULTIPLY_DIVIDE_PRECEDENCE = 2;
	final int POWER_OF = 3;
	final int BRACKET_PRECEDENCE = 4;
	final int DECIMAL_POINT = 5;
	
	int operand;
	int displayValue;
	char operator;
    String expression; // create a string with the full expression
	String postfixExpression;

	public CalcEngineStackApi(){
		operator = ' ';
		displayValue = 0;
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
		return Integer.toString((displayValue));
	}

	public void numberPressed(){
		
	}
	
	public void plus(){
		
	}
	public void minus(){
		
	}
	public void multiply(){
		
	}
	public void divide(){
		
	}
	public void powerTo(){
		
	}
	public void decimalPoint(){
		
	}
	public void equals(){
		// do infix to postfix transition and calculate
		convertToPostfix(expression);
		calculatePostFix(postfixExpression);
	}
	
	//this method calculates the result of a given postfix sum.
	private void calculatePostFix(String postfixExpression) {
	 /*use a switch statement to go through the characters.
	  * if its a number then push it onto the stack calculation stack.
	  * if we see an operator pop two items and operate on them.
	  * then add this result into the stack.
	  * eg.
	  * 11+3* (1+1)*3
	  * this will result in 1+1 =2		----> Stack now has: 2,3
	  * so we then pop 2 and 3 and multiply. RESULT = 6 (Correct answer).
	  */
	}

	public void hasPrecedenceOver(char thisChar,int precedence1){
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
	public void getEndBracket(char character){
		while(!calcStack.isEmpty()){
			char topChar = (char) calcStack.pop();
			if(topChar == '(') break;
			else
				postfixExpression += topChar;
		}
	}
	
	//this methods converts a given infix sum to postfix.
	public void convertToPostfix(String expression){
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
			default:
				postfixExpression += currentChar; 	//this will be an operand.
			}
		}
		
		while(!calcStack.isEmpty()){
			postfixExpression += calcStack.pop();
			System.out.println(postfixExpression);
		}
		System.out.println(postfixExpression);
	}
	
	//method clears the calculator screen and resets any calculations.
    public void clear()
    {
        displayValue = 0;
        expression = "";	
        postfixExpression = "";// reset expressions to nothing.
		operand = 0;
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
