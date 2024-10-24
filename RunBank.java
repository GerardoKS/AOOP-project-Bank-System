import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.lang.NumberFormatException;


/*
 * Driver class containg User Interface and CSV conversion to create the bank system. 
 * @author Gerardo Sillas
 * @author Hannah Ayala
 */

public class RunBank{
    private static Dictionary<?, Customer> customerList; //dictionary with key (firstName lastName) and value Customer
    private static Dictionary<?, Customer> accountList; 
    private static boolean exit = false; //customer wants to exit
    private static boolean mainMenu = false; //customer wants to return to main menu
    private static boolean successD = true; //customer deposit was successful
    private static boolean successW = true; //customer withdraw was successful
    private static boolean successT = true; //customer transfer was successful
    private static boolean successP = true; //customer paying someone was successful
    private static Scanner sc = new Scanner(System.in);

    public static void main(String args[]){
        //declare file location (file path)
        String filePath = "./CS 3331 - Bank Users.csv";
        //read CSV file and create a list of "Customer"s from the entreis in the file
        List<Dictionary<?, Customer>> dictionaries = listOfCustomersFromCSV(filePath);
        accountList = dictionaries.get(0);
        customerList = dictionaries.get(1);
        
        //runs the bank
        Files f = new Files("log");
        f.createFile();

        System.out.println("Welcome to El Paso Miners Bank!");
        while(!exit){ //will continue going to main menu unless the user chooses to exit
           main_menu(f); //manager or customer        
        }
        System.out.println("Thank you for choosing us!");
        //ends

        filePath = "./Result.csv";
        //Update the CSV with any changes made the to the list of "Customer"s
       // updateCSVFile(dictionaries, filePath);
    }

