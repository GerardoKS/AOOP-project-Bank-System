package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;




/**
 * Driver class containg User Interface and CSV conversion to create the bank system. 
 * @author Gerardo Sillas
 * @author Hannah Ayala
 */

public class RunBank{
/** 
 * A dictionary that maps a combined key of first name and last name to a Customer object.
 */
private static Dictionary<String, Customer> customerList = new Hashtable<>(); 

/** 
 * A dictionary that maps account identifiers to Customer objects.
 */
private static Dictionary<Integer, Customer> accountList = new Hashtable<>(); 

/** 
 * A flag indicating whether the customer wants to exit the application.
 */
private static boolean exit = false; 

/** 
 * A flag indicating whether the customer wants to return to the main menu.
 */
private static boolean mainMenu = false; 

/** 
 * A flag indicating whether a customer's deposit was successful.
 */
private static boolean successD = true; 

/** 
 * A flag indicating whether a customer's withdrawal was successful.
 */
private static boolean successW = true; 

/** 
 * A flag indicating whether a customer's transfer was successful.
 */
private static boolean successT = true; 

/** 
 * A flag indicating whether a payment made by a customer was successful.
 */
private static boolean successP = true; 

/** 
 * A Scanner object used for input from the user.
 */
private static Scanner sc = new Scanner(System.in);

/**
 * List of names to maintain the order of the users, so that when the updatingCSVFile method is invoked, it can update the CSV file based on the order they were read
 */
private static List<String> names = new ArrayList<>();

/**
 * titles for the updated CSV file(first line) 
 */
private static String titles;

/**
 * shows the total number of customers so the ID and the account numbers can be created.
 */
private static int totalCustomers;
    /** 
     * The entry point of the application. This method initializes the bank application,
     * reads customer data from a CSV file, and runs the main banking loop.
     *
     * @param args Command line arguments (not used in this application).
     */
    public static void main(String args[]){
        //declare file location (file path)
        String filePath = "./src/resources/CS 3331 - Bank Users.csv";
        //read CSV file and create a list of "Customer"s from the entreis in the file
        ReadCustomersFromCSV(filePath);
        totalCustomers = names.size();
        //runs the bank
        Files f = new Files("log");
        f.createFile();
        System.out.println("Welcome to El Paso Miners Bank!");
        while(!exit){ //will continue going to main manu unless the user chooses to exit
           main_menu(f); //manager or customer        
        }
        System.out.println("Thank you for choosing us!");
        //ends

        filePath = "./Result.csv";
        //Update the CSV with any changes made the to the list of "Customer"s
        updateCSVFile(filePath);
    }

    /** 
     * Displays the main menu for the banking application, allowing access 
     * based on the type of user (manager or regular customer). It manages 
     * the flow of the application, directing users to the appropriate menu 
     * options based on their selection.
     *
     * @param f An instance of the Files class used for logging transactions.
     */
    private static void main_menu(Files f){
        int users = typeOfUser(); //will get the type of user
           switch (users){
                case 0: //manager
                    System.out.println("You have chosen Bank Manager access");
                    mainMenu = false;
                    while(!mainMenu && !exit){  //while they dont choose to go back to the main manu
                        managerMenu(f); //provide manager with options
                    }
                    break;
                case 1: //normal user
                    System.out.println("You have chosen regular user.");
                    mainMenu = false;
                    String customerName = getCustomer(); //will get name of customer and validate it
                    while (!mainMenu && !exit){ //while they do not choose to go back to the main menu
                        userMenu(customerName, f); //provide the user with transaction options
                    }
           }
           
    }

