package reuven.java.interviewquestions;

public class MainFile {

	public static void main(String[] args) {
		DupChars dc = new DupChars();

		//Example 1
		String word = "hhhhhhellooo";
		System.out.printf("%s\n%s\n", "Example:", word);
		dc.FindDupChars(word);
		
		//Example 2
		word = "bananas";
		System.out.printf("\n%s\n%s\n", "Example:", word);
		dc.FindDupChars(word);

		//Example 3
		word = "veneer-room";
		System.out.printf("\n%s\n%s\n", "Example:", word);
		dc.FindDupChars(word);
		
		//Example 4
		word = "wood-deer";
		System.out.printf("\n%s\n%s\n", "Example:", word);
		dc.FindDupChars(word);
		
		//Example 5
		word = "hoof-footed";
		System.out.printf("\n%s\n%s\n", "Example:", word);
		dc.FindDupChars(word);
		
		//Example 6
		word = "racecar";
		System.out.printf("\n%s\n%s\n", "Example:", word);
		dc.FindDupChars(word);
	}

}
