import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Controls the ATM options chosen by the user
 */

// Quit
// Withdraw
// Deposit
// Transfer
// Different Accounts (work on this after other functionality)
// Transaction History
// Maintain the last 30 transactions and use for statistical information
// Show transactions from greatest to smallest and have an option that does the
// reverse
// Show greatest withdrawl
// Show greatest deposit
// Account Stats (use last 30 transactions)
// Show the average withdrawn
// Show the average of Deposited
// Show percentage of money deposited vs overall funds at that time
// Show percentage of money withrawn vs overall funds at that time
// Percentage of withdraws vs deposits
// Settings?
public class ATMMenu {
    /**
     * When the balance has been changed by either withdrawal or deposit
     */
    boolean modifyBalance_flag = false;
    
    ATMMenu(Map<String, String> accountDetails, CSVFileHandler csv)
            throws FileNotFoundException, IOException {
        // Array of values to be added to <user.csv>: transactionID,transactionType,amount,balance
        String[] currentTransaction = new String[4];
        // ATM Transaction menu selection name
        String transactionType = "";
        // Logged in user's most recent transaction's balance from <user>.csv in USD
        Double balance = 0.00;
        // The user's input in USD
        Double amount = 0.00;

        while (!modifyBalance_flag) {
            Main.msg.ATMMenuMessage();

            try {
                // int selection = Integer.parseInt(Main.userInput.nextLine());
                String selection = Main.userInput.nextLine().toLowerCase();
                if (selection.toLowerCase().equals("back")) break;

                /**
                 * *****NEED TO CREATE a way of backing out of an option if chosen by mistake WITHOUT saving any transaction details
                 */
                if (selection.equals("1") || selection.equals("withdraw")) {
                    System.out.print("WITHDRAW\n"+Main.msg.returnToATMMenu()+"\n");
                    transactionType = "withdrawal";
                    modifyBalance_flag = false;
                    // Most recent balance
                    balance = Double.parseDouble(csv.userRecordsList.get(1)[3]);
                    System.out.println(Main.msg.currentBalanceMessage(balance));

                    while (!modifyBalance_flag) {
                        System.out.print(Main.msg.withdrawAmountMessage());
                        String s_amount = Main.userInput.nextLine();
                        if (s_amount.toLowerCase().equals("back")) break;
                        amount = Double.parseDouble(s_amount);
                        balance = withdraw(amount, balance);
                    }
                } else if (selection.equals("2") || selection.equals("deposit")) {
                    System.out.print("DEPOSIT\n"+Main.msg.returnToATMMenu()+"\n");
                    transactionType = "deposit";
                    modifyBalance_flag = false;
                    // Most recent balance
                    balance = Double.parseDouble(csv.userRecordsList.get(1)[3]);
                    System.out.println(Main.msg.currentBalanceMessage(balance));

                    while (!modifyBalance_flag) {
                        System.out.print(Main.msg.depositAmountMessage());
                        String s_amount = Main.userInput.nextLine();
                        if (s_amount.toLowerCase().equals("back")) break;
                        amount = Double.parseDouble(s_amount);
                        balance = deposit(amount, balance);
                    }
                } else if (selection.equals("3") || selection.equals("transfer")) {
                    System.out.print("TRANSFER\n"+Main.msg.returnToATMMenu()+"\n");
                    //Take you to TRANSFER
                    // transfer(amount);
                    System.out.println(Main.msg.ATMMenuIncompleteMessage());
                    break;
                } else if (selection.equals("4") || selection.equals("transaction history")) {      //FIXME: Print this out in a pretty to read format
                                                                                                    // Create a new method in Printer.java > print4ColTable
                                                                                                    //Need to create a function for printing Title and then function for printing Columns to get everything in 1 table
                    System.out.print("TRANSACTION HISTORY\n"+Main.msg.returnToATMMenu()+"\n");
                    
                    Printer.printMultipleColTable(csv.userRecordsList);
                    // boolean newLineFlag = false;
                    // int i=0;
                    // int j=0;
                    // String[][] transArr = new String[][];
                    // for (String[] key : csv.userRecordsList) {
                    //     int count = 0;
                    //     if (newLineFlag) {
                    //         System.out.print("\n");
                    //     }
                    //     for (String subkey : key) {
                    //         System.out.print(subkey);                                               //FIXME: Instead of printing this out, put this into a 2D array and copy this over to the new function "Printer.printMultipleColTable()"
                    //                                                                                 // Read the code below which works in compiler: https://www.tutorialspoint.com/compile_java_online.php
                    //         if (count != csv.userRecordsList.get(0).length-1) {
                    //             System.out.print(",");
                    //         }
                    //         count++;
                    //     }
                    //     newLineFlag = true;
                    // }
                    // Print newline for formatting
                    System.out.print("\n");
                    break;
                } else if (selection.equals("5") || selection.equals("account stats")) {
                    System.out.print("ACCOUNT STATS\n"+Main.msg.returnToATMMenu()+"\n");
                    //Take you to ACCOUNT_STATS
                    System.out.println(Main.msg.ATMMenuIncompleteMessage());
                    break;
                } else if (selection.equals("6") || selection.equals("delete account")) {
                    //DELETE ACCOUNT
                    //Bring you to a confirmation page that will require you to type in your username to delete
                    //Gives a successfully deleted account message
                    System.out.print("DELETE ACCOUNT\n"+Main.msg.returnToATMMenu()+"\n");
                    System.out.println(Main.msg.accountDeleteConfirmationMessage());
                    String confirmUserInput = Main.userInput.nextLine();
                    if (confirmUserInput.toLowerCase().equals("back")) break;

                    // Checks if username matches confirmation user input
                    if (confirmUserInput.equals(accountDetails.get("username"))){
                        csv.removeFromAccountsCSV(accountDetails);
                        System.out.println(Main.msg.accountDeletedMessage(accountDetails));
                    } else {
                        System.out.println(Main.msg.accountDeleteConfirmFailMessage());
                    }
                    System.out.println(Main.msg.exitMessage());
                    break;
                } else if (selection.equals("7") || selection.equals("sign out")) {
                    //SIGN OUT from account
                    System.out.println("SIGN OUT");
                    break;
                } else {
                    //Error message
                    System.out.println(Main.msg.ATMMenuInvalidChoice());
                }
            } catch (InputMismatchException e) {
                System.out.println(Main.msg.ATMMenuInvalidChoice());
                Main.userInput.nextLine();
            }
        }
        
        if (modifyBalance_flag) {
            // Add new transaction: transactionID,transactionType,amount,balance
            // TransactionID will always be 1 as it will be the latest
            currentTransaction[0] = "1";
            currentTransaction[1] = transactionType;
            currentTransaction[2] = amount.toString();
            currentTransaction[3] = balance.toString();
            
            // Update <user>.csv with new transaction details
            csv.addToUserCSV(accountDetails, currentTransaction);
            // Confirmation message
            System.out.println(Main.msg.TransactionCompleteMessage(balance));
            System.out.println("\n"+Main.msg.exitMessage()+"\n\n");
            csv.fw.close();
        }
    }
    
