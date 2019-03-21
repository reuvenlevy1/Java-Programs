package reuven.java.interviewquestions;

import java.util.Scanner;

public class MainFile {

	public static void main(String[] args) {
		int value;									//used to store method's return
		SumOfDigits sod = new SumOfDigits();
		Scanner input = new Scanner(System.in);
		System.out.println("Input any integer");
		int num = input.nextInt();
		input.close();

		//Iterative
		value = sod.iSum(num);
		System.out.println(value);
		
		//Recursive
		value = sod.rSum(num);
		System.out.println(value);		
	}

}
