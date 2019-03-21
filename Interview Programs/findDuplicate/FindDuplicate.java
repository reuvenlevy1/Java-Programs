package reuven.java.interviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Returns all duplicates from an integer array by adding numbers to a HashMap and checking/storing duplicates.
 * This is done in 2 ways:
 * 1. Add duplicates to String class using the += operator. [Slowest]
 * --> 2 6 7 7 9 5 4 6 3 7 4 6 4 6 7 4 4 4 8 4 4 6 4 4 6 4 6 = 5.2222 ms Average
 * 2. Append duplicates to StringBuilder class using append() method. [Mid Speed]
 * --> 1 4 6 4 2 2 2 4 2 2 2 4 2 5 4 2 2 2 4 2 2 4 2 2 3 2 4 = 2.8519 ms Average
 * 3. Add duplicates to ArrayList using add() method. [Fastest]
 * --> 1 2 1 1 2 2 2 2 2 2 2 1 2 1 1 2 2 3 2 2 2 1 2 2 2 2 2 = 1.7778 ms Average
 * 
 * @author Reuven
 */
public class FindDuplicate {
	
	public FindDuplicate() {
		
	}
	
	public String searchDupNumbers_String(int[] numList) {
		return _searchDupNumbers_String(numList);
	}
	
	private String _searchDupNumbers_String(int[] list) {
		Map<Integer, Integer> dupMap = new HashMap<Integer, Integer>();
		String dupList = "";
		
		for (int i = 0; i < list.length; i++) {
			if (dupMap.containsKey(list[i]) && dupList == "") {
				dupList += list[i];
			} else if (dupMap.containsKey(list[i]) && dupList != "") {
				dupList += ", " + list[i];
			} else {
				dupMap.put(list[i], i);
			}
		}
		
		return dupList;
	}
	
	public StringBuilder searchDupNumbers_SB(int[] numList) {
		return _searchDupNumbers_SB(numList);
	}
	
	private StringBuilder _searchDupNumbers_SB(int[] list) {
		Map<Integer, Integer> dupMap = new HashMap<Integer, Integer>();
		StringBuilder dupList = new StringBuilder();
		
		for (int i = 0; i < list.length; i++) {
			if (dupMap.containsKey(list[i]) && dupList.length() == 0) {
				dupList.append(list[i]);
			} else if (dupMap.containsKey(list[i]) && dupList.length() != 0) {
				dupList.append(", " + list[i]);
			} else {
				dupMap.put(list[i], i);
			}
		}
		
		return dupList;
	}
	
	public ArrayList<Integer> searchDupNumbers(int[] numList) {
		return _searchDupNumbers(numList);
	}
	
	private ArrayList<Integer> _searchDupNumbers(int[] list) {
		Map<Integer, Integer> dupMap = new HashMap<Integer, Integer>();
		List<Integer> dupList = new ArrayList<Integer>();
		
		for (int i = 0; i < list.length; i++) {
			if (dupMap.containsKey(list[i])) {
				dupList.add(list[i]);
			} else {
				dupMap.put(list[i], i);
			}
		}
		
		return dupList;
	}
	
}