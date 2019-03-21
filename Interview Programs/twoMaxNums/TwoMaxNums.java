package reuven.java.interviewquestions;

/**
 * Locate and display the two maximum numbers in an array.
 * This is done in the following way:
 * 1. Assign numbers in array to max1 and max2 variables
 * 2. Iterate through array and check values against max1 and max2 variables when appropriate.
 * 
 * Works for the following:
 * 1. Duplicate numbers
 * 2. Arrays with length less than 2
 * 3. Negative numbers
 * 
 * @author Reuven
 */

public class TwoMaxNums {
	
	public TwoMaxNums() {
		
	}
	
	public void findTwoMaxNums(int[] numArray) {
		_findTwoMaxNums(numArray);
	}
	
	private void _findTwoMaxNums(int[] numArray) {
		int max2, max1;
		
		if (numArray.length < 2) {
			System.out.printf("max1 = %d\nmax2 = null", max1 = numArray[0]);
			return;
		}
		
		if (numArray[0] > numArray[1]) {
			max1 = numArray[0];
			max2 = numArray[1];
		} else {
			max1 = numArray[1];
			max2 = numArray[0];
		}
		
		if (numArray.length > 2) {
			for (int num = 2; num < numArray.length; num++) {
				if (max1 < numArray[num]) {
					max2 = max1;
					max1 = numArray[num];
				} else if (max2 < numArray[num]) {
					max2 = numArray[num];
				}
			}
		}
		_display(max1, max2);
	}
	
	private void _display(int max1, int max2) {
		System.out.printf("max1 = %d\nmax2 = %d\n", max1, max2);
	}
	
}
