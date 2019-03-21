package reuven.java.datastructures;

public class Example1 {

	public static void main(String[] args) {
		Queue q = new Queue();
		q.setMaxSize(12);
		
		System.out.println("queue is empty: " + q.isEmpty());
		System.out.println("queue is full: " + q.isFull());
		q.enqueue(5);
		q.enqueue(9);
		q.enqueue(2);
		System.out.println("queue size: " + q.size());
		System.out.println("peek: " + q.peek());
		System.out.println("dequeue: " + q.dequeue());
		
	}

}
