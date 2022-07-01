import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class Printer {
    private final static String TABLE_BORDER = "-";
    private final static String TABLE_BORDER_EDGE = "+";

    /**
     * Prints single column table.
     * 
     * @param title         A string title for the table.
     * @param columnsArr    An array of columns for the table.
     */
    public static void print1ColTable(String title, String[] columnsArr) {
        // Get longest String in array
        int maxColLen = maxLengthString1Col(columnsArr);
        // Find longest String length between maxColLen and title
        maxColLen = Math.max(maxColLen, title.length());
        // Add 2 to the maxColLen (needed for proper spacing)
        int maxDashLen = maxColLen+2;

        // Repeat tableBorder max length # of times
        String tableBorder = IntStream.range(0, maxDashLen).mapToObj(i -> TABLE_BORDER).collect(Collectors.joining(""));
        // Set alignment format
        String leftAlignFormat = "| %-"+maxColLen+"s |%n";
        // Print title in format
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        System.out.format(leftAlignFormat, title);
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        
        // Print columns in format
        for (String column : columnsArr)
            System.out.format(leftAlignFormat, column);
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
    }

    /**
     * Finds the maximum string length from an array of strings for 1 column table.
     * 
     * @param columnsArr    An array of columns for the table.
     * @return              Maximum length of longest string.
     */
    private static int maxLengthString1Col(String[] columnsArr) {
        int maxStringNum = 0;
        for (String word : columnsArr) {
            if (word.length() > maxStringNum)
                maxStringNum = word.length();
        }
        return maxStringNum;
    }

    /**
     * Prints multiple columns table.
     * 
     * @param transArr  A list of string arrays for each row of the table.
     */
    public static void printMultipleColTable(ArrayList<String[]> transArr) {
        List<String[]> myList = deepCopyListOfStrArr(transArr);
        // Find maximum length of all columns
        int[] maxColLens = _maxLengthStringMultipleCol(myList);
        
        // Builds leftAlignFormat for each column for table output
        List<String> leftAlignFormat = new ArrayList<String>();
        for (int i=0; i<maxColLens.length; i++) {
            if (i == maxColLens.length-1)
                leftAlignFormat.add("| %-"+maxColLens[i]+"s |%n");
            else
                leftAlignFormat.add("| %-"+maxColLens[i]+"s ");
        }

        // Sum of lengths in leftAlignFormat + 11 (needed for proper spacing)
        int maxDashLen = 11;
        for (int i=0; i<maxColLens.length; i++)
            maxDashLen += maxColLens[i];
        // Create a string with number of dashes needed for border
        String tableBorder = IntStream.range(0, maxDashLen).mapToObj(i -> TABLE_BORDER).collect(Collectors.joining(""));

        _printMultipleColTable(myList, leftAlignFormat, tableBorder);
    }

    /**
     * Finds the maximum character length for a column.
     * 
     * @param myList    A list of string arrays for each row of the table.
     * @return          Maximum length of longest string.
     */
    private static int[] _maxLengthStringMultipleCol(List<String[]> myList) {
        int numOfCols = myList.get(0).length;
        int longestString = 0;
        int[] maxLenInCol = new int[numOfCols];
        
        for (int i=0; i<numOfCols; i++) {
            longestString = 0;
            // Length of rows in the ith column
            for (int j=0; j<myList.size(); j++) {
                // Call method here that will change output as needed
                myList.get(j)[i] = DataHandler.beautifier(myList.get(j)[i]);
                // Store longest string
                if (myList.get(j)[i].length() > longestString)
                    longestString = myList.get(j)[i].length();
            }
            maxLenInCol[i] = longestString;
        }
        return maxLenInCol;
    }
     
    /**
     * Prints a border around a table of multiple columns.
     * 
     * @param transArr          A list of string arrays for each row of the table.
     * @param leftAlignFormat   An array of left alignment formats for each column.
     * @param tableBorder       String that contains the border for the table.
     */
    private static void _printMultipleColTable(List<String[]> transArr, List<String> leftAlignFormat, String tableBorder) {
        // Prints a border for title of table
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        for (int i=0; i<transArr.get(0).length; i++)
            System.out.format(leftAlignFormat.get(i), transArr.get(0)[i]);
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        
        // Prints body of table (row at a time)
        for (int i=1; i<transArr.size(); i++) {
            for (int j=0; j<transArr.get(0).length; j++) {
                // If string contains only numbers and decimal point
                if (transArr.get(i)[j].matches("[0-9]+.[0-9]+")) {
                    System.out.format(leftAlignFormat.get(j),
                    transArr.get(i)[j]);
                } else
                    System.out.format(leftAlignFormat.get(j), transArr.get(i)[j]);
            }
        }
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
    }

    /**
     * Makes a deep copy of the List of String arrays object.
     * 
     * @param myList    Original list of String arrays.
     * @return          A deep copy of the the old list of {@code String}s.
     */
    public static List<String[]> deepCopyListOfStrArr(List<String[]> myList){
        List<String[]> newList = new ArrayList<String[]>(myList.size());
        
        for (String[] arr : myList){
            String[] newArr = new String[arr.length];
            int i = 0;
            for (String s : arr){
                // Add each string to new string array
                newArr [i] = s;
                i++;
            }
            // Add each new string array to new list
            newList.add(newArr);
        }
        return newList;
    }
}
