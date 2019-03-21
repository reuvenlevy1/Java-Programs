package reuven.java.interviewquestions;

import java.util.Arrays;

public class MainFile {

	public static void main(String[] args) {
		TwoMaxNums tmn = new TwoMaxNums();
		
		//Example 1 - random order
		int[] array = new int[] {4,2,8,7,3,0};
		System.out.println("Example 1:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
		
		//Example 2 - negative numbers
		System.out.println();
		array = new int[] {-32,-6,-87,-451,-2,-1};
		System.out.println("Example 2:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
		
		//Example 3 - descending order
		System.out.println();
		array = new int[] {10,8,6,4,2,0};
		System.out.println("Example 3:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
		
		//Example 4 - ascending order
		System.out.println();
		array = new int[] {0,2,4,6,8,10};
		System.out.println("Example 4:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
		
		//Example 5 - all same number
		System.out.println();
		array = new int[] {1,1,1,1,1,1};
		System.out.println("Example 6:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
		
		//Example 7 - all but one are same number
		System.out.println();
		array = new int[] {1,1,2,1,1,1};
		System.out.println("Example 7:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
		
		//Example 8 - array length < 2
		System.out.println();
		array = new int[] {1};
		System.out.println("Example 7:\n" + Arrays.toString(array));
		tmn.findTwoMaxNums(array);
	}

}
