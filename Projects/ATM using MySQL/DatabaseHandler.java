import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code DatabaseHandler} class contains database connection details and all
 * databse qeury executions for program.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021 
 */
public class DatabaseHandler {
    // Database connection
    Connection connect = null;
    Statement statement = null;
    
    // Table names
    /**
     * Table name for accounts.
     */
    final static String ACCOUNT_TABLE = "account";

    /**
     * Table name for all user transactions.
     */
    final static String EVERY_TRANSACTION_TABLE = "every_transaction";

    // Column names
    /**
     * Column name for user account ID.
     */
    final static String ACCOUNT_ID_COLUMN = "account_id";

    /**
     * Column name for username.
     */
    final static String USERNAME_COLUMN = "username";

    /**
     * Column name for PIN.
     */
    final static String PIN_COLUMN = "pin";

    /**
     * Column name for transaction ID.
     */
    final static String TRANSACTION_ID_COLUMN = "transaction_id";
    
    /**
     * Column name for transaction type.
     */
    final static String TRANSACTION_TYPE_COLUMN = "transaction_type";
    
    /**
     * Column name for amount.
     */
    final static String AMOUNT_COLUMN = "amount";

    /**
     * Column name for balance.
     */
    final static String BALANCE_COLUMN = "balance";
    
    /**
     * Max username length.
     */
    final static String MAX_USERNAME_LEN = ReadINIFile.MAX_USERNAME_LEN;
    
    // New user details
    /**
     * Transaction ID for new account.
     */
    final static int NEW_TRANSACTION_ID = 1;
    
    /**
     * Transaction type for new account.
     */
    final static String NEW_TRANSACTION_TYPE = "New Account";
    
    /**
     * Transaction balance for new account.
     */
    final static Double NEW_BALANCE = 0.0;
    
    /**
     * Transaction amount for new account.
     */
    final static Double NEW_AMOUNT = 0.0;

    /**
     * Constructor that creates database connection.
     */
    public DatabaseHandler() {
        String mysqlURL = ReadINIFile.MYSQL_URL;
        String dbUser = ReadINIFile.DB_USERNAME;
        String dbPass = ReadINIFile.DB_PASSWORD;
        try {
            connect = DriverManager.getConnection(mysqlURL, dbUser, dbPass);
            if (connect != null) {
                System.out.println(Messages.dbConnectionMessage());
                statement = connect.createStatement();
            }
        } catch (Exception e) {
            System.out.println(Messages.databaseConnectionErrorMessage() + "\n");
            System.exit(0);
        }
    }

    /**
     * Creates a new user if the username doesn't exist.
     *
     * @param username  Account username.
     * @param pin       Account PIN.
     */
    public void createUser(String username, String pin) {
      
        // All tables will be created if they don't exist
        createAccountTable();
        createEveryTransactionTable();
        addUserToAccountTable(username, pin);
        createUserTable(username);
        // Adds an initial transaction to the user table
        addTransactionToUserTable(username, NEW_TRANSACTION_ID, NEW_TRANSACTION_TYPE,
            NEW_AMOUNT, NEW_BALANCE);
    }

