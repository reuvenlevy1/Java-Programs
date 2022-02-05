import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * The {@code Main} class is the driver for the program.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class Main {
    /**
     * {@code Scanner} object that takes all user input for the entire program.
     */
    public static final Scanner userInput = new Scanner(System.in);

    /**
     * The main driver method for program.
     * 
     * @param args      Default parameters for method
     */
    public static void main(String[] args) {
        // Read in user settings
        ReadINIFile ini = new ReadINIFile();
        try {
            ini.readUserCSV();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Connect to DB
        DatabaseHandler db = new DatabaseHandler();

        while (true) {
            SignIn signIn = new SignIn(db);
            
           
            //FIXME - Complete User Stats
                // Stats should ignore any transactions that have a "transaction_type" that says "New Account".

            
            //FIXME - Add Admin ability to see user stats based on a transaction number they place
                //--> Can also see stats across ALL accounts
                //HOW???? How do I query every single username that exists for their transactions in ANY effective way? I don't think it's possible. Any query of this magnitude would be too much.
                //--> It would make more sense to create a new DB table (all_transactions) that adds to a collective "Withdraw", "Deposit" transaction list (same as for individual user tables) whenever 1 has been done by a user.
                    

            //FIXME - Add admin ability to NOT be kicked out of ATM after every transaction
                // In Progress - Will need to finish during testing phase
            

            //FIXME - Add documentation above all method names
                    //--> Completed for Main.java, SignIn.java, ATMDataHandler.java, AdminATMMenu.java, ATMMenu.java, AccountsHandler.java

            //FIXME - what happens when you have database tables already created and you change the settings in the ini file. What happens to the data? does it change? create a new table? delete the table and replace it?
            //FIXME - test user transaction history
            //FIXME - test user delete an account
            //FIXME - delete all unecessary files, methods, variables, imports
            //FIXME - fix all errors/warnings that are fixable
            //FIXME - Add classes to appropriately named packages: With this, change all public classes in special classes to
                //protected where able
            //FIXME - fix all lines to a maximum of 120 characters, undo cutoffs until this point
                //Add an "enter" (newline) b/w the method name and the first line in the body after any method that continues past 1st line

            // Complete:
                // Added DB Error Message
                // Edited Error Message
                // Added no spaces to user requirements

        }
    }
}