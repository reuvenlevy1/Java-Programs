package reuven.java.interviewquestions;

//import java.util.Arrays;

/**
 * Calculates and prints Fibonnaci of any integer in two ways:
 * 1. Prints by exact value.
 * 2. Prints entire sequence.
 * 
 * @author Reuven
 */

public class FibonacciCalculator {
	private static int[] fibonnaciSeq;
	
	private static void calc(int num) {
		fibonnaciSeq = new int[num+1];							//include 0
        fibonnaciSeq[0] = 0;
        fibonnaciSeq[1] = 1;
        
        for (int i = 2; i < fibonnaciSeq.length; i++){
        	fibonnaciSeq[i] = fibonnaciSeq[i-1] + fibonnaciSeq[i-2];
        }
	}
	
	private static void calcNeg(int num) {
		fibonnaciSeq = new int[num+1];
        fibonnaciSeq[0] = 0;
        fibonnaciSeq[1] = 1;
        
        for (int i = 2; i < fibonnaciSeq.length; i++){
        	fibonnaciSeq[i] = -fibonnaciSeq[i-1] + fibonnaciSeq[i-2];
        }
	}
	
	public static void printValue(int num) {
		if (num < 0) {
			num = num*-1;
			calcNeg(num);
			System.out.println(fibonnaciSeq[num]);
			return;
        } else if (num == 0) {
        	System.out.println(0);
        	return;
        }
		calc(num);
		System.out.println(fibonnaciSeq[num]);
	}
	
	public static void printSeq(int num) {
		if (num < 0) {
			num = num*-1;
			calcNeg(num);
			
			for (int i = 0; i < fibonnaciSeq.length; i++) {
				System.out.printf("%s ", fibonnaciSeq[i]);
			}
			return;
        } else if (num == 0) {
        	System.out.println(0);
        	return;
        }
		calc(num);
		
		for (int fNum : fibonnaciSeq) {
			System.out.printf("%s ", fNum);
		}
//		or
//		System.out.print(Arrays.toString(fibonnaciSeq));
	}
	
	public static void main(String[] args) {
//		FibonacciCalculator fc = new FibonacciCalculator();		//If you use this, don't need to set methods as static to access them as this is creating a new object
		
		//Example 1 - Special case
		System.out.println("\nF(0):");
		FibonacciCalculator.printValue(0);						//when in same file, use classname instead of new object
		FibonacciCalculator.printSeq(0);
		
		//Example 2 - length exactly 2
		System.out.println("\nF(1):");
		FibonacciCalculator.printValue(1);
		FibonacciCalculator.printSeq(1);
		
		//Example 3 - length over 2
		System.out.println("\n\nF(2):");
		FibonacciCalculator.printValue(5);
		FibonacciCalculator.printSeq(5);
		
		//Example 4 -large number
		System.out.println("\n\nF(10):");
		FibonacciCalculator.printValue(10);
		FibonacciCalculator.printSeq(10);
		
		//Example 5 - humongous number
		System.out.println("\n\nF(20):");
		FibonacciCalculator.printValue(20);
		FibonacciCalculator.printSeq(20);
		
		//Example 6 - negative number
		System.out.println("\n\nF(-1):");
		FibonacciCalculator.printValue(-1);
		FibonacciCalculator.printSeq(-1);
		
		//Example 7 - negative number over length 2
		System.out.println("\n\nF(-2):");
		FibonacciCalculator.printValue(-2);
		FibonacciCalculator.printSeq(-2);
		
		//Example 8 - large negative number
		System.out.println("\n\nF(-10):");
		FibonacciCalculator.printValue(-10);
		FibonacciCalculator.printSeq(-10);
		
		//Example 9 - humongous negative number
		System.out.println("\n\nF(-20):");
		FibonacciCalculator.printValue(-20);
		FibonacciCalculator.printSeq(-20);
	}
	
}
