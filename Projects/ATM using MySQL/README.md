# _ATM Project Using MySQL_

#### Simulates an ATM machine's basic functionalities using command line

#### By _**Reuven Levy**_

## Technologies Used

* Java JDK 17
* IDE: Visual Studio Code (VSC) v1.62.1
* DB: MySQL server 8.0
* JDBC: Connector/J 8.0.27

## Description

This ATM project simulates some of the real-world applications of ATMs. It uses a command line to interface with the program. A MySQL database was used along with 
SQL Fiddle, an online SQL tool to develop schemas and test queries. All user data is kept in 2 tables:<br>
  1. ACCOUNTS - _A list of all user accounts and their respective PINs_
  2. \<username\> - _A transaction history for specific user, which doubles as their current balance (every user will have one)_

Some of the code here could be simplified into using Java's built-in methods, but I explored building my own methods in order to demonstrate the critical thinking
process. It consists of the following major features:
* User Login
  * Withdraw              - _Withdraw from account's available funds_
  * Deposit               - _Deposit funds into account_
  * Transfer              - **[NOT YET IMPLEMENTED]** _Transfer funds from between savings and checking accounts_
  * Transaction History   - _List the last 30 transactions (any 31st transaction will be removed from this list)_
  * Account Stats         - **[NOT YET IMPLEMENTED]** _Use last 30 transactions to show: average amount withdrawn and deposited, show percentage of money deposited_
                            _vs overall funds, and percentage of money withrawn vs overall funds_
  * Delete Account        - _Deletes current account and all data on accceptance of follow-up confirmation message_
  * Sign Out              - _Logs out of account and returns to login menu_
* Admin Login
  * Add Account           - _Adds new account with creation of username and PIN_
  * Sign Out              - _Logs out of account and returns to login menu_
* Requirement Checks
  * Username & PIN        - _Username must be alpha-numeric & < 16 characters; PIN must be 6 digits_
  * Money Format          - _Input must contain at most 2 decimal places and must be numeric_
* Custom Error Messages
* Pretty Printing
  * Resizing Table Borders  - **Proud of this one!** _Custom written tables around ATM menu and transaction history that will resize depending on length of data_
  * Money Formatting        - _Add commas after every 3rd digit if over 1000, enforce 2nd decimal place is shown and include dollar sign_
* Quit From Any Input       - _At any point in the program where it asks for user input, can type case-insensitive "quit" to end the program_
* Back Out to ATM Menu      - _Within any chosen ATM menu option, can type in case-insensitive "back" to return to the list of ATM options_
* CSV File Handling
  * Create, Delete, Read, Write and Append CSV Files
* Hash Encrypt PINs         - **[NOT IMPLEMENTED YET]** _Encrypts the PINs for each user and stores them in the accounts.csv file_

## Setup/Installation Requirements

1. Install Java 8 or greater
2. Install JDBC
3. Install MySQL server or any MySQL service where a database can be created
4. Configure SQL.properties file with your database details
5. Download project
6. Run Main.java from IDE of choice
7. Do initial sign-in with admin credentials **[Will implement option to easily change admin and user PINs in the future]**<br>
Username/PIN  = admin/000000
8. From here, you can create new accounts. Note, there will only be 1 admin account as these cannot be created as of yet

### Things to Remember 
* Be sure that you keep the "csv files" folder within the same directory as the Java classes
* Within the aforementioned folder, there needs to be an "accounts.csv" file already created, but can be empty
**[Will implement automatic file creation if not found in the future]**

## Known Bugs

* None as far as I know
