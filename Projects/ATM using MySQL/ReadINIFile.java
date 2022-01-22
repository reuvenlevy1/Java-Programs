import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * The {@code READINIFile} class gets user configuration details from the {@code atm_details.ini}
 * file. This information is for database connection, main table name for all accounts and username
 * and PIN requirements.
 * 
 * @author Reuven Levy
 * @version 1.0
 * @since Jan-15-2022
 */
public class ReadINIFile {
    /**
     * {@code atm_details.ini} file path.
     */
    public final String INI_FILEPATH = System.getProperty("user.dir") + "\\atm_details.ini";

    /**
     * Database hostname.
     */
    public static String DB_HOSTNAME;

    /**
     * Database port number.
     */
    public static String DB_PORT;
    
    /**
     * Database name.
     */
    public static String DB_NAME;

    /**
     * Database username.
     */
    public static String DB_USERNAME;

    /**
     * Database password.
     */
    public static String DB_PASSWORD;
    
    /**
     * MySQL URL String for database connection.
     */
    public static String MYSQL_URL;

    /**
     * Username required minimum character length.
     */
    public static String MIN_USERNAME_LEN;
    
    /**
     * Username required maximum character length.
     */
    public static String MAX_USERNAME_LEN;

    /**
     * PIN required number of digits.
     */
    public static String REQUIRED_PIN_LEN;

    /**
     * Default name of the table created to hold all user account data.
     */
    public static String ACCOUNT_TABLE;

    /**
     * Reads {@code atm_details.ini} file and gets database, max/min user length, PIN length
     * and account table name information.
     * 
     * @throws FileNotFoundException    If {@code atm_details.ini} file cannot be found
     * @throws IOException              If {@code atm_details.ini} file cannot be opened or closed
     */
    public void readUserCSV() throws FileNotFoundException, IOException {
        /**
        * Using BufferedReader instead of Scanner class for better
        * efficiency reading line by line.
        */ 
        try (BufferedReader br = new BufferedReader(new FileReader(INI_FILEPATH))) {
            String line;
                        
            while ((line = br.readLine()) != null) {
                // Index of the equal sign within the string
                int equalIndex = 0;
                
                if (line.length() > 1 && !line.substring(0,1).equals("#")){
                    if (line.contains("Database_Hostname")) {
                        equalIndex = line.indexOf("=");
                        DB_HOSTNAME = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Database_Port")) {
                        equalIndex = line.indexOf("=");
                        DB_PORT = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Database_Name")) {
                        equalIndex = line.indexOf("=");
                        DB_NAME = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Database_Username")) {
                        equalIndex = line.indexOf("=");
                        DB_USERNAME = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Database_Password")) {
                        equalIndex = line.indexOf("=");
                        DB_PASSWORD = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Min_Username_Length")) {
                        equalIndex = line.indexOf("=");
                        MIN_USERNAME_LEN = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Max_Username_Length")) {
                        equalIndex = line.indexOf("=");
                        MAX_USERNAME_LEN = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Required_PIN_Length")) {
                        equalIndex = line.indexOf("=");
                        REQUIRED_PIN_LEN = line.substring(equalIndex+1).trim();
                    } else if (line.contains("Account_Table_Name")) {
                        equalIndex = line.indexOf("=");
                        ACCOUNT_TABLE = line.substring(equalIndex+1).trim();
                    }
                }
            }
            // Form the MySQL URL for database login
            MYSQL_URL = "jdbc:mysql://" + DB_HOSTNAME + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false";
            br.close();
        } catch (FileNotFoundException e) {
            System.out.println(Messages.fileNotFoundExceptionMessage(INI_FILEPATH));
            System.exit(0);
        }
    }
}
