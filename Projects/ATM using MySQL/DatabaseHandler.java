import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
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
    
    /**
     * Table name for accounts.
     */
    final static String ACCOUNT_TABLE = "account";
    
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
        String mysqlURL = "jdbc:mysql://localhost:3306/atm_app?useSSL=false";
        String dbUser = "root";
        String dbPass = "password";
        try {
            connect = DriverManager.getConnection(mysqlURL, dbUser, dbPass);
            if (connect != null) {
                System.out.println("Database connection is successful");
                statement = connect.createStatement();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a new user if the username doesn't exist.
     *
     * @param username  Account username
     * @param pin       Account PIN
     */
    public void createUser(String username, String pin) {
      
        // All tables will be created if they don't exist
        createAccountTable();
        addUserToAccountTable(username, pin);
        createUserTable(username);
        // Adds an initial transaction to the user table
        addTransactionToUserTable(username, NEW_TRANSACTION_ID, NEW_TRANSACTION_TYPE,
            NEW_AMOUNT, NEW_BALANCE);
    }

    /**
     * Adds the new user to the {@code ACCOUNT_TABLE} table.
     * 
     * @param username  Username input from the user
     * @param pin       Account PIN
     */
    private void addUserToAccountTable(String username, String pin) {
        String query = DBQueries.addUserToAccountTableQuery(username, pin, ACCOUNT_TABLE);
        // Run query
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Creates the {@code ACCOUNT_TABLE} table in DB
     */
    private void createAccountTable() {
        // Create table if doesn't exist
        String query = DBQueries.createAccountTableQuery(ACCOUNT_TABLE);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param username  Account username
     */
    private void createUserTable(String username) {
        String query = DBQueries.createUserTableQuery(username);
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param username  Account username
     * @param transactionType
     * @param amount
     * @param balance
     */
    public void addTransactionToUserTable(String username, int transactionID,
        String transactionType, Double amount, Double balance) {                //FIXME - Test for Withdraw and deposit
        // Execute query to update transaction_id numbers
        String updateQuery = DBQueries.updateUserTransactionIDsQuery(username);
        // Add new transaction
        String addQuery =
            DBQueries.addTransactionToUserTableQuery(username, transactionID, transactionType, amount, balance);
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
     * Verify if username and password details entered are correct /incorrect
     * 
     * @param accountDetails    {@code Map} that holds username and pin:
     *                          <p>{@code accountDetails["username"]}
     *                          <p>{@code accountDetails["pin"]}
     * @return                  {@code false} if {@code accountDetails} doesn't exist in database
     */
    public boolean verifyUser(Map<String, String> accountDetails) {
        // verify both username and PIN are valid
        String accountUser = accountDetails.get("username");
        String accountPin = accountDetails.get("pin");
        String query = DBQueries.verifyUserQuery(accountUser, accountPin, ACCOUNT_TABLE);

        // Run query and take result if exists
        try {
            ResultSet resultset = statement.executeQuery(query);

            if (resultset.next())
                if (resultset.getString("username").toLowerCase().equals(accountUser.toLowerCase())
                        && resultset.getString("pin").equals(accountPin))
                    return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    /**
     * Checks if username already exists in accounts.csv
     * 
     * @param username  Account username
     * @return
     */
    public boolean checkDupUsers(String username) {
        try {
            if(isDupUsername(username))
                return true;
            else
                return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * 
     * 
     * @param username  Account username
     * @return
     */
    private boolean isDupUsername(String username) {
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
            return false;
        }
    }

    /**
     * 
     * @param username  Account username
     * @return
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
     * 
     * 
     * @param username  Account username
     * @param maxTransNum
     * @return
     */
    public ArrayList<String[]> getUserTransactionHistory(String username, int maxTransNum) {
        ArrayList<String[]> transactionList = new ArrayList<>();
        String query = DBQueries.getUserTransactionListQuery(username, maxTransNum);
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
     * Removes username from list of accounts and drops user table
     * 
     * @param username  Account username
     */
    public boolean deleteUserAccount(String username) {
        String accountQuery = DBQueries.deleteUserFromAccountTableQuery(username, ACCOUNT_TABLE);
        String userQuery = DBQueries.deleteUserTableQuery(username);
        // Run queries
        try {
            statement.executeUpdate(accountQuery);
            statement.executeUpdate(userQuery);
            return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
    
    public boolean changeUserPIN(String username, String pin) {
        String query = DBQueries.changeUserPINQuery(username, pin, ACCOUNT_TABLE);
        // Run query
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 
     */
    public void listUsernames() {
        String query = DBQueries.listUsernamesQuery(ACCOUNT_TABLE);
        // Run query and take results if exists
        try {
            ResultSet resultset = statement.executeQuery(query);
            // Get usernames data
            while (resultset.next())
                System.out.println(resultset.getString(1));
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsHandler.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Closes database connection
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