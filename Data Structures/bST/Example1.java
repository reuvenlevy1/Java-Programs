package reuven.java.datastructures;

public class Example1 {

	public static void main(String[] args) {
		BST b = new BST();
		b.insert(2);
		b.insert(5);
		b.insert(1);
		b.insert(20);
		b.insert(15);
		System.out.println("b is empty: " + b.isEmpty());
		System.out.println("find 6: " + b.find(6));
		System.out.println("find 5: " + b.find(5));
		System.out.println("In-Order Traversal:");
		b.inOrderTraversal();
		System.out.println("\ndelete 0: " + b.delete(0));
		System.out.println("delete 1: " + b.delete(1));
		
		System.out.println("In-Order Traversal_2:");
		b.inOrderTraversal();
		
		System.out.println("\nPre-Order Traversal:");
		b.preOrderTraversal();
		
		System.out.println("\nPost-Order Traversal:");
		b.postOrderTraversal();
		
		b.clear();
		System.out.println("\nb is empty: " + b.isEmpty());
	
	}

}
