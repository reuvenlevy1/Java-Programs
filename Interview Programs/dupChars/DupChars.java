package reuven.java.interviewquestions;

import java.util.HashMap;
import java.util.Map;

/**
 * Identifies duplicate or repeated characters in a string as well as its number of
 * occurrences.
 * This is done in the following way:
 * 1. Uses toCharArray() method to make String into an array of char.
 * 2. Uses HashMap to detect and store number of occurrence. 
 * 
 * @author Reuven
 */

public class DupChars {
	
	public DupChars () {
		
	}
	
	public void FindDupChars(String word) {
		_FindDupChars(word);
	}
	
	private void _FindDupChars(String word) {
		Map<Character, Integer> letterMap = new HashMap<Character, Integer>();
		char[] letterArray = word.toCharArray();										//toCharArray(): converts the given string into a sequence of characters
		
		for (char letter : letterArray) {
			if (letterMap.containsKey(letter)) {
				letterMap.put(letter, letterMap.get(letter)+1);
			} else {
				letterMap.put(letter, 1);
			}
		}
		_display(letterMap);
	}
	
	private void _display(Map<Character, Integer> letterMap) {
		for (Map.Entry<Character, Integer> entry : letterMap.entrySet()) {				//entrySet(): iterate both key and value
			if (entry.getValue() > 1) {
				System.out.printf("%c -> %d\n", entry.getKey(), entry.getValue());
			}
		}
	}
	
}
