import java.util.Map;

/**
 * This class contains all String messages for project
 */

public class Messages {
    public Messages() {
    }

    public static String greetMessage() {
        return "Welcome to Bank of Money!";
    }

    public static String exitMessage() {
        return "Thank you for choosing Bank of Money!";
    }

    public static String signInUsernameMessage() {
        return "Enter Username: ";
    }

    public static String signInPINMessage() {
        return "Enter PIN: ";
    }

    public static String adminCreateUsernameMessage() {
        return "Please type in the name of the user you wish to create:\nUsername: ";
    }

    public static String adminCreatePINMessage() {
        return "Please type in the user's 6 digit PIN:\nPIN: ";
    }

    public static String accountErrorMessage() {
        return "Error with Username or PIN. Please try again.";
    }

    public static String accountCheckDuplicateUserErrorMessage() {
        return "This username already exists. Please choose a different username.";
    }

    public static String accountCheckRequirementsErrorMessage() {
        return "Your account details do not fit the requirements. Username must be "
                + "alpha-numerical and less than 16 characters, and PIN must be 6 " + "digits. Please try again.";
    }

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

    public static String accountDeleteConfirmFailMessage() {
        return "Username was mistyped, account deletion was aborted.";
    }

    public static String accountDeleteConfirmationMessage() {
        return "Please type in your case-sensitive Username to confirm DELETING your account.";
    }

    public static String atmMenuIncompleteMessage() {
        return "This choice is currently being worked on. Please check again in the future.";
    }

    public static String atmMenuInvalidChoiceMessage() {
        return "You have chosen an invalid option. Please try again.";
    }

    public static String accountDeletedMessage(Map<String, String> accountDetails) {
        return "Your account " + accountDetails.get("username") + " has successfully been deleted.";
    }

    public static String withdrawAmountMessage() {
        return "Please enter the amount you would like to withdraw: $";
    }

    public static String withdrawErrorMessage(String amount, String balance) {
        return "Your withrawal amount: " + amount + "\nexceeds your current balance: " + balance
                + "\nPlease enter a new amount within your funds.";
    }

    public static String withdrawNewAccountMessage() {
        return "You just opened your account and don't have any funds yet! Please make a deposit first. ";
    }

    public static String depositAmountMessage() {
        return "Please enter the amount you would like to deposit: $";
    }

    public static String depositErrorMessage(String amount, String balance) {
        return "Please enter an amount greater than $0.00.";
    }

    public static String depositNewAccountMessage() {
        return "You just opened your account and don't have any funds yet. ";
    }

    public static String invalidTransactionInputMessage() {
        return "You have entered an invalid input. input must contain only numbers and at most 2 decimal places. "
                + "Please try again";
    }

    public static String currentBalanceMessage(String balance) {
        return "Your current balance is " + balance;
    }

    public static String transactionCompleteMessage(String balance) {
        return "Your new balance is: " + balance + "\nYour transaction has now been completed.";
    }

    public static String fileNotFoundExceptionMessage(String userCSV) {
        return "ERROR: The filename " + userCSV + " does not exist. Please make sure " + userCSV
                + " file was created successfully.";
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

    public static void atmMenuMessage() {
        System.out.println();
        String title = "Enter the name/number of the action:";
        String[] atmMenuOptions = new String[] { ATMMenu.WITHDRAW_NUM + ". " + ATMMenu.WITHDRAW,
                ATMMenu.DEPOSIT_NUM + ". " + ATMMenu.DEPOSIT, ATMMenu.TRANSFER_NUM + ". " + ATMMenu.TRANSFER,
                ATMMenu.TRANSACTION_HISTORY_NUM + ". " + ATMMenu.TRANSACTION_HISTORY,
                ATMMenu.ACCOUNT_STATS_NUM + ". " + ATMMenu.ACCOUNT_STATS,
                ATMMenu.DELETE_ACCOUNT_NUM + ". " + ATMMenu.DELETE_ACCOUNT,
                ATMMenu.SIGN_OUT_NUM + ". " + ATMMenu.SIGN_OUT };
        Printer.print1ColTable(title, atmMenuOptions);
    }

    public static void adminATMMenuMessage() {
        System.out.println();
        String title = "Enter the name/number of the action:";
        String[] atmMenuOptions = new String[] { AdminATMMenu.ADD_ACCOUNT_NUM + ". " + AdminATMMenu.ADD_ACCOUNT,
                // AdminATMMenu.DELETE_ACCOUNT_NUM+". "+AdminATMMenu.DELETE_ACCOUNT,
                AdminATMMenu.SIGN_OUT_NUM + ". " + AdminATMMenu.SIGN_OUT };
        Printer.print1ColTable(title, atmMenuOptions);
    }
}