import java.util.Map;

/**
 * This class contains all String messages for project
 */

public class Messages {
    // Errors messages
    // Screen options
    // Welcome text
    // Goodbye Text
    final String WITHDRAW = "Withdraw";
    final String DEPOSIT = "Deposit";
    final String TRANSFER = "Transfer";
    final String TRANSACTION_HISTORY = "Transaction History";
    final String ACCOUNT_STATS = "Account Stats";
    final String DELETE_ACCOUNT = "DELETE ACCOUNT";
    final String SIGN_OUT = "Sign Out";

    public Messages(){
        //Scanner sc = new Scanner(System.in);
        //String boner = sc.nextLine();
        //System.out.println(boner);
        //sc.close();
    }

    public String greetMessage() {
        return "Welcome to Bank of Money!";
    }

    public String exitMessage() {
        return "Thank you for choosing Bank of Money!";
    }

    public String signInUsernameMessage() {
        return "Enter username: ";
    }

    public String signInPINMessage() {
        return "Enter PIN: ";
    }

    public String accountErrorMessage() {
        return "Error with username or PIN. Please try again.";
    }

    public String accountCheckRequirementsErrorMessage() {
        return "Your account details do not fit the requirements. Please try again.";
    }

    public String accountValidMessage() {
        return "Welcome into your account.";
    }

    public String generalErrorMessage() {
        return "Something went wrong!";
    }

    public String accountDeleteConfirmFailMessage() {
        return "Username was mistyped, account deletion was aborted.";
    }

    public String accountDeleteConfirmationMessage() {
        return "Please type in your case-sensitive username to confirm DELETING your account.";
    }

    public String ATMMenuIncompleteMessage() {
        return "This choice is currently being worked on. Please check again in the future.";
    }

    public String ATMMenuInvalidChoice() {
        return "You have chosen an invalid option. Please try again.\n";
    }
    
    public String accountDeletedMessage(Map<String,String>accountDetails) {
        return "Your account "+accountDetails.get("username")+" has successfully been deleted.";
    }

    public String withdrawAmountMessage() {
        return "Please enter the amount you would like to withdraw: $";
    }

    public String depositAmountMessage() {
        return "Please enter the amount you would like to deposit: $";
    }

    public String withdrawErrorMessage(double amount, double balance) {
        return "Your withrawal amount: "+amount+"\nexceeds your current balance: "+balance+"\nPlease enter a new amount within your funds.";
    }

    public String depositErrorMessage(double amount, double balance) {
        return "Your deposit amount: "+amount+"\nexceeds your current balance: "+balance+"\nPlease enter a new amount greater than $0.00.";
    }

    public String currentBalanceMessage(double balance) {
        return "Your current balance is $"+balance;
    }

    public String TransactionCompleteMessage(double balance) {
        return "Your new balance is: $"+balance+"\nYour transaction has now been completed.";
    }

    public String fileNotFoundExceptionMessage(String userCSV) {
        return "ERROR: The filename "+userCSV+" does not exist. Please make sure "+userCSV
        + " file was created successfully.";
    }

    public String shutdownATMMessage() {
        return "Type in 'quit' to shutdown ATM.";
    }

    public String returnToATMMenu() {
        return "Type in 'back' to return to ATM Menu";
    }

    public void ATMMenuMessage() {
        String title = "Enter the name/number of the action:";
        String[] atmMenuOptions = new String[]{"1. "+WITHDRAW, "2. "+DEPOSIT, "3. "+TRANSFER, "4. "+TRANSACTION_HISTORY, "5. "+ACCOUNT_STATS, "6. "+DELETE_ACCOUNT, "7. "+SIGN_OUT,};
        
        Printer.print1ColTable(title, atmMenuOptions);
    }
}