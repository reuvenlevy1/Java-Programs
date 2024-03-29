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
     * <p>1. If the user input was set to "quit", which will exit the program.
     * <p>2. If the {@code username} and {@code pin} meet character requirements.
     * <p>3. If the login details provided match that of an Admin or a general user and send the
     * to the proper ATM menu.
     * 
     * @param db    Holds database connection information.
     */
    public SignIn(DatabaseHandler db) {
        this.db = db;
        ah = new AccountsHandler(db);
        // Holds value for account being correct/incorrect
        boolean valid = false;
        // Holds value for username passing requirements check
        boolean validUser = false;
        // Holds value for PIN passing requirements check
        boolean validPIN = false;
        accountDetails = new HashMap<>();
        System.out.println(Messages.greetMessage());
        AccountsHandler validateAcc = new AccountsHandler(db);

        while (!valid) {
            validUser = false;
            validPIN = false;
            // Enter username
            System.out.print(Messages.shutdownATMMessage() + "\n\n"
                + Messages.signInUsernameMessage());
            username = Main.userInput.nextLine().trim();
            // Check user input
            if (!DataHandler.checkInput(username, db))
                break;
            accountDetails.put("username", username);
            validUser = true;

            // Enter PIN
            System.out.print(Messages.signInPINMessage());
            pin = Main.userInput.nextLine().trim();

            // Check user input
            if (!DataHandler.checkInput(pin, db))
                break;
            accountDetails.put("pin", pin);
            validPIN = true;

            // Map of errors for account details requirement checking
            Map<String, Boolean> errorMap = new HashMap<>();
            errorMap = ah.checkAccountRequirements(username, pin);

            // Check if username and PIN requirements contained no errors
            if (errorMap.get("noErrors")) {
                // Check if user account details exist for login
                valid = validateAcc.validAccount(accountDetails);
            } else
                System.out.println(Messages.accountCheckRequirementsErrorMessage(errorMap,
                    AccountsHandler.MIN_USERNAME_LEN, AccountsHandler.MAX_USERNAME_LEN,
                    AccountsHandler.REQUIRED_PIN_LEN) + "\n");
        }

        if (validUser && validPIN && validateAcc.verifyAdmin(accountDetails)) {
            // Create new stats object
            StatsHandler sh = new StatsHandler(db);
            // Send to AdminATMMenu page for valid admin account
            AdminATMMenu atm = new AdminATMMenu(accountDetails, db, ah, sh);                // FIXME: finish implementing maxTransactionsNum to match user input and IF nothing was placed, use the default in .ini file. See how AdminATMMenu does this
        } else if (validUser && validPIN) {
            // Create new stats object
            StatsHandler sh = new StatsHandler(db);
            // Send to ATMMenu page for valid account
            ATMMenu atm = new ATMMenu(accountDetails, db, ah, sh);                          // FIXME: finish changing all instances of maxTransactionsNum to match user input and IF nothing was placed, use the default in .ini file. See how AdminATMMenu does this
        }
    }
}