    /** 
     * Displays the manager menu, allowing bank managers to inquire about
     * customer accounts either by customer name or account number. 
     *
     * @param f An instance of the Files class used for logging transactions.
     */
    private static void managerMenu(Files f){
        exit = false;
        mainMenu = false; 
        boolean managerMenu = false; //reset flags
        System.out.println("A. Inquire by name: \nB.Inquire by account number: \n(exit (e) or main menu (m))"); //options
        String option = sc.nextLine();
        while(option.equals("")) option = sc.nextLine();
        switch (option.toLowerCase()){ //based on option
            case("a"): //by name
                String customerName = getCustomer(); //get customer name and validate it
                while (!exit && !mainMenu && !managerMenu){ //while they do not choose to leave
                    System.out.println("Enter the account type"); //get account type
                    String input = sc.nextLine();
                    while(input.equals("")) input = sc.nextLine();
                    switch (input){
                        case("e"):
                        case("exit"): //actions
                            mainMenu = true;
                            exit = true;
                            break;
                        case("m"):
                        case("main"):
                        case("mainMenu"): //actions
                            mainMenu = true;
                            break;
                        default: //provided type or invalid
                            int type = verifyAccount(customerName, input); //verify account exists
                            if (type>0){
                                System.out.println(customerList.get(customerName).getAccounts().get(input).toString()); //if it exists, print the tostring
                                managerMenu = true; //done with this person, return to managerMenu
                                break;
                            }
                    }
                }
                break;
            case("b"): //by account number
                int num = getAccountNumber(f); //get account number and validate
                Customer customer = (accountList.get(num));
                System.out.println(customer.getAccounts().get(customer.findAccountType(num)).toString()); //print the tostring
                break;
            case("e"):
            case("exit"): //actions
                mainMenu = true;
                exit = true;
                break;
            case("m"):
            case("main"):
            case("main menu"): //actions
                mainMenu = true;
                break;
            default: //invalid input
                System.out.println("Please select from the options");
                managerMenu(f);
        }
    }

    /**
     * Displays the user menu for the banking application, allowing 
     * customers to perform various transactions such as checking balance, 
     * depositing, withdrawing, transferring funds, paying another customer, 
     * or logging out. The method handles user input and ensures valid 
     * operations are performed based on the chosen transaction type.
     *
     * @param customerName The name of the customer using the menu.
     * @param f An instance of the Files class used for logging transactions.
     */
    private static void userMenu(String customerName, Files f){
        successD =true;
        successP = true;
        successT = true;
        successW = true; //reset flags
        String customerName2 = "";
        System.out.println("\nPlease input what transaction you would like to do.\nCheck Balance (B)\nDeposit (D)\nwithdraw (W)\nTransfer (T)\nPay someone (P)\nLogout/Return to Main Menu (L/M)\nExit (E)"); //options for the customer
        String input = sc.nextLine(); //stores the transaction (or action)

        while(input.equals("")) input = sc.nextLine();
        switch (input.toLowerCase()){
            case("b"):
            case("balance"):
            case("check balance"):
                int status = customerList.get(customerName).getBalance(f); //will get the balance and display it (or action)
                if (status == -1){ //exit
                    exit = true;
                    break;
                }else if (status == -2){ //return to main menu
                    mainMenu = true;
                    break;
                }
                break;
            case("d"):
            case("deposit"):
                successD = transaction("deposit", customerName, f); //will deposit into one of customerName's accounts
                break;
            case("w"):
            case("withdraw"):
                successW = transaction("withdraw", customerName, f); //will withdraw from one of customerName's acocunts
                break;
            case("t"):
            case("transfer"):
                successT = transfer(customerName, customerName, f);
                break;
            case("p"):
            case("pay"):
            case("pay someone"):
                System.out.println("You will be prompted on the person that you will be paying");
                customerName2 = getCustomer();
                successP = transfer(customerName2, customerName, f);
                break;
            case("l"):
            case("logout"):
            case("m"):
            case("main menu"):
            case("return to main menu"): //actions
                mainMenu = true;
                break;
            case("e"):
            case("exit"): //actions
                exit = true;
                break;
            default: //user did not select an option
                System.out.println("Please choose from the options provided and/or ensure your spelling is correct");
        }
        //if they haven't selected the option to exit verify if the operation they chose was successful
        //if the transaction wasn't successful, try it again until the user selects to return to main menu or to the user menu
        if(!exit){
            if (!successD){
                successD = transaction("deposit", customerName, f);
            }
            if (!successW){
                successW = transaction("withdraw", customerName, f);
            }
            if (!successT){
                successT = transfer(customerName, customerName, f);              
            }
            if (!successP){
                successP = transfer(customerName2, customerName, f);;
            }
        }
    }


