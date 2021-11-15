import java.io.IOException;

/**
 * Prepares data for printing
 */
public class DataHandler {

    public static String beautifier(String myString) {
        //create an integer variable of the string
        // int iNum = (int)Double.parseDouble(myString);

        // Assure there's 2 digits after decimal
        myString = twoDecimalFormat(myString);
        // Remove comment character from string if exists
        myString = removeCommentChar(myString);

        return myString;
        /*
        //ORIGINAL CODE
        public class test {
            public static void main(String[] args) {
                int num = 985421;
                int leftover = 0;
                String pretty = "";
                while(num > 1000) {
                    System.out.println("current num = " + num);
                    leftover = num % 1000;
                    num /= 1000;
                    System.out.println("leftover = " + leftover);
                    System.out.println("num = " + num);
                    pretty = "," + leftover + pretty;
                    System.out.println(pretty);
                }
                pretty = num + pretty;
                System.out.println(pretty);    
            }
        }

        //NEW CODE
        public class HelloWorld{
            public static void main(String []args){
                String sNum = "1000.00";
                int num = (int)Double.parseDouble(sNum);
                
                // in final code, switch all "int"s and "long"s to just "double"
                // Code will work up to depositing 6 digits (999,999) Nothing greater.
                
                // int num = 1001;                      //works
                // int num = 1010;
                // int num = 999;                      // 3 digits, works
                // int num = 4120;                  // 4 digits, works
                // int num = 41201;                  // 5 digits, works
                // int num = 956456;                // 6 digits, Works
                // int num = 900000;
                // int num = 5481348;                  // 7 digits
                // int num = 108956456;         // 9 digits, Works
                // long num = 108956456423L;       // 12 digits, Works
                int digits = numOfDigits(num);
                int remainder = 0;
                int beginning = 0;
                // long remainder = 0L;
                // long beginning = 0L;
                String newNum = "";
                
                if (digits < 4){
                    System.out.println(num);
                    System.exit(0);
                }
                
                while (digits > 3) {
                    remainder = num % 1000;
                    System.out.println("remainder: " + remainder);
                    beginning = num / 1000;
                    System.out.println("beginning: " + beginning);
                    System.out.println("# of digits: " + digits);
                    // if (digits == 4) {
                    //     if (remainder == 0 && digits == 3){
                    //         newNum += beginning + "," + remainder + "00";
                    //     } else {
                    //         newNum += beginning + "," + remainder + "00";
                    //     }
                        
                    //     System.out.println (newNum);
                    //  } else {
                    
                    if (num % 1000 == 0) {
                        newNum += "," + "000";
                        System.out.println("if 000: " + newNum);
                    } else {
                        if(numOfDigits(remainder) == 1) {
                            newNum += ",00" + remainder;
                            System.out.println("if numOfDig=1: " + newNum);    
                        } else if (numOfDigits(remainder) == 2) {
                            newNum += ",0" + remainder;
                            System.out.println("if numOfDig=2: " + newNum); 
                        } else if (numOfDigits(remainder) == 3) {
                            newNum += "," + remainder;
                            System.out.println("if numOfDig=3: " + newNum);
                        }
                    }
                    //  }
                    digits -= 3;
                    num = beginning;
                }
                newNum = beginning + newNum;
                System.out.print("Final num: " + newNum + sNum.substring(sNum.length()-3));
            }
            
            // Uses Divide and Conquer method to figure out. No calculations or conversions are being performed, so this is the fastest method for receiving # of digits of a passed in integer
            private static int numOfDigits(int number) {
                if (number < 1000000) {          // 7 digits
                    if (number < 100) {         // 3 digits
                        if (number < 10) {
                            return 1;
                        } else {
                            return 2;
                        }
                    } else {
                        if (number < 1000) {        // 4 digits
                            return 3;
                        } else {
                            if (number < 10000) {   // 5 digits
                                return 4;
                            } else {
                                return 5;
                            }
                        }
                    }
                } else {
                    return 6;
                }
            }
        }
        */
        
    }

    /**
     * Assure String is in money format with 2 decimal places
     * 
     * @param myString: input string from chosen ATM Menu option 
     * @return
     */
    private static String twoDecimalFormat(String myString){
        // Check if string starts with a number and contains a decimal
        if (myString.matches("^[0-9].*."))
            // Check for 2 digits after decimal
            if (!myString.matches(".[0-9]") &&
            !myString.substring(myString.length()-3,myString.length()-2).equals("."))
                myString = myString+0;
        return myString;
    }

    /**
     * Removes the comment character from a string
     * 
     * @param myString: input string from chosen ATM Menu option data
     * @return
     */
    private static String removeCommentChar(String myString){
        // Remove comment character
        if (myString.substring(0,1).equals("#"))
            myString = myString.substring(1);
        return myString;
    }

    /**
     * Check if user input is valid for money transactions or contains invalid characters
     */
    public static boolean checkValidMoneyInput(String input) {
        if (input.toLowerCase().matches(".*[a-z].*")) return false;
        else return true;
    }

    /**
     * Closes csv file and exits program if input is "quit"
     * 
     * @param input: user input
     * @param csv: current accounts.csv object
     * @throws IOException
     */
    public static void checkInputForQuit(String input, CSVFileHandler csv) throws IOException {
        if (input.toLowerCase().equals("quit")) {
            System.out.println("\n"+Messages.exitMessage()+"\n\n");
            csv.fw.close();
            System.exit(0);
        }
    }
}