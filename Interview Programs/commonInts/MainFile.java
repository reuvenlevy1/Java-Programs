package reuven.java.interviewquestions;

import java.util.Arrays;

public class MainFile {

	public static void main(String[] args) {		
		CommonIntegers ci = new CommonIntegers();
		
		//Test Case 1: Matching Number
		System.out.printf("%s\n\n", "Test Case 1: Matching Number");
		int[] arr1 = {1,2,3,4,5,10};
		int[] arr2 = {7,9,10,15};
		System.out.printf("%s\n%s\n%s\n", "input:", Arrays.toString(arr1), Arrays.toString(arr2));
		System.out.printf("%s\n%s\n\n", "output:", ci.findCommonInts(arr1, arr2));

		//Test Case 2: No Matching Number
		System.out.printf("%s\n\n", "Test Case 2: No Matching Number");
		int[] arr3 = {1,2,3,4,5};
		int[] arr4 = {7,9,10,15};
		System.out.printf("%s\n%s\n%s\n", "input:", Arrays.toString(arr3), Arrays.toString(arr4));
		System.out.printf("%s\n%s\n\n", "output:", ci.findCommonInts(arr3, arr4));
		
		//Test Case 3: Duplicate Numbers
		System.out.printf("%s\n\n", "Test Case 3: Duplicate Numbers");
		int[] arr5 = {4,0,0,2,4,3,1,5,1};
		System.out.printf("%s\n%s\n", "input:", Arrays.toString(arr5));
		int[] sortedUnique_arr5 = ci.removeDuplicates(arr5);
		System.out.printf("%s\n%s\n\n", "output:", Arrays.toString(sortedUnique_arr5));
		
		//Test Case 4: Numbers Out Of Order
		System.out.printf("%s\n\n", "Test Case 4: Numbers Out Of Order");
		int[] arr6 = {1,3,5,2,0,4};
		System.out.printf("%s\n%s\n", "input:", Arrays.toString(arr6));
		int[] sorted_arr6 = ci.sortArray(arr6);
		System.out.printf("%s\n%s\n\n", "output:", Arrays.toString(sorted_arr6));
				
		//Test Case 5: Numbers Out Of Order & Duplicate Numbers
		System.out.printf("%s\n\n", "Test Case 5: Numbers Out Of Order & Duplicate Numbers");
		int[] arr7 = {4,0,0,2,4,3,1,5,1};
		System.out.printf("%s\n%s\n", "input:", Arrays.toString(arr7));
		int[] sortedUnique_arr7 = ci.sort_RemoveDups(arr7);
		System.out.printf("%s\n%s\n\n", "output:", Arrays.toString(sortedUnique_arr7));
	}
	
}
