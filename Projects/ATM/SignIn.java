// Add to checkRequirements()

/**
 * This class confirms the user's correct username and PIN has been entered.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignIn {
    /**
     * Holds username and pin: accountDetails["username"] accountDetails["pin"]
     */
    Map<String, String> accountDetails;
    String username;
    String pin;

    public SignIn(CSVFileHandler csv) throws FileNotFoundException, IOException {
        // Holds value for account being correct/incorrect
        boolean valid = false;
        accountDetails = new HashMap<String, String>();
        System.out.println(Messages.greetMessage());

        while (!valid) {
            // Enter username
            System.out.print(Messages.shutdownATMMessage()+"\n\n"+Messages.signInUsernameMessage());
            username = Main.userInput.nextLine().trim();
            DataHandler.checkInputForQuit(username, csv, CSVFileHandler.csvOpen);
            accountDetails.put("username", username);

            // Enter PIN
            System.out.print(Messages.signInPINMessage());
            pin = Main.userInput.nextLine().trim();

            DataHandler.checkInputForQuit(pin, csv, CSVFileHandler.csvOpen);
            accountDetails.put("pin", pin);

            // Check username and PIN requirement
            if(checkRequirements(username,pin)) {
                // Check if user account details are valid
                AccountsCheck validateAcc = new AccountsCheck(csv);
                valid = validateAcc.validAccount(accountDetails);
            } else System.out.println(Messages.accountCheckRequirementsErrorMessage()+"\n");
        }
        AccountsCheck validateAcc = new AccountsCheck(csv);
        
        if (validateAcc.verifyAdmin(accountDetails)) {
            // Send to ATMMenu page for valid account
            AdminATMMenu atm = new AdminATMMenu(accountDetails, csv);
        }
        // Call to read readUserCSV file for validated user     
        else if (csv.checkUserCSV(accountDetails)) {
            // Send to ATMMenu page for valid account
            ATMMenu atm = new ATMMenu(accountDetails, csv);
        } 
    }
    
    /**
     * 
     * @param username
     * @param pin
     * @return
     */
    private boolean checkRequirements(String username, String pin) {
        // Check if username contains invalid characters
        char[] usernameInvalidCharsList = "!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : usernameInvalidCharsList) {
            if (username.contains(Character.toString(invalidChar)))
                return false;
        }

        // Check if pin contains invalid characters
        char[] pinInvalidCharsList = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : pinInvalidCharsList) {
            if (pin.contains(Character.toString(invalidChar)))
                return false;
        }

        // Check if username is over 16 characters
        if (username.length() > 16) {
            return false;
        }

        // Check if PIN is not equal to 6 digits
        if (pin.length() != 6) {
            return false;
        }

        // Add more requirements in here
        return true;
    }
}