package reuven.java.datastructures;

import java.util.NoSuchElementException;

public class Stack {								//FILO (First In Last Out)
	
	private int MAX;
	private int top;
	private int[] stack;
	
	
	public void setMaxSize(int maxSize) {
		MAX = maxSize;
		top = 0;
		stack = new int[MAX];
	}
	
	public boolean isEmpty() {
		return (top == 0);
	}
	
	public boolean isFull() {
		return (top == MAX);
	}
	
	public void push(int item) {
		if (!isFull()) stack[top++] = item;			//If S:top exceeds n, the stack overflows
	}
	
	public int pop() {								//"the stack underflows" - Popping an empty stack, which is normally an error.
		if (top == 0) throw new NoSuchElementException();
		else {
			return stack[--top];
		}
	}
	
	public int peek() {								//Gets the top element in the stack
		if (top == 0) throw new NoSuchElementException();
		else {
			return (stack[--top]);
		}
	}
	
	public int size() {
		return (top);
	}

	public void display() {
		System.out.print("Stack: ");
		
		for (int i = 0; i<stack.length; i++) {
			System.out.print(stack[i] + " ");
		}
		System.out.println();
	}
	
}