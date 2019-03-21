package reuven.java.interviewquestions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;						//User input
import java.util.Set;
import java.util.TreeMap;						//Order Hash Map by keys

//FileChooser GUI
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import java.util.Map.Entry;
import java.io.BufferedReader;					//Read character input stream (in this case it will be the file, which is opened with FileReader class)
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;						//Read text from file
import java.io.IOException;

/**
 * Uses JavaSwing's FileChooser to open text file, store each word and the number of times they are found in a HashMap. 
 * This is done in 2 different ways:
 * 1. Constructor "WordCounter()" uses BufferedReader to read words from file.
 * 2. Constructor "WordCounter(String s)" uses Scanner to read words from file. Timing the process proves BufferedReader
 * is faster.
 * 
 * HashMap is then sorted in 4 ways:
 * 1. Ascending values making Set of HashMap, store in List, sort list using Comparator of new Map.
 * 2. Descending values using same method above.
 * 3. Ascending keys using a TreeMap.
 * 4. Descending values using a TreeMap with a Comparator.
 * 
 * @author Reuven
 */

public class WordCounter {		
	String line;													//stores text input line
	String[] words;													//line split into words
	int freq;														//Number of times a word is encountered
	Map<String, Integer> wordList;
	String fn;
	
	WordCounter() throws FileNotFoundException, IOException {		//Way Faster method
		fn = fileChooser();											//returns filename
	}
	
	public String fileChooser() throws FileNotFoundException, IOException {
		return _fileChooser();
	}
	
	private String _fileChooser() {
		wordList = new HashMap<>();
		
		//Open file using FileChooser
		while (true) {												//infinite loop unless you use a break, return or System.exit() statement.
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());		//JFileChooser points to user's default directory
			jfc.setDialogTitle("Choose a Text File to Read From");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
			jfc.addChoosableFileFilter(filter);
			
			int returnValue = jfc.showOpenDialog(null);				//Opens dialogue
			
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File fileName = jfc.getSelectedFile();
				System.out.println("File path: " + fileName.getAbsolutePath());
				return fileName.toString();
			} else {
				
				//Custom button text: Error message
				Object[] options = {"Choose File", "Exit"};
				int n = JOptionPane.showOptionDialog(
						null,
					    "Please Choose A Valid Text File.",
					    "ERROR!",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.ERROR_MESSAGE,
					    null,										//don't use a custom icon
					    options,									//the titles of buttons
					    options[1]);								//default button title
				
				if (n == JOptionPane.NO_OPTION) System.exit(0);		//closes program
			}
		}
	}
	
	public void countBufferedReader() throws FileNotFoundException, IOException {
		_countBufferedReader(fn);
	}
	
	private void _countBufferedReader(String file) throws FileNotFoundException, IOException {
		final long startTime = System.currentTimeMillis();			//Start Time
		
		//Read in text from file
		BufferedReader br = new BufferedReader(new FileReader(file));

		while ((line = br.readLine()) != null) {
			
			//Read a word at a time
			words = line.split("\\s+");								//delimiter for whitespace character, short for [ \t\n\x0b\r\f]

			for (int i = 0; i < words.length; i++) {
				
				//if word not found, store word with a counter of 1
				if (wordList.containsKey(words[i])) {
					freq = wordList.get(words[i]);
					wordList.put(words[i], ++freq);
				}
				
				//else increase counter by 1
				else wordList.put(words[i], 1);
			}
		}
		final long endTime = System.currentTimeMillis();			//End Time
		System.out.println("Total execution time: " + (endTime - startTime) + "ms");
	}
	
	public void countScanner() throws FileNotFoundException, IOException {
		_countScanner(fn);
	}
	
	private void _countScanner(String file) throws FileNotFoundException, IOException {
		final long startTime = System.currentTimeMillis();			//Start Time
		
		//Read in text from file
		Scanner input = new Scanner(new FileReader(file));
		input.useDelimiter("\\s+");									//delimiter for whitespace character, short for [ \t\n\x0b\r\f]
		
		while (input.hasNext()) {
			String word = input.next();
			
			if (wordList.containsKey(word)) {
				freq = wordList.get(word);
				wordList.put(word, ++freq);
			} else wordList.put(word, 1);
		}
		input.close();
		final long endTime = System.currentTimeMillis();			//End Time
		System.out.println("Total execution time: " + (endTime - startTime) + "ms");
	}
	
	public void display() {											//Unsorted
        System.out.printf("%s%n%-3s %15s%n%s%n", "-------------------","key","value","-------------------");
		wordList.forEach( (k,v) -> { System.out.printf("%-15s %d%n", k,v); });
	}
	
	public void displayValuesDesc() {								//Sort Values in descending order
		Set<Entry<String, Integer>> set = wordList.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        System.out.printf("%s%n%-3s %15s%n%s%n", "-------------------","key","value","-------------------");
        
        for (Map.Entry<String, Integer> entry : list) {
        	System.out.printf("%-15s %d%n", entry.getKey(),entry.getValue());
        }
	}
	
	public void displayValuesAsc() {								//Sort Values in ascending order
		
		/*
		 * Store entry set in a list, sort list on the basis of values, fetch values and keys from
		 * list and put them in new hashmap.
		*/
		Set<Entry<String, Integer>> set = wordList.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        System.out.printf("%s%n%-3s %15s%n%s%n", "-------------------","key","value","-------------------");
        
        for (Map.Entry<String, Integer> entry : list) {
        	System.out.printf("%-15s %d%n", entry.getKey(),entry.getValue());
        }
	}
	
	//Sort keys in ascending order naturally by inserting into TreeMap class
	public void displayKeysAsc() {									//Sort Keys in ascending order
        TreeMap<String, Integer> sorted = new TreeMap<>();
        sorted.putAll(wordList);
        System.out.printf("%s%n%-3s %15s%n%s%n", "-------------------","key","value","-------------------");
        
        for (Map.Entry<String, Integer> entry : sorted.entrySet())
        	System.out.printf("%-15s %d%n", entry.getKey(),entry.getValue());
	}

	public void displayKeysDesc() {									//Sort Keys in descending order
		
		//Creating a TreeMap with a Custom comparator (Descending order)		
		Map<String,Integer> treeMap = new TreeMap<>(
			new Comparator<String>() {
				public int compare(String o1, String o2) {
					return o2.compareTo(o1);//sort in descending order
				}
			});
		
		treeMap.putAll(wordList);
		System.out.printf("%s%n%-3s %15s%n%s%n", "-------------------","key","value","-------------------");

		for (Map.Entry<String, Integer> entry : treeMap.entrySet()) {
        	System.out.printf("%-15s %d%n", entry.getKey(),entry.getValue());
        }
	}
	
}
