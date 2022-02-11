import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Controls the ATM options chosen by the user
 */
public class ATMMenu {
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
    final static String DELETE_ACCOUNT = "DELETE ACCOUNT";
    final static String SIGN_OUT = "Sign Out";
    // ATM number options
    final static String WITHDRAW_NUM = "1";
    final static String DEPOSIT_NUM = "2";
    final static String TRANSFER_NUM = "3";
    final static String TRANSACTION_HISTORY_NUM = "4";
    final static String ACCOUNT_STATS_NUM = "5";
    final static String DELETE_ACCOUNT_NUM = "6";
    final static String SIGN_OUT_NUM = "7";
    // Other keywords
    final static String BACK = "Back";
    final static String QUIT = "Quit";
    
    ATMMenu(Map<String, String> accountDetails, CSVFileHandler csv)
            throws FileNotFoundException, IOException {
        // Array of values to be added to <user.csv>: transactionID,transactionType,amount,balance
        String[] currentTransaction = new String[4];
        // ATM Transaction menu selection name
        String transactionType = "";
        // Logged in user's most recent transaction's balance from <user>.csv in USD
        Double balance = 0.0;
        // The user's input in USD
        Double amount = 0.0;

        while (!modifyBalance_flag) {
            Messages.atmMenuMessage();

            try {
                String selection = Main.userInput.nextLine().toLowerCase().trim();
                // check user input
                DataHandler.checkInputForQuit(selection, csv, CSVFileHandler.csvOpen);

                if (selection.equals(WITHDRAW_NUM) || selection.equals(WITHDRAW)) {
                    System.out.print(WITHDRAW.toUpperCase()+"\n"+Messages.returnToATMMenuMessage()+"\n");
                    transactionType = WITHDRAW;
                    modifyBalance_flag = false;
                    
                    if (csv.userRecordsList.size() > 1) {
                        // Most recent balance
                        balance = Double.parseDouble(csv.userRecordsList.get(1)[3]);
                        System.out.println(Messages.currentBalanceMessage(DataHandler.beautifier(balance.toString())));

                        while (!modifyBalance_flag) {
                            System.out.print(Messages.withdrawAmountMessage());
                            String s_amount = Main.userInput.nextLine().trim();
                            // Check user input
                            if (s_amount.toLowerCase().equals(BACK.toLowerCase())) break;
                            DataHandler.checkInputForQuit(s_amount, csv, true);
                            if (!DataHandler.checkValidMoneyInput(s_amount))
                                System.out.println(Messages.invalidTransactionInputMessage());
                            else {
                                // Set user values
                                amount = Double.parseDouble(s_amount);
                                balance = withdraw(amount, balance);
                            }
                        }
                    } else System.out.println(Messages.withdrawNewAccountMessage());
                } else if (selection.equals(DEPOSIT_NUM) || selection.equals(DEPOSIT)) {
                    System.out.print(DEPOSIT.toUpperCase()+"\n"+Messages.returnToATMMenuMessage()+"\n");
                    transactionType = DEPOSIT;
                    modifyBalance_flag = false;
                    
                    if (csv.userRecordsList.size() > 1) {
                        // Most recent balance
                        balance = Double.parseDouble(csv.userRecordsList.get(1)[3]);
                        System.out.println(Messages.currentBalanceMessage(DataHandler.beautifier(balance.toString())));
                    } else System.out.println(Messages.depositNewAccountMessage());

                    while (!modifyBalance_flag) {
                        System.out.print(Messages.depositAmountMessage());
                        String s_amount = Main.userInput.nextLine().trim();
                        // Check user input
                        if (s_amount.toLowerCase().equals(BACK.toLowerCase())) break;
                        DataHandler.checkInputForQuit(s_amount, csv, true);
                        if (!DataHandler.checkValidMoneyInput(s_amount))
                            System.out.println(Messages.invalidTransactionInputMessage());
                        else {
                            // Set user values
                            amount = Double.parseDouble(s_amount);
                            balance = deposit(amount, balance);
                        }
                    }
                } else if (selection.equals(TRANSFER_NUM) || selection.equals("transfer")) {
                    System.out.print("TRANSFER\n"+Messages.returnToATMMenuMessage()+"\n");
                    // Take you to TRANSFER
                    // transfer(amount);
                    System.out.println(Messages.atmMenuIncompleteMessage());
                    break;
                } else if (selection.equals(TRANSACTION_HISTORY_NUM) || selection.equals(TRANSACTION_HISTORY)) {
                    System.out.print(TRANSACTION_HISTORY.toUpperCase()+"\n"+Messages.returnToATMMenuMessage()+"\n");
                    Printer.printMultipleColTable(csv.userRecordsList);
                    System.out.print("\n");
                    System.out.println(Messages.exitMessage()+"\n\n");
                    break;
                } else if (selection.equals(ACCOUNT_STATS_NUM) || selection.equals(ACCOUNT_STATS)) {
                    System.out.print(ACCOUNT_STATS.toUpperCase()+"\n"+Messages.returnToATMMenuMessage()+"\n");
                    //Take you to ACCOUNT_STATS
                    System.out.println(Messages.atmMenuIncompleteMessage());
                    break;
                } else if (selection.equals(DELETE_ACCOUNT_NUM) || selection.equals(DELETE_ACCOUNT)) {
                    //DELETE ACCOUNT
                    //Bring you to a confirmation page that will require you to type in your username to delete
                    //Gives a successfully deleted account message
                    System.out.print(DELETE_ACCOUNT.toUpperCase()+"\n"+Messages.returnToATMMenuMessage()+"\n");
                    System.out.println(Messages.accountDeleteConfirmationMessage());
                    String confirmUserInput = Main.userInput.nextLine().trim();
                    // Check user input
                    if (confirmUserInput.toLowerCase().equals(BACK.toLowerCase())) break;
                    DataHandler.checkInputForQuit(confirmUserInput, csv, true);

                    // Checks if username matches confirmation user input
                    if (confirmUserInput.equals(accountDetails.get("username"))) {
                        csv.removeFromAccountsCSV(accountDetails);
                        System.out.println(Messages.accountDeletedMessage(accountDetails));
                    } else {
                        System.out.println(Messages.accountDeleteConfirmFailMessage());
                    }
                    System.out.println(Messages.exitMessage());
                    break;
                } else if (selection.equals(SIGN_OUT_NUM) || selection.equals(SIGN_OUT)) {
                    //SIGN OUT from account
                    System.out.println(SIGN_OUT.toUpperCase()+"\n"+Messages.signOutMessage()+"\n");
                    break;
                } else {
                    //Error message
                    System.out.println(Messages.atmMenuInvalidChoiceMessage());
                }
            } catch (InputMismatchException e) {
                System.out.println(Messages.atmMenuInvalidChoiceMessage());
                Main.userInput.nextLine().trim();
            }
        }
        
        if (modifyBalance_flag) {
            // Add new transaction: transactionID,transactionType,amount,balance
            // TransactionID will always be 1 as it will be most recent
            currentTransaction[0] = "1";
            currentTransaction[1] = transactionType.toLowerCase();
            currentTransaction[2] = amount.toString();
            currentTransaction[3] = balance.toString();
            
            // Update <user>.csv with new transaction details
            csv.addToUserCSV(accountDetails, currentTransaction);
            // Confirmation message
            System.out.println(Messages.transactionCompleteMessage(DataHandler.beautifier(balance.toString())));
            System.out.println("\n"+Messages.exitMessage()+"\n\n");
            csv.fw.close();
        }
    }
    
    /**
     * 
     * @param amount
     * @param balance
     * @return
     */
    private double withdraw(double amount, double balance) {
        if (amount <= balance) {
            balance -= amount;
            modifyBalance_flag = true;
        } else {
            System.out.println(Messages.withdrawErrorMessage(
                DataHandler.beautifier(Double.toString(amount)),
                DataHandler.beautifier(Double.toString(balance))));
        }
        return balance;
    }

    /**
     * 
     * @param amount
     * @param balance
     * @return
     */
    private double deposit(double amount, double balance) {
        if (amount >= 0.01) {
            balance += amount;
            modifyBalance_flag = true;
        } else {
            System.out.println(Messages.depositErrorMessage(
                DataHandler.beautifier(Double.toString(amount)),
                DataHandler.beautifier(Double.toString(balance))));
        }
        return balance;
    }

    // private double transfer(double amount) {
    //     return 0.0;
    // }

    // private double accountStats() {
    //     return 0.0;
    // }
}