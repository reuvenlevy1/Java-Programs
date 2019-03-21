package reuven.java.datastructures;

public class DoublyLinkedListStack {
	private Node hp;					//head pointer; top
	private Node tp;					//tail pointer; bottom
	private Node cp;					//current pointer
	
	private class Node{
		private int data;
		private Node next;
		private Node prev;
		
		private Node(int data) {
			this.data = data;
			next = null;
			prev = null;
		}
	}

	public void push_to_front(int item) {
		Node n = new Node(item);
		
		if(hp == null) hp = tp = n;
		else {
			n.next = hp;
			hp.prev = n;
			hp = n;
		}
	}
	
	public void push_after(int item, int find) {	//item = value to be pushed; find = value to push after
		Node temp = new Node(0);
		Node n = new Node(item);
		cp = hp;
		
		while(cp != null) {
			if (cp.data == find) {
				temp = cp.next;					//node after the "to be inserted" new node  
				cp.next = n;					//before node "next" points to new node
				n.prev = cp;					//new node's "prev" points to before node
				n.next = temp;					//new node's "next" points to node after  
				temp.prev = n;					//after node's "prev" points to new node
				return;
			}
			else {
				if (cp.next != null) {
					cp = cp.next;
				}
				else {
					System.out.println("\nGiven value " + find + " does not exist");
					return;
				}
			}
		}
	}
	
	public void push_to_end(int item) {
		Node n = new Node(item);
		n.prev = tp;
		tp.next = n;
		tp = n;	
	}
	
	public void display() {
		System.out.println("Stack:");
		cp = hp;
		
		while(cp != null) {
			System.out.println(cp.data);
			cp = cp.next;
		}
	}
	
}
