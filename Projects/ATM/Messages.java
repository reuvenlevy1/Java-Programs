import java.util.Map;

/**
 * This class contains all String messages for project
 */

public class Messages {
    public Messages(){}

    public static String greetMessage() {
        return "Welcome to Bank of Money!";
    }

    public static String exitMessage() {
        return "Thank you for choosing Bank of Money!";
    }

    public static String signInUsernameMessage() {
        return "Enter username: ";
    }

    public static String signInPINMessage() {
        return "Enter PIN: ";
    }

    public static String accountErrorMessage() {
        return "Error with username or PIN. Please try again.";
    }

    public static String accountCheckRequirementsErrorMessage() {
        return "Your account details do not fit the requirements. Please try again.";
    }

    public static String accountValidMessage() {
        return "Welcome into your account.";
    }

    public static String generalErrorMessage() {
        return "Something went wrong!";
    }

    public static String accountDeleteConfirmFailMessage() {
        return "Username was mistyped, account deletion was aborted.";
    }

    public static String accountDeleteConfirmationMessage() {
        return "Please type in your case-sensitive username to confirm DELETING your account.";
    }

    public static String atmMenuIncompleteMessage() {
        return "This choice is currently being worked on. Please check again in the future.";
    }

    public static String atmMenuInvalidChoice() {
        return "You have chosen an invalid option. Please try again.";
    }
    
    public static String accountDeletedMessage(Map<String,String>accountDetails) {
        return "Your account "+accountDetails.get("username")+" has successfully been deleted.";
    }

    public static String withdrawAmountMessage() {
        return "Please enter the amount you would like to withdraw: $";
    }

    public static String depositAmountMessage() {
        return "Please enter the amount you would like to deposit: $";
    }

    public static String withdrawErrorMessage(String amount, String balance) {
        return "Your withrawal amount: "+amount+"\nexceeds your current balance: "+balance
        +"\nPlease enter a new amount within your funds.";
    }

    public static String depositErrorMessage(String amount, String balance) {
        return "Your deposit amount: "+amount+"\nexceeds your current balance: "+balance
        +"\nPlease enter a new amount greater than $0.00.";
    }

    public static String invalidTransactionInputMessage() {
        return "You have entered an invalid input. input must contain only numbers and at most 2 decimal places."
        +"Please try again";
    }

    public static String currentBalanceMessage(String balance){
        return "Your current balance is $"+balance;
    }

    public static String transactionCompleteMessage(String balance) {
        return "Your new balance is: $"+balance+"\nYour transaction has now been completed.";
    }

    public static String fileNotFoundExceptionMessage(String userCSV) {
        return "ERROR: The filename "+userCSV+" does not exist. Please make sure "+userCSV
        + " file was created successfully.";
    }

    public static String shutdownATMMessage() {
        return "Type in \""+ATMMenu.QUIT.toLowerCase()+"\" at any time to shutdown ATM.";
    }

    public static String returnToATMMenu() {
        return "Type in 'back' to return to ATM Menu";
    }

    public static void atmMenuMessage() {
        String title = "Enter the name/number of the action:";
        String[] atmMenuOptions = new String[]{"1. "+ATMMenu.WITHDRAW, "2. "+ATMMenu.DEPOSIT, "3. "
        +ATMMenu.TRANSFER, "4. "+ATMMenu.TRANSACTION_HISTORY,"5. "+ATMMenu.ACCOUNT_STATS, "6. "
        +ATMMenu.DELETE_ACCOUNT, "7. "+ATMMenu.SIGN_OUT};
        
        Printer.print1ColTable(title, atmMenuOptions);
    }
}