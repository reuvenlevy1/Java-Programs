package reuven.java.datastructures;

public class HashTable {
	private String[] hashTable;
	private int index;
	
	public HashTable() {
		hashTable = new String[11];
	}
	
	public HashTable(int size) {
		hashTable = new String[size];
	}

	private int string_to_ascii_sum(String key) {		//Converts String to Sum of ASCII values
		int sum = 0;
		char[] letters = key.toCharArray();				//Converts passed String into an array with each index holding a single character
	
		for (char ch : letters) {
			sum += ch;
		}
		return sum;
	}
	
	private int get_index(String key) {
		int key_ascii = string_to_ascii_sum(key);		//Retrieves ASCII sum of String
		int n = hashTable.length;
		index = key_ascii % n;							//Index that variable should go into
		return index;
	}
	
	public int size() {
		return hashTable.length;
	}
	
	public void put(String key, String value) {
		index = get_index(key);

		//check if there's a value already located in calculated index
		if(hashTable[index] == null) hashTable[index] = value;
		else {
//			add to end of linked list (need to create linked list) --> A Collision resolution technique 
			//known as "Chaining" or "Closed Addressing"
			
		}
	}
	
	public String get(String key) {
		index = get_index(key);
		return hashTable[index];
	}
	
}
