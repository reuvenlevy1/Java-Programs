// Things to fix:
// Read the accounts.csv does not read in comments.
// Removing a line from accounts.csv, requires all information to be rewritten. This will lose all the comments

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Reads/writes to/from CSV files
 */
public class CSVFileHandler {
    // Directory of all CSV files
    public String csvPath = System.getProperty("user.dir") + "\\ATM\\csv files\\";
    // Location of a CSV file that holds all username and pin data for every user
    final String ACCOUNTSCSV = csvPath + "accounts.csv";

    private static final String COMMA_DELIMITER = ",";
    /**
     * Holds all the accounts.csv data in username:pin format All data will be
     * manipulated here and added to file at end of transaction
     */
    Map<String, String> accountRecordsMap = new LinkedHashMap<String, String>();
    /**
     * Holds all the <user>.csv data in a list of transactions that hold an array of
     * transactionID,transactionType,amount,balance
     */
    ArrayList<String[]> userRecordsList = new ArrayList<String[]>();

    FileWriter fw;

    static boolean csvOpen = false;

    // Manually put it the username and pin to account for comment in map
    public CSVFileHandler() {
    }

    /**
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void checkAccountsCSV() throws FileNotFoundException, IOException {
        readAccountsCSV();
    }

    /**
     * 
     * @param accountDetails
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean checkUserCSV(Map<String, String> accountDetails) throws FileNotFoundException, IOException {
        if (userRecordsList.size() == 0) {
            return readUserCSV(accountDetails);
        } else {
            userRecordsList = new ArrayList<String[]>();
            return readUserCSV(accountDetails);
        }

    }

    /**
     * Reads accounts.csv file and fills values within hashmap
     * 
     * @param filename
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void readAccountsCSV() throws FileNotFoundException, IOException { // Set this up to read any csv file to
                                                                               // work for both accountCSV and UserCSV
        /**
         * Using BufferedReader instead of Scanner class for better efficiency reading
         * line by line
         */
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNTSCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                accountRecordsMap.put(values[0], values[1]);
            }
            br.close();
        }
    }

    /**
     * Reads accounts.csv file and fills values within hashmap
     * 
     * @param accountDetails
     * @throws FileNotFoundException
     * @throws IOException
     */
    private boolean readUserCSV(Map<String, String> accountDetails) throws FileNotFoundException, IOException {
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username") + ".csv";

        /**
         * Using BufferedReader instead of Scanner class for better efficiency reading
         * line by line
         */
        try (BufferedReader br = new BufferedReader(new FileReader(userCSV))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] values = new String[4];
                values = line.split(COMMA_DELIMITER);
                userRecordsList.add(values);
            }
            br.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(Messages.fileNotFoundExceptionMessage(userCSV));
            return false;
        }
    }

    /**
     * Write all new transactions to <user>.csv file
     * 
     * @param accountDetails
     * @param currentTransaction
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void addToUserCSV(Map<String, String> accountDetails, String[] currentTransaction)
            throws FileNotFoundException, IOException {
        // Gets array from userRecordsList
        String[] tempArray;
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username") + ".csv";

        if (userRecordsList.size() == 31)
            // Removes last transaction (#30)
            userRecordsList.remove(userRecordsList.size() - 1);

        if (userRecordsList.size() > 1) {
            // Increments remaining 29 transactionIDs by 1
            for (int i = 1; i < userRecordsList.size(); i++) {
                tempArray = userRecordsList.get(i);
                // Increments transactionID by 1
                tempArray[0] = Integer.toString(i + 1);
                // Sets entire array into userRecordsList
                userRecordsList.set(i, tempArray);
            }
        } else {
            userRecordsList.add(0, new String[] { "#transactionID", "transactionType", "amount", "balance" });
        }

        // Add at index 1 to not move comment in <user>.csv
        userRecordsList.add(1, currentTransaction);

        // Write list of transactions to <user>.csv
        try {
            fw = new FileWriter(userCSV);
            Boolean newLineFlag = false;

            for (String[] key : userRecordsList) {
                int count = 0;
                if (newLineFlag)
                    fw.write("\n");
                for (String subkey : key) {
                    fw.write(subkey);
                    // Avoid placing a comma for the last subkey
                    // if (count != userRecordsList.get(0).length-1) {
                    if (count != key.length - 1) {
                        fw.write(",");
                    }
                    count++;
                }
                newLineFlag = true;
            }
            fw.flush();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Appends account username and pin to the end of accounts.csv in csv format
     * 
     * @param accountDetails
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected void addToAccountsCSV(Map<String, String> accountDetails) throws FileNotFoundException, IOException {

        try {
            // The true parameter will append to the file
            fw = new FileWriter(ACCOUNTSCSV, true);
            // Appends string to file
            fw.write("\n" + accountDetails.get("username") + "," + accountDetails.get("pin"));
            fw.flush();
            createUserCSV(accountDetails);
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    /**
     * Creates <user>.csv file
     * 
     * @param accountDetails
     * @throws FileNotFoundException
     * @throws IOException
     */
    private void createUserCSV(Map<String, String> accountDetails) throws FileNotFoundException, IOException {
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username") + ".csv";

        try {
            File file = new File(userCSV);
            boolean fileCreated = file.createNewFile();

            if (fileCreated)
                System.out.println("File created successfully");
            else
                System.out.println("File already exists");
        } catch (IOException ex) {
            System.out.println("Exception : ");
            ex.printStackTrace();
        }
    }

    /**
     * Removes an account username and pin from accounts.csv
     * 
     * @param accountDetails
     */
    protected void removeFromAccountsCSV(Map<String, String> accountDetails) {
        accountRecordsMap.remove(accountDetails.get("username"));

        // Write list of usernames and pins to accounts.csv
        try {
            fw = new FileWriter(ACCOUNTSCSV);
            Boolean newLineFlag = false;
            for (String key : accountRecordsMap.keySet()) {
                if (newLineFlag)
                    fw.write("\n");
                fw.write(key + "," + accountRecordsMap.get(key));
                newLineFlag = true;
            }
            fw.flush();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
        removeFromUserCSV(accountDetails);
    }

    /**
     * Delete <user>.csv file
     * 
     * @param accountDetails
     */
    private void removeFromUserCSV(Map<String, String> accountDetails) {
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username") + ".csv";
        File file = new File(userCSV);

        if (file.delete())
            System.out.println("File deleted successfully");
        else
            System.out.println("Failed to delete the file");
    }
}