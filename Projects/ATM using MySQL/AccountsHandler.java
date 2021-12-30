import java.util.HashMap;
import java.util.Map;

/**
 * The {@code AccountsCheck} class handles information related to user
 * login/creation validation requirements and verification of account details.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Dec-25-2021
 */
public class AccountsHandler {
    /**
     * Holds database connection information and other database
     * related methods for executing queries.
     */
    DatabaseHandler db;

    /**
     * Admin account username.
     */
    final String ADMIN_USERNAME = "admin";
    
    /**
     * Admin account password.
     */
    final String ADMIN_PASSWORD = "000000";

    /**
     * Minimum number of characters for username.
     */
    final static int MIN_USERNAME_LEN = 4;

    /**
     * Maximum number of characters for username.
     */
    final static int MAX_USERNAME_LEN = 16;

    /**
     * Required number of digits for pin.
     */
    final static int REQUIRED_PIN_LEN = 6;

    /**
     * Map of errors for account details requirement checking. Keys and values are set in
     * {@code checkRequirements()} method.
     * 
     * <p><p>Value will be {@code true} for all
     * errors that are found with username or pin requirements. The value for {@code noErrors} key
     * will be {@code true} only if every other value is {@code false}, meaning no errors were
     * found.
     * <p> Keys:
     * <p> {@code noErrors}
     * <p> {@code usernameInvalidChar}
     * <p> {@code usernameFirstCharNotLetter}
     * <p> {@code usernameUnderMinLen}
     * <p> {@code usernameOverMaxLen}
     * <p> {@code pinInvalidChar}
     * <p> {@code pinNotReqLen}
     */
    Map<String, Boolean> errorMap;

    /**
     * Constructor that sets the {@code db} object.
     * 
     * @param db    Holds database connection information and other database
     *              related methods for executing queries.
     */
    public AccountsHandler(DatabaseHandler db) {
        this.db = db;
    }

    /**
     * Checks {@code username} and {@code pin} for invalid characters and invalid
     * charcter lengths.
     * 
     * @param username  Username input from the user
     * @param pin       PIN inputted from the user
     * @return          {@code true} if username and PIN meet requirements
     */
    public Map<String, Boolean> checkUserRequirements(String username, String pin) {
        return checkRequirements(username, pin);
    }
    
    /**
     * Private method that checks {@code username} and {@code pin} for both invalid
     * characters and invalid character lengths.
     * 
     * @param username  Username input from the user
     * @param pin       PIN inputted from the user
     * @return          {@code true} if username and PIN meet requirements
     */
    private Map<String, Boolean> checkRequirements(String username, String pin) {        
        // Set map keys to default values
        errorMap = new HashMap<>() {{
            put("noErrors", true);
            put("usernameInvalidChar", false);
            put("usernameFirstCharNotLetter", false);
            put("usernameUnderMinLen", false);
            put("usernameOverMaxLen", false);
            put("pinInvalidChar", false);
            put("pinNotReqLen", false);
        }};

        // Check if username contains invalid characters
        char[] usernameInvalidCharsList = "!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : usernameInvalidCharsList) {
            if (username.contains(Character.toString(invalidChar))) {
                errorMap.replace("usernameInvalidChar", true);
                errorMap.replace("noErrors", false);
                break;
            }
        }
        
        // Check if username doesn't start with a letter
        if (!username.toLowerCase().matches("^[a-z]+[0-9]*")) {
            errorMap.replace("usernameFirstCharNotLetter", true);
            errorMap.replace("noErrors", false);
        }

        // Check if username is under minimum number of characters
        if (username.length() < MIN_USERNAME_LEN) {
            errorMap.replace("usernameUnderMinLen", true);
            errorMap.replace("noErrors", false);
        }

        // Check if username is over maximum number of characters
        if (username.length() > MAX_USERNAME_LEN) {
            errorMap.replace("usernameOverMaxLen", true);
            errorMap.replace("noErrors", false);
        }

        // Check if pin contains invalid characters
        char[] pinInvalidCharsList = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : pinInvalidCharsList) {
            if (pin.contains(Character.toString(invalidChar))) {
                errorMap.replace("pinInvalidChar", true);
                errorMap.replace("noErrors", false);
                break;
            }
        }

        // Check if pin is not equal to REQUIRED_PIN_LEN digits
        if (pin.length() != REQUIRED_PIN_LEN) {
            errorMap.replace("pinNotReqLen", true);
            errorMap.replace("noErrors", false);
        }

        return errorMap;
    }

    /**
     * Checks account details to see if they have an existing admin account,
     * user account or neither.
     * 
     * @param accountDetails    {@code Map} that holds username and pin:
     *                          <p>{@code accountDetails["username"]}
     *                          <p>{@code accountDetails["pin"]}
     * @return                  {@code true} if {@code accountDetails} match
     *                          an existing account.
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
     * Verify if username and password details are correct admin credentials
     * 
     * @param accountDetails    {@code Map} that holds username and pin:
     *                          <p>{@code accountDetails["username"]}
     *                          <p>{@code accountDetails["pin"]}
     * @return                  {@code true} if {@code accountDetails} match
     *                          the existing admin account.
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
