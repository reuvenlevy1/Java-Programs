package reuven.java.datastructures;

public class SinglyLinkedListStack {
	private Node hp;					//head pointer; top
	private Node cp;					//current pointer
	
	private class Node{
		private int data;
		private Node next;				//pointer to next node;
		
		public Node(int data) {
			this.data = data;
			next = null;
		}
	}
	
	public void push(int item) {
		Node n = new Node(item);

		if (hp == null) hp = n;
		else {
			n.next = hp;				//EVERY NEW NODE INSERTS AT THE BEGINNING OF LIST; hp points to most recent node.
			hp = n;
		}
	}
	
	public int pop() {
		if (hp == null) { 
            System.out.print("\nStack Underflow");	//stack is empty
            return -1;
        } 
		
		Node temp = hp;
		hp = hp.next;
		return temp.data;
	}
	
	public int peek() {
		if (isEmpty()) {
			System.out.println("Stack is empty"); 
            return -1;
		}
		else return hp.data;
	}
	
	public void clear() {
		hp = null;
	}
	
	public boolean isEmpty() {
		return hp == null;
	}
	
	public void printList() {
		cp = hp;
		System.out.println("Stack: ");
		
		while(cp != null) {
			System.out.println(cp.data + " ");
			cp = cp.next;
		}
	}
	
}
