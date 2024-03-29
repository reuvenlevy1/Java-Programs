import java.util.ArrayList;
import java.util.Map;

/**
 * The {@code Messages} class contains all String messages for program.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */

public class Messages {
    public Messages() {}

    public static String dbConnectionMessage() {
        return "Database connection is successful\n";
    }
    
    public static String greetMessage() {
        return "Welcome to Bank of Money!";
    }

    public static String exitMessage() {
        return "Thank you for choosing Bank of Money!";
    }

    public static String databaseConnectionErrorMessage() {
        return "Error connecting to the database. Please make sure your database details are set correctly in "
            + "the atm_details.ini file and that your database is properly installed and running. Then try "
            + "running the program again.";
    }

    public static String signInUsernameMessage() {
        return "Enter Username: ";
    }

    public static String signInPINMessage() {
        return "Enter PIN: ";
    }

    public static String adminCreateUsernameMessage() {
        return "Please type in the name of the user you wish to create: ";
    }

    public static String adminCreatePINMessage() {
        return "Please type in the user's 6 digit PIN: ";
    }

    public static String accountErrorMessage() {
        return "Error with Username and/or PIN. Please try again.";
    }

    public static String usernameErrorMessage() {
        return "Username does not exist. Please try again.";
    }

    public static String accountCheckDuplicateUserErrorMessage() {
        return "This username already exists. Please choose a different username.";
    }

    /**
     * <include all of the errors in documentation explanation that it checks for to build this error message>
     * 
     * @param errorMap      Map of failed requirements:
     *                      <p> Key is {@code String} of the requirement name
     *                      <p> Value is {@code Boolean} where {@code true} is failing the requirement
     * 
     * @param minUserLen    
     * @param maxUserLen    
     * @param reqPINLen     
     * @return              Built String of errors
     */
    public static String accountCheckRequirementsErrorMessage(Map<String, Boolean> errorMap,
        int minUserLen, int maxUserLen, int reqPINLen) {
        
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append("Your account details do not meet the following requirements:");
        
        // If username contains an invalid character
        if (errorMap.get("usernameInvalidChar"))
            errorMessage.append("\n   -Username must only be alpha-numerical characters with no "
                + "special characters or spaces");
        // If username doesn't start with a letter
        if (errorMap.get("usernameFirstCharNotLetter"))
            errorMessage.append("\n   -Username must start with a letter");
        // If username is under the minimum character length
        if (errorMap.get("usernameUnderMinLen"))
            errorMessage.append("\n   -Username must be greater than " + minUserLen
                + " characters");
        // If username is over the maximum character length
        if (errorMap.get("usernameOverMaxLen"))
            errorMessage.append("\n   -Username must be less than " + maxUserLen
                + " characters");
        // If PIN contains an invalid character
        if (errorMap.get("pinInvalidChar"))
            errorMessage.append("\n   -PIN must only be numerical with no other characters");
        // If PIN length is not the required digit length
        if (errorMap.get("pinNotReqLen"))
            errorMessage.append("\n   -PIN must be exactly " + reqPINLen + " digits");
        errorMessage.append("\nPlease try again.");

        return errorMessage.toString();
    }

    /**
     * FIXME
     * 
     * @return
     */
    public static String accountValidMessage() {
        return "Welcome into your account.";
    }

    public static String adminAccountValidMessage() {
        return "Welcome into your admin account!";
    }

    public static String adminAccountCreatedMessage() {
        return "Account has been successfully created.";
    }

    public static String generalErrorMessage() {
        return "Something went wrong!";
    }

    public static String accountDeleteConfirmationMessage() {
        return "Please type in your username to confirm DELETING your account: ";
    }

    public static String userAccountDeleteMessage() {
        return "Please type in the username of the account you wish to DELETE: ";
    }

    public static String retypeUserAccountDeleteMessage() {
        return "Please retype in the username of the account you wish to DELETE: ";
    }

    public static String accountDeleteConfirmFailMessage() {
        return "Username was mistyped, account deletion was aborted.";
    }

    /**
     * FIXME
     * 
     * @param username
     * @return
     */
    public static String accountDeletedMessage(String username) {
        return "Your account " + username + " has successfully been deleted.";
    }

    /**
     * FIXME
     * 
     * @param username
     * @return
     */
    public static String accountDeletionErrorMessage(String username) {
        return "Your account " + username + "could not be successfully deleted at this time. Please try again.";
    }

    public static String changePINConfirmationMessage() {
        return "Please type in your current PIN to confirm changing it: ";
    }

    public static String changePINConfirmFailMessage() {
        return "PIN was mistyped, account PIN change was aborted.";
    }

    public static String adminSelectUsernameToChangePIN() {
        return "Please type in the username of the account you wish change the PIN: ";
    }

    public static String adminSelectUsernameToChangePINDoesNotExist() {
        return "The account username does not exist.";
    }

    public static String changePINToNewPINMessage() {       //FIXME - finish implementing into ATMMenu.java
        return "Please type in your new PIN now: ";
    }

