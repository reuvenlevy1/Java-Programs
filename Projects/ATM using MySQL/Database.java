import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

// import java.sql.PreparedStatement;
public class Database {
    // Database connection
    Connection connect = null;
    Statement statement = null;
    // Table name for accounts
    final static String ACCOUNT_TABLE = "account";
    // Table name for specific user accounts
    final String ACCOUNT = "account";

    public Database() {
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
     * Creates user if it doesn't exist
     *
     * @param query SQL query for inserting username and pin
     */
    public void createUser(String username, String pin) {
        // ATM Transaction menu selection name
        String transactionType = "New Account";
        // Logged in user's most recent transaction's balance from <user> table in USD
        Double balance = 0.0;
        // The user's input in USD
        Double amount = 0.0;
        String query = DBQueries.addUserToAccountTableQuery(username, pin);    
        // All tables will be created if they don't exist
        createAccountTable();
        addUserToAccountTable(query);
        createUserTable(username);
        addTransactionToUserTable(username, transactionType, amount, balance);
    }

    /**
     * 
     * 
     * @param query
     */
    private void addUserToAccountTable(String query) {
        // Run query and take result
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsCheck.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Creates the account table in DB
     */
    private void createAccountTable() {
        // Create table if doesn't exist
        String query = DBQueries.createAccountTableQuery();
        try {
            statement.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param username
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
     * @param username
     * @param transactionType
     * @param amount
     * @param balance
     */
    public void addTransactionToUserTable(String username, String transactionType, Double amount, Double balance) {
        String query = DBQueries.addTransactionToUserTableQuery(username, transactionType, amount, balance);
        try {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsCheck.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * Verify if username and password details entered are correct /incorrect
     * 
     * @param accountDetails
     * @return
     */
    public boolean verifyUser(Map<String, String> accountDetails) {
        // verify both username and PIN are valid
        String accountUser = accountDetails.get("username");
        String accountPin = accountDetails.get("pin");
        String query = DBQueries.verifyUserQuery(accountUser, accountPin, ACCOUNT);

        // Run query and take result
        try {
            ResultSet resultset = statement.executeQuery(query);
            // Get single row results back if exists
            if (resultset.next())
                if (resultset.getString("username").toLowerCase().equals(accountUser.toLowerCase())
                        && resultset.getString("pin").equals(accountPin))
                    return true;
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsCheck.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return false;
    }

    public void executeQuery(String query) {                        // should be set to private as this function should not be called outside this class
        // Run query and take result
        try {
            ResultSet resultset = statement.executeQuery(query);
            // Get results back if exists
            if (resultset.next())
                System.out.println(resultset.getString(1)); // FIXME: Need to test after using admin to create a user.
                                                            // What happens when it doesn't find a user? Instead of
                                                            // logging error, it SHOULD show error of username doesn't
                                                            // exist.
        } catch (SQLException ex) {
            Logger logger = Logger.getLogger(AccountsCheck.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    /**
     * 
     * @return
     */
    public boolean isDupUsername(String username) {
        String query = DBQueries.isDupUsernameQuery(username, ACCOUNT);
        // Run query and take result
        try {
            ResultSet resultset = statement.executeQuery(query); // FIXME: This currently breaks with the
                                                                 // createTableQuery above, why?
            // Get results back if exists
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
            Logger logger = Logger.getLogger(AccountsCheck.class.getName());
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }

    // FIXME need to change csv below for MySQL queries
    // public boolean checkUserData(Map<String, String> accountDetails) {
    //     if (userRecordsList.size() == 0) {
    //         return readUserCSV(accountDetails);
    //     } else {
    //         userRecordsList = new ArrayList<String[]>();
    //         return readUserCSV(accountDetails);
    //     }
    // }

    // private boolean readUserCSV(Map<String, String> accountDetails) throws FileNotFoundException, IOException {
    //     // Location <user>.csv, which holds all transactional data for a specific user
    //     String userCSV = csvPath + accountDetails.get("username") + ".csv";

    //     /**
    //      * Using BufferedReader instead of Scanner class for better efficiency reading
    //      * line by line
    //      */
    //     try (BufferedReader br = new BufferedReader(new FileReader(userCSV))) {
    //         String line;

    //         while ((line = br.readLine()) != null) {
    //             String[] values = new String[4];
    //             values = line.split(COMMA_DELIMITER);
    //             userRecordsList.add(values);
    //         }
    //         br.close();
    //         return true;
    //     } catch (FileNotFoundException e) {
    //         System.out.println(Messages.fileNotFoundExceptionMessage(userCSV));
    //         return false;
    //     }
    // }




}