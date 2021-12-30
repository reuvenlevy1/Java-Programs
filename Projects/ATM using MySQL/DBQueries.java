/**
 * 
 */
public class DBQueries {

    public static String createAccountTableQuery() {
        return "CREATE TABLE IF NOT EXISTS account (" + "account_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "username varchar(16) NOT NULL," + "pin INT NOT NULL);";
    }

    public static String createUserTableQuery(String username) {            //FIXME add columns that match first row in transaction history
        return "CREATE TABLE IF NOT EXISTS " + username + " (" + "transaction_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "transaction_type varchar(11) NOT NULL," + "amount FLOAT NOT NULL, balance FLOAT NOT NULL);";
    }

    public static String addUserToAccountTableQuery(String username, String pin) {
        return "INSERT INTO account (username, pin) values (\"" + username + "\", "
            + Integer.parseInt(pin) + ");";
    }

    public static String addTransactionToUserTableQuery(String username, String transactionType, Double amount, Double balance) {
        return "INSERT INTO " + username + " (transaction_type, amount, balance) values (\""
            + transactionType + "\", " + amount + ", " + balance + ");";
    }

    public static String verifyUserQuery(String accountUser, String accountPIN, String ACCOUNT) {
        return  "SELECT username, pin FROM " + ACCOUNT + " WHERE username='" + accountUser + "' AND pin="
            + Integer.parseInt(accountPIN) + ";";
    }

    public static String isDupUsernameQuery(String username, String ACCOUNT) {
        return  "SELECT username FROM " + ACCOUNT + " WHERE username='" + username + "'" ;
    }
    
}