    private static void main_menu(Files f){
        int users = typeOfUser(); //will get the type of user
           switch (users){
                case 0: //manager
                    System.out.println("You have chosen Bank Manager access");
                    exit = true;
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

    

    private static void userMenu(String customerName, Files f){
        successD =true;
        successP = true;
        successT = true;
        successW = true;
        String customerName2 = "";
        System.out.println("\nPlease input what transaction you would like to do.\nCheck Balance (B)\nDeposit (D)\nwithdraw (W)\nTransfer (T)\nPay someone (P)\nLogout/Return to Main Menu (L/M)\nExit (E)"); //options for the customer
        String input = sc.next(); //stores the transaction (or action)

        while(input == "") input = sc.nextLine();
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
                //FIX
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
            case("return to main menu"):
                mainMenu = true;
                break;
            case("e"):
            case("exit"):
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

    private static boolean transfer(String customerName2, String customerName, Files f){
                //will get the second customer that will partake in this transaction
                //will withdraw from one of customerName's accounts and deposit into one of customerName2's accounts
                System.out.println("How much would you like to pay"); //gets the amount for the transaction
                Double payAmount = sc.nextDouble();
                System.out.println("You will be prompted to provide information regarding the account you are paying from");
                String typeOne = getAccount();
                int typeOneStat = verifyAccount(customerName, typeOne);
                while (typeOneStat==0){
                    typeOne = getAccount();
                    typeOneStat = verifyAccount(customerName2, typeOne);
                }
                if (typeOneStat<0) return true;
                System.out.println("You will be prompted to provide information regarding the account you are paying");
                String typeTwo = getAccount();
                int typeTwoStat = verifyAccount(customerName2, typeTwo);
                while (typeTwoStat==0){
                    typeTwo = getAccount();
                    typeTwoStat = verifyAccount(customerName2, typeTwo);
                }

                if (typeTwoStat<0) return true;
                
                if(typeOneStat == 0 || typeTwoStat == 0){
                    return false;
                }
                if (typeOneStat == 1 && typeOneStat == typeTwoStat){
                    if (customerName.equals(customerName2)) return customerList.get(customerName).transfer(typeOne, typeTwo, payAmount, f);
                    else return customerList.get(customerName).pay(customerList.get(customerName2), typeOne, typeTwo, payAmount, f);
                }else if (typeOneStat ==2 && typeOneStat == typeTwoStat){
                    if (customerName.equals(customerName2)) return customerList.get(customerName).transfer(Integer.parseInt(typeOne), Integer.parseInt(typeTwo), payAmount, f);
                    else return customerList.get(customerName).pay(customerList.get(customerName2), Integer.parseInt(typeOne), Integer.parseInt(typeTwo), payAmount, f);
                }
                System.out.println("Please provide both types of accounts or both account numbers not mismatch");
                return false;
    }
    /*
    private static boolean transfer(String customerName, Files f){
        //will withdraw and deposit into one of customerName's account 
        System.out.println("How much would you like to transfer from the accounts"); //gets the amount for the transaction
        double transferAmount = sc.nextDouble();
        System.out.println("You will be prompted to provide information regarding the account you are paying from");
        String tTypeOne = getAccount();
        int tTypeOneStat = verifyAccount(customerName, tTypeOne);
        while (tTypeOneStat==0){
            tTypeOne = getAccount();
            tTypeOneStat = verifyAccount(customerName, tTypeOne);
        }
        if (tTypeOneStat<0) return true;
        System.out.println("You will be prompted to provide information regarding the account you are paying");
        String tTypeTwo = getAccount();
        int tTypeTwoStat = verifyAccount(customerName, tTypeTwo);
        while (tTypeTwoStat==0){
            tTypeTwo = getAccount();
            tTypeTwoStat = verifyAccount(customerName, tTypeTwo);
        }
        if (tTypeTwoStat<0) return true;
        
        if(tTypeOneStat == 0 || tTypeTwoStat == 0){ //CHECK IF POSSIBLE
            return false;
        }

        if (tTypeOneStat == 1 && tTypeOneStat == tTypeTwoStat){
            return customerList.get(customerName).transfer(tTypeOne, tTypeTwo, transferAmount, f);
        }else if (tTypeOneStat ==2 && tTypeOneStat == tTypeTwoStat){
            return customerList.get(customerName).transfer(Integer.parseInt(tTypeOne), Integer.parseInt(tTypeTwo), transferAmount, f);
        }
        System.out.println("Please provide both types of accounts or both account numbers not mismatch");
        return false;
    }*/

    private static boolean transaction(String transaction, String customerName, Files f){
        String type = getAccount();
        int state = verifyAccount(customerName, type);
        switch (state){
            case(-1):
            case(-2):
            case(-3):
                return true;
            case(0):
                return transaction(transaction, customerName, f);
            case(1):
                return transactionHelper(transaction, customerName, type, f);
            case(2):
                return transactionHelper(transaction, customerName, Integer.parseInt(type), f);
        }
        return false;
    }

    private static String getAccount(){
        System.out.printf("Please specify which account you would like to perform this transaction to, include the type or the account number (exit (e) or main menu (m) or user menu (u))\n"); //get account number or account type to perform transaction for or action to perform
        String type = sc.nextLine();
        while(type == "") type = sc.nextLine();
        return type;
    }

    private static int verifyAccount(String customerName, String type){
        switch (type){
            case("u"):
            case("user menu"):
                return -3;
            case("e"):
            case("exit"):
                mainMenu = true;
                exit = true;
                return -1; 
            case("m"):
            case("main menu"):
                mainMenu = true;
                return -2;
            default:
                if (customerList.get(customerName).getAccounts().get(type.toLowerCase()) == null){//type is not the accountType, it could be an account number or not correct input
                try {
                    String str = customerList.get(customerName).findAccountType(Integer.parseInt(type));
                    if (str == null) throw new Exception();
                    return 2;
                } catch (Exception e) {
                    System.out.println("No such account found");
                    return 0;
                }//check the dictionary for type  
                        /* 
                        if (type == null){ //if there was not an account with that number, it is an invalid account
                            System.out.println("Account wasn't found please try again or return to main menu");
                            return 0;
                        }else{ //there was an account with that number
                            return 2;
                        }*/
                    }else{
                     //there is an account for customerName of type accountType
                        return 1;
                    }
        }
    }


    private static boolean transactionHelper(String transaction, String customerName, String type, Files f){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(type, amount, f);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(type, amount, f);
        }
    }
    private static boolean transactionHelper(String transaction, String customerName, int number, Files f){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(number, amount, f);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(number, amount, f);
        }
    }


    private static int typeOfUser(){
        System.out.println("Will the following transaction be from a manager (m) or a regular user (u)? (exit (e))");
        String input = sc.nextLine();
        while(input == "") input = sc.nextLine();
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

    private static String getCustomer(){
        System.out.println("Please provide your first and last name  (exit (e) or main menu (m))");
        String name = sc.nextLine(); //get name of customer or action to perform
        while(name == "") name = sc.nextLine();
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
    
    /*
     * converts the entries given in the CSV to a List of "Customer" building each "Customer" object and their 3 subclass "Account" objects.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Map<Object, Object>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent maps are for quickly looking up the customer based on their name or on their account number
     */
    static List<Dictionary<?,Customer>> listOfCustomersFromCSV(String filePath){
        List<Dictionary<?,Customer>> customerList = new ArrayList<>();
        List<String> names = new ArrayList<>();
        Dictionary<String,Customer> customerNameList = new Hashtable<>();
        Dictionary<Integer,Customer> customerAccountNumberList = new Hashtable<>();
        String line;
        //try to read CSV file
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            boolean skipCategoryNames = true;
            //read each line until no more lines
            while((line = br.readLine())!= null){
                //skip first lines since it is just attribute names
                if(skipCategoryNames){
                    skipCategoryNames = false;
                    continue;
                }
                //split line by the commas and store in a String array
                String[] customerData = line.split(",");
                // Create customer and set all its attributes
                Customer currentAccountHolder = new Customer();
                currentAccountHolder.setId(Integer.parseInt(customerData[0]));
                currentAccountHolder.setFirstName(customerData[1]);
                currentAccountHolder.setLastName(customerData[2]);
                currentAccountHolder.setDOB(customerData[3]);
 
                currentAccountHolder.setAddress(customerData[4]+", "+customerData[5]+", "+ customerData[6]);
                currentAccountHolder.setPhoneNumber(customerData[7]);
                //Create the Accounts and set all its attributes
                int checkingAccountNumber = Integer.parseInt(customerData[8]);
                double checkingAccountBalance = Double.parseDouble(customerData[9]);
                Account checkingAccount = new Checking(checkingAccountNumber, checkingAccountBalance, currentAccountHolder);
 
                int savingAccountNumber = Integer.parseInt(customerData[10]);
                double savingAccountBalance = Double.parseDouble(customerData[11]);
                Account savingAccount = new Saving(savingAccountNumber, savingAccountBalance, currentAccountHolder);
 
 
                int creditAccountNumber = Integer.parseInt(customerData[12]);
                double creditMax = Double.parseDouble(customerData[13]);
                double creditAccountBalance = Double.parseDouble(customerData[14]);
                Account creditAccount = new Credit(creditAccountNumber,creditAccountBalance, currentAccountHolder, creditMax);
                //set the accounts in the Customer object
                Dictionary<String,Account> accounts = new Hashtable<>();
                accounts.put("checking", checkingAccount);
                accounts.put("saving", savingAccount);
                accounts.put("credit", creditAccount);
                //put accoounts in the Customers accounts Dictionary
                currentAccountHolder.setAccounts(accounts);
                //Store Customer in Dictionary of Customers with the key as the ID
                //wrote with the getter to be more readable
                customerAccountNumberList.put(checkingAccountNumber, currentAccountHolder);
                customerAccountNumberList.put(savingAccountNumber, currentAccountHolder);
                customerAccountNumberList.put(creditAccountNumber, currentAccountHolder);
                customerNameList.put(currentAccountHolder.getFirstName()+" "+currentAccountHolder.getLastName(),currentAccountHolder);
                //create a list storing all the names so that when you update your CSV file you can keep the order
                names.add(currentAccountHolder.getFirstName()+" "+currentAccountHolder.getLastName());
            }
        }
        //catch if reading failed
        catch(IOException e){
            e.printStackTrace();
        }
        //create a dictionary to store list of names so that it can be returned
        Dictionary<List<String>, Customer> Dummy = new Hashtable<>();
        //make list unmodifiable O(1) so that it can be used as a key
        names = Collections.unmodifiableList(names);
        //create a dummy customer so your Dictionary value doesnt throw a NULL pointer exception
        Customer dummy = new Customer();
        //put the List and dummy Customer object in the Dummy Dictionary
        Dummy.put(names, dummy);
        //return all customers read
        customerList.add(customerAccountNumberList);
        customerList.add(customerNameList);
        //add the Dummy dictionary to the list that is going to be returned
        customerList.add(Dummy);
        //return the list
        return customerList;
    }
    /*
     * Update the CSV File with the new entries and any altered entry that happened throughout the life cycle of the program
     * @param List<Customer> list of Customers that are going have there attributes and account attributes convereted into strings and updated in the CSV file
     * @param filePath to increase flexibility, filePath added incase needed again for future project
     */
    public static void updateCSVFile(List<Dictionary<?,Customer>> customerList, String filePath){
        //try to update CSV
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))){
            //write the titles before the data
            String titles = "Identification Number,First Name,Last Name,Date of Birth,Address,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance";
            writer.write(titles);
            writer.newLine();
            //get the list of names to keep the order in the CSV from the list of dictionaries
            Enumeration<?> list = customerList.get(2).keys();
            //cast the into a List<String>
            List<String> names = (List<String>)list.nextElement();
            //Update each customer one by one
            for(String name : names){
                Customer currentAccountHolder = customerList.get(1).get(name);
                //turn Customer attribute into a String
                String data =   Integer.toString(currentAccountHolder.getId())+","+currentAccountHolder.getFirstName()+","+currentAccountHolder.getLastName()+","+ currentAccountHolder.getDOB()+","+currentAccountHolder.getAddress() +","+ currentAccountHolder.getPhoneNumber()+",";
                //get accounts and store in more descriptive varaibles for readability
                Dictionary <String, Account> accounts = currentAccountHolder.getAccounts();
                //get the Acount objects
                Account checkingAccount = accounts.get("checking");
                Account savingAccount = accounts.get("saving");
                Account creditAccount = accounts.get("credit");
                //turn Accounts' attributes into String and then added them to the end of data
                data = data + Integer.toString(checkingAccount.getAccountNumber()) + "," + Double.toString(checkingAccount.getBalance()) + "," + Integer.toString(savingAccount.getAccountNumber()) + "," + Double.toString(savingAccount.getBalance()) + "," + Integer.toString(creditAccount.getAccountNumber()) + "," + Double.toString(((Credit)creditAccount).getMax()) +","+ Double.toString(creditAccount.getBalance());
                //write Customers data into the CSV file
                writer.write(data);
                //create the next line for the next entry
                writer.newLine();
            }
        }
        //catch if update fails
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

