Uses JavaSwing's FileChooser to open text file, store each word and the number of times they are found in a HashMap. 

This is done in 2 different ways:
  1. Constructor "WordCounter()" uses BufferedReader to read words from file.
  2. Constructor "WordCounter(String s)" uses Scanner to read words from file. Timing the process proves BufferedReader
     is faster.
 
HashMap is then sorted in 4 ways:
  1. Ascending values making Set of HashMap, store in List, sort list using Comparator of new Map.
  2. Descending values using same method above.
  3. Ascending keys using a TreeMap.
  4. Descending values using a TreeMap with a Comparator.
