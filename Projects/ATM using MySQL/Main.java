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
     * @param args                      Default parameters for method
     */
    public static void main(String[] args) {
        // Connect to DB
        DatabaseHandler db = new DatabaseHandler();
        // Generates the accounts.csv
        // CSVFileHandler csv = new CSVFileHandler();
        // csv.checkAccountsCSV();

        while (true) {
            // if (AdminATMMenu.accountAdded) //FIXME: fix this later
            // csv.checkAccountsCSV();
            // SignIn signIn = new SignIn(csv);
            SignIn signIn = new SignIn(db);
            
            //FIXME - Add the ability for users to change their PINS
            //FIXME - Add the ability for admin to view all list of users and to reset their PINS
            //FIXME - Add Admin feature to be able to delete account: include all accounts, have confirming deletion of account just
                //like in ATMMenu.java
            //FIXME - Add Admin feature to be able to see a user's complete transaction history or to specify a max limit of records
                //to return
            

            //FIXME - Add hashing to stored passwords in DB for security
            //FIXME - Add SQL.properties file that will allow configuration of database connection details to the DatabaseHandler.java class.
                    //--> Also allow configuration to some of the hardcoded variables, such as max/min length of characters for username & PIN

            //FIXME - Add documentation above all method names
                    //--> Completed for Main.java, SignIn.java, ATMDataHandler.java, AdminATMMenu.java, ATMMenu.java, AccountsHandler.java

            //FIXME - make sure to check that username goes into database case-sensitively, but all comparisons are done
                //case-insensitively (set to lowercase comparison)
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