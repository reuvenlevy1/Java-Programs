import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Controls the ATM options chosen by the user
 */

// Quit
// Withdraw
// Deposit
// Transfer
// Different Accounts (work on this after other functionality)
// Transaction History
// Maintain the last 30 transactions and use for statistical information
// Show transactions from greatest to smallest and have an option that does the
// reverse
// Show greatest withdrawl
// Show greatest deposit
// Account Stats (use last 30 transactions)
// Show the average withdrawn
// Show the average of Deposited
// Show percentage of money deposited vs overall funds at that time
// Show percentage of money withrawn vs overall funds at that time
// Percentage of withdraws vs deposits
// Settings?
public class ATMMenu {
    /**
     * When the balance has been changed by either withdrawal or deposit
     */
    boolean modifyBalance_flag = false;
    // ATM options
    final static String WITHDRAW = "Withdraw";
    final static String DEPOSIT = "Deposit";
    final static String TRANSFER = "Transfer";
    final static String TRANSACTION_HISTORY = "Transaction History";
    final static String ACCOUNT_STATS = "Account Stats";
    final static String DELETE_ACCOUNT = "DELETE ACCOUNT";
    final static String SIGN_OUT = "Sign Out";
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
                String selection = Main.userInput.nextLine().toLowerCase();
                // check user input
                if (selection.toLowerCase().equals(BACK.toLowerCase())) break;
                DataHandler.checkInputForQuit(selection, csv);

                //FIXEME: *****NEED TO CREATE a way of backing out of an option if chosen by mistake WITHOUT saving any transaction details
                if (selection.equals("1") || selection.toLowerCase().equals(WITHDRAW)) {
                    System.out.print(WITHDRAW.toUpperCase()+"\n"+Messages.returnToATMMenu()+"\n");
                    transactionType = WITHDRAW;
                    modifyBalance_flag = false;
                    // Most recent balance
                    balance = Double.parseDouble(csv.userRecordsList.get(1)[3]);
                    System.out.println(Messages.currentBalanceMessage(DataHandler.beautifier(balance.toString())));

                    while (!modifyBalance_flag) {
                        System.out.print(Messages.withdrawAmountMessage());
                        String s_amount = Main.userInput.nextLine();
                        // Check user input
                        if (s_amount.toLowerCase().equals(BACK.toLowerCase())) break;
                        DataHandler.checkInputForQuit(s_amount, csv);
                        if (!DataHandler.checkValidMoneyInput(s_amount))
                            System.out.println(Messages.invalidTransactionInputMessage());
                        else {
                            // Set user values
                            amount = Double.parseDouble(s_amount);
                            balance = withdraw(amount, balance);
                        }
                    }
                } else if (selection.equals("2") || selection.toLowerCase().equals("deposit")) {
                    System.out.print("DEPOSIT\n"+Messages.returnToATMMenu()+"\n");
                    transactionType = "deposit";
                    modifyBalance_flag = false;
                    // Most recent balance
                    balance = Double.parseDouble(csv.userRecordsList.get(1)[3]);
                    System.out.println(Messages.currentBalanceMessage(DataHandler.beautifier(balance.toString())));

                    while (!modifyBalance_flag) {
                        System.out.print(Messages.depositAmountMessage());
                        String s_amount = Main.userInput.nextLine();
                        // check user input
                        if (s_amount.toLowerCase().equals(BACK.toLowerCase())) break;
                        DataHandler.checkInputForQuit(s_amount, csv);
                        if (!DataHandler.checkValidMoneyInput(s_amount))
                            System.out.println(Messages.invalidTransactionInputMessage());
                        else {
                            // Set user values
                            amount = Double.parseDouble(s_amount);
                            balance = deposit(amount, balance);
                        }
                    }
                } else if (selection.equals("3") || selection.toLowerCase().equals("transfer")) {
                    System.out.print("TRANSFER\n"+Messages.returnToATMMenu()+"\n");
                    // Take you to TRANSFER
                    // transfer(amount);
                    System.out.println(Messages.atmMenuIncompleteMessage());
                    break;
                } else if (selection.equals("4") || selection.toLowerCase().equals("transaction history")) {
                    System.out.print("TRANSACTION HISTORY\n"+Messages.returnToATMMenu()+"\n");
                    Printer.printMultipleColTable(csv.userRecordsList);
                    System.out.print("\n");
                    break;
                } else if (selection.equals("5") || selection.toLowerCase().equals("account stats")) {
                    System.out.print("ACCOUNT STATS\n"+Messages.returnToATMMenu()+"\n");
                    //Take you to ACCOUNT_STATS
                    System.out.println(Messages.atmMenuIncompleteMessage());
                    break;
                } else if (selection.equals("6") || selection.toLowerCase().equals("delete account")) {
                    //DELETE ACCOUNT
                    //Bring you to a confirmation page that will require you to type in your username to delete
                    //Gives a successfully deleted account message
                    System.out.print("DELETE ACCOUNT\n"+Messages.returnToATMMenu()+"\n");
                    System.out.println(Messages.accountDeleteConfirmationMessage());
                    String confirmUserInput = Main.userInput.nextLine();
                    // check user input
                    if (confirmUserInput.toLowerCase().equals(BACK.toLowerCase())) break;
                    DataHandler.checkInputForQuit(confirmUserInput, csv);

                    // Checks if username matches confirmation user input
                    if (confirmUserInput.equals(accountDetails.get("username"))) {
                        csv.removeFromAccountsCSV(accountDetails);
                        System.out.println(Messages.accountDeletedMessage(accountDetails));
                    } else {
                        System.out.println(Messages.accountDeleteConfirmFailMessage());
                    }
                    System.out.println(Messages.exitMessage());
                    break;
                } else if (selection.equals("7") || selection.toLowerCase().equals("sign out")) {
                    //SIGN OUT from account
                    System.out.println("SIGN OUT");
                    break;
                } else {
                    //Error message
                    System.out.println(Messages.atmMenuInvalidChoice());
                }
            } catch (InputMismatchException e) {
                System.out.println(Messages.atmMenuInvalidChoice());
                Main.userInput.nextLine();
            }
        }
        
        if (modifyBalance_flag) {
            // Add new transaction: transactionID,transactionType,amount,balance
            // TransactionID will always be 1 as it will be the latest
            currentTransaction[0] = "1";
            currentTransaction[1] = transactionType.toLowerCase();
            currentTransaction[2] = DataHandler.beautifier(amount.toString());
            currentTransaction[3] = DataHandler.beautifier(balance.toString());
            
            // Update <user>.csv with new transaction details
            csv.addToUserCSV(accountDetails, currentTransaction);
            // Confirmation message
            System.out.println(Messages.transactionCompleteMessage(DataHandler.beautifier(balance.toString())));
            System.out.println("\n"+Messages.exitMessage()+"\n\n");
            csv.fw.close();
        }
    }
    
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