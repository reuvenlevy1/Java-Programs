package reuven.java.datastructures;

public class Example1 {

	public static void main(String[] args) {
		HashTable ht = new HashTable(11);
		System.out.println("Hash Table size: " + ht.size());
		ht.put("John", "A happy boy");
		ht.put("Reuven", "A swell fella");
		ht.put("Peter", "A random man");
		ht.put("Stacey", "A random woman");
		System.out.println("Get John: " + ht.get("John"));
		System.out.println("Get Reuven: " + ht.get("Reuven"));
		System.out.println("Get Peter: " + ht.get("Peter"));
		System.out.println("Get Stacey: " + ht.get("Stacey"));
	}
}
