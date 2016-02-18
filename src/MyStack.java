/**
 * @file        MyStack.java
 * @author      Dean Gaffney 20067423
 * @assignment  Implementation of a generic Stack class
 * @brief       Creating a generic stack class for the calculator engine.
 *
 * @notes       
 * 				
 */
public class MyStack <E>{ // make the class generic to accept all data types.

	private final int size; // size of stack
	private int top; // this will keep track of the index of the top element in the stack
	private E []stack; // create an array of a generic data type.

	public MyStack(){
		size = 25; //just a default capacity.
		top = -1; //start it at -1 to show stack is empty
		stack = (E[]) new Object[size];
	}
	
	//method pushes an object onto the stack.
	public void push(E objectToPush){
		if(top == size - 1) // makes sure they cant push if stack is meant to be full
			System.out.println("Sorry the stack is full. Cant push object.");
		else{
			stack[++top] = objectToPush; //adds the object to the location of the stack.
		}
	}
	
	//pops an object off the stack and returns the object.
	public E pop(){
		if(top == -1){
			return (E) "Stack is empty. Cant pop any objects.";
		}
		return stack[top--]; //get the object at current location and then decrement the top.
	}
	
	//shows what object is at the top of the stack without modifying the object.
	public E peek(){
		if(top == -1){
			return (E) "Stack is empty. Cant peek any objects.";
		}
		return stack[top]; // return the object at the top but dont modify it.
	}
	
	//returns the size of the stack.
	public int size(){
		if(top == -1){
			return 0;
		}
		return stack.length; // the size of the stack will be the length of the array.
	}
	
	//returns true if the stack is empty,false otherwise.
	public boolean isEmpty(){
		boolean empty = false;
		if(top == -1){
			empty = true;
			return empty;
		}else{
			empty = false;
			return empty;
		}
	}
	
	//removes all the elements from the stack.
	public void removeAllElements(){
		if(top == -1){
			System.out.println("There are no objects to remove from the stack as it is empty.");
		}else{
			for(int i = 0; i<stack.length;i++){
				pop(); // pop off all elements
			}
		}
	}
}
