/**
 * This class reads from a csv file that acts as a DB of account information.
 */
import java.util.Map;
public class AccountsCheck {
    // CSVFileHandler object that holds accounts.csv
    CSVFileHandler accountCSV;
    // CSVFileHandler object that holds <user>.csv
    CSVFileHandler userCSV;
    // Admin Account Username
    final String ADMIN_USERNAME = "Admin";
    // Admin Account Password
    final String ADMIN_PASSWORD = "000000";
    
    /**
     * 
     * @param accountCSV
     */
    public AccountsCheck(CSVFileHandler accountCSV) {
        this.accountCSV = accountCSV;
    }
    
    /**
     * 
     * @param accountDetails
     * @return
     */
    public boolean validAccount(Map<String, String> accountDetails) {
        if (verifyAdmin(accountDetails)) {
            System.out.println("\n"+Messages.adminAccountValidMessage());
            return true;
        }
        if (verifyUser(accountDetails)) {
            System.out.println("\n"+Messages.accountValidMessage());
            System.out.println(Messages.returnToATMMenuMessage());
            return true;
        } else {
            System.out.println(Messages.accountErrorMessage());
            return false;
        }
    }

    /**
     * Verify if username and password details entered are correct
     * /incorrect
     * 
     * @param accountDetails
     * @return
     */
    private boolean verifyUser(Map<String, String> accountDetails) {
        //verify both username and PIN are valid
        String accountUser = accountDetails.get("username");
        String accountPin = accountDetails.get("pin");
        
        if(accountCSV.accountRecordsMap.containsKey(accountUser)) {
            if (accountCSV.accountRecordsMap.get(accountUser).equals(accountPin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verify if username and password details entered are correct admin
     * credentials
     * 
     * @param accountDetails
     * @return
     */
    public boolean verifyAdmin(Map<String, String> accountDetails) {
        //verify both username and PIN are valid
        String accountUser = accountDetails.get("username");
        String accountPin = accountDetails.get("pin");
        
        if(accountUser.equals(ADMIN_USERNAME) && accountPin.equals(ADMIN_PASSWORD))
            return true;
        return false;
    }
}
