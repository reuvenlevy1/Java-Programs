package reuven.java.interviewquestions;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Returns an array of common integers between two sorted arrays.
 * This is done in the following way:
 * 1. One while loop incrementing indexes of either array depending on if 
 * the value of one array is less than the value of the other. 
 * 
 * Assumption:
 * 1. Integers would be positive.
 * 
 * The following are requirements for both arrays:
 * 1. Sorted in ascending order.
 * 2. Do not contain any duplicate numbers.
 *  
 * Contains the following features:
 * 1. Uses Selection Sort to sort any array into ascending order. 
 * 2. Uses LinkedHashMap to remove duplicates and maintain order of array.
 * 3. Uses HashSet to automatically sort and remove duplicates of array.
 * 
 * @author Reuven
 */

public class CommonIntegers {
	
	public CommonIntegers() {
		
	}
	
	public int[] sortArray(int[] nums) {
		return _sortArray(nums);
	}
	
	/*
	 * Uses Selection Sort
	 */
	private int[] _sortArray(int[] nums) {
		int min;
		int tmp;
		
		for (int x = 0; x < nums.length-1; x++) {
			min = x;
			
			for (int y = x+1; y < nums.length; y++) {
				if (nums[y] < nums[min]) {
					min = y;
				}
			}

			//Swap numbers in array:
			tmp = nums[x];
			nums[x] = nums[min];
			nums[min] = tmp;
		}
		return nums;
	}
	
	public int[] removeDuplicates(int[] nums) {
		return _removeDuplicates(nums);
	}

	private int[] _removeDuplicates(int[] nums) {
		
		//Didn't use HashSet or HashMap here as this will reorder array automatically,
		//which defeats purpose of the exercise for this method.
		Map<Integer, Integer> numsMap = new LinkedHashMap<Integer, Integer>();	//preserves insertion order
		
		if (nums.length == 1) {
			return nums;
		}
		
		for (int i = 0; i < nums.length; i++) {
			if (!numsMap.containsKey(nums[i])) {
				numsMap.put(nums[i], i);
			}
		}
		
		int[] newNums = new int[numsMap.size()];
		int j = 0;
		
		for (int key : numsMap.keySet()) {
			newNums[j] = key;
			j++;
		}
		return newNums;
	}
	
	public int[] sort_RemoveDups(int[] nums) {
		return _sort_RemoveDups(nums);
	}

	private int[] _sort_RemoveDups(int[] nums) {		
		Set<Integer> numSet = new HashSet<Integer>();			//preserves integers and will not take duplicates
		
		for (int i : nums) {
			numSet.add(i);
		}
		
		int[] new_nums = new int[numSet.size()];
		
		Iterator<Integer> it = numSet.iterator();				//new iterator for HashSet
	    
		for (int k = 0; k < numSet.size(); k++){
			new_nums[k] = it.next();
	    }
				
		return new_nums;
	}
	
	public int findCommonInts(int[] nums1, int[] nums2) {
		return _findCommonInts(nums1, nums2);
	}
	
	private int _findCommonInts(int[] nums1, int[] nums2) {
		int index1, index2;
		index2 = index1 = 0;
		
		try {
			while (true) {
				if (nums1[index1] == nums2[index2]) {			//Finds match
					return nums1[index1];
				} else if (nums1[index1] < nums2[index2]) {
					++index1;
				} else {
					++index2;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {			//if index exceeds array size (will only happen if there is no match)
			errorMessage();
		}
		return -1;
	}
	
	private void errorMessage() {
		String error = "No common integer exists between array1 and array2.";
		System.out.println(error);
	}
	
}
