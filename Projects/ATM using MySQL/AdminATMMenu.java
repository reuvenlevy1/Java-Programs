import java.util.ArrayList;
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
        CHANGE_USER_PIN_NUM + ". " + CHANGE_USER_PIN,
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

                if (selection.equals(ADD_ACCOUNT_NUM) || selection.equals(ADD_ACCOUNT)) {
                    System.out.println(ADD_ACCOUNT.toUpperCase() + "\n"
                        + Messages.returnToATMMenuMessage());
                    boolean validRequirements = false;
                    boolean notDuplicate = false;
                    Map<String, String> newAccountDetails = new HashMap<>();

                    while (!validRequirements && !notDuplicate) {
                        validRequirements = false;
                        notDuplicate = false;
                        // Enter username
                        System.out.println(Messages.adminCreateUsernameMessage());
                        String username = Main.userInput.nextLine().trim();
                        
                        // Check admin input
                        if (username.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(username, db))
                            break;
                        System.out.println(Messages.adminCreatePINMessage());
                        String pin = Main.userInput.nextLine().trim();
                        
                        // Check admin input
                        if (pin.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(pin, db))
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
                    USER_TRANSACTION_HISTORY)) {
                    
                    System.out.println(USER_TRANSACTION_HISTORY.toUpperCase() + "\n"
                        + Messages.returnToATMMenuMessage());
                    
                    // Username for the transactions
                    String username = "";
                    String maxTransactionsNum = "";
                    boolean validUsernameRequirements = false;
                    boolean validTransNumRequirements = false;
                    // Map of errors for account details requirement checking
                    Map<String, Boolean> errorMap = new HashMap<>();
                    // Get list of usernames
                    ArrayList<String> userList = db.listUsernames();
                    // Convert ArrayList to String Array
                    String[] userArray = userList.toArray(new String[userList.size()]);
                    // Transaction history of the user
                    ArrayList<String[]> userTransHistory = new ArrayList<>();
                    
                    // Display list of users
                    Messages.listUsernamesMessage(userArray);
                    
                    while (true) {
                        // Ask the admin for a username for the transactions
                        Messages.transactionHistorySelectUserMessage();
                        username = Main.userInput.nextLine().trim();
                        
                        // Check admin input
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;
                        
                        if (DataHandler.checkInput(username, db)) {
                            // Check if it's a valid username
                            errorMap = ah.checkAccountRequirements(username, null);

                            // Check username and PIN requirements
                            if (errorMap.get("noErrors")) {
                                // Check if username exists from list of usernames
                                if (userList.stream().anyMatch(username::equalsIgnoreCase)) {
                                    validUsernameRequirements = true;
                                    break;
                                } else
                                    // Error message
                                    System.out.println(Messages.usernameErrorMessage() + "\n");
                            } else
                                // Error message
                                System.out.println(Messages.accountCheckRequirementsErrorMessage(
                                    errorMap, AccountsHandler.MIN_USERNAME_LEN,
                                    AccountsHandler.MAX_USERNAME_LEN, AccountsHandler.REQUIRED_PIN_LEN)
                                    + "\n");
                        }
                    }
                    
                    if (validUsernameRequirements) {
                        // Ask the admin for a number of transactions that should be returned
                        System.out.println(Messages.accountTransNumMessage());
                        while (true) {
                            maxTransactionsNum = Main.userInput.nextLine().trim();
                            // Check admin input
                            if (maxTransactionsNum.toLowerCase().equals(BACK.toLowerCase()))
                                break;
                            if (DataHandler.checkInputAllowEmpty(maxTransactionsNum, db)) {
                                // Check if maxTransactionsNum is a number
                                if (DataHandler.checkNumRequirements(maxTransactionsNum)) {             //FIXME: put an or statement to set a limit with this data?
                                    validTransNumRequirements = true;
                                    break;
                                } else
                                    // Error message
                                    System.out.println(Messages.invalidNumInput() + "\n");
                            }
                        }
                    }

                    if (validTransNumRequirements) {
                        if (!maxTransactionsNum.isEmpty())
                            // Use query to pull up to last maxTransactionNum records of user transaction data
                            userTransHistory = db.getUserTransactionHistory(username, Integer.parseInt(maxTransactionsNum));
                        else
                            // Use query to pull up to last DEFAULT_MAX_TRANS_NUM records of user transaction data
                            // userTransHistory = db.getUserTransactionHistory(accountDetails.get("username"), DEFAULT_MAX_TRANS_NUM);                          //FIXME: where do we keep DEFAULT_MAX_TRANS_NUM value? maybe all .ini file settings appear in a new class called "InitialSettings.java"
                            userTransHistory = db.getUserTransactionHistory(username, 30);                                                                      //FIXME: currently put 30 instead of variable. When above is fixed, delete this line
                            // Print transaction history starting with most recent
                            Messages.transactionHistoryMessage(userTransHistory);
                            System.out.println("\n" + Messages.exitMessage() + "\n\n");
                            break;
                    }
                } else if (selection.equals(CHANGE_USER_PIN_NUM) || selection.toLowerCase().equals(CHANGE_USER_PIN)) {
                    String username = "";
                    // Map of errors for account details requirement checking
                    Map<String, Boolean> errorMap = new HashMap<>();
                    // Get list of usernames
                    ArrayList<String> userList = db.listUsernames();
                    // Convert ArrayList to String Array
                    String[] userArray = userList.toArray(new String[userList.size()]);

                    // Display list of users
                    Messages.listUsernamesMessage(userArray);
                    
                    // Have a prompt to type in a username to change the PIN of
                    // Check if user account exists in the DB
                    // If the user account exists, type in new PIN
                    // Confirm message that has you retype the same PIN to save new PIN to DB or typing in "back" to go back to the main menu
                    
                    // Prompts in terminal
                    System.out.println(CHANGE_USER_PIN.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());

                    while (true) {
                        // Type username of account PIN you want to change
                        System.out.println(Messages.adminSelectUsernameToChangePIN());
                        username = Main.userInput.nextLine().trim();
                        
                        // Check admin input
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;
                        
                        if (DataHandler.checkInput(username, db)) {
                            // Check if it's a valid username
                            errorMap = ah.checkAccountRequirements(username, null);

                            // Check username requirements
                            if (errorMap.get("noErrors")) {
                                // Check if username exists from list of usernames
                                if (userList.stream().anyMatch(username::equalsIgnoreCase)) {
                                    break;
                                } else
                                    // Error message
                                    System.out.println(Messages.usernameErrorMessage() + "\n");
                            } else
                                // Error message
                                System.out.println(Messages.accountCheckRequirementsErrorMessage(
                                    errorMap, AccountsHandler.MIN_USERNAME_LEN,
                                    AccountsHandler.MAX_USERNAME_LEN, AccountsHandler.REQUIRED_PIN_LEN)
                                    + "\n");
                        }
                    }

                    // Check if user account exists in DB before proceeding //FIXME
                    if (db.verifyUser(username)) {
                        String newPIN = "";
                        boolean changePINIsValid = false;
                        
                        while (true) {
                            // Type new PIN for account
                            System.out.println(Messages.changePINToNewPINMessage());
                            newPIN = Main.userInput.nextLine().trim();

                            // Check admin input
                            if (newPIN.toLowerCase().equals(BACK.toLowerCase()))
                                break;
                            
                            if (DataHandler.checkInput(newPIN, db)) {
                                // Check if it's a valid PIN
                                errorMap = ah.checkAccountRequirements(null, newPIN);
                                
                                // Check if PIN requirements contained no errors
                                if (errorMap.get("noErrors")) {
                                    changePINIsValid = true;
                                    break;
                                } else {
                                    // Error message that lists requirement errors
                                    System.out.println(Messages.accountCheckRequirementsErrorMessage(errorMap,
                                    AccountsHandler.MIN_USERNAME_LEN, AccountsHandler.MAX_USERNAME_LEN,
                                    AccountsHandler.REQUIRED_PIN_LEN) + "\n");
                                }
                            }
                        }

                        // If new PIN entered is valid
                        while (changePINIsValid) {
                            // Retype changed PIN
                            System.out.println(Messages.retypePINToNewPINMessage());
                            String confirmUserPIN = Main.userInput.nextLine().trim();

                            // Check admin input
                            if (username.toLowerCase().equals(BACK.toLowerCase()))
                                break;
                            
                            if (DataHandler.checkInput(confirmUserPIN, db)) {
                                // Checks if admin input PIN matches confirmation input
                                if (confirmUserPIN.equals(newPIN)) {
                                    if (db.changeUserPIN(username, newPIN)) { //FIXME
                                        System.out.println("\n" + Messages.changePINSuccessMessage());
                                        break;
                                    } else
                                        // Error message
                                        System.out.println(Messages.changePINErrorMessage());
                                } else 
                                    // Error message saying the PINs don't match
                                    System.out.println(Messages.changePINConfirmFailMessage());
                                    break;
                            }
                        }
                    } else
                        // Error message saying the account name does not exist in the DB
                        System.out.println(Messages.adminSelectUsernameToChangePINDoesNotExist());
                    System.out.println("\n" + Messages.exitMessage() + "\n\n");
                    break;

                } else if (selection.equals(DELETE_ACCOUNT_NUM) || selection.toLowerCase().equals(DELETE_ACCOUNT)) {
                    String username = "";
                    // Map of errors for account details requirement checking
                    Map<String, Boolean> errorMap = new HashMap<>();
                    // Get list of usernames
                    ArrayList<String> userList = db.listUsernames();
                    // Convert ArrayList to String Array
                    String[] userArray = userList.toArray(new String[userList.size()]);

                    // Display list of users
                    Messages.listUsernamesMessage(userArray);

                    // Prompts in terminal
                    System.out.println(DELETE_ACCOUNT.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());

                    while (true) {
                        // Type username of account you want to delete
                        System.out.println(Messages.userAccountDeleteMessage());
                        username = Main.userInput.nextLine().trim();
                        
                        // Check admin input
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;
                        
                        if (DataHandler.checkInput(username, db)) {
                            // Check if it's a valid username
                            errorMap = ah.checkAccountRequirements(username, null);

                            // Check username requirements
                            if (errorMap.get("noErrors")) {
                                // Check if username exists from list of usernames
                                if (userList.stream().anyMatch(username::equalsIgnoreCase)) {
                                    break;
                                } else
                                    // Error message
                                    System.out.println(Messages.usernameErrorMessage() + "\n");
                            } else
                                // Error message
                                System.out.println(Messages.accountCheckRequirementsErrorMessage(
                                    errorMap, AccountsHandler.MIN_USERNAME_LEN,
                                    AccountsHandler.MAX_USERNAME_LEN, AccountsHandler.REQUIRED_PIN_LEN)
                                    + "\n");
                        }
                    }

                    // Check if user account exists in DB before proceeding
                    if (db.verifyUser(username)) {
                        // Retype username to be deleted
                        System.out.println(Messages.retypeUserAccountDeleteMessage());
                        String confirmUsername = Main.userInput.nextLine().trim();
                        
                        // Check admin input
                        if (confirmUsername.toLowerCase().equals(BACK.toLowerCase()))
                            break;

                        if (DataHandler.checkInput(confirmUsername, db)) {
                            // Checks if username matches confirmation user input
                            if (confirmUsername.toLowerCase().equals(username)) {
                                // boolean on the status of the account being deleted
                                if (db.deleteUserAccount(username)) {
                                    System.out.println(Messages.accountDeletedMessage(username));
                                    break;
                                } else
                                    // Error message
                                    System.out.println(Messages.accountDeletionErrorMessage(username));
                            } else {
                                // Error message
                                System.out.println(Messages.accountDeleteConfirmFailMessage());
                                break;
                            }
                        }
                        System.out.println("\n" + Messages.exitMessage() + "\n\n");
                        break;
                    } else
                        // Error message saying the account name does not exist in the DB
                        System.out.println(Messages.adminSelectUsernameToChangePINDoesNotExist());
                    System.out.println("\n" + Messages.exitMessage() + "\n\n");
                    break;

                /*  OLD CODE
                    System.out.println(DELETE_ACCOUNT.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    // FIXME: Finish implementation
                    // List all available usernames
                    db.listUsernames(); // FIXME: Need to add this to a single table with a title set in Messages class: "List of Current Users"

                    // Confirm account deletion
                    System.out.print(Messages.userAccountDeleteMessage());
                    String confirmUserInput = Main.userInput.nextLine().trim();
                    
                    // Check admin input
                    if (confirmUserInput.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(confirmUserInput, db))
                        break;

                    // Checks if username matches confirmation user input
                    if (confirmUserInput.toLowerCase().equals(accountDetails.get("username").toLowerCase())) {
                        // boolean on the status of the account being deleted
                        if (db.deleteUserAccount(accountDetails.get("username")))
                            System.out.println(Messages.accountDeletedMessage(accountDetails.get("username")));
                        else
                            // Error message
                            System.out.println(Messages.accountDeletionErrorMessage(accountDetails.get("username")));
                    } else
                        // Error message
                        System.out.println(Messages.accountDeleteConfirmFailMessage());
                    System.out.println("\n" + Messages.exitMessage() + "\n\n");
                    break;
                */
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