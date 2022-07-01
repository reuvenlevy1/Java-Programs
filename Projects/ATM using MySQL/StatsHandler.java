import java.util.ArrayList;
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
 * This class consists of 3 major subjects of information:
 * <p>{@code 1. Deposits}                    <li>Shows the average times a "Deposit" ATM transaction was called.
 *                                           <li>Shows the percentage "Deposit" options chosen vs. overall transactions.
 *                                           <li>Shows the average amount of money deposited.
 *                                           <li>Shows the percentage of money deposited vs. current overall funds.
 * <p>{@code 2. Withdrawals}                 <li>Shows the average times a "Withdraw" ATM transaction was called.
 *                                           <li>Shows the percentage "Withraw" options chosen vs overall transactions.
 *                                           <li>Shows the average amount of money withdrawn.
 *                                           <li>Shows the percentage of money withrawn vs. current overall funds.
 * <p>{@code 3. Deposits vs. Withdrawals}    <li>Shows the average times a "Withdraw" ATM transaction was called vs. "Deposit".
 *                                           <li>Shows the percentage times a "Withdraw" ATM transaction was called vs. "Deposit".
 *                                           <li>Shows the average amount of money withdrawn vs. deposited.
                                             <li>Show overall funds withdawn and overall funds deposited next to each other.
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
     * FIXME
     * 
     * @param username              FIXME
     * @param maxTransactionsNum    FIXME
     */
    public void getUserStats(String username, String maxTransactionsNum) {
        // Calculate stats needed
        Map<String, String> depositMap = calculateDepositsStats(username, maxTransactionsNum);
        Map<String, String> withdrawalMap = calculateWithdrawalsStats(username, maxTransactionsNum);
        // Convert Map to an ArrayList of String arrays
        ArrayList<String[]> transArr = new ArrayList<>();
        String[] numOfTransactions = new String[]{depositMap.get("numOfTransactions"), withdrawalMap.get("numOfTransactions")};
        String[] numOfTransactionTypeCalls = new String[]{depositMap.get("numOfTransactionTypeCalls"), withdrawalMap.get("numOfTransactionTypeCalls")};
        String[] percentOfTransactionTypeCalls = new String[]{depositMap.get("percentOfTransactionTypeCalls"), withdrawalMap.get("percentOfTransactionTypeCalls")};
        String[] averageAmountOfTransactionType = new String[]{depositMap.get("averageAmountOfTransactionType"), withdrawalMap.get("averageAmountOfTransactionType")};
        String[] percentAmountOfTransactionType = new String[]{depositMap.get("percentAmountOfTransactionType"), withdrawalMap.get("percentAmountOfTransactionType")};
        // Add stats to ArrayList
        transArr.add(numOfTransactions);
        transArr.add(numOfTransactionTypeCalls);
        transArr.add(percentOfTransactionTypeCalls);
        transArr.add(averageAmountOfTransactionType);
        transArr.add(percentAmountOfTransactionType);
        // Print account stats
        Messages.accountStatsMessage(transArr);

        // calculateDepositsVsWithdrawalsStats(username, maxTransactionsNum);              //FIXME - Not sure I need this
    }

    /**
     * FIXME
     * 
     * @param maxTransactionsNum    FIXME
     */
    public void getAdminStats(String maxTransactionsNum) {
        // Calculate stats needed
        Map<String, String> depositMap = calculateEveryDepositStats(maxTransactionsNum);
        Map<String, String> withdrawalMap = calculateEveryWithdrawalStats(maxTransactionsNum);
        // Convert Map to an ArrayList of String arrays
        ArrayList<String[]> transArr = new ArrayList<>();
        String[] numOfEveryTransactions = new String[]{depositMap.get("numOfEveryTransactions"), withdrawalMap.get("numOfEveryTransactions")};
        String[] numOfEveryTransactionTypeCalls = new String[]{depositMap.get("numOfEveryTransactionTypeCalls"), withdrawalMap.get("numOfEveryTransactionTypeCalls")};
        String[] percentOfEveryTransactionTypeCalls = new String[]{depositMap.get("percentOfEveryTransactionTypeCalls"), withdrawalMap.get("percentOfEveryTransactionTypeCalls")};
        String[] averageAmountOfEveryTransactionType = new String[]{depositMap.get("averageAmountOfEveryTransactionType"), withdrawalMap.get("averageAmountOfEveryTransactionType")};
        String[] percentAmountOfEveryTransactionType = new String[]{depositMap.get("percentAmountOfEveryTransactionType"), withdrawalMap.get("percentAmountOfEveryTransactionType")};
        // Add stats to ArrayList
        transArr.add(numOfEveryTransactions);
        transArr.add(numOfEveryTransactionTypeCalls);
        transArr.add(percentOfEveryTransactionTypeCalls);
        transArr.add(averageAmountOfEveryTransactionType);
        transArr.add(percentAmountOfEveryTransactionType);
        // Print account stats for all users
        Messages.accountStatsMessage(transArr);

        // calculateEveryDepositVsWithdrawalStats(username, maxTransactionsNum);                                              //FIXME - Not sure I need this
    }

    /**
     * FIXME
     * 
     * @param username              FIXME
     * @param maxTransactionsNum    FIXME
     * @return                      FIXME
     */
    private Map<String, String> calculateDepositsStats(String username, String maxTransactionsNum) {
        //------------------------------------------------------------------------------------------------------------------------------------------------------
        // Get total number of transactions and total number of deposits
        // Use query to pull up to last maxTransactionsNum records of user transaction type. Want both data to come back in 1 table of 2 column values.
        Map<String, String> transTypeDepositNumMap = db.getTransactionTypeNumMap(username, maxTransactionsNum, "deposit");      // Getting a map of 5 key-value pairs
        return transTypeDepositNumMap;
    }
 
    /**
     * FIXME
     * 
     * @param username              FIXME
     * @param maxTransactionsNum    FIXME
     * @return                      FIXME
     */
    private Map<String, String> calculateWithdrawalsStats(String username, String maxTransactionsNum) {
        Map<String, String> transTypeWithdrawNumMap = db.getTransactionTypeNumMap(username, maxTransactionsNum, "withdraw");
        return transTypeWithdrawNumMap;
    }

    /**
     * FIXME
     * 
     * @param username              FIXME
     * @param maxTransactionsNum    FIXME
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

    /**
     * FIXME
     * 
     * @param maxTransactionsNum    FIXME
     * @return                      FIXME
     */
    private Map<String, String> calculateEveryDepositStats(String maxTransactionsNum) {
        // Get total number of transactions and total number of deposits
        // Use query to pull up to last maxTransactionsNum records of user transaction type. Want both data to come back in 1 table of 2 column values.
        Map<String, String> everyTransTypeDepositNumMap = db.getEveryTransactionTypeNumMap(maxTransactionsNum, "deposit");
        return everyTransTypeDepositNumMap;
    }
 
    /**
     * FIXME
     * 
     * @param maxTransactionsNum    FIXME
     * @return                      FIXME
     */
    private Map<String, String> calculateEveryWithdrawalStats(String maxTransactionsNum) {
        Map<String, String> everyTransTypeWithdrawNumMap = db.getEveryTransactionTypeNumMap(maxTransactionsNum, "withdraw");
        return everyTransTypeWithdrawNumMap;
    }

    /**
     * FIXME
     * 
     * @param username              FIXME
     * @param maxTransactionsNum    FIXME
     */
    private void calculateEveryDepositVsWithdrawalStats(String username, String maxTransactionsNum) {       //FIXME: finish code here...For this function, can use the data received from deposit and withdraw maps above 
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