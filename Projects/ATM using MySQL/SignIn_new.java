// Add to checkRequirements()

/**
 * This class confirms the user's correct username and PIN has been entered.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignIn_new {
    Database db;
    /**
     * Holds username and pin: accountDetails["username"] accountDetails["pin"]
     */
    Map<String, String> accountDetails;
    String username;
    String pin;

    public SignIn_new(Database db) throws FileNotFoundException, IOException {
        this.db = db;
        // Holds value for account being correct/incorrect
        boolean valid = false;
        accountDetails = new HashMap<>();
        System.out.println(Messages.greetMessage());
        AccountsCheck validateAcc = new AccountsCheck(db);

        while (!valid) {
            // Enter username
            System.out.print(Messages.shutdownATMMessage() + "\n\n" + Messages.signInUsernameMessage());
            username = Main.userInput.nextLine().trim();
            DataHandler.checkInputForQuit(username);
            accountDetails.put("username", username);

            // Enter PIN
            System.out.print(Messages.signInPINMessage());
            pin = Main.userInput.nextLine().trim();

            DataHandler.checkInputForQuit(pin);
            accountDetails.put("pin", pin);

            // Check username and PIN requirement
            if (checkRequirements(username, pin)) {
                // Check if user account details are valid
                valid = validateAcc.validAccount(accountDetails);
            } else
                System.out.println(Messages.accountCheckRequirementsErrorMessage() + "\n");
        }

        if (validateAcc.verifyAdmin(accountDetails)) {
            // Send to ATMMenu page for valid account
            AdminATMMenu atm = new AdminATMMenu(accountDetails, db);
        }
        // Call to read readUserCSV file for validated user
        else {           // FIXME: Figure out how to work for users
            // Send to ATMMenu page for valid account
            ATMMenu atm = new ATMMenu(db);
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