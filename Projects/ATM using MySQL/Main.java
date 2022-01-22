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
            // if (AdminATMMenu.accountAdded) //FIXME: fix this later
            // csv.checkAccountsCSV();
            // SignIn signIn = new SignIn(csv);
            SignIn signIn = new SignIn(db);
            
           
            //FIXME - Add Admin ability to see user stats based on a transaction number they place
                //--> Can also see stats across ALL accounts

            //FIXME - Complete User Stats

            //FIXME - Add admin ability to NOT be kicked out of ATM after every transaction
            


            //FIXME - Add documentation above all method names
                    //--> Completed for Main.java, SignIn.java, ATMDataHandler.java, AdminATMMenu.java, ATMMenu.java, AccountsHandler.java

            //FIXME - test user transaction history
            //FIXME - test user delete an account
            //FIXME - delete all unecessary files, methods, variables, imports
            //FIXME - fix all errors/warnings that are fixable
            //FIXME - Add classes to appropriately named packages: With this, change all public classes in special classes to
                //protected where able
            //FIXME - fix all lines to a maximum of 120 characters, undo cutoffs until this point
                //Add an "enter" (newline) b/w the method name and the first line in the body after any method that continues past 1st line
        }
    }
}