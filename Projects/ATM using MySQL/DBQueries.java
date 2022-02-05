import java.util.HashMap;
import java.util.Map;

/**
 * The {@code DBQueries} class contains all String database qeuries for program.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class DBQueries {

    /**
     * Creates the {@code accountTable} table if it doesn't exist. This
     * includes the following columns:
     * <p> account_id
     * <p> username
     * <p> pin
     * 
     * @param accountTable      Fixed value for the account table name
     * @return                  SQL Query
     */
    public static String createAccountTableQuery(String accountTable) {
        return "CREATE TABLE IF NOT EXISTS " + accountTable + " ("
            + "account_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
            + "username VARCHAR(16) NOT NULL, "
            + "pin VARCHAR(255) NOT NULL);";
    }

    /**
     * 
     * 
     * @param username      Account username
     * @return              SQL Query
     */
    public static String createUserTableQuery(String username) {
        return "CREATE TABLE IF NOT EXISTS " + username + " (" + "transaction_id INT NOT NULL PRIMARY KEY, "
            + "transaction_type VARCHAR(11) NOT NULL, " + "amount FLOAT NOT NULL, balance FLOAT NOT NULL);";
    }

    /**
     * 
     * 
     * @param everyTransactionTable
     * @param maxUsernameLen
     * @return
     */
    public static String createEveryTransactionTableQuery(String everyTransactionTable, String maxUsernameLen) {                 //FIXME: didn't test
        return "CREATE TABLE IF NOT EXISTS " + everyTransactionTable + " (transaction_id INT NOT NULL PRIMARY KEY, "
            + "username VARCHAR(" + maxUsernameLen + ") transaction_type VARCHAR(11) NOT NULL, amount FLOAT NOT NULL,"
            + "balance FLOAT NOT NULL);";
    }

    /**
     * 
     * 
     * @param username          Account username
     * @param pin               Account PIN
     * @param accountTable      Fixed value for the account table name     
     * @return                  SQL Query
     */
    public static String addUserToAccountTableQuery(String username, String pin, String accountTable) {
        return "INSERT INTO " + accountTable + " (username, pin) values (\"" + username + "\", \""
            + pin + "\");";
    }

    /**
     * 
     * 
     * @param username          Account username
     * @param transactionID
     * @param transactionType
     * @param amount
     * @param balance
     * @return                  SQL Query
     */
    public static String addTransactionToUserTableQuery(String username, int transactionID,
        String transactionType, Double amount, Double balance) {
        return "INSERT INTO " + username + " (transaction_id, transaction_type, amount, balance) "
            + "values (" + transactionID + ", \"" + transactionType + "\", " + amount
            + ", " + balance + ");";
    }

    public static String addTransactionToEveryTransactionTableQuery(String everyTransactionTable, int transactionID,
        String username, String transactionType, Double amount, Double balance) {
        return "INSERT INTO " + everyTransactionTable
            + " (username, transaction_id, transaction_type, amount, balance)"
            + "values (" + transactionID + ", \"" + username + ", \"" + transactionType + "\", " + amount + ", "
            + balance + ");";
    }
    

    /**
     * 
     * 
     * @param accountUser       Account username
     * @param accountPIN        Account PIN
     * @param accountTable      Fixed value for the account table name
     * @return                  SQL Query
     */
    public static String verifyAccountQuery(String accountUser, String accountPIN, String accountTable) {
        return "SELECT username, pin FROM " + accountTable + " WHERE username='" + accountUser + "' AND pin=\""
            + accountPIN + "\";";
    }

    /**
     * 
     * 
     * @param username          Account username
     * @param accountTable      Fixed value for the account table name
     * @return                  SQL Query
     */
    public static String verifyUserQuery(String username, String accountTable) {
        return "SELECT username FROM " + accountTable + " WHERE username='" + username + "';";
    }

    /**
     * 
     * 
     * @param username          Account username
     * @param accountTable      Fixed value for the account table name
     * @return                  SQL Query
     */
    public static String isDupUsernameQuery(String username, String accountTable) {
        return "SELECT username FROM " + accountTable + " WHERE username='" + username + "';" ;
    }

    /**
     * 
     * 
     * @param username      Account username
     * @return              SQL Query
     */
    public static String getLatestUserBalanceQuery(String username) {
        return "SELECT balance FROM " + username + " WHERE transaction_id=1;";
    }

    /**
     * 
     * 
     * @param username          Account username
     * @param maxTransNum
     * @return                  SQL Query
     */
    public static String getUserTransactionListQuery(String username, int maxTransNum) {
        return "SELECT * FROM " + username + " WHERE transaction_id<=" + maxTransNum + ";";
    }

    /**
     * 
     * 
     * @param table         Name of the table: every transaction table or user table
     * @return              SQL Query
     */
    public static String updateTransactionIDsQuery(String table) {
        return "UPDATE " + table + " SET transaction_id=transaction_id+1 ORDER BY transaction_id DESC;";
    }

    /**
     * 
     * 
     * @param username          Account username
     * @param accountTable      Fixed value for the account table name
     * @return                  SQL Query
     */
    public static String deleteUserFromAccountTableQuery(String username, String accountTable) {
        return "DELETE FROM " + accountTable + " WHERE username='" + username + "';";
    }

    /**
     * 
     * 
     * @param username      Account username
     * @return              SQL Query
     */
    public static String deleteUserTableQuery(String username) {
        return "DROP TABLE " + username + ";";
    }

    /**
     * 
     * 
     * @param username
     * @param newPin
     * @param accountTable
     * @return
     */
    public static String changeUserPINQuery(String username, String newPin, String accountTable) {
        return "UPDATE " + accountTable + " SET pin=\"" + newPin + "\" WHERE username='" + username + "';";
    }

    /**
     * 
     * 
     * @param accountTable
     * @return
     */
    public static String listUsernamesQuery(String accountTable) {
        return "SELECT username FROM " + accountTable + " ORDER BY username ASC";
    }

    /**
     * <Describe this function here>
     * <Describe the content of queryMap here>
     * 
     * @param username
     * @param maxTransactionsNum
     * @param transactionType
     * @return
     */
    public static Map<String, String> listTransactionTypeQuery(String username, String maxTransactionsNum, String transactionType) {      //FIXME: call to public methods for all 4 String queries and add them to a map
        Map<String, String> queryMap = new HashMap<>();
        
        queryMap.put("numOfTransactions", getNumOfTransactionsQuery(username, maxTransactionsNum));
        queryMap.put("numOfTransTypeCalls", getNumOfTransTypeQuery(username, maxTransactionsNum, transactionType));
        queryMap.put("percentOfTransTypeCalls", getPercentOfTransTypeCallsQuery(username, maxTransactionsNum, transactionType));
        queryMap.put("averageAmountOfTransType", getAverageAmountOfTransTypeQuery(username, maxTransactionsNum, transactionType));
        queryMap.put("percentAmountOfTransType", getPercentAmountOfTransTypeQuery(username, maxTransactionsNum, transactionType));
        
        return queryMap;
    }

    /**
     * Gets the total number of transactions available. This list may be less than the {@code maxTransactionsNum}.
     * 
     * @param username
     * @param maxTransactionsNum
     * @return
     */
    public static String getNumOfTransactionsQuery(String username, String maxTransactionsNum) {
        return "SELECT COUNT(*) FROM " + username + " LIMIT " + maxTransactionsNum + ";";
    }
    
    /**
     * Gets the number of times a "Deposit" ATM transaction option was chosen.
     * 
     * @param username
     * @param maxTransactionsNum
     * @param transactionType
     * @return
     */
    public static String getNumOfTransTypeQuery(String username, String maxTransactionsNum, String transactionType) {
        return "SELECT COUNT(*) FROM " + username + " WHERE transaction_type='" + transactionType + "' LIMIT " + maxTransactionsNum + ";";
    }

    /**
     * Gets the percentage of how often a "Deposit" ATM transaction option was chosen.
     * 
     * @param username
     * @param maxTransactionsNum
     * @param transactionType
     * @return
     */
    public static String getPercentOfTransTypeCallsQuery(String username, String maxTransactionsNum, String transactionType) {
        return "SELECT COUNT(*) / (SELECT COUNT(*) FROM " + username + " LIMIT " + maxTransactionsNum + ") *100 FROM "
            + username + " WHERE transaction_type='" + transactionType + "' LIMIT " + maxTransactionsNum + ";";
    }
       
    /**
     * Gets the average amount of money deposited up to {@code maxTransactionsNum}.
     * 
     * @param username
     * @param maxTransactionsNum
     * @param transactionType
     * @return
     */
    public static String getAverageAmountOfTransTypeQuery(String username, String maxTransactionsNum, String transactionType) {
        return "SELECT AVG(amount) FROM " + username + " WHERE transaction_type='" + transactionType + "' LIMIT " + maxTransactionsNum + ";";
    }

    /**
     * Gets the percentage amount of money deposited compared to the balance at {@code maxTransactionsNum}.
     * 
     * @param username
     * @param maxTransactionsNum
     * @param transactionType
     * @return
     */
    public static String getPercentAmountOfTransTypeQuery(String username, String maxTransactionsNum, String transactionType) {
        return "SELECT SUM(amount) / (SELECT balance FROM " + username + " WHERE transaction_id="
            + "(SELECT COUNT(*) FROM " + username + " LIMIT " + maxTransactionsNum + ")) "
            + "* 100 FROM " + username + " WHERE transaction_type='" + transactionType + "' LIMIT " + maxTransactionsNum + ";";
    }
}
