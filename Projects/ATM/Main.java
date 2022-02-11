import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This is the driver class for the code
 */

// This code is currently setup to test the CSVFileHandler class and reading "accounts.csv" file
import java.util.Scanner;
public class Main {
    public static final Scanner userInput = new Scanner(System.in);
    public static void main(String[] args) throws FileNotFoundException, IOException {         
        // Generates the accounts.csv
        CSVFileHandler csv = new CSVFileHandler();
        csv.checkAccountsCSV();
        // csv.addToAccountsCSV(accountDetails);
        
        while (true) {
            if (AdminATMMenu.accountAdded)
                csv.checkAccountsCSV();
            SignIn signIn = new SignIn(csv);
        }
            // withdrawals and deposits, need to make a case for when user doesn't input an integer.

    }
}