    public static String retypePINToNewPINMessage() {
        return "Please retype in your new PIN now: ";
    }

    public static String changePINSuccessMessage() {
        return "Your PIN has successfully been changed.";
    }

    public static String changePINErrorMessage() {
        return "Your PIN could not be successfully changed at this time. Please try again.";
    }

    public static String atmMenuInvalidChoiceMessage() {
        return "You have chosen an invalid option. Please try again.";
    }

    public static String withdrawAmountMessage() {
        return "Please enter the amount you would like to withdraw: $";
    }

    /**
     * FIXME
     * 
     * @param amount
     * @param balance
     * @return
     */
    public static String withdrawExceededErrorMessage(String amount, String balance) {
        return "Your withrawal amount: " + amount + "\nexceeds your current balance: " + balance
                + "\nPlease enter a new amount within your funds.";
    }

    public static String withdrawZeroErrorMessage() {
        return "Please enter an amount greater than $0.00";
    }

    public static String withdrawNewAccountMessage() {
        return "You just opened your account and don't have any funds yet! Please make a deposit first. ";
    }

    /**
     * 
     * 
     * @param username
     * @return
     */
    public static String withdrawEmptyDBMessage(String username) {
        return "A fatal error. There are no transaction data associated with your account, " + username
            + ". This must have been manually deleted from our database. Please first make a deposit and "
            + "try again.";
    }

    public static String depositAmountMessage() {
        return "Please enter the amount you would like to deposit: $";
    }

    public static String depositZeroErrorMessage() {
        return "Please enter an amount greater than $0.00";
    }

    public static String depositNewAccountMessage() {
        return "You just opened your account and don't have any funds yet. ";
    }

    public static String invalidTransactionInputMessage() {
        return "You have entered an invalid input. Input must contain only numbers and at most 2 decimal places. "
                + "Please try again.";
    }

    /**
     *
     * 
     * @param balance
     * @return
     */
    public static String currentBalanceMessage(String balance) {
        return "Your current balance is " + balance;
    }

    /**
     * 
     * 
     * @param balance
     * @return
     */
    public static String transactionCompleteMessage(String balance) {
        return "Your new balance is: " + balance + "\nYour transaction has now been completed.";
    }

    public static String transactionHistorySelectUserMessage() {
        return "Please type in the username of the account you wish to see the transactions for:";
    }
    
    public static String accountTransNumMessage() {
		return "Please type in the maximum number of transactions you wish to see or press \"Enter\" for default:";
	}

    public static String invalidNumInput() {
        return "Value must only be numerical with no other characters. Please try again.";
    }

    public static String inputIsEmptyErrorMessage() {
        return "You must enter a valid value. Please try again.";
    }

    public static String shutdownATMMessage() {
        return "Type in \"" + ATMMenu.QUIT.toLowerCase() + "\" at any time to shutdown ATM.";
    }

    public static String returnToATMMenuMessage() {
        return "Type in \"back\" to return to ATM Menu";
    }

    public static String signOutMessage() {
        return "You have successfully signed out of your account.";
    }

    /**
     * 
     * 
     * @param atmMenuOptions
     */
    public static void atmMenuMessage(String[] atmMenuOptions) {
        System.out.println();
        String title = "Enter the name/number of the action:";
        Printer.print1ColTable(title, atmMenuOptions);
    }

    /**
     * 
     * 
     * @param adminATMMenuOptions
     */
    public static void adminATMMenuMessage(String[] adminATMMenuOptions) {
        System.out.println();
        String title = "Enter the name/number of the action:";
        Printer.print1ColTable(title, adminATMMenuOptions);
    }

    /**
     * 
     * @param listUsernames
     */
    public static void listUsernamesMessage(String[] listUsernames) {
        System.out.println();
        String title = "These are a list of users:";
        Printer.print1ColTable(title, listUsernames);
    }

    /**
     * 
     * 
     * @param userTransHistory
     */
    public static void transactionHistoryMessage(ArrayList<String[]> userTransHistory) {
        String[] title = new String[]{"Transaction #", "Transaction Type", "Amount", "Balance"};
        // Add title to beginning of list
        userTransHistory.add(0, title);
        System.out.println("Transactions are listed from most recent to oldest.\n");
        Printer.printMultipleColTable(userTransHistory);
    }

    public static String fileNotFoundExceptionMessage(String INI_FILEPATH) {
        return "ERROR: The filename " + INI_FILEPATH + " does not exist. Please make sure "
            + INI_FILEPATH + " file was created successfully.";
    }

    /**
     * 
     * 
     * @param transArr
     */
    public static void accountStatsMessage(ArrayList<String[]> transArr) {
        String[] title = new String[]{"Deposit", "Withdrawal"};
        // Add title to beginning of list
        transArr.add(0, title);
        System.out.println("Account stats are listed below.\n");
        Printer.printMultipleColTable(transArr);
    }
}