    private double withdraw(double amount, double balance) {
        if (amount <= balance) {
            balance -= amount;
            modifyBalance_flag = true;
        } else {
            System.out.println(Main.msg.withdrawErrorMessage(amount, balance));
        }
        return balance;
    }

    private double deposit(double amount, double balance) {
        if (amount >= 0.01) {
            balance += amount;
            modifyBalance_flag = true;
        } else {
            System.out.println(Main.msg.depositErrorMessage(amount, balance));
        }
        return balance;
    }

    /**
     * Check user input for invalid characters
     */
    // Create method name here. Are some input strings? Are some ints? Handle this


    // private double transfer(double amount) {
    //     return 0.0;
    // }

    // private double accountStats() {
    //     return 0.0;
    // }
}



// Transition this code for "4. Transaction History", then can delete:
// import java.util.ArrayList;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;
// public class HelloWorld{
//     static final String TABLE_BORDER = "-";
    
//      public static void main(String []args){
//         // Data input for table
//         String title = "#transactionID,transactionType,amount,balance";
//         String first = "1,deposit,50.0,1100.0";
//         String second = "2,deposit,0.75,1050.0";
//         String third = "3,withdrawal,250.75,1049.25";
//         String fourth = "4,deposit,320.0,1300.0";
//         String fifth = "5,withdrawal,20.0,980.0";
        
//         // Create 2D transaction array (4 columns + title and n # of rows)
//         String[][] transArr = new String[][]{title.split(","), first.split(","), second.split(","), third.split(","), fourth.split(","), fifth.split(",")};
        
//         // Find maximum length of all columns
//         int[] maxColLens = maxLengthStringMultipleCol(transArr);
        
//         ArrayList<String> leftAlignFormat = new ArrayList<String>();
//         for (int i=0; i<maxColLens.length; i++){
//             if (i == maxColLens.length-1)
//                 leftAlignFormat.add("| %-"+maxColLens[i]+"s |%n");
//             else
//                 leftAlignFormat.add("| %-"+maxColLens[i]+"s ");
//         }

//         // All numbers in leftAlignFormat + 11
//         int maxDashes = 11;
//         for (int i=0; i<maxColLens.length; i++)
//             maxDashes += maxColLens[i];
        
//         String tableBorder = IntStream.range(0, maxDashes).mapToObj(i -> TABLE_BORDER).collect(Collectors.joining(""));
        
//         // printTable(transArr, leftAlignFormat.toString());
//         printTable(transArr, leftAlignFormat, tableBorder);
//      }
     
//      // Finds the maximum character length of each row value within a column
//      private static int[] maxLengthStringMultipleCol(String[][] myString){
//         int numOfCols = myString[0].length;
//         int longestString;
//         int[] maxLenInCol = new int[numOfCols];
        
//         for (int i=0; i<numOfCols; i++){
//             longestString = 0;
//             // Length of rows in the ith column
//             for (int j=0; j<myString.length; j++){
//                 if (myString[j][i].length() > longestString)
//                     longestString = myString[j][i].length();
//             }
//             maxLenInCol[i] = longestString;
//         }
//         return maxLenInCol;
//      }
     
//      // Prints a border of around a table of multiple columns
//     private static void printTable(String[][] transArr, ArrayList<String> leftAlignFormat, String tableBorder){
//         // Prints a border for title of table
//         System.out.format("+"+tableBorder+"+%n");
//         for (int i=0; i<transArr[0].length; i++){
//             System.out.format(leftAlignFormat.get(i), transArr[0][i]);
//         }
//         System.out.format("+"+tableBorder+"+%n");
        
//         // Prints body of table (row at a time)
//         for(int i=1; i<transArr.length; i++){
//             for (int j=0; j<transArr[0].length; j++){
//                 System.out.format(leftAlignFormat.get(j), transArr[i][j]);
//             }
//         }
//         System.out.format("+"+tableBorder+"+%n");
//      }
// }