    /**
     * Facilitates a transfer of funds between two customers' accounts. 
     * The method prompts the user for the amount to transfer and the 
     * account details for both the sender and the recipient. It verifies 
     * the accounts involved in the transaction and performs the transfer 
     * if valid.
     *
     * @param customerName2 The name of the customer receiving the payment.
     * @param customerName The name of the customer making the payment.
     * @param f An instance of the Files class used for logging transactions.
     * @return True if the transfer was successful; otherwise, false.
     */
    private static boolean transfer(String customerName2, String customerName, Files f){
        System.out.println("How much would you like to pay"); //gets the amount for the transaction
        Double payAmount = sc.nextDouble();
        System.out.println("You will be prompted to provide information regarding the account you are paying from");
        String typeOne = getAccount();
        int typeOneStat = verifyAccount(customerName, typeOne); //get and verify account source
        while (typeOneStat==0){ //if not found, try again
            typeOne = getAccount();
            typeOneStat = verifyAccount(customerName2, typeOne);
        }
        if (typeOneStat<0) return true; //action (exit or main)

        System.out.println("You will be prompted to provide information regarding the account you are paying");
        String typeTwo = getAccount();
        int typeTwoStat = verifyAccount(customerName2, typeTwo); //get and verify account dest
        while (typeTwoStat==0){ //if not found, try again
            typeTwo = getAccount();
            typeTwoStat = verifyAccount(customerName2, typeTwo);
        }
        if (typeTwoStat<0) return true; //action (exit or main)
        
        if(typeOneStat == 0 || typeTwoStat == 0){ 
            return false;
        }
        if (typeOneStat == 1 && typeOneStat == typeTwoStat){ //both are types
            if (customerName.equals(customerName2)) return customerList.get(customerName).transfer(typeOne, typeTwo, payAmount, f); //between the same person, transfer
            else return customerList.get(customerName).pay(customerList.get(customerName2), typeOne, typeTwo, payAmount, f); //between two people, pay
        }else if (typeOneStat ==2 && typeOneStat == typeTwoStat){ //both are account numbers
            if (customerName.equals(customerName2)) return customerList.get(customerName).transfer(Integer.parseInt(typeOne), Integer.parseInt(typeTwo), payAmount, f); //between the saem person, transfer
            else return customerList.get(customerName).pay(customerList.get(customerName2), Integer.parseInt(typeOne), Integer.parseInt(typeTwo), payAmount, f); //between two people, pay
        }
        System.out.println("Please provide both types of accounts or both account numbers not mismatch"); //accounts not correct
        return false;
    }

    /**
     * Handles a banking transaction (deposit or withdrawal) for a specified customer. 
     * It prompts for account and verifies the account type or number.
     * If the account is valid, it calls the appropriate helper method to complete the transaction.
     *
     * @param transaction A string indicating the type of transaction ("deposit" or "withdraw").
     * @param customerName The name of the customer making the transaction.
     * @param f An instance of the Files class used for logging transactions.
     * @return True if the transaction was successful; otherwise, false.
     */
    private static boolean transaction(String transaction, String customerName, Files f){
        String type = getAccount(); 
        int state = verifyAccount(customerName, type); //get and verify the account
        switch (state){
            case(-1):
            case(-2):
            case(-3): //actions
                return true; 
            case(0): //try again
                return transaction(transaction, customerName, f);
            case(1): //valid type
                return transactionHelper(transaction, customerName, type, f);
            case(2): //valid account number
                return transactionHelper(transaction, customerName, Integer.parseInt(type), f);
        }
        return false;
    }


    /**
     * Verifies the validity of the specified account for the given customer. 
     * It checks if the account type or account number exists and returns 
     * an appropriate status code indicating the result of the verification.
     *
     * @param customerName The name of the customer whose account is being verified.
     * @param type The account type or account number to be verified.
     * @return An integer representing the verification result:
     *          -1, -2, -3 for exit codes
     *          0 if the account is invalid,
     *          1 if the account type is valid,
     *          2 if the account number is valid.
 */
    private static int verifyAccount(String customerName, String type){
        switch (type){
            case("u"):
            case("user menu"): //action
                return -3;
            case("e"):
            case("exit"): //action
                mainMenu = true;
                exit = true;
                return -1; 
            case("m"):
            case("main menu"): //action
                mainMenu = true;
                return -2;
            default: //
                if (customerList.get(customerName).getAccounts().get(type.toLowerCase()) == null){ //type is not the accountType, it could be an account number or not correct input
                    try {
                        String str = customerList.get(customerName).findAccountType(Integer.parseInt(type)); //if it isnt int, throw error
                        if (str == null) throw new Exception(); //if it is an int but not an account number, throw error
                        return 2; 
                    } catch (Exception e) {
                        System.out.println("No such account found");
                        return 0;
                    }
                }else{ //there is an account for customerName of type accountType
                    return 1;
                } 
        }
    }


