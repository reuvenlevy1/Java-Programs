package reuven.java.datastructures;

import java.util.NoSuchElementException;

public class Queue {						// FIFO (First In First Out)
	
	private int MAX;
	private int last;
	private int first;
	private int[] queue;
	
	public void setMaxSize(int maxSize) {	//Sets max size of queue
		MAX = maxSize;
		queue = new int[MAX];
	}
	
	public boolean isEmpty() {
		return (last == 0);
	}
	
	public boolean isFull() {
		return (last == MAX);
	}
	
	public void enqueue(int item) {
		queue[last++] = item;
	}
	
	public int dequeue() { 
		if (queue[0] == 0) throw new NoSuchElementException();
		return queue[first++];
	}
	
	public int peek() {
		if (queue[0] == 0) throw new NoSuchElementException();
		return (queue[first]);
	}
	
	public int size() {
		return (last - first);
	}
	
}
