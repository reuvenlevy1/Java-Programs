import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

/**
 * Controls the ATM options chosen by the admin
 */

public class AdminATMMenu {
    Database db;
    /**
     * When the balance has been changed by either withdrawal or deposit
     */
    // ATM string options
    final static String ADD_ACCOUNT = "Add Account";
    // final static String DELETE_ACCOUNT = "DELETE ACCOUNT";
    final static String SIGN_OUT = "Sign Out";
    // ATM number options
    final static String ADD_ACCOUNT_NUM = "1";
    // final static String DELETE_ACCOUNT_NUM = "2";
    final static String SIGN_OUT_NUM = "2";
    // Other keywords
    final static String BACK = "Back";
    final static String QUIT = "Quit";

    static boolean accountAdded = false;

    AdminATMMenu(Map<String, String> accountDetails, Database db) throws FileNotFoundException, IOException {
        this.db = db;

        while (true) {
            Messages.adminATMMenuMessage();

            try {
                String selection = Main.userInput.nextLine().toLowerCase().trim();
                // check user input
                DataHandler.checkInputForQuit(selection);

                if (selection.equals(ADD_ACCOUNT_NUM) || selection.equals(ADD_ACCOUNT)) {
                    System.out.print(ADD_ACCOUNT.toUpperCase() + "\n" + Messages.returnToATMMenuMessage() + "\n");
                    boolean validRequirements = false;
                    boolean notDuplicate = false;
                    Map<String, String> newAccountDetails = new HashMap<>();

                    while (!validRequirements && !notDuplicate) {
                        validRequirements = false;
                        notDuplicate = false;
                        // Enter username
                        System.out.print(Messages.adminCreateUsernameMessage());
                        String username = Main.userInput.nextLine().trim();
                        // Check user input
                        DataHandler.checkInputForQuit(username);
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;
                        System.out.print(Messages.adminCreatePINMessage());
                        String pin = Main.userInput.nextLine().trim();
                        // Check user input
                        DataHandler.checkInputForQuit(pin);
                        if (username.toLowerCase().equals(BACK.toLowerCase()))
                            break;

                        newAccountDetails.put("username", username);
                        newAccountDetails.put("pin", pin);

                        // Check username and PIN requirement
                        if (checkRequirements(username, pin)) {
                            validRequirements = true;
                            // Check if username is a duplicate
                            if (!checkDupUsers(username))        //FIXME
                                notDuplicate = true;
                            else
                                System.out.println(Messages.accountCheckDuplicateUserErrorMessage());
                        } else
                            System.out.println(Messages.accountCheckRequirementsErrorMessage() + "\n");
                    }

                    if (validRequirements && notDuplicate) {
                        // Add new user to accounts.csv and create their own table
                        db.createUser(newAccountDetails.get("username"), newAccountDetails.get("pin"));

                        // CSVFileHandler.csvOpen = true;
                        accountAdded = true;
                        System.out.println();
                        System.out.println(
                                Messages.adminAccountCreatedMessage() + "\n\n" + Messages.exitMessage() + "\n\n");
                        break;
                    }
                    // } else if (selection.equals(DELETE_ACCOUNT_NUM) ||
                    // selection.toLowerCase().equals(DELETE_ACCOUNT)) {
                    // //DELETE ACCOUNT
                    // // Bring you to a confirmation page that will require you to type in your
                    // username to delete
                    // // Gives a successfully deleted account message
                    // System.out.print(DELETE_ACCOUNT.toUpperCase()+"\n"+Messages.returnToATMMenu()+"\n");
                    // System.out.println(Messages.accountDeleteConfirmationMessage());
                    // String confirmUserInput = Main.userInput.nextLine();
                    // // Check user input
                    // if (confirmUserInput.toLowerCase().equals(BACK.toLowerCase())) break;
                    // DataHandler.checkAdminInputForQuit(confirmUserInput, csv, csvOpen);

                    // // Checks if username matches confirmation user input
                    // if (confirmUserInput.equals(accountDetails.get("username"))) {
                    // csv.removeFromAccountsCSV(accountDetails);
                    // System.out.println(Messages.accountDeletedMessage(accountDetails));
                    // } else {
                    // System.out.println(Messages.accountDeleteConfirmFailMessage());
                    // }
                    // System.out.println(Messages.exitMessage());
                    // break;
                } else if (selection.equals(SIGN_OUT_NUM) || selection.equals(SIGN_OUT)) {
                    // SIGN OUT from account
                    System.out.println(SIGN_OUT.toUpperCase() + "\n" + Messages.signOutMessage() + "\n");
                    break;
                } else {
                    // Error message
                    System.out.println(Messages.atmMenuInvalidChoiceMessage());
                }
            } catch (InputMismatchException e) {
                System.out.println(Messages.atmMenuInvalidChoiceMessage());
                Main.userInput.nextLine();
            }
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

    /**
     * Checks if username already exists in accounts.csv
     * 
     * @param username
     * @param csv
     * @return
     */
    private boolean checkDupUsers(String username) {
        try {
            // csv.accountRecordsMap.get(username);
            if(db.isDupUsername(username))                     //FIXME
                return true;
            else
                return false;
        } catch (NullPointerException e) {
            return false;
        }
    }
}