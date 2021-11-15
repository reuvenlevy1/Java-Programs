
/**
 * This class reads from a csv file that acts as a DB of account information.
 */
import java.util.Map;
public class AccountsCheck {
    /**
     * CSVFileHandler object that holds accounts.csv 
     */
    CSVFileHandler accountCSV;

    /**
     * CSVFileHandler object that holds <user>.csv
     */
    CSVFileHandler userCSV;
    
    public AccountsCheck(CSVFileHandler accountCSV) {
        this.accountCSV = accountCSV;
    }
    
    public boolean validAccount(Map<String, String> accountDetails) {
        if (verifyUser(accountDetails)) {
            System.out.println("\n"+Messages.accountValidMessage());
            return true;
        } 
        else if (verifyUser(accountDetails) == false) {         //return <error message>;
            System.out.println(Messages.accountErrorMessage());
            return false;
        }
        // Shouldn't come here
        return false;
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
}
