
/**
 * This class reads from a csv file that acts as a DB of account information.
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountsCheck {
    Database db;
    // CSVFileHandler object that holds accounts.csv
    CSVFileHandler accountCSV;                                          //FIXME
    // CSVFileHandler object that holds <user>.csv
    CSVFileHandler userCSV;                                             //FIXME
    // Admin Account Username
    final String ADMIN_USERNAME = "admin";
    // Admin Account Password
    final String ADMIN_PASSWORD = "000000";

    public AccountsCheck(Database db) {
        this.db = db;
    }

    /**
     * 
     * @param accountDetails
     * @return
     */
    public boolean validAccount(Map<String, String> accountDetails) {
        if (verifyAdmin(accountDetails)) {
            System.out.println("\n" + Messages.adminAccountValidMessage());
            return true;
        }
        if (db.verifyUser(accountDetails)) { // FIXME: I left off here inside verifyUser()
            System.out.println("\n" + Messages.accountValidMessage());
            System.out.println(Messages.returnToATMMenuMessage());
            return true;
        } else {
            System.out.println(Messages.accountErrorMessage());
            return false;
        }
    }

    /**
     * Verify if username and password details entered are correct admin credentials
     * 
     * @param accountDetails
     * @return
     */
    public boolean verifyAdmin(Map<String, String> accountDetails) {
        // verify both username and PIN are valid
        String accountUser = accountDetails.get("username");
        String accountPin = accountDetails.get("pin");

        if (accountUser.toLowerCase().equals(ADMIN_USERNAME) && accountPin.equals(ADMIN_PASSWORD))
            return true;
        return false;
    }
}
