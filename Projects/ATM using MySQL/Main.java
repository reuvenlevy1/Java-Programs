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
        // Connect to DB
        Database db = new Database();
        // Generates the accounts.csv
        // CSVFileHandler csv = new CSVFileHandler();
        // csv.checkAccountsCSV();

        while (true) {
            // if (AdminATMMenu.accountAdded) //FIXME: fix this later
            // csv.checkAccountsCSV();
            // SignIn signIn = new SignIn(csv);
            SignIn_new signIn = new SignIn_new(db);                             //FIXME - make sure to check that username goes into database case-sensitively, but all comparisons are done case-insensitively (set to lowercase comparison)
        }
    }
}