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

            //FIXME - Add documentation above all method names
                //--> Finish:
                    //DBQueries.java 2 query map comment documentation
                    //StatsHandler.java, everything
                    //DatabaseHandler.java, most
                //--> Completed for Main.java, SignIn.java, ATMDataHandler.java, AdminATMMenu.java, ATMMenu.java, AccountsHandler.java, ReadINIFile.java, Printer.java
                    
            
            //FIXME - Complete User Stats
                // Stats should ignore any transactions that have a "transaction_type" that says "New Account".
            
            //FIXME: in adminATM, put checks for any options that require users to exist. If they don't exist, have a message telling them to create a user first

            //FIXME: Check .ini file for all values present, if any value is missing, come up with error message specific to what is missing (can use hashmap for all errors similar to what was done in Messaging class)
                //In the message mention that if they wish to generate a new .ini file then they must delete theirs first and a fresh one will be generated
                    //Have the ini file be created with all the default content if missing --> Create a new class for this? Already have an INI class for this? If so, place create .ini file method within that class

                    //Add this as a future feature within the readme:
                        // --> Have the .ini file be a settings instead that the user must configure when first logging into an account or creating an account that will have all the information for the .ini file.
                        // In here, the user should have control over what they can create and what they cannot.

            
            //FIXME - Add Admin ability to see user stats based on a transaction number they place
                //--> Can also see stats across ALL accounts
                //HOW???? How do I query every single username that exists for their transactions in ANY effective way? I don't think it's possible. Any query of this magnitude would be too much.
                //--> It would make more sense to create a new DB table (all_transactions) that adds to a collective "Withdraw", "Deposit" transaction list (same as for individual user tables) whenever 1 has been done by a user.
                    

            //FIXME - Add admin ability to NOT be kicked out of ATM after every transaction
                // In Progress - Will need to finish during testing phase
            

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