    /**
     * A helper method that performs the actual deposit or withdrawal transaction 
     * for the specified customer and account type. It prompts the user for the 
     * amount and processes the transaction accordingly.
     *
     * @param transaction A string indicating the type of transaction ("deposit" or "withdraw").
     * @param customerName The name of the customer making the transaction.
     * @param type The account type for the transaction.
     * @param f An instance of the Files class used for logging transactions.
     * @return True if the transaction was successful; otherwise, false.
     */
    private static boolean transactionHelper(String transaction, String customerName, String type, Files f){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(type, amount, f);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(type, amount, f);
        }
    }

    /**
     * A helper method that performs the actual deposit or withdrawal transaction 
     * for the specified customer and account number. It prompts the user for the 
     * amount and processes the transaction accordingly.
     *
     * @param transaction A string indicating the type of transaction ("deposit" or "withdraw").
     * @param customerName The name of the customer making the transaction.
     * @param number The account number for the transaction.
     * @param f An instance of the Files class used for logging transactions.
     * @return True if the transaction was successful; otherwise, false.
     */
    private static boolean transactionHelper(String transaction, String customerName, int number, Files f){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(number, amount, f);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(number, amount, f);
        }
    }

    /**
     * Prompts the user to specify their type: a manager or a regular user. 
     * The method also allows the user to exit the program. 
     * It validates the input and returns an integer indicating the user type:
     *
     * @return An integer representing the type of user:
     *         0 for manager,
     *         1 for regular user,
     *         -1 for exit.
     */
    private static int typeOfUser(){
        System.out.println("Will the following transaction be from a manager (m) or a regular user (u)? (exit (e))");
        String input = sc.nextLine();
        while(input.equals("")) input = sc.nextLine();
        switch (input.toLowerCase()){
            case("e"):
            case("exit"):
                exit = true;
                return -1;
            case("m"):
            case("manager"):
                return 0;
            case("u"):
            case("user"):
            case("regular user"):
                return 1;
            default:
                System.out.println("Please input the correct term");
                return (typeOfUser());
        }
    }

    /**
     * Prompts the user for their first and last name to identify the customer. 
     * It allows for options to exit the program or return to the main menu. 
     * If the customer exists in the database, it welcomes them; otherwise, 
     * it prompts the user to try again.
     *
     * @return The full name of the customer if found in the database; 
     *         otherwise, returns null.
     */
    private static String getCustomer(){
        System.out.println("Please provide your first and last name  (exit (e) or main menu (m))");
        String name = sc.nextLine(); //get name of customer or action to perform
        while(name.equals("")) name = sc.nextLine();
        switch(name){
            case ("e"):
            case("exit"):
                exit = true;
                return null;
            case ("m"):
            case("main"):
            case("main menu"):
                mainMenu = true;
                return null;
            default:
                if (customerList.get(name) == null){ //if the customer doesn't exist rerun the function
                System.out.println("Cannot find user in the database\nEnsure that the spelling is correct or that the user is in the database");
                return getCustomer();
                }
                else{ //else customer does exist in the bank
                System.out.println("Welcome, " + customerList.get(name).getName()); //greets customer with full name
                return name;
                }
        }
    }

        /** 
     * Prompts the user to enter an account number, allowing for 
     * the option to exit or return to the main menu. The method 
     * validates the input and returns the account number if valid. 
     * If not, the user is prompted to enter the number again.
     *
     * @param f An instance of the Files class used for logging transactions.
     * @return The valid account number entered by the user, or 0 if the user 
     * chooses to exit or return to the main menu.
     */
    private static int getAccountNumber(Files f){
        System.out.println("Enter account number: (exit (e) or main(m))"); 
        String number = sc.nextLine(); //get input
        while(number.equals("")) number = sc.nextLine();
        switch (number){
            case("e"):
            case("exit"): //actions
                mainMenu = true;
                exit = true;
                return 0;
            case("m"):
            case("main"):
            case("main menu"): //actions
                mainMenu = true;
                return 0;
            default: //actual number or invalid input
                try{
                    int num = Integer.parseInt(number); //if it cant be an int, throw error
                    if (accountList.get(num) == null) throw new Exception(); //if it is int but isnt an account num, throw error
                    return num; //it is an int valid account
                }catch (Exception e){
                    System.out.println("Account of number " + number + " was not found."); //not found, try again
                    return getAccountNumber(f);
                }
        }
    }

    /**
     * Prompts the user to specify which account to perform a transaction on, 
     * including the account type or account number. It also provides options 
     * to exit or return to the main or user menu.
     *
     * @return A string representing the account type or account number specified by the user.
     */
    private static String getAccount(){
        System.out.printf("Please specify which account you would like to perform this transaction to, include the type or the account number (exit (e) or main menu (m) or user menu (u))\n"); //get account number or account type to perform transaction for or action to perform
        String type = sc.nextLine();
        while(type.equals ("")) type = sc.nextLine();
        return type;
    }
    
    /**
     * In this method every input line read is converted into Customer with fully set attributes and Account attributes. They are then returned for use in the system
     * The last dictionary is used as a way of sorting the list when you update the CSV file. CSV file can overwrite original but we decided not to just in case it is needed.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Map<?, Customer>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent maps are for quickly looking up the customer based on their name or on their account number. The last dictionary is used for keeping track of the order for later use when updating the CSV file
     */
    static void ReadCustomersFromCSV(String filePath){
        Random random = new Random();
        String line;
        //try to read CSV file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            line = br.readLine();
            titles = line;
            //Get the categories so you can set the attributes of the customer and accounts properly
            String[] categories = line.split(",");
            //read each line until no more lines
            int currentID = 1;
            while((line = br.readLine())!= null){
                //split line by the commas and store in a String array
                String[] customerData = line.split(",");
                // Create customer and set all its attributes
                Customer currentAccountHolder = new Customer();
                Account checkingAccount = new Checking();
                checkingAccount.setAccountHolder(currentAccountHolder);
                Account savingAccount = new Saving();
                savingAccount.setAccountHolder(currentAccountHolder);
                Credit creditAccount = new Credit();
                creditAccount.setAccountHolder(currentAccountHolder);

                int checkingAccountNumber = 0;
                int savingAccountNumber = 0;
                int creditAccountNumber = 0;
                checkingAccountNumber = currentID + 999;
                checkingAccount.setAccountNumber(checkingAccountNumber);
                savingAccountNumber = currentID + 1999;
                savingAccount.setAccountNumber(savingAccountNumber);
                creditAccountNumber = currentID + 2999;
                creditAccount.setAccountNumber(creditAccountNumber);

                int customerDataCurrentIndex = 0;
                for(String category: categories){
                    switch (category){
                        case "Identification Number":
                            currentAccountHolder.setId(Integer.parseInt(customerData[customerDataCurrentIndex]));

                        case "First Name":
                            currentAccountHolder.setFirstName(customerData[customerDataCurrentIndex]);
                            break;
                        case "Last Name":
                            currentAccountHolder.setLastName(customerData[customerDataCurrentIndex]);
                            break;
                        case "Date of Birth":
                            currentAccountHolder.setDOB(customerData[customerDataCurrentIndex]);
                            break;
                        case "Address":
                            currentAccountHolder.setAddress(customerData[customerDataCurrentIndex]+","+customerData[customerDataCurrentIndex+ 1]+","+ customerData[customerDataCurrentIndex+2]);
                            customerDataCurrentIndex += 2;
                            break;
                        case "Phone Number":
                            currentAccountHolder.setPhoneNumber(customerData[customerDataCurrentIndex]);
                            break;

                        case "Checking Account Number":
                            checkingAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            checkingAccount.setAccountNumber(checkingAccountNumber);
                            break;

                        case "Checking Starting Balance":
                            checkingAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Savings Account Number":
                            savingAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            savingAccount.setAccountNumber(savingAccountNumber);
                            break;

                        case "Savings Starting Balance":
                            savingAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Account Number":
                            creditAccountNumber = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            creditAccount.setAccountNumber(creditAccountNumber);
                            break;

                        case "Credit Max":
                            creditAccount.setMax(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Starting Balance":
                            creditAccount.setBalance(Double.parseDouble(customerData[customerDataCurrentIndex]));
                            break;

                        case "Credit Score":
                            int creditScore = Integer.parseInt(customerData[customerDataCurrentIndex]);
                            currentAccountHolder.setCreditScore(creditScore);
                            if(creditScore <= 580)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(599) + 100);
                            else if(581<= creditScore && creditScore<= 669)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(4299) + 700);
                            else if(670<= creditScore && creditScore<= 739)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(2499) + 5000);
                            else if(740<= creditScore && creditScore<= 799)
                                creditAccount.setMax(random.nextDouble() + random.nextInt(8499) + 7500);
                            else
                                creditAccount.setMax(random.nextDouble() + random.nextInt(9000) + 16000);
                            break;
                                
                    }
                    customerDataCurrentIndex++;
                }
                currentAccountHolder.setId(currentID);
                currentID++;
                Dictionary<String,Account> accounts = new Hashtable<>();
                accounts.put("checking", checkingAccount);
                accounts.put("saving", savingAccount);
                accounts.put("credit", creditAccount);
                //put accoounts in the Customers accounts Dictionary
                currentAccountHolder.setAccounts(accounts);

                //Store Customer in Dictionary of Customers with the key as the ID
                //wrote with the getter to be more readable
                accountList.put(checkingAccountNumber, currentAccountHolder);
                accountList.put(savingAccountNumber, currentAccountHolder);
                accountList.put(creditAccountNumber, currentAccountHolder);
                customerList.put(currentAccountHolder.getFirstName()+" "+currentAccountHolder.getLastName(),currentAccountHolder);
                //create a list storing all the names so that when you update your CSV file you can keep the order
                names.add(currentAccountHolder.getFirstName()+" "+currentAccountHolder.getLastName());
            }
        }
        //catch if reading failed
        catch(IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Update the CSV File with the new entries and any altered entry that happened throughout the life cycle of the program. 
     * We decided to create a seperate file path for the updated CSV file rather than overwriting the original. If needed the file path can be changed to the original file so that the updates end up there. 
     * @param List<Dictionary<?,Customer>> list of Customers that are going to have there attributes and account attributes convereted into strings and updated in the CSV file
     * @param filePath to increase flexibility, filePath added incase needed again for future project
     */
    public static void updateCSVFile(String filePath){
        //try to update CSV
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            //write the titles before the data
            writer.write(titles);
            writer.newLine();
            //Update each customer one by one
            //iterate through every customer and write their data in the CSV
            String[] categories = titles.split(",");
            for(String name : names){
                Customer currentAccountHolder = customerList.get(name);
                Account checkingAccount = currentAccountHolder.getAccounts().get("checking");
                Account savingAccount = currentAccountHolder.getAccounts().get("saving");
                Credit creditAccount = (Credit) currentAccountHolder.getAccounts().get("credit");
                boolean addComma = false;
                for(String category : categories){
                    if(addComma)
                        writer.write(",");
                    else
                        addComma = true;
                    switch (category){
                        case "Identification Number":
                            writer.write(Integer.toString(currentAccountHolder.getId()));
                            break;
                        
                        case "First Name":
                            writer.write(currentAccountHolder.getFirstName());
                            break;
                        case "Last Name":
                            writer.write(currentAccountHolder.getLastName());
                            break;
                        case "Date of Birth":
                            writer.write(currentAccountHolder.getDOB());
                            break;
                        case "Address":
                            writer.write(currentAccountHolder.getAddress());
                            break;
                        case "Phone Number":
                            writer.write(currentAccountHolder.getPhoneNumber());
                            break;

                        case "Checking Account Number":
                            writer.write(Integer.toString(checkingAccount.getAccountNumber()));
                            break;

                        case "Checking Starting Balance":
                            writer.write(Double.toString(checkingAccount.getBalance()));
                            break;

                        case "Savings Account Number":
                            writer.write(Integer.toString(savingAccount.getAccountNumber()));
                            break;

                        case "Savings Starting Balance":
                            writer.write(Double.toString(savingAccount.getBalance()));
                            break;

                        case "Credit Account Number":
                            writer.write(Integer.toString(creditAccount.getAccountNumber()));
                            break;

                        case "Credit Max":
                            writer.write(Double.toString(creditAccount.getMax()));
                            break;

                        case "Credit Starting Balance":
                            writer.write(Double.toString(creditAccount.getBalance()));
                            break;

                        case "Credit Score":
                            writer.write(Integer.toString(currentAccountHolder.getCreditScore()));
                            break;
                    }  
                }
                writer.newLine();
            }
        }
        //catch if update fails
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

