package reuven.java.datastructures;

public class Example1 {

	public static void main(String[] args) {
		Stack s = new Stack();
		s.setMaxSize(12);
		
		System.out.println("stack is empty: " + s.isEmpty());
		System.out.println("stack is full: " + s.isFull());
		s.size();
//		s.peek();			//NoSuchElementException
		s.display();
		s.push(5);
		s.push(9);
		s.display();
		
		System.out.println("stack size: " + s.size());
		System.out.println("pop: " + s.pop());
		System.out.println("pop: " + s.pop());
		s.display();
		
		System.out.println("stack is empty: " + s.isEmpty());
		s.peek();
		s.push(8);
		s.peek();			//NoSuchElementException
	}

}