    /**
     * Adds the new user to the {@code ACCOUNT_TABLE} table.
     * 
     * @param username  Account username.
     * @param pin       Account PIN.
     */
    private void addUserToAccountTable(String username, String pin) {
        String query = DBQueries.addUserToAccountTableQuery(username, AES256.encrypt(pin), ACCOUNT_TABLE, USERNAME_COLUMN, PIN_COLUMN);
        // Run query
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Creates the {@code ACCOUNT_TABLE} table in DB.
     */
    private void createAccountTable() {
        // Create table if doesn't exist
        String query = DBQueries.createAccountTableQuery(ACCOUNT_TABLE, ACCOUNT_ID_COLUMN, USERNAME_COLUMN, PIN_COLUMN);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * FIXME
     */
    private void createEveryTransactionTable() {
        // Create table if doesn't exist
        String query = DBQueries.createEveryTransactionTableQuery(MAX_USERNAME_LEN, EVERY_TRANSACTION_TABLE, TRANSACTION_ID_COLUMN, USERNAME_COLUMN, TRANSACTION_TYPE_COLUMN, AMOUNT_COLUMN, BALANCE_COLUMN);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * FIXME
     * 
     * @param username  Account username
     */
    private void createUserTable(String username) {
        String query = DBQueries.createUserTableQuery(username, TRANSACTION_ID_COLUMN, TRANSACTION_TYPE_COLUMN, AMOUNT_COLUMN, BALANCE_COLUMN);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * FIXME
     * 
     * @param username
     * @param transactionID
     * @param transactionType
     * @param amount
     * @param balance
     */
    public void addTransactionToUserTable(String username, int transactionID,
        String transactionType, Double amount, Double balance) {                //FIXME - Test for Withdraw and deposit
        // Execute query to update transaction_id numbers
        String updateQuery = DBQueries.updateTransactionIDsQuery(username);
        // Add new transaction
        String addQuery =
            DBQueries.addTransactionToUserTableQuery(username, transactionID, transactionType, amount, balance, TRANSACTION_ID_COLUMN, TRANSACTION_TYPE_COLUMN, AMOUNT_COLUMN, BALANCE_COLUMN);
        // Run queries
        try {
            statement.executeUpdate(updateQuery);
            statement.executeUpdate(addQuery);
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * FIXME
     * 
     * @param username
     * @param transactionID
     * @param transactionType
     * @param amount
     * @param balance
     */
    public void addTransactionToEveryTransactionTable(String username, int transactionID,
        String transactionType, Double amount, Double balance) {                                //FIXME - Test for Withdraw and deposit; never tested
        // Execute query to update transaction_id numbers
        String updateQuery = DBQueries.updateTransactionIDsQuery(EVERY_TRANSACTION_TABLE);
        // Add new transaction
        String addQuery =
            DBQueries.addTransactionToEveryTransactionTableQuery(transactionID, username, transactionType, amount, balance, EVERY_TRANSACTION_TABLE,, TRANSACTION_ID_COLUMN, USERNAME_COLUMN, TRANSACTION_TYPE_COLUMN, AMOUNT_COLUMN, BALANCE_COLUMN);
        // Run queries
        try {
            statement.executeUpdate(updateQuery);
            statement.executeUpdate(addQuery);                                                          //FIXME
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Verify if username and password details entered are correct/incorrect.
     * 
     * @param accountDetails    {@code Map} that holds username and pin:
     *                          <p>{@code accountDetails["username"]}
     *                          <p>{@code accountDetails["pin"]}
     * @return                  {@code false} if {@code accountDetails} doesn't exist in database.
     */
    public boolean verifyAccount(Map<String, String> accountDetails) {
        // verify both username and PIN are valid
        String accountUser = accountDetails.get("username");
        String accountPin = accountDetails.get("pin");
        String query = DBQueries.verifyAccountQuery(accountUser, AES256.encrypt(accountPin), ACCOUNT_TABLE);

        // Run query and take result if exists
        try {
            ResultSet resultset = statement.executeQuery(query);

            if (resultset.next())
                if (resultset.getString("username").toLowerCase().equals(accountUser.toLowerCase())
                        && AES256.decrypt(resultset.getString("pin")).equals(accountPin))
                    return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * Verify if username details entered are correct/incorrect.
     * 
     * @param username  Account username.
     * @return          {@code false} if {@code accountDetails} doesn't exist in database.
     */
    public boolean verifyUser(String username) {
        // verify username is valid
        String query = DBQueries.verifyUserQuery(username, ACCOUNT_TABLE);

        // Run query and take result if exists
        try {
            ResultSet resultset = statement.executeQuery(query);

            if (resultset.next())
                if (resultset.getString("username").toLowerCase().equals(username.toLowerCase()))
                    return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * Checks if username already exists in ACCOUNT_TABLE.
     * 
     * @param username  Account username.
     * @return          FIXME
     */
    public boolean checkDupUsers(String username) {
        String query = DBQueries.isDupUsernameQuery(username, ACCOUNT_TABLE);
        // Run query and take result if exists
        try {
            ResultSet resultset = statement.executeQuery(query);

            if (resultset.next()) {
                System.out.println(resultset.getString(1)); // FIXME: Need to test after using admin to create a user.
                                                            // What happens when it doesn't find a user? Instead of
                                                            // logging error, it SHOULD show error of username doesn't
                                                            // exist.
                return true;
            } else
                return false;
        } catch (SQLSyntaxErrorException e) {
            return false;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * FIXME
     * 
     * @param username  Account username.
     * @return          FIXME
     */
    public String getLatestUserBalance(String username) {
        String query = DBQueries.getLatestUserBalanceQuery(username);
        // Run query and take result if exists
        try {
            ResultSet resultset = statement.executeQuery(query);
            if (resultset.next())
                return resultset.getString(1);
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return "";
    }

    /**
     * FIXME
     * 
     * @param username              Account username.
     * @param maxTransactionsNum    Number of transactions returned.
     * @return                      FIXME
     */
    public ArrayList<String[]> getUserTransactionHistory(String username, int maxTransactionsNum, String transactionIDColumn) { //FIXME: RL - added transactionIDColumn, need to add this parameter and other ones to their originating classes
        ArrayList<String[]> transactionList = new ArrayList<>();
        String query = DBQueries.getUserTransactionListQuery(username, maxTransactionsNum, transactionIDColumn);
        // Run query and take results if exists
        try {
            ResultSet resultset = statement.executeQuery(query);
            // Get entire row of data (4 columns)
            while (resultset.next()) {
                String transactionID = resultset.getString(1);
                String transactionType = resultset.getString(2);
                String amount = resultset.getString(3);
                String balance = resultset.getString(4);
                String[] userRecord = new String[]{transactionID, transactionType, amount, balance};
                transactionList.add(userRecord);
            }
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return transactionList;
    }

    /**
     * Removes username from list of accounts and drops user table.
     * 
     * @param username  Account username.
     * @return          FIXME
     */
    public boolean deleteUserAccount(String username) {
        String accountQuery = DBQueries.deleteUserFromAccountTableQuery(username, ACCOUNT_TABLE, USERNAME_COLUMN);
        String userQuery = DBQueries.deleteUserTableQuery(username);
        // Run queries
        try {
            statement.executeUpdate(accountQuery);
            statement.executeUpdate(userQuery);
            return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }
    
    /**
     * Change the PIN of the entered username.
     * 
     * @param username  Account username.
     * @param pin       Account PIN.
     * @return          FIXME
     */
    public boolean changeUserPIN(String username, String pin) {
        String query = DBQueries.changeUserPINQuery(username, AES256.encrypt(pin), ACCOUNT_TABLE);
        // Run query
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * FIXME
     * 
     * @return  FIXME
     */
    public ArrayList<String> listUsernames() {
        ArrayList<String> userList = new ArrayList<>();
        String query = DBQueries.listUsernamesQuery(ACCOUNT_TABLE);
        // Run query and take results if exists
        try {
            ResultSet resultset = statement.executeQuery(query);
            // Get usernames data
            while (resultset.next())
                userList.add(resultset.getString(1));
            return userList;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * FIXME
     * 
     * @param username
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionType       
     * @return                      FIXME
     */
    public Map<String, String> getTransactionTypeNumMap(String username, String maxTransactionsNum, String transactionType) {
        /**
         * <Describe the content of queryMap here>
         */
        Map<String, String> queryMap = new HashMap<>();                     // Holds all the DB queries
        Map<String, String> transactionTypeNumMap = new HashMap<>();        // Holds all the results from the DB queries

        queryMap = DBQueries.listTransactionTypeQuery(username, maxTransactionsNum, transactionType);     //FIXME: Write documentation on what this function is and what it returns. Then use the executeQuery() method on all values (should be 5)
        // Run query and take results if exists
        try {
            ResultSet resultset = statement.executeQuery(queryMap.get("numOfTransactions"));
            transactionTypeNumMap.put("numOfTransactions", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("numOfTransactionTypeCalls"));
            transactionTypeNumMap.put("numOfTransactionTypeCalls", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("percentOfTransactionTypeCalls"));
            transactionTypeNumMap.put("percentOfTransactionTypeCalls", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("averageAmountOfTransactionType"));
            transactionTypeNumMap.put("averageAmountOfTransactionType", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("percentAmountOfTransactionType"));
            transactionTypeNumMap.put("percentAmountOfTransactionType", resultset.getString(1));
            return transactionTypeNumMap;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * FIXME
     * 
     * @param maxTransactionsNum    Number of transactions returned.
     * @param transactionType       FIXME
     * @return                      FIXME
     */
    public Map<String, String> getEveryTransactionTypeNumMap(String maxTransactionsNum, String transactionType) {
        /**
         * <Describe the content of queryMap here>
         */
        Map<String, String> queryMap = new HashMap<>();
        Map<String, String> everyTransactionTypeNumMap = new HashMap<>();

        queryMap = DBQueries.listEveryTransactionTypeQuery(EVERY_TRANSACTION_TABLE, maxTransactionsNum, transactionType);     //FIXME: Write documentation on what this function is and what it returns. Then use the executeQuery() method on all values (should be 5)
        // Run query and take results if exists
        try {
            ResultSet resultset = statement.executeQuery(queryMap.get("numOfEveryTransactions"));
            everyTransactionTypeNumMap.put("numOfEveryTransactions", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("numOfEveryTransactionTypeCalls"));
            everyTransactionTypeNumMap.put("numOfEveryTransactionTypeCalls", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("percentOfEveryTransactionTypeCalls"));
            everyTransactionTypeNumMap.put("percentOfEveryTransactionTypeCalls", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("averageAmountOfEveryTransactionType"));
            everyTransactionTypeNumMap.put("averageAmountOfEveryTransactionType", resultset.getString(1));
            resultset = statement.executeQuery(queryMap.get("percentAmountOfEveryTransactionType"));
            everyTransactionTypeNumMap.put("percentAmountOfEveryTransactionType", resultset.getString(1));
            return everyTransactionTypeNumMap;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return null;
    }

    /**
     * Closes database connection.
     */
    public void closeDBConnection() {
        // Close the DB connection
        try {
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}