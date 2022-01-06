import java.util.HashMap;
import java.util.Map;

/**
 * The {@code SignIn} class confirms the user's correct username and PIN has been entered.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class SignIn {
    /**
     * Holds database connection information and other database
     * related methods for executing queries.
     */
    DatabaseHandler db;

    /**
     * Holds object for user validation and verification methods.
     */
    AccountsHandler ah;

    /**
     * {@code Map} that holds username and pin:
     * <p>{@code accountDetails["username"]}
     * <p>{@code accountDetails["pin"]}
     */
    Map<String, String> accountDetails;
    
    /**
     * Account username.
     */
    String username;

    /**
     * Account PIN.
     */
    String pin;

    /**
     * Uses a while loop to continously ask user for {@code username} and {@code pin}
     * until login deatils are valid. Will add user details into a Map, {@code accountDetails},
     * which will be used to verify the following:
     * <p>1. If the user input was set to "quit", which will exit the program
     * <p>2. If the {@code username} and {@code pin} meet character requirements
     * <p>3. If the login details provided match that of an Admin or a general user and send them
     * to the proper ATM menu.
     * 
     * @param db    Holds database connection information
     */
    public SignIn(DatabaseHandler db) {
        this.db = db;
        ah = new AccountsHandler(db);
        // Holds value for account being correct/incorrect
        boolean valid = false;
        accountDetails = new HashMap<>();
        System.out.println(Messages.greetMessage());
        AccountsHandler validateAcc = new AccountsHandler(db);

        while (!valid) {
            // Enter username
            System.out.print(Messages.shutdownATMMessage() + "\n\n"
                + Messages.signInUsernameMessage());
            username = Main.userInput.nextLine().trim();
            DataHandler.checkInputForQuit(username, db);
            accountDetails.put("username", username);

            // Enter PIN
            System.out.print(Messages.signInPINMessage());
            pin = Main.userInput.nextLine().trim();

            DataHandler.checkInputForQuit(pin, db);
            accountDetails.put("pin", pin);

            // Map of errors for account details requirement checking
            Map<String, Boolean> errorMap = new HashMap<>();
            errorMap = ah.checkAccountRequirements(username, pin);

            // Check if username and PIN requirements contained no errors
            if (errorMap.get("noErrors")) {
                // Check if user account details are valid
                valid = validateAcc.validAccount(accountDetails);
            } else
                System.out.println(Messages.accountCheckRequirementsErrorMessage(errorMap,
                    AccountsHandler.MIN_USERNAME_LEN, AccountsHandler.MAX_USERNAME_LEN,
                    AccountsHandler.REQUIRED_PIN_LEN) + "\n");
        }

        if (validateAcc.verifyAdmin(accountDetails)) {
            // Send to ATMMenu page for valid account
            AdminATMMenu atm = new AdminATMMenu(accountDetails, db, ah);
        }
        // Call to read readUserCSV file for validated user
        else {           // FIXME: Figure out how to work for users
            // Send to ATMMenu page for valid account
            ATMMenu atm = new ATMMenu(accountDetails, db, ah);
        }
    }
}