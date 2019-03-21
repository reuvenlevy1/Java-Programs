package reuven.java.datastructures;

public class DLLStackExample1 {

	public static void main(String[] args) {
		DoublyLinkedListStack dll = new DoublyLinkedListStack();
		dll.push_to_front(1);
		dll.push_to_front(2);
		dll.push_to_front(3);
		dll.display();
		
		dll.push_to_end(7);
		System.out.println();
		dll.display();
		
		dll.push_to_end(20);
		System.out.println();
		dll.display();
		
		dll.push_after(25, 2);
		System.out.println();
		dll.display();
		
		dll.push_after(30, 11);
		System.out.println();
		dll.display();
		
	}

}
