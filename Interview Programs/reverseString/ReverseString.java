package reuven.java.interviewquestions;

/**
 * Takes String input from user and file using BufferedReader and reverses it.
 * This is done in 3 different ways:
 * 1. Using StringBuffer and appending the characters with substring method
 * 2. Using String concatenation with substring method
 * 3. Using String concatenation with charAt method to get last letter added with substring method for other letters recursively
 * 
 * @author Reuven
 */

public class ReverseString {

	public ReverseString() {
		
	}

	/*
	 * System.in: Faster half the time
	 * FileReader: Twice as Fast
	 */
	public StringBuffer reverseString(String string) {
		return _reverseString(string);
	}
	
	private StringBuffer _reverseString(String s) {
		StringBuffer rString = new StringBuffer();

		if (s.length() == 1) return rString.append(s);

		for (int i= s.length() - 1; i >= 0; i--) rString.append(s.substring(i, i + 1));
		return rString;
	}

	/*
	 * System.in: Faster half the time, but other times, can be twice as slow
	 * Twice as slow
	 */
	public String reverseString2(String string) {
		return _reverseString2(string);
	}
	
	private String _reverseString2(String s) {
		String newS = "";

		if (s.length() == 1) return s;

		for (int i = s.length() - 1; i >= 0; i--) newS += s.substring(i, i + 1);
		return newS;
	}

	/*
	 * charAt() gets last letter of current object call.
	 * recursiveRevString() passes string with 1 less letter on end.
	 * This continues until 1 letter is left and returns it to it's previous object with 1 more letter.
	 * The last letter from the charAt() call is added to what was returned from previous object (1 letter in this case) and is stored in reverse, which is returned to previous object.
	 * This continues until all letters have been returned and is now stored in reverse and is returned to main from where recursiveRevString() was first called.
	 */
	public String recursiveRevString(String string) {
		return _recursiveRevString(string);
	}
	
	private String _recursiveRevString(String s) {
		String reverse = "";

		//base case
		if (s.length() == 1) {
			return s;
		} else {
			reverse += s.charAt(s.length() - 1)								//charAt gets the last letter
					+ recursiveRevString(s.substring(0, s.length() - 1));	//recursive call with 1 less letter
            return reverse;
		}
	}
	
}
