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
    /**
     * Directory of all CSV files
     */
    public String csvPath = System.getProperty("user.dir")+"\\ATM\\csv files\\";

    /**
     * Location of a CSV file that holds all username and pin data for every user
     */
    // final String ACCOUNTSCSV = "C:/Users/Reuven/Java Projects/ATM/accounts.csv";        //FIXME: Include a relative path (or MACHINE_PATH + RELATIVE PATH)
    final String ACCOUNTSCSV = csvPath+"accounts.csv";
    
    /**
     *  Holds all the accounts.csv data in username:pin format
     *  All data will be manipulated here and added to file at end
     *  of transaction
     */
    Map<String,String> accountRecordsMap = new LinkedHashMap<String,String>();

    /**
     * Holds all the <user>.csv data in a list of transactions that hold an array of
     * transactionID,transactionType,amount,balance
     */
    ArrayList<String[]> userRecordsList = new ArrayList<String[]>();

    /**
     * Constants
     */
    private static final String COMMA_DELIMITER = ",";

    /**
     * FileWriter
     */
    FileWriter fw;
    
    // Manually put it the username and pin to account for comment in map
    public CSVFileHandler(){}

    public void checkAccountsCSV() throws FileNotFoundException, IOException {
        if (accountRecordsMap.size() == 0) {
            readAccountsCSV();
        }
    }

    // void checkUserCSV(Map<String, String> accountDetails) throws FileNotFoundException, IOException {
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
    private void readAccountsCSV() throws FileNotFoundException, IOException {          // Set this up to read any csv file to work for both accountCSV and UserCSV
        
        /**
        * Using BufferedReader instead of Scanner class for better
        * efficiency reading line by line
        */ 
        // List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ACCOUNTSCSV))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                accountRecordsMap.put(values[0],values[1]);
            }
            br.close();
        }
    }

    /**
     * Appends account username and pin to the end of accounts.csv
     * in csv format
     * 
     * @param accountDetails
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void addToAccountsCSV(Map<String,String> accountDetails) throws FileNotFoundException, IOException {
        
        try{
            // The true parameter will append to the file
            fw = new FileWriter(ACCOUNTSCSV,true);
            // Appends string to file
            fw.write("\n"+accountDetails.get("username")+","+accountDetails.get("pin"));
            fw.flush();
        }catch(IOException e){
            System.err.println("IOException: " + e.getMessage());
        }     
    }

    /**
     * Removes an account username and pin from accounts.csv
     * 
     * @param accountDetails
     */
    public void removeFromAccountsCSV(Map<String,String> accountDetails){
        accountRecordsMap.remove(accountDetails.get("username"));
        
        // Write list of usernames and pins to accounts.csv
        try{
            fw = new FileWriter(ACCOUNTSCSV);
            Boolean newLineFlag = false;
            for (String key : accountRecordsMap.keySet()) {
                if (newLineFlag) fw.write("\n");
                fw.write(key + "," + accountRecordsMap.get(key));
                newLineFlag = true;
            }
            fw.flush();
        }catch(IOException e){
            System.err.println("IOException: " + e.getMessage());
        }
        removeFromUserCSV(accountDetails);
    }

    /**
     * Reads accounts.csv file and fills values within hashmap
     * 
     * @param accountDetails
     * @throws FileNotFoundException
     * @throws IOException
     */
    private boolean readUserCSV(Map<String,String> accountDetails) throws FileNotFoundException, IOException {
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username")+".csv";
        
        /**
        * Using BufferedReader instead of Scanner class for better
        * efficiency reading line by line
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
            System.out.println(Main.msg.fileNotFoundExceptionMessage(userCSV));
            return false;
            // System.exit(0);
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
    public void addToUserCSV(Map<String,String> accountDetails, String[] currentTransaction) throws FileNotFoundException, IOException {
        // Gets array from userRecordsList
        String[] tempArray;
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username")+".csv";
        
        if (userRecordsList.size() == 31) 
            // Removes last transaction (#30)
            userRecordsList.remove(userRecordsList.size()-1);

        // Increments remaining 29 transactionIDs by 1
        for (int i=1; i<userRecordsList.size(); i++) {
            tempArray = userRecordsList.get(i);
            // Increments transactionID by 1
            tempArray[0] = Integer.toString(i+1);
            // Sets entire array into userRecordsList
            userRecordsList.set(i, tempArray);
        }

        // Add at index 1 to not move comment in <user>.csv
        userRecordsList.add(1, currentTransaction);

        // Write list of transactions to <user>.csv
        try{
            fw = new FileWriter(userCSV);
            Boolean newLineFlag = false;
            
            
            // Needs to be changed to work with userRecordsList --> which is an ArrayList
            // Data being added will look like:
               // #transactionID,transactionType,amount,balance
               // 1,withdrawal,250.00,1000.00
               // 2,deposit,420.00,1420.00
            /*
            for (String key : accountRecordsMap.keySet()) {
                if (newLineFlag) fw.write("\n");
                fw.write(key + "," + accountRecordsMap.get(key));
                newLineFlag = true;
            }
            */
            for (String[] key : userRecordsList) {
                int count = 0;
                if (newLineFlag) fw.write("\n");
                for (String subkey : key){
                    fw.write(subkey);
                    // Avoid placing a comma for the last subkey
                    if (count != userRecordsList.get(0).length-1) {
                        fw.write(",");
                    }
                    count++;
                }
                newLineFlag = true;
            }
            fw.flush();
        }catch(IOException e){
            System.err.println("IOException: " + e.getMessage());
        }
        
        /* ADDING TRANSACTIONS
            This code is how to remove last transaction, increment remaining 29 transactionIDs by 1 and add new transaction to top of arraylist
                ArrayList<String> cars = new ArrayList<String>();
                cars.add("1");
                cars.add("2");
                cars.add("3");
                cars.add("4");
                cars.add("5");
                System.out.println(cars);
                System.out.println("Remove: "+cars.get(0));
                cars.remove(cars.size()-1);
                System.out.println(cars.get(0));
                System.out.println(cars);
                
                for(int i=0; i<cars.size(); i++){
                cars.set(i, Integer.toString(i+2));
                }
                
                cars.add(0,"1");
                System.out.println(cars);
                -------------------------------------------------


                // How to call specific values in an ArrayList of Arrays:
                
                // Java ArrayList of Arrays 
                import java.io.*; 
                import java.util.*; 
                
                class Main { 
                    public static void main(String[] args) 
                    { 
                        // create an ArryaList of String Array type 
                        ArrayList<String[]> list = new ArrayList<String[]>(); 
                        String[] names = new String[4];
                        String name1 = "A1";
                        String name2 = "A2";
                        String name3 = "A3";
                        String name4 = "A4";
                        
                        for (int i=0; i<3; i++){
                            String name01 = (i+1)+name1;
                            String name02 = (i+1)+name2;
                            String name03 = (i+1)+name3;
                            String name04 = (i+1)+name4;
                            String name_all = name01+","+name02+","+name03+","+name04;
                            System.out.println(name_all);
                            names = name_all.split(",");
                            
                            // create a string array called Names 
                            // names = name_all.split(",");
                            
                            list.add(names);
                        }
                        
                        // for (String num : names ){
                        //     System.out.println(num);
                        // }
                                
                        // add the above arrays to ArrayList Object 
                        // list.add(names); 
                        // names[] = { "B1", "B2", "B3", "B4" }; 
                        // list.add(names);
                        // names[] = { "C1", "C2", "C3", "C4" }; 
                        // list.add(names3);
                        
                        // print arrays from ArrayList 
                        for (String i[] : list) { 
                            System.out.println(Arrays.toString(i)); 
                        }
                        System.out.println(list.get(1)[3]);
                        System.out.println(list.size());
                        
                    } 
                } 
            */
    }

    /**
     * Delete <user>.csv file
     * 
     * @param accountDetails
     */
    private void removeFromUserCSV(Map<String,String> accountDetails) {
        // Location <user>.csv, which holds all transactional data for a specific user
        String userCSV = csvPath + accountDetails.get("username")+".csv";
        File file = new File(userCSV);
          
        if(file.delete()) { 
            System.out.println("File deleted successfully"); 
        } else { 
            System.out.println("Failed to delete the file"); 
        } 
    }

}