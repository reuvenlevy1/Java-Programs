package reuven.java.datastructures;

public class SLLStackExample1 {

	public static void main(String[] args) {
		SinglyLinkedListStack sll = new SinglyLinkedListStack();
		System.out.println("Linked List isEmpty: " + sll.isEmpty());
		sll.push(7);
		sll.push(3);
		sll.push(8);
		System.out.println("Linked List isEmpty: " + sll.isEmpty());
		sll.printList();
		
		System.out.println("Pop: " + sll.pop());
		System.out.println("Pop: " + sll.pop());
		System.out.println("peek: " + sll.peek());
		System.out.println("Pop: " + sll.pop());
		sll.clear();
		
		sll.push(22);
		sll.printList();		
	}

}
