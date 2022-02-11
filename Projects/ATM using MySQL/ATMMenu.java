import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * The {@code ATMMenu} class controls the ATM options chosen by the user.
 * These consist of 7 options:
 * <p>{@code 1. Withdraw}               Withdraw money from account
 * <p>{@code 2. Deposit}                Deposit money into account
 * <p>{@code 3. Transfer}               Transfer money from 1 account type to another
 * <p>{@code 4. Transaction History}    Get full transaction history
 * <p>{@code 5. Account Stats}          Get statistical data on account
 * <p>{@code 6. DELETE ACCOUNT}         Delete account
 * <p>{@code 7. Sign Out}               Sign out of user account
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class ATMMenu {
    /**
     * Holds database connection information and other database
     * related methods for executing queries.
     */
    DatabaseHandler db;

    /**
     * Holds ATM calculation methods
     */
    ATMDataHandler atmDH = new ATMDataHandler();

    /**
     * Holds account methods used for verifying user input
     */
    AccountsHandler ah;
    
    /**
     * When the balance has been changed by either withdrawal or deposit
     */
    boolean modifyBalance_flag = false;
    
    // ATM string options
    final static String WITHDRAW = "Withdraw";
    final static String DEPOSIT = "Deposit";
    final static String TRANSFER = "Transfer";
    final static String TRANSACTION_HISTORY = "Transaction History";
    final static String ACCOUNT_STATS = "Account Stats";
    final static String CHANGE_PIN = "Change PIN";
    final static String DELETE_ACCOUNT = "DELETE ACCOUNT";
    final static String SIGN_OUT = "Sign Out";
    // ATM number options
    final static String WITHDRAW_NUM = "1";
    final static String DEPOSIT_NUM = "2";
    final static String TRANSFER_NUM = "3";
    final static String TRANSACTION_HISTORY_NUM = "4";
    final static String ACCOUNT_STATS_NUM = "5";
    final static String CHANGE_PIN_NUM = "6";
    final static String DELETE_ACCOUNT_NUM = "7";
    final static String SIGN_OUT_NUM = "8";
    // Other keywords
    final static String BACK = "Back";
    final static String QUIT = "Quit";
    /**
     * A list of all ATM menu options.
     */
    final static String[] atmMenuOptions = new String[] {      //FIXME - Add options here when created
        WITHDRAW_NUM + ". " + WITHDRAW,
        DEPOSIT_NUM + ". " + DEPOSIT,
        TRANSFER_NUM + ". " + TRANSFER,                         //FIXME - Not done
        TRANSACTION_HISTORY_NUM + ". " + TRANSACTION_HISTORY,
        ACCOUNT_STATS_NUM + ". " + ACCOUNT_STATS,               //FIXME - Not done
        CHANGE_PIN_NUM + ". " + CHANGE_PIN,
        DELETE_ACCOUNT_NUM + ". " + DELETE_ACCOUNT,
        SIGN_OUT_NUM + ". " + SIGN_OUT};
    
    /**
     * Default maximum past transactions to show user 
     */
    final static int DEFAULT_MAX_TRANS_NUM = 30;            //FIXME: This should be set by the atm_details.ini file

    /**
     * Constructor that runs user ATM menu options.
     * 
     * @param accountDetails    {@code Map} that holds username and pin:
     *                          <p>{@code accountDetails["username"]}
     *                          <p>{@code accountDetails["pin"]}
     * @param db                Holds database connection information and other database
     *                          related methods for executing queries.
     */
    ATMMenu(Map<String, String> accountDetails, DatabaseHandler db, AccountsHandler ah) {
        this.db = db;
        int transactionID=1;
        // ATM Transaction menu selection name
        String transactionType = "";
        // Logged in user's most recent transaction's balance from <user>.csv in USD
        Double balance = 0.0;
        // The user's input in USD
        Double amount = 0.0;

        while (!modifyBalance_flag) {
            Messages.atmMenuMessage(atmMenuOptions);

            try {
                String selection = Main.userInput.nextLine().toLowerCase().trim();

                if (selection.equals(WITHDRAW_NUM) || selection.equals(WITHDRAW)) {
                    System.out.println(WITHDRAW.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    transactionType = WITHDRAW;
                    modifyBalance_flag = false;

                    // Use query to pull latest user transaction data
                    String userBalance = db.getLatestUserBalance(accountDetails.get("username"));
                    
                    // Compare data to being greater than 1
                    if (userBalance.length() > 1) {
                        balance = Double.parseDouble(userBalance);
                        
                        if (balance > 0) {
                            System.out.println(Messages.currentBalanceMessage(
                                DataHandler.beautifier(balance.toString())));
                        
                            while (!modifyBalance_flag) {
                                System.out.print(Messages.withdrawAmountMessage());
                                String s_amount = Main.userInput.nextLine().trim();
                                
                                // Check user input
                                if (s_amount.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(s_amount, db))
                                    break;
                                
                                if (DataHandler.checkValidMoneyInput(s_amount)) {
                                    // Set user values
                                    amount = Double.parseDouble(s_amount);
                                    balance = atmDH.withdraw(amount, balance);
                                    // balance was successfully changed
                                    if (balance >= 0)
                                        modifyBalance_flag = true;
                                } else
                                    System.out.println(Messages.invalidTransactionInputMessage());
                            }
                        } else
                            System.out.println(Messages.withdrawNewAccountMessage());
                    } else
                        System.out.println(Messages.withdrawEmptyDBMessage(accountDetails.get("username")));
                } else if (selection.equals(DEPOSIT_NUM) || selection.equals(DEPOSIT)) {
                    System.out.println(DEPOSIT.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    transactionType = DEPOSIT;
                    modifyBalance_flag = false;

                    // Use query to pull latest user transaction data
                    String userBalance = db.getLatestUserBalance(accountDetails.get("username"));
                    
                    // Compare data to being greater than 1
                    if (userBalance.length() > 1) {
                        balance = Double.parseDouble(userBalance);
                        System.out.println(Messages.currentBalanceMessage(DataHandler.beautifier(balance.toString())));

                        while (!modifyBalance_flag) {
                            System.out.print(Messages.depositAmountMessage());
                            String s_amount = Main.userInput.nextLine().trim();
                            
                            // Check user input
                            if (s_amount.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(s_amount, db))
                                break;

                            if (!DataHandler.checkValidMoneyInput(s_amount))
                                System.out.println(Messages.invalidTransactionInputMessage());
                            else {
                                // Set user values
                                amount = Double.parseDouble(s_amount);
                                balance = atmDH.deposit(amount, balance);
                                // balance was successfully changed
                                if (balance >= 0)
                                    modifyBalance_flag = true;
                            }
                        }
                    } else
                        System.out.println(Messages.depositNewAccountMessage());
                } else if (selection.equals(TRANSFER_NUM) || selection.equals("transfer")) {
                    System.out.println("TRANSFER\n" + Messages.returnToATMMenuMessage());
                    // Take you to TRANSFER
                    // transfer(amount);
                    System.out.println(Messages.atmMenuIncompleteMessage());
                    break;
                } else if (selection.equals(TRANSACTION_HISTORY_NUM) || selection.equals(TRANSACTION_HISTORY)) {                                    //FIXME
                    System.out.println(TRANSACTION_HISTORY.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    // FIXME: Ask user for maxTransactionsNum --> Maximum number of transactions to return
                    // Use query to pull last DEFAULT_MAX_TRANS_NUM records of user transaction data
                    ArrayList<String[]> userTransHistory =
                        db.getUserTransactionHistory(accountDetails.get("username"), DEFAULT_MAX_TRANS_NUM);                                        //FIXME: Should only use the default IF no transaction was specified!
                    // Print transaction history starting with most recent
                    Messages.transactionHistoryMessage(userTransHistory);
                    System.out.println("\n" + Messages.exitMessage() + "\n\n");
                    break;
                } else if (selection.equals(ACCOUNT_STATS_NUM) || selection.equals(ACCOUNT_STATS)) {                        //FIXME: Currently working on this method   -   All this should do is return a table/message of all statistical information gathered
                    System.out.println(ACCOUNT_STATS.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    // Take you to ACCOUNT_STATS
                    System.out.println(Messages.atmMenuIncompleteMessage());
                    break;
                } else if (selection.equals(CHANGE_PIN_NUM) || selection.equals(CHANGE_PIN)) {
                    System.out.println(CHANGE_PIN.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    System.out.println(Messages.changePINConfirmationMessage());
                    String confirmUserInput = Main.userInput.nextLine().trim();
                    // Check user input
                    if (confirmUserInput.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(confirmUserInput, db))
                        break;

                    // Checks if username matches confirmation user input
                    if (confirmUserInput.equals(accountDetails.get("pin"))) {
                        while (true) {
                            // Get new PIN
                            System.out.println(Messages.changePINToNewPINMessage());
                            String newPIN = Main.userInput.nextLine().trim();
                            // Check user input
                            if (newPIN.toLowerCase().equals(BACK.toLowerCase()) || !DataHandler.checkInput(newPIN, db))
                                break;
                            // Map of errors for account details requirement checking
                            Map<String, Boolean> errorMap = new HashMap<>();
                            errorMap = ah.checkAccountRequirements(null, newPIN);
                            
                            // Check if PIN requirements contained no errors
                            if (errorMap.get("noErrors")) {
                                // boolean on the status of the account being deleted
                                if (db.changeUserPIN(accountDetails.get("username"), newPIN)) {
                                    System.out.println("\n" + Messages.changePINSuccessMessage());
                                    break;
                                } else
                                    // Error message
                                    System.out.println(Messages.changePINErrorMessage());
                            } else {
                                // Error message that lists requirement errors
                                System.out.println(Messages.accountCheckRequirementsErrorMessage(errorMap,
                                AccountsHandler.MIN_USERNAME_LEN, AccountsHandler.MAX_USERNAME_LEN,
                                AccountsHandler.REQUIRED_PIN_LEN) + "\n");
                            }
                        }
                    } else
                        // Error message
                        System.out.println(Messages.changePINConfirmFailMessage());
                    System.out.println("\n" + Messages.exitMessage() + "\n\n");
                    break;
                } else if (selection.equals(DELETE_ACCOUNT_NUM) || selection.equals(DELETE_ACCOUNT)) {
                    System.out.println(DELETE_ACCOUNT.toUpperCase() + "\n" + Messages.returnToATMMenuMessage());
                    // Confirm account deletion
                    System.out.print(Messages.accountDeleteConfirmationMessage());
                    String confirmUserInput = Main.userInput.nextLine().trim();
                    // Check user input
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
                } else if (selection.equals(SIGN_OUT_NUM) || selection.equals(SIGN_OUT)) {
                    // SIGN OUT from account
                    System.out.println(SIGN_OUT.toUpperCase() + "\n" + Messages.signOutMessage() + "\n");
                    break;
                } else
                    // Error message
                    System.out.println(Messages.atmMenuInvalidChoiceMessage());
            } catch (InputMismatchException e) {
                System.out.println(Messages.atmMenuInvalidChoiceMessage());
                Main.userInput.nextLine().trim();
            }
        }

        if (modifyBalance_flag) {
            db.addTransactionToUserTable(
                accountDetails.get("username"),
                transactionID,
                transactionType,
                amount,
                balance);
            db.addTransactionToEveryTransactionTable(               //FIXME: test!
                accountDetails.get("username"),
                transactionID,
                transactionType,
                amount,
                balance);
            // Confirmation message
            System.out.println(Messages.transactionCompleteMessage(DataHandler.beautifier(balance.toString())));
            System.out.println("\n" + Messages.exitMessage() + "\n\n");
        }
    }
}