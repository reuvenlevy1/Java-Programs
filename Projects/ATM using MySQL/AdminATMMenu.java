import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * The {@code AdminATMMenu} class controls the ATM options chosen by the admin.
 * These options consist of 4 method options:
 * <p>{@code 1. Add Account}                  Add a new user
 * <p>{@code 2. User Transaction History}     Get a user's full transaction history
 * <p>{@code 3. Change User PIN}              Change a user's PIN from a list of users     //FIXME: adding
 * <p>{@code 4. DELETE ACCOUNT}               Delete a user
 * <p>{@code 5. Sign Out}                     Sign out of admin account
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */

public class AdminATMMenu {
    /**
     * Holds database connection information and other database
     * related methods for executing queries.
     */
    DatabaseHandler db;

    /**
     * Holds account methods used for verifying user input.
     */
    AccountsHandler ah;

    // ATM string options
    final static String ADD_ACCOUNT = "Add Account";
    final static String USER_TRANSACTION_HISTORY = "User Transaction History";
    final static String CHANGE_USER_PIN = "Change User PIN";
    final static String DELETE_ACCOUNT = "DELETE ACCOUNT";
    final static String SIGN_OUT = "Sign Out";
    // ATM number options
    final static String ADD_ACCOUNT_NUM = "1";
    final static String USER_TRANSACTION_HISTORY_NUM = "2";
    final static String CHANGE_USER_PIN_NUM = "3";
    final static String DELETE_ACCOUNT_NUM = "4";
    final static String SIGN_OUT_NUM = "5";
    // Other keywords
    final static String BACK = "Back";
    final static String QUIT = "Quit";
    /**
     * A list of all admin ATM menu options.
     */
    final static String[] adminATMMenuOptions = new String[] {        //FIXME - Add options here when created
        ADD_ACCOUNT_NUM + ". " + ADD_ACCOUNT,
        USER_TRANSACTION_HISTORY_NUM + ". " + USER_TRANSACTION_HISTORY,
        DELETE_ACCOUNT_NUM + ". " + DELETE_ACCOUNT,
        SIGN_OUT_NUM + ". " + SIGN_OUT};

    /**
     * Determines if an account has been added.
     */
    static boolean accountAdded = false;

