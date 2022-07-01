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
     * SQL query that creates the {@code accountTable} table if it doesn't exist.
     * 
     * @param accountTable      Fixed value for the account table name.
     * @param accountIDColumn   Fixed value for account ID column name.
     * @param usernameColumn    Fixed value for username column name.
     * @param pinColumn         Fixed value for PIN column name.
     * @return                  SQL query.
     */
    public static String createAccountTableQuery(String accountTable, String accountIDColumn, String usernameColumn, String pinColumn) {
        return "CREATE TABLE IF NOT EXISTS " + accountTable + " ("
            + accountIDColumn + " INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
            + usernameColumn + " VARCHAR(16) NOT NULL, "                            //FIXME: change 16 to maxUsernameLen
            + pinColumn + " VARCHAR(255) NOT NULL);";                               //FIXME: change 255 to maxPINLen
    }

    /**
     * SQL query that creates the {@code <username>} table if it doesn't exist.
     * 
     * @param username              Account username.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param amountColumn          Fixed value for the amount column name.
     * @param balanceColumn         Fixed value for the balance column name.
     * @return                      SQL query.
     */
    public static String createUserTableQuery(String username, String transactionIDColumn, String transactionTypeColumn, String amountColumn, String balanceColumn) {
        return "CREATE TABLE IF NOT EXISTS " + username + " ("
            + transactionIDColumn + " INT NOT NULL PRIMARY KEY, "
            + transactionTypeColumn + " VARCHAR(11) NOT NULL, "                     //FIXME: change 11 to maxTranactionTypeLen
            + amountColumn + " FLOAT NOT NULL, "
            + balanceColumn + " FLOAT NOT NULL);";
    }

    /**
     * SQL query that creates the {@code everyTransactionTable} table if it doesn't exist.
     * 
     * @param maxUsernameLen        Fixed value for max username length.
     * @param everyTransactionTable Fixed value for the every transaction table name.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @param usernameColumn        Fixed value for the username column name.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param amountColumn          Fixed value for the amount column name.
     * @param balanceColumn         Fixed value for the balance column name.
     * @return                      SQL query.
     */
    public static String createEveryTransactionTableQuery(String maxUsernameLen, String everyTransactionTable, String transactionIDColumn, String usernameColumn, String transactionTypeColumn, String amountColumn, String balanceColumn) {                 //FIXME: didn't test
        return "CREATE TABLE IF NOT EXISTS " + everyTransactionTable + "("
            + transactionIDColumn + " INT NOT NULL PRIMARY KEY, "
            + usernameColumn + " VARCHAR(" + maxUsernameLen + ") NOT NULL, "
            + transactionTypeColumn + " VARCHAR(11) NOT NULL, "                     //FIXME: change 11 to maxTranactionTypeLen
            + amountColumn + " FLOAT NOT NULL, "
            + balanceColumn + " FLOAT NOT NULL);";
    }

    /**
     * SQL query that inserts username and PIN to the {@code accountTable} table.
     * 
     * @param username          Account username.
     * @param pin               Account PIN.
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @param pinColumn         Fixed value for the PIN column name.
     * @return                  SQL query.
     */
    public static String addUserToAccountTableQuery(String username, String pin, String accountTable, String usernameColumn, String pinColumn) {
        return "INSERT INTO " + accountTable + " ("
            + usernameColumn + ", "
            + pinColumn + ") values (\""
            + username + "\", \""
            + pin + "\");";
    }

    /**
     * SQL query that inserts username and PIN to the {@code accountTable} table.
     * 
     * @param username              Account username.
     * @param transactionID         Unique transaction identifier.
     * @param transactionType       Type of tansaction performed.
     * @param amount                Transaction amount.
     * @param balance               Current balance.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param amountColumn          Fixed value for the amount column name.
     * @param balanceColumn         Fixed value for the balance column name.
     * @return                      SQL query.
     */
    public static String addTransactionToUserTableQuery(String username, int transactionID, String transactionType, Double amount, Double balance, String transactionIDColumn, String transactionTypeColumn, String amountColumn, String balanceColumn) {
        return "INSERT INTO " + username + " ("
            + transactionIDColumn + ", "
            + transactionTypeColumn + ", "
            + amountColumn + ", "
            + balanceColumn + ") values ("
            + transactionID + ", \""
            + transactionType + "\", "
            + amount + ", "
            + balance + ");";
    }

    /**
     * SQL query that inserts transaction ID, username transaction type, amount of transaction and remaining balance to the {@code everyTransactionTable} table.
     * 
     * @param transactionID         Unique transaction identifier.
     * @param username              Account username.
     * @param transactionType       Type of tansaction performed.
     * @param amount                Transaction amount.
     * @param balance               Current balance.
     * @param everyTransactionTable Fixed value for the transaction table name that holds all transactions.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @param usernameColumn        Fixed value for the username column name.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param amountColumn          Fixed value for the amount column name.
     * @param balanceColumn         Fixed value for the balance column name.
     * @return                      SQL query.
     */
    public static String addTransactionToEveryTransactionTableQuery(int transactionID, String username, String transactionType, Double amount, Double balance, String everyTransactionTable, String transactionIDColumn, String usernameColumn, String transactionTypeColumn, String amountColumn, String balanceColumn) {
        return "INSERT INTO " + everyTransactionTable + " ("
            + transactionIDColumn + ", "
            + usernameColumn + ", "
            + transactionTypeColumn + ", "
            + amountColumn + ", "
            + balanceColumn + ") values ("
            + transactionID + ", \""
            + username + "\", \""
            + transactionType + "\", "
            + amount + ", "
            + balance + ");";
    }

    /**
     * SQL query that selects username and pin from {@code accountUser} and {@code accountPIN} to verify user account information for login.
     * 
     * @param accountUser       Account username.
     * @param accountPIN        Account PIN.
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @param pinColumn         Fixed value for the PIN column name.
     * @return                  SQL query.
     */
    public static String verifyAccountQuery(String accountUser, String accountPIN, String accountTable, String usernameColumn, String pinColumn) {
        return "SELECT " + usernameColumn + ", "
            + pinColumn + " FROM "
            + accountTable + " WHERE "
            + usernameColumn + "='"
            + accountUser + "' AND "
            + pinColumn + "=\""
            + accountPIN + "\";";
    }

    /**
     * SQL query that selects {@code username} from {@code accountTable} to verify a user account.
     * 
     * @param username          Account username.
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @return                  SQL query.
     */
    public static String verifyUserQuery(String username, String accountTable, String usernameColumn) {
        return "SELECT " + username + " FROM "
            + accountTable + " WHERE "
            + usernameColumn + "='"
            + username + "';";
    }

    /**
     * SQL query that selects {@code username} from {@code accountTable} to verify a user account already exists upon new account creation.
     * 
     * @param username          Account username.
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @return                  SQL query.
     */
    public static String isDupUsernameQuery(String username, String accountTable, String usernameColumn) {
        return "SELECT " + usernameColumn + " FROM "
            + accountTable + " WHERE "
            + usernameColumn + "='"
            + username + "';";
    }

    /**
     * SQL query that selects latest balance from {@code username}.
     * 
     * @param username              Account username.
     * @param balanceColumn         Fixed value for the balance column name.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      SQL query.
     */
    public static String getLatestUserBalanceQuery(String username, String balanceColumn, String transactionIDColumn) {
        return "SELECT " + balanceColumn + " FROM "
            + username + " WHERE "
            + transactionIDColumn + "=1;";
    }

    /**
     * SQL query that selects the entire transaction list up to {@code max}.
     * 
     * @param username              Account username.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      SQL query.
     */
    public static String getUserTransactionListQuery(String username, int maxTransactionsNum, String transactionIDColumn) {
        return "SELECT * FROM " + username + " WHERE "
            + transactionIDColumn + "<="
            + maxTransactionsNum + ";";
    }

    /**
     * SQL query that will update all transaction IDs in a table by adding 1.
     * 
     * @param table                 Name of the table: every transaction table or user table.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      SQL query.
     */
    public static String updateTransactionIDsQuery(String table, String transactionIDColumn) {
        return "UPDATE " + table + " SET "
            + transactionIDColumn + "="
            + transactionIDColumn + "+1 ORDER BY "
            + transactionIDColumn + " DESC;";
    }

    /**
     * SQL query that will delete a user from the the {@code accountTable} table's list of users.
     * 
     * @param username          Account username.
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @return                  SQL query.
     */
    public static String deleteUserFromAccountTableQuery(String username, String accountTable, String usernameColumn) {
        return "DELETE FROM " + accountTable + " WHERE "
            + usernameColumn + "='"
            + username + "';";
    }

    /**
     * SQL query that will drop a user's {@code username} table along with list of transactions and balances.
     * 
     * @param username      Account username.
     * @return              SQL query.
     */
    public static String deleteUserTableQuery(String username) {
        return "DROP TABLE " + username + ";";
    }

    /**
     * SQL query that will update a user's PIN from an old one to a new one.
     * 
     * @param username          Account username.
     * @param newPin            User inputted PIN value.
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @param pinColumn         Fixed value for the PIN column name.
     * @return                  SQL query.
     */
    public static String changeUserPINQuery(String username, String newPin, String accountTable, String usernameColumn, String pinColumn) {
        return "UPDATE " + accountTable + " SET "
            + pinColumn + "=\""
            + newPin + "\" WHERE "
            + usernameColumn + "='"
            + username + "';";
    }

    /**
     * SQL query that select all existing usernames from within the {@code accountTable} table.
     * 
     * @param accountTable      Fixed value for the account table name.
     * @param usernameColumn    Fixed value for the username column name.
     * @return                  SQL query.
     */
    public static String listUsernamesQuery(String accountTable, String usernameColumn) {
        return "SELECT " + usernameColumn + " FROM "
            + accountTable + " ORDER BY "
            + usernameColumn + " ASC";
    }

    /**
     * Creates a map, {@code queryMap}, that holds a list of queries:
     * <p>{@code numOfTransactions}         holds the total number of transactions available. This list may be less than the {@code maxTransactionsNum}.
     * <p>{@code numOfTransTypeCalls}       holds the number of times a Deposit or Withdraw ATM transaction option was chosen.
     * <p>{@code percentOfTransTypeCalls}   holds the percentage of how often a Deposit or Withdraw ATM transaction option was chosen.
     * <p>{@code averageAmountOfTransType}  holds the average amount of money deposited or withdrawn up to {@code maxTransactionsNum}.
     * <p>{@code percentAmountOfTransType}  holds the percentage amount of money deposited or withdrawn compared to the balance at {@code maxTransactionsNum}.
     * 
     * @param username              Account username.
     * @param transactionType       Type of tansaction performed.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      Map of SQL queries.
     */
    public static Map<String, String> listTransactionTypeQuery(String username, String transactionType, String maxTransactionsNum, String transactionTypeColumn, String transactionIDColumn) {      //FIXME: call to public methods for all 4 String queries and add them to a map
                                                                                                                                          //FIXME: I moved the variable order here, need to fix what passes to this
        Map<String, String> queryMap = new HashMap<>();
        
        queryMap.put("numOfTransactions", getNumOfTransactionsQuery(username, maxTransactionsNum));
        queryMap.put("numOfTransTypeCalls", getNumOfTransTypeQuery(username, maxTransactionsNum, transactionType, transactionTypeColumn));
        queryMap.put("percentOfTransTypeCalls", getPercentOfTransTypeCallsQuery(username, maxTransactionsNum, transactionType, transactionTypeColumn, transactionIDColumn));
        queryMap.put("averageAmountOfTransType", getAverageAmountOfTransTypeQuery(username, maxTransactionsNum, transactionType, transactionIDColumn));
        queryMap.put("percentAmountOfTransType", getPercentAmountOfTransTypeQuery(username, maxTransactionsNum, transactionType, transactionTypeColumn, transactionIDColumn));
        
        return queryMap;
    }

    /**
     * Creates a map, {@code queryMap}, that holds a list of queries:
     * <p>{@code numOfEveryTransactions}         holds the total number of transactions available from every user. This list may be less than the {@code maxTransactionsNum}.
     * <p>{@code numOfEveryTransTypeCalls}       holds the number of times a Deposit or Withdraw ATM transaction option was chosen from every user.
     * <p>{@code percentOfEveryTransTypeCalls}   holds the percentage of how often a Deposit or Withdraw ATM transaction option was chosen from every user.
     * <p>{@code averageAmountOfEveryTransType}  holds the average amount of money deposited or withdrawn up to {@code maxTransactionsNum} from every user.
     * <p>{@code percentAmountOfEveryTransType}  holds the percentage amount of money deposited or withdrawn compared to the balance at {@code maxTransactionsNum} from every user.
     * 
     * @param transactionType       Type of tansaction performed.
     * @param everyTransactionTable Fixed value for the transaction table name that holds all transactions.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      Map of SQL queries.
     */
    public static Map<String, String> listEveryTransactionTypeQuery(String transactionType, String everyTransactionTable, String maxTransactionsNum, String transactionTypeColumn, String transactionIDColumn) {      //FIXME: call to public methods for all 4 String queries and add them to a map  
                                                                                                                                                            //FIXME: I moved the variable order here, need to fix what passes to this
        Map<String, String> queryMap = new HashMap<>();
        
        queryMap.put("numOfEveryTransactions", getNumOfTransactionsQuery(everyTransactionTable, maxTransactionsNum));
        queryMap.put("numOfEveryTransTypeCalls", getNumOfTransTypeQuery(everyTransactionTable, maxTransactionsNum, transactionType, transactionTypeColumn));
        queryMap.put("percentOfEveryTransTypeCalls", getPercentOfTransTypeCallsQuery(everyTransactionTable, maxTransactionsNum, transactionType, transactionTypeColumn, transactionIDColumn));
        queryMap.put("averageAmountOfEveryTransType", getAverageAmountOfTransTypeQuery(everyTransactionTable, maxTransactionsNum, transactionType, transactionIDColumn));
        queryMap.put("percentAmountOfEveryTransType", getPercentAmountOfTransTypeQuery(everyTransactionTable, maxTransactionsNum, transactionType, transactionTypeColumn, transactionIDColumn));
        
        return queryMap;
    }

    /**
     * Gets the total number of transactions available. This list may be less than the {@code maxTransactionsNum}.
     * 
     * @param table                 A User's transactions table or the every transaction table.
     * @param maxTransactionsNum    Number of transactions returned.
     * @return                      SQL query.
     */
    public static String getNumOfTransactionsQuery(String table, String maxTransactionsNum) {
        return "SELECT COUNT(*) FROM " + table + " LIMIT "
            + maxTransactionsNum + ";";
    }
    
    /**
     * Gets the number of times a Deposit or Withdraw ATM transaction option was chosen.
     * 
     * @param table                 A User's transactions table or the every transaction table.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionType       Type of tansaction performed.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @return                      SQL query.
     */
    public static String getNumOfTransTypeQuery(String table, String maxTransactionsNum, String transactionType, String transactionTypeColumn) {
        return "SELECT COUNT(*) FROM " + table + " WHERE "
            + transactionTypeColumn + "='"
            + transactionType + "' LIMIT "
            + maxTransactionsNum + ";";
    }

    /**
     * Gets the percentage of how often a Deposit or Withdraw ATM transaction option was chosen.
     * 
     * @param table                 A User's transactions table or the every transaction table.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionType       Type of tansaction performed.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      SQL query.
     */
    public static String getPercentOfTransTypeCallsQuery(String table, String maxTransactionsNum, String transactionType, String transactionTypeColumn, String transactionIDColumn) {
        return "SELECT COUNT(*) / (SELECT COUNT(*) FROM " + table + " LIMIT "
            + maxTransactionsNum + ") *100 FROM "
            + table + " WHERE "
            + transactionIDColumn + "='"
            + transactionType + "' LIMIT "
            + maxTransactionsNum + ";";
    }
       
    /**
     * Gets the average amount of money deposited or withdrawn up to {@code maxTransactionsNum}.
     * 
     * @param table                 A User's transactions table or the every transaction table.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionType       Type of tansaction performed.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      SQL query.
     */
    public static String getAverageAmountOfTransTypeQuery(String table, String maxTransactionsNum, String transactionType, String transactionIDColumn) {
        return "SELECT AVG(amount) FROM " + table + " WHERE "
            + transactionIDColumn + "='"
            + transactionType + "' LIMIT "
            + maxTransactionsNum + ";";
    }

    /**
     * Gets the percentage amount of money deposited or withdrawn compared to the balance at {@code maxTransactionsNum}.
     * 
     * @param table                 A User's transactions table or the every transaction table.
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionType       Type of tansaction performed.
     * @param transactionTypeColumn Fixed value for the transaction type column name.
     * @param transactionIDColumn   Fixed value for the transaction ID column name.
     * @return                      SQL query.
     */
    public static String getPercentAmountOfTransTypeQuery(String table, String maxTransactionsNum, String transactionType, String transactionTypeColumn, String transactionIDColumn) {
        return "SELECT SUM(amount) / (SELECT balance FROM " + table + " WHERE "
            + transactionIDColumn + "="
            + "(SELECT COUNT(*) FROM " + table + " LIMIT "
            + maxTransactionsNum + ")) "
            + "* 100 FROM " + table + " WHERE "
            + transactionTypeColumn + "='"
            + transactionType + "' LIMIT "
            + maxTransactionsNum + ";";
    }
}
