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
        System.out.println("\n"+Main.msg.greetMessage());

        while (!valid) {
            // Enter username
            System.out.println("\n"+Main.msg.shutdownATMMessage()+"\n"+Main.msg.signInUsernameMessage());
            username = Main.userInput.nextLine().trim();
            checkInputForQuit(username);
            accountDetails.put("username", username);

            // Enter PIN
            System.out.println(Main.msg.signInPINMessage());
            pin = Main.userInput.nextLine();            // FIXME: must perform a check to make sure PIN is only numbers.
            // Do a try and catch statmement to make the pin equal to an int
                // we do this to make sure if the user types in a string, it won't crash the program, but
                // should inform the user to re-enter password
                // If this works, can delete the PIN checks for anything that isn't an integer
            

            checkInputForQuit(pin);
            accountDetails.put("pin", pin);

            // Check username and PIN requirement
            if(checkRequirements(username,pin)) {
                // Check if user account details are valid
                AccountsCheck validateAcc = new AccountsCheck(csv);
                valid = validateAcc.validAccount(accountDetails);
            } else System.out.println(Main.msg.accountCheckRequirementsErrorMessage());
        }
        // Call to read readUserCSV file for validated user     
        if (csv.checkUserCSV(accountDetails)){
            // Send to ATMMenu page for valid account
            ATMMenu atm = new ATMMenu(accountDetails, csv);
        }
    }

    private boolean checkRequirements(String username, String pin) {
        // Check if username contains invalid characters
        char[] usernameInvalidCharsList = "!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : usernameInvalidCharsList){
            if (username.contains(Character.toString(invalidChar))){
                return false;
                // return exact error message: "username contains 1 or more invalid characters. Please make sure to use only alpha-numeric characters."
            }
        }

        // Check if pin contains invalid characters
        char[] pinInvalidCharsList = "abcdefghijklmnopqrstuvwxyz!@#$%^&*()-_=+`~\\|[];:'\",./?".toCharArray();
        for (char invalidChar : pinInvalidCharsList){
            if (pin.contains(Character.toString(invalidChar))){
                return false;
                // return exact error message: "pin contains 1 or more invalid characters. Please make sure to use only numeric characters."
            }
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

    /**
     * Checks if username already exists in accounts.csv
     * 
     * @return
     */
    private boolean checkDupUsers(String username, CSVFileHandler csv) {
        try {
            csv.accountRecordsMap.get(username);
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public static void checkInputForQuit(String input) {
        if (input.equals("quit".toLowerCase())) {
            System.exit(0);
        }
    }

}