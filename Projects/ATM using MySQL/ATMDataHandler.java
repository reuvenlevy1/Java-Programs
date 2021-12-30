/**
 * The {@code UserHandler} class handles information related to user
 * ATM data operations.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-29-2021
 */
public class ATMDataHandler {
    /**
     * Withdraws funds from user account.
     * 
     * @param amount    user input of amount to withdraw
     * @param balance   current balance of account
     * @return          new balance after withdrawal
     */
    public double withdraw(double amount, double balance) {
        return withdrawFromAccount(amount, balance);
    }

    /**
     * Private method that withdraws funds from user account.
     * 
     * @param amount    user input of amount to withdraw
     * @param balance   current balance of account
     * @return          new balance after withdrawal
     */
    private double withdrawFromAccount(double amount, double balance) {
        if (amount >= 0.01) {
            if (amount <= balance) {
                balance -= amount;
            } else
                System.out.println(Messages.withdrawExceededErrorMessage(DataHandler.beautifier(Double.toString(amount)),
                        DataHandler.beautifier(Double.toString(balance))));
        } else
            System.out.println(Messages.withdrawZeroErrorMessage());
        return -1;
    }

    /**
     * Deposits funds to user account.
     * 
     * @param amount    user input of amount to deposit
     * @param balance   current balance of account
     * @return          new balance after deposit
     */
    public double deposit(double amount, double balance) {
        return depositToAccount(amount, balance);
    }
    
    /**
     * Private method that deposits funds to user account.
     * 
     * @param amount    user input of amount to deposit
     * @param balance   current balance of account
     * @return          new balance after deposit
     */
    private double depositToAccount(double amount, double balance) {
        if (amount >= 0.01) {
            balance += amount;
        } else {
            System.out.println(Messages.depositZeroErrorMessage());
        }
        return -1;
    }

    // private double transfer(double amount) {
    // return 0.0;
    // }

    // private double accountStats() {
    // return 0.0;
    // }

}