    /**
     * Constructor that runs admin ATM menu options.
     * 
     * @param accountDetails    {@code Map} that holds username and pin:
     *                          <p>{@code accountDetails["username"]}
     *                          <p>{@code accountDetails["pin"]}
     * @param db                Holds database connection information and other database
     *                          related methods for executing queries.
     */
    AdminATMMenu(Map<String, String> accountDetails, DatabaseHandler db, AccountsHandler ah) {
        this.db = db;
        this.ah = ah;

        while (true) {
            Messages.adminATMMenuMessage(adminATMMenuOptions);

            try {
                String selection = Main.userInput.nextLine().toLowerCase().trim();
                // check user input
                DataHandler.checkInputForQuit(selection, db);

                if (selection.equals(ADD_ACCOUNT_NUM) || selection.equals(ADD_ACCOUNT)) {
                    System.out.print(ADD_ACCOUNT.toUpperCase() + "\n"
                        + Messages.returnToATMMenuMessage() + "\n");
                    boolean validRequirements = false;
                    boolean notDuplicate = false;
                    Map<String, String> newAccountDetails = new HashMap<>();

                    while (!validRequirements && !notDuplicate) {
                        validRequirements = false;
                        notDuplicate = false;
                        // Enter username
                        System.out.print(Messages.adminCreateUsernameMessage());
                        String username = Main.userInput.nextLine().trim();
                        // Check user input
                        DataHandler.checkInputForQuit(username, db);
                        
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;
                        System.out.print(Messages.adminCreatePINMessage());
                        String pin = Main.userInput.nextLine().trim();
                        // Check user input
                        DataHandler.checkInputForQuit(pin, db);
                        
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;

                        newAccountDetails.put("username", username);
                        newAccountDetails.put("pin", pin);

                        // Map of errors for account details requirement checking
                        Map<String, Boolean> errorMap = new HashMap<>();
                        errorMap = ah.checkAccountRequirements(username, pin);

                        // Check username and PIN requirements
                        if (errorMap.get("noErrors")) {
                            validRequirements = true;
                            
                            // Check if username is a duplicate
                            if (!db.checkDupUsers(username))
                                notDuplicate = true;
                            else
                                System.out.println(
                                    Messages.accountCheckDuplicateUserErrorMessage());
                        } else
                            System.out.println(Messages.accountCheckRequirementsErrorMessage(
                                errorMap, AccountsHandler.MIN_USERNAME_LEN,
                                AccountsHandler.MAX_USERNAME_LEN, AccountsHandler.REQUIRED_PIN_LEN)
                                + "\n");
                    }

                    if (validRequirements && notDuplicate) {
                        // Add new user to accounts.csv and create their own table
                        db.createUser(newAccountDetails.get("username"),
                            newAccountDetails.get("pin"));

                        // CSVFileHandler.csvOpen = true;
                        accountAdded = true;
                        System.out.println();
                        System.out.println(
                                Messages.adminAccountCreatedMessage() + "\n"
                                + Messages.exitMessage() + "\n\n");
                        break;
                    }
                } else if (selection.equals(USER_TRANSACTION_HISTORY_NUM) || selection.equals(
                    USER_TRANSACTION_HISTORY)) {                      //FIXME

                    // Change everything in here to match new method information
                    // SIGN OUT from account
                    // System.out.println(SIGN_OUT.toUpperCase() + "\n" + Messages.signOutMessage() + "\n");
                    // break;
                } else if (selection.equals(CHANGE_USER_PIN_NUM) || selection.toLowerCase().equals(CHANGE_USER_PIN)) {
                    // FIXME: Finish implementation
                    // List all available usernames
                    db.listUsernames();
                } else if (selection.equals(DELETE_ACCOUNT_NUM) || selection.toLowerCase().equals(DELETE_ACCOUNT)) {
                    System.out.print(DELETE_ACCOUNT.toUpperCase() + "\n" + Messages.returnToATMMenuMessage() + "\n");
                    // FIXME: Finish implementation
                    // List all available usernames
                    db.listUsernames();

                    // Confirm account deletion
                    System.out.print(Messages.accountDeleteConfirmationMessage());  //FIXME: Add a new message for choosing a username to delete
                    // String confirmUserInput = Main.userInput.nextLine().trim();
                    
                    // // Check user input
                    // if (confirmUserInput.toLowerCase().equals(BACK.toLowerCase()))
                    //     break;
                    // DataHandler.checkInputForQuit(confirmUserInput, db);

                    // // Checks if username matches confirmation user input
                    // if (confirmUserInput.toLowerCase().equals(accountDetails.get("username").toLowerCase())) {
                    //     // boolean on the status of the account being deleted
                    //     if (db.deleteUserAccount(accountDetails.get("username")))
                    //         System.out.println(Messages.accountDeletedMessage(accountDetails.get("username")));
                    //     else
                    //         // Error message
                    //         System.out.println(Messages.accountDeletionErrorMessage(accountDetails.get("username")));
                    // } else
                    //     // Error message
                    //     System.out.println(Messages.accountDeleteConfirmFailMessage());
                    // System.out.println("\n" + Messages.exitMessage() + "\n\n");
                    // break;
                } else if (selection.equals(SIGN_OUT_NUM) || selection.equals(SIGN_OUT)) {
                    // SIGN OUT from account
                    System.out.println(SIGN_OUT.toUpperCase() + "\n" + Messages.signOutMessage()
                        + "\n");
                    break;
                } else {
                    // Error message
                    System.out.println(Messages.atmMenuInvalidChoiceMessage());
                }
            } catch (InputMismatchException e) {
                System.out.println(Messages.atmMenuInvalidChoiceMessage());
                Main.userInput.nextLine();
            }
        }
    }
}