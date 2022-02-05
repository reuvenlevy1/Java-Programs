import java.util.Map;

// Stats Table should look like this:
//   Deposits        |        Withdrawals        |        VS
//  --------------------------------------------------------------------
//    Avg calls:     |     Avg. calls:           |    Avg. calls:
//      # (%)        |        # (%)              |     # (%)
//  --------------------------------------------------------------------
//    Avg $:         |     Avg. $:               |    Avg. $:
//      # (%)        |        # (%)              |     # (%)


/**
 * The {@code StatsHandler} class controls the statistical calculations of last given number of transactions.
 * This class consists of 3 major subjects of information
 * <p>{@code 1. Deposits}                    <li>Shows the average times a "Deposit" ATM transaction was called
 *                                           <li>Shows the percentage "Deposit" options chosen vs. overall transactions
 *                                           <li>Shows the average amount of money deposited
 *                                           <li>Shows the percentage of money deposited vs. current overall funds
 * <p>{@code 2. Withdrawals}                 <li>Shows the average times a "Withdraw" ATM transaction was called
 *                                           <li>Shows the percentage "Withraw" options chosen vs overall transactions
 *                                           <li>Shows the average amount of money withdrawn
 *                                           <li>Shows the percentage of money withrawn vs. current overall funds
 * <p>{@code 3. Deposits vs. Withdrawals}    <li>Shows the average times a "Withdraw" ATM transaction was called vs. "Deposit"
 *                                           <li>Shows the percentage times a "Withdraw" ATM transaction was called vs. "Deposit"
 *                                           <li>Shows the average amount of money withdrawn vs. deposited
                                             <li>Show overall funds withdawn and overall funds deposited next to each other
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Jan-23-2022
 */
public class StatsHandler {
    /**
     * Holds database connection information and other database
     * related methods for executing queries. 
     */
    DatabaseHandler db;

    public StatsHandler(DatabaseHandler db) {
        this.db = db;
    }

    /**
     * 
     * 
     * @param username
     * @param maxTransactionsNum
     */
    public void getUserStats(String username, String maxTransactionsNum) {              //FIXME: what should this method return (if anything)?
                                                                                            // --> Check the top of class to see how the table should look.
                                                                                            // --> Return whaetever data structure is needed for multicolumn table function to work
        // Calculate stats needed
        calculateDepositsStats(username, maxTransactionsNum);
        calculateWithdrawalsStats(username, maxTransactionsNum);
        calculateDepositsVsWithdrawalsStats(username, maxTransactionsNum);
    }

    /**
     * 
     * 
     * @param username
     * @param maxTransactionsNum
     */
    public void getAdminStats(String username, String maxTransactionsNum) {              //FIXME: what should this method return (if anything)?
        // --> Check the top of class to see how the table should look.
        // --> Return whaetever data structure is needed for multicolumn table function to work
        // Calculate stats needed
        calculateEveryDepositStats(username, maxTransactionsNum);                                                           //FIXME - Need to implement
        calculateEveryWithdrawalStats(username, maxTransactionsNum);                                                        //FIXME - Need to implement
        calculateEveryDepositVsWithdrawalsStats(username, maxTransactionsNum);                                              //FIXME - Need to implement
    }

    /**
     * 
     */
    private void calculateDepositsStats(String username, String maxTransactionsNum) {
        //------------------------------------------------------------------------------------------------------------------------------------------------------
        // Get total number of transactions and total number of deposits
        // Use query to pull up to last maxTransactionsNum records of user transaction type. Want both data to come back in 1 table of 2 column values.
        Map<String, String> transTypeDepositNumMap = db.getTransactionTypeNumMap(username, maxTransactionsNum, "deposit");
        // Print transaction history starting with most recent
        
        
        Messages.transactionStatsMessage(transTypeDepositNumMap, transTypeWithdrawNumMap);          //FIXME: should include a combination of both maps into 1 table AFTER the schema of the table and info has been detailed

        //------------------------------------------------------------------------------------------------------------------------------------------------------
        //Shows the number of times a deposit was called and the total amount of transactions (this should be supplied by the user, but repeated in a line BEFORE the table)
            //FIXME: in the read me, put the formulas for these statistics
        
        //Shows the average times a "Deposit" ATM transaction was called
            // average = (sum of times deposit was called) / total_trans_num
        
        //Shows the percentage "Deposit" options chosen vs. overall transactions
            // %_deposit_trans = deposit_trans_num / total_trans_num * 100
        
        //Shows the average amount of money deposited
            // average_money_deposit = (sum of all money deposited) / num_of_deposit_trans

        //Shows the percentage of money deposited vs. overall funds at the beginning of the transaction period
            // %_money_deposit = total_deposit_amount / Beginning_trans_amount * 100

    }
 
    /**
     * 
     */
    private void calculateWithdrawalsStats(String username, String maxTransactionsNum) {
        Map<String, String> transTypeWithdrawNumMap = db.getTransactionTypeNumMap(username, maxTransactionsNum, "withdraw");
        //Shows the average times a "Withdraw" ATM transaction was called

        //Shows the average amount of money withdrawn

        //Shows the percentage "Withraw" options chosen vs overall transactions

        //Shows the percentage of money withrawn vs. current overall funds

    }

    /**
     * 
     */
    private void calculateDepositsVsWithdrawalsStats(String username, String maxTransactionsNum) {          //FIXME: finish code here...For this function, can use the data received from deposit and withdraw maps above 
                                                                                                            //--> Wouldn't these numbers just be the same thing found above?? Do we need this method??
        // Shows the average times a "Withdraw" ATM transaction was called vs. "Deposit"
            // Average Deposit = (Deposit)

        // Shows the percentage times a "Withdraw" ATM transaction was called vs. "Deposit"

        // Shows the average amount of money withdrawn vs. deposited

        // Show overall funds withdawn and overall funds deposited next to each other
        
        
        
        
        //Shows the average times a "Withdraw" ATM transaction was called vs. "Deposit"

        //Shows the average amount of money withdrawn vs. deposited
        
        //Show overall funds withdawn and overall funds deposited next to each other

    }
}