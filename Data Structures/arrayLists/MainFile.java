package reuven.java.datastructures;

public class MainFile {

	public static void main(String[] args) {
		ArrayList al = new ArrayList();
		System.out.println("current size: " + al.size());
		al.add(2);
		al.add(5);
		al.add(8);
		al.add(21);
		System.out.println("current size: " + al.size());
		System.out.println("value at index 0: " + al.get(0));
		System.out.println("value at index 1: " + al.get(1));
		System.out.println("value at index " + (al.size()-1) + 
				": " + al.get(al.size()-1));
//		System.out.println("value at index 5" + al.get(5));			//ArrayIndexOutOfBoundsException
		System.out.println("remove index " + (al.size()-1) + 
				": " + al.remove(al.size()-1));
		System.out.println("current size: " + al.size());
	}
}
