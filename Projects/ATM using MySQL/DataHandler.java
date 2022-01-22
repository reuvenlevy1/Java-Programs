/**
 * The {@code DataHandler} class prepares data for printing in money format.
 * Will also check input for valid money format {@literal #.##} or if user
 * typed in {@literal quit}.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class DataHandler {
    /**
     * Runs methods to format String inputs for end user.
     * 
     * @param myString  Input string to be formatted
     * @return
     */
    public static String beautifier(String myString) {
        // Assure there's 2 digits after decimal
        myString = twoDecimalFormat(myString);
        // Add comma if needed to money format
        myString = addCommasToMoneyFormat(myString);
        // Prefix dollar sign to money
        myString = addDollarSign(myString);
        // Remove comment character from string if exists
        myString = removeCommentChar(myString);
        return myString;
    }

    /**
     * Assure String is in money format with 2 decimal places.
     * 
     * @param myString  Input string from chosen ATM Menu option
     * @return
     */
    private static String twoDecimalFormat(String myString) {
        // Check if string starts with a number and contains a decimal
        if (myString.matches("^[0-9].*."))
            // Check for 2 digits after decimal
            if (!myString.matches(".[0-9]")
                    && !myString.substring(myString.length() - 3, myString.length() - 2).equals("."))
                myString = myString + 0;
        return myString;
    }

    /**
     * Prefix dollar sign to money.
     * 
     * @param myString  Input string from chosen ATM Menu option
     * @return
     */
    private static String addCommasToMoneyFormat(String myString) {
        // Check if starts with, and only contains, numbers
        if (myString.matches("^[0-9].*")) {
            double ds = Double.parseDouble(myString);
            int i = (int) ds;

            StringBuilder sb = new StringBuilder(myString);
            int len = 0;
            while (true) {
                if ((int) ds / 1000 >= 1) {
                    ds /= 1000;
                    len += 3;
                    sb.insert(Integer.toString(i).length() - len, ",");
                } else
                    break;
            }
            return sb.toString();
        } else
            return myString;
    }

    /**
     * 
     * @param myString
     * @return
     */
    private static String addDollarSign(String myString) {
        // Check if starts with, and only contains, numbers
        if (myString.matches("^[0-9].*.")) {
            return "$" + myString;
        } else
            return myString;
    }

    /**
     * Removes the comment character from a string.
     * 
     * @param myString  Input string from chosen ATM Menu option data
     * @return
     */
    private static String removeCommentChar(String myString) {
        // Remove comment character
        if (myString.substring(0, 1).equals("#"))
            myString = myString.substring(1);
        return myString;
    }

    /**
     * Check if user input is valid for money transactions or contains invalid
     * characters.
     * 
     * @param input     User input for expected money format
     * @return
     */
    public static boolean checkValidMoneyInput(String input) {
        // Check if input is not only numbers or contains more than 2 digits after
        // decimal
        if (!input.toLowerCase().matches("[0-9]*[.]*[0-9]*"))
            return false;
        else if (input.contains("."))
            if ( input.substring(input.indexOf(".") + 1).length() > 2)
                return false;
        return true;
    }

    public static boolean checkNumRequirements(String num) {
        // Check if num contains invalid characters
        char[] pinInvalidCharsList = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : pinInvalidCharsList) {
            if (num.contains(Character.toString(invalidChar)))
                return false;
        }
        return true;
    }

    /**
     * Check the user input for the following:
     * <blockquote><pre>
     *      <p>If the input is empty, display an error message</p>
     *      <p>If the user typed "quit", display closing message end the program</p>
     * </pre></blockquote>
     * 
     * @param input     User input
     * @param db        Holds database connection information
     * @return          <p>{@code true} if {@code input} is not empty</p>
     *                  <p>{@code false} if {@code input} is empty</p>
     */
    public static boolean checkInput(String input, DatabaseHandler db) {
        // Check user input
        if (isInputEmpty(input))
            return false;
        checkInputForQuit(input, db);
        return true;
    }

    /**
     * 
     * 
     * @param input     User Input
     * @param db        Holds database connection information
     * @return
     */
    public static boolean checkInputAllowEmpty(String input, DatabaseHandler db) {
        // Check user input
        checkInputForQuit(input, db);
        return true;
    }

    private static boolean isInputEmpty(String input) {
        if (input.isBlank()) {
            System.out.println(Messages.inputIsEmptyErrorMessage() + "\n");
            return true;
        } else
            return false;
    }

    /**
     * Closes database connection and exits program if input is "quit"
     * in any letter casing.
     * 
     * @param input     User input
     * @param db        Holds database connection information
     */
    private static void checkInputForQuit(String input, DatabaseHandler db) {
        if (input.toLowerCase().equals("quit")) {
            System.out.println("\n" + Messages.exitMessage() + "\n\n");
            db.closeDBConnection();
            System.exit(0);
        }
    }
}