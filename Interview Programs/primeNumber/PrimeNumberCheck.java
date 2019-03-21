package reuven.java.interviewquestions;

/**
 * Takes integer and returns boolean true/false if prime. 
 * Definition:
 * 	A prime number is a whole number greater than 1 whose only factors are 1 and itself.
 * 
 * @author Reuven
 */
public class PrimeNumberCheck {
	
	public PrimeNumberCheck() {
		
	}
	
	public static boolean isPrime(int num) {
		if (num <= 0 || num == 1) {
			return false;
		}
		
		for (int i=2; i <= num/2; i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		boolean value;
		int num;
		System.out.println("It's prime:\n------------");
		
		//Non-Prime number
		num = -4;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
		
		num = -2;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
		
		num = 0;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
		
		num = 1;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
		
		//Prime Numbers
		num = 2;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
		
		num = 3;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
		
		num = 199;
		value = PrimeNumberCheck.isPrime(num);
		System.out.println(num + ": " + value);
	}

}
