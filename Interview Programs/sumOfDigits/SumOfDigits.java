package reuven.java.interviewquestions;

/**
 * Takes an Integer (positive or negative) and returns the sum of it's digits.
 * This is done in 2 ways:
 * 1. Iterative
 * 2. Recursive
 * 
 * @author Reuven
 */

public class SumOfDigits {
	int recursiveSum = 0; 
	
	public SumOfDigits() {
		
	}
	
	//Iterative
	public int iSum(int num) {
		return _iSum(num);
	}
	
	private int _iSum(int num) {
		int sum = 0;
		
		while(num != 0) {
			sum += num % 10;
			num = num / 10;
		}
		return sum;
	}
	
	//Recursive
	public int rSum(int num) {
		if (num == 0) {
			return recursiveSum;
		} else {
			recursiveSum += num % 10;
			rSum(num / 10);
		}
		return recursiveSum;
	}
	
}
