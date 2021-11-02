import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Printer {
    private final static String TABLE_BORDER = "-";
    private final static String TABLE_BORDER_EDGE = "+";

    /**
     * Prints single column table
     * 
     * @param title: A string title for the table
     * @param columnsArr: An array of columns for the table
     */
    public static void print1ColTable(String title, String[] columnsArr) {      //FIXME: Make this better, use second method to figure this out
        // String dashes = "-";
        String spaces = " ";
        int maxSpaceLen = 0;

        // Get longest String in array
        int maxColumnLen = maxLengthString1Col(columnsArr);
        // Find longest String length between columns and title
        int maxDashLen = Math.max(maxColumnLen, title.length())+1;
        
        if (maxColumnLen > title.length()) {
            maxSpaceLen = maxColumnLen - title.length();
        }
        // Repeat tableBorder and spaces max length # of times
        String tableBorder = IntStream.range(0, maxDashLen).mapToObj(i -> TABLE_BORDER).collect(Collectors.joining(""));
        spaces += IntStream.range(0, maxSpaceLen).mapToObj(i -> " ").collect(Collectors.joining(""));
        // Set alignment format
        String leftAlignFormat = "| %-" + maxDashLen + "s|%n";
        // Print title in format
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        System.out.format("|" + title + spaces + "|%n");
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        
        // Print columns in format
        for (String column : columnsArr)
            System.out.format(leftAlignFormat, column);
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
    }

    /**
     * Finds the maximum string length from an array of strings for 1 column  table
     * 
     * @param columnsArr: An array of columns for the table
     * @return
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
     * Prints multiple columns table
     * 
     * @param title: A string title for the table
     * @param columnsArr: An array of columns for the table
     */
    public static void printMultipleColTable(String[][] transArr) {                 //FIXME: Finish writing the rest of the comments for the variables
        // Find maximum length of all columns
        int[] maxColLens = _maxLengthStringMultipleCol(transArr);
        
        // Builds leftAlignFormat for each column for table output
        ArrayList<String> leftAlignFormat = new ArrayList<String>();
        for (int i=0; i<maxColLens.length; i++){
            if (i == maxColLens.length-1)
                leftAlignFormat.add("| %-"+maxColLens[i]+"s |%n");
            else
                leftAlignFormat.add("| %-"+maxColLens[i]+"s ");
        }

        // Sum of lengths in leftAlignFormat + 11
        int maxDashes = 11;
        for (int i=0; i<maxColLens.length; i++)
            maxDashes += maxColLens[i];
        // Create a string with number of dashes needed for border
        String tableBorder = IntStream.range(0, maxDashes).mapToObj(i -> TABLE_BORDER).collect(Collectors.joining(""));

        _printMultipleColTable(transArr, leftAlignFormat, tableBorder);
    }

    // Finds the maximum character length of each row value within a column
    private static int[] _maxLengthStringMultipleCol(String[][] myString){
        int numOfCols = myString[0].length;
        int longestString;
        int[] maxLenInCol = new int[numOfCols];
        
        for (int i=0; i<numOfCols; i++){
            longestString = 0;
            // Length of rows in the ith column
            for (int j=0; j<myString.length; j++){
                if (myString[j][i].length() > longestString)
                    longestString = myString[j][i].length();
            }
            maxLenInCol[i] = longestString;
        }
        return maxLenInCol;
     }
     
     // Prints a border of around a table of multiple columns
    private static void _printMultipleColTable(String[][] transArr, ArrayList<String> leftAlignFormat, String tableBorder){
        // Prints a border for title of table
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        for (int i=0; i<transArr[0].length; i++){
            System.out.format(leftAlignFormat.get(i), transArr[0][i]);
        }
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
        
        // Prints body of table (row at a time)
        for(int i=1; i<transArr.length; i++){
            for (int j=0; j<transArr[0].length; j++){
                System.out.format(leftAlignFormat.get(j), transArr[i][j]);
            }
        }
        System.out.format(TABLE_BORDER_EDGE+tableBorder+TABLE_BORDER_EDGE+"%n");
     }
}
