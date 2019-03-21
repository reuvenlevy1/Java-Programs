package reuven.java.interviewquestions;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {

	public static void main(String[] args) throws FileNotFoundException, IOException {
//		String s = "";
		WordCounter wc = new WordCounter();
		wc.countBufferedReader();
		
		System.out.println("Unordered");
		wc.display();
		System.out.println("\nOrdered by Descending Values");
		wc.displayValuesDesc();
		System.out.println("\nOrdered by Ascending Values");
		wc.displayValuesAsc();
		System.out.println("\nOrdered by Descending Keys");
		wc.displayKeysDesc();
		System.out.println("\nOrdered by Ascending Keys");
		wc.displayKeysAsc();
		
		System.out.println("\n\n");
		wc.countScanner();
		
		System.out.println("Unordered");
		wc.display();
		System.out.println("\nOrdered by Descending Values");
		wc.displayValuesDesc();
		System.out.println("\nOrdered by Ascending Values");
		wc.displayValuesAsc();
		System.out.println("\nOrdered by Descending Keys");
		wc.displayKeysDesc();
		System.out.println("\nOrdered by Ascending Keys");
		wc.displayKeysAsc();
	}
	
}
