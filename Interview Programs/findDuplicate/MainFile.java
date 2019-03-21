package reuven.java.interviewquestions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainFile {
//	static int size;
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
//		Scanner input = new Scanner(new InputStreamReader(System.in));
//		size = input.nextInt();
//		int[] numbers = new int[size];
		
//		int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//				11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
//				21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
//				31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
//				41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
//				51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
//				61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
//				71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
//				81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
//				91, 92, 93, 94, 95, 96, 97, 98, 99, 100,
//				
//				11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
//				31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
//				51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
//				71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
//				91, 92, 93, 94, 95, 96, 97, 98, 99, 100,
//				1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//				21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
//				41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
//				61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
//				81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
//				
//				51, 52, 53, 54, 55, 56, 57, 58, 59, 60,
//				1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
//				61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
//				31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
//				91, 92, 93, 94, 95, 96, 97, 98, 99, 100,
//				41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
//				11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
//				71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
//				21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
//				81, 82, 83, 84, 85, 86, 87, 88, 89, 90};
		
		//Read in text from file
		String file = "numbers.txt";
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		String[] words;
		Map<Integer, String> wordList = new HashMap<Integer, String>();
		int[] numbers;
		int y = 0;
		
		while ((line = br.readLine()) != null) {
			if(line.length() > 0) {											//ignores empty lines
				//Read a word at a time
				words = line.split("[\\s+]");								//delimiter for whitespace character, short for [ \t\n\x0b\r\f]

				for (int i = 0; i < words.length; i++) {
					wordList.put(y, words[i]);
					y++;
				}
			}
		}
		
		numbers = new int[wordList.size()];
		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = Integer.parseInt(wordList.get(i));
		}
		//-------------------------------------------------------------------
				
		FindDuplicate duplicate = new FindDuplicate();
		
		long startTime = System.currentTimeMillis();						//Start Time
		String list_S = duplicate.searchDupNumbers_String(numbers);
		System.out.println(list_S);
		long endTime = System.currentTimeMillis();							//End Time
		System.out.println("Total execution time: " + (endTime - startTime) + "ms\n");
		
		startTime = System.currentTimeMillis();
		StringBuilder list_SB = duplicate.searchDupNumbers_SB(numbers);
		System.out.println(list_SB);
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + "ms\n");
		
		startTime = System.currentTimeMillis();
		ArrayList<Integer> list_A = duplicate.searchDupNumbers(numbers);
		System.out.println(list_A);
		endTime = System.currentTimeMillis();
		System.out.println("Total execution time: " + (endTime - startTime) + "ms");

	}

}
