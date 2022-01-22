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
     * <p> account_id </p>
     * <p> username </p>
     * <p> pin </p>
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
     * @param username      Account username
     * @return              SQL Query
     */
    public static String createUserTableQuery(String username) {
        return "CREATE TABLE IF NOT EXISTS " + username + " (" + "transaction_id INT NOT NULL PRIMARY KEY, "
            + "transaction_type VARCHAR(11) NOT NULL, " + "amount FLOAT NOT NULL, balance FLOAT NOT NULL);";
    }

    /**
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

    /**
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
     * @param username          Account username
     * @param accountTable      Fixed value for the account table name
     * @return                  SQL Query
     */
    public static String isDupUsernameQuery(String username, String accountTable) {
        return "SELECT username FROM " + accountTable + " WHERE username='" + username + "';" ;
    }

    /**
     * 
     * @param username      Account username
     * @return              SQL Query
     */
    public static String getLatestUserBalanceQuery(String username) {
        return "SELECT balance FROM " + username + " WHERE transaction_id=1;";
    }

    /**
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
     * @param username      Account username
     * @return              SQL Query
     */
    public static String updateUserTransactionIDsQuery(String username) {
        return "UPDATE " + username + " SET transaction_id=transaction_id+1 ORDER BY transaction_id DESC;";
    }

    /**
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
     * @param username      Account username
     * @return              SQL Query
     */
    public static String deleteUserTableQuery(String username) {
        return "DROP TABLE " + username + ";";
    }

    /**
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
     * @param accountTable
     * @return
     */
    public static String listUsernamesQuery(String accountTable) {
        return "SELECT username FROM " + accountTable + " ORDER BY username ASC";
    }
}
