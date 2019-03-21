package reuven.java.interviewquestions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

public class MainFile {

	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();			//Start Time
		long endTime = System.currentTimeMillis();				//End Time

		//Read from user input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//Read from String
//		String string = "hello";
//		Reader inputString = new StringReader(string);
//		BufferedReader br = new BufferedReader(inputString);

		//Read from file
//		System.out.println(System.getProperty("user.dir"));		//Shows current working directory --> This is where text file was created
//		BufferedReader br = new BufferedReader(new FileReader("story.txt"));


		System.out.println("Enter a string to see displayed in reverse");
		String s = br.readLine();

		ReverseString rs = new ReverseString();
		
//		startTime = System.currentTimeMillis();
//		System.out.println("reverse: " + rs.reverseString(s));
//		endTime = System.currentTimeMillis();
//		System.out.println("\nTotal execution time: " + (endTime - startTime) + "ms\n");
//		
//		startTime = System.currentTimeMillis();
//		System.out.println("reverse2: " + rs.reverseString2(s));
//		endTime = System.currentTimeMillis();
//		System.out.println("\nTotal execution time: " + (endTime - startTime) + "ms");
		
		startTime = System.currentTimeMillis();
		System.out.println(rs.recursiveRevString(s));
		endTime = System.currentTimeMillis();
		System.out.println("\nTotal execution time: " + (endTime - startTime) + "ms");
	}
	
}
