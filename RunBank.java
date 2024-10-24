import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Enumeration;

import java.util.Hashtable;
import java.util.Dictionary;
<<<<<<< HEAD
=======

import java.util.List;
import java.util.ArrayList;

import java.util.Scanner;
>>>>>>> abc22a24436ee409fe6bdf884251d6fb83270aee
/*
 * Driver class containg User Interface and CSV conversion to create the bank system. 
 * @author Gerardo Sillas
 * @author Hannah Ayala
 */

public class RunBank{
    private static Dictionary<?, Customer> customerList; //dictionary with key (firstName lastName) and value Customer
    private static boolean exit = false; //customer wants to exit
    private static boolean mainMenu = false; //customer wants to return to main menu
    private static boolean successD = true; //customer deposit was successful
    private static boolean successW = true; //customer withdraw was successful
    private static boolean successT = true; //customer transfer was successful
    private static boolean successP = true; //customer paying someone was successful


    public static void main(String args[]){
        //declare file location (file path)
        String filePath = "./CS 3331 - Bank Users.csv";
        System.out.println("--------START-------");
        System.out.println("--------START-------");
        //read CSV file and create a list of "Customer"s from the entreis in the file
        List<Dictionary<?, Customer>> customerList = listOfCustomersFromCSV(filePath);
        System.out.println("------Read FILE -------");
        Enumeration<Customer> customers = customerList.get(1).elements();
<<<<<<< HEAD
    
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to El Paso Miners Bank!");
        while(!exit){ //will continue going to main menu unless the user chooses to exit
           main_menu(sc); //manager or customer        
        }
        System.out.println("Thank you for choosing us!");


        // Iterate over each Customer in the second Dictionary
        while (customers.hasMoreElements()) {
            Customer currentAccountHolder = customers.nextElement();
            System.out.println(currentAccountHolder.getId());
        }
        System.out.println("------FINISHED FILE------");
        filePath = "./Result.csv";
        //Update the CSV with any changes made the to the list of "Customer"s
        System.out.println("-------UPDATE--------");
        updateCSVFile(customerList, filePath);
        System.out.println("-------FINISH UPDATE-------");


    }
 
    /*
     * converts the entries given in the CSV to a List of "Customer" building each "Customer" object and their 3 subclass "Account" objects.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Dictionary<?, Object>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent Dictionary are for quickly looking up the customer based on their name or on their account number. The wild card(?) is needed in order to have the Dictionary in the list
     */
        //getting the information from the csv file
        String filePath = "./CS 3331 - Bank Users.csv";
        customerList = listOfCustomersFromCSV(filePath);
        System.out.println("customerList obtained");
=======
        System.out.println("customerList obtained");
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to El Paso Miners Bank!");
        while(!exit){ //will continue going to main menu unless the user chooses to exit
           main_menu(sc); //manager or customer        
        }
        System.out.println("Thank you for choosing us!");
        System.out.println("------FINISHED FILE------");
        filePath = "./Result.csv";
        //Update the CSV with any changes made the to the list of "Customer"s
        System.out.println("-------UPDATE--------");
        System.out.println("-------UPDATE--------");
        updateCSVFile(customerList, filePath);
        System.out.println("-------FINISH UPDATE-------");
    }
 
    /*
     * converts the entries given in the CSV to a List of "Customer" building each "Customer" object and their 3 subclass "Account" objects.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Dictionary<?, Object>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent Dictionary are for quickly looking up the customer based on their name or on their account number. The wild card(?) is needed in order to have the Dictionary in the list
     */
    
    

>>>>>>> abc22a24436ee409fe6bdf884251d6fb83270aee

    
    private static void main_menu(Scanner sc){
        int users = typeOfUser(sc); //will get the type of user
           switch (users){
                case 0: //manager
                    System.out.println("You have chosen Bank Manager access");
                case 1: //normal user
                    System.out.println("You have chosen regular user.");
                    String customerName = getCustomer(sc); //will get name of customer and validate it
                    while (!mainMenu){ //while they do not choose to go back to the main menu
                        userMenu(sc, customerName); //provide the user with transaction options
                    }
           }
           
    }

    private static void userMenu(Scanner sc, String customerName){
        String customerName2 = null; //prepares for the probablity of having two customers (paying someone)
        System.out.println("Please input what you transaction you would like to do.\nCheck Balance (B)\nDeposit (D)\nwithdraw (W)\nTransfer (T)\nPay someone (P)\nLogout/Return to Main Menu (L/M)\nExit (E)"); //options for the customer
        String input = sc.next(); //stores the transaction (or action)
        switch (input.toLowerCase()){
            case("b"):
            case("balance"):
            case("check balance"):
                double status = customerList.get(customerName).getBalance(); //will get the balance and display it (or action)
                switch (status){
                    case(-1.0): //exit
                        exit = true;
                        break;
                    case(-2.0): //return to main menu
                        mainMenu = true;
                        break;
                    default:
                        break;
                }
                break;
            case("d"):
            case("deposit"):
                successD = transaction(sc, "deposit", customerName); //will deposit into one of customerName's accounts
                break;
            case("w"):
            case("withdraw"):
                //FIX
                successW = transaction(sc, "withdraw", customerName); //will withdraw from one of customerName's acocunts
                break;
            case("t"):
            case("transfer"):
                //will withdraw and deposit into one of customerName's account 
                successT = transaction(sc, "withdraw", customerName) && transaction(sc, "deposit", customerName); 
                break;
            case("p"):
            case("pay"):
            case("pay someone"):
                customerName2 = getCustomer(sc); //will get the second customer that will partake in this transaction
                //will withdraw from one of customerName's accounts and deposit into one of customerName2's accounts
                successP = transaction(sc, "withdraw", customerName) && transaction(sc, "deposit", customerName2) ;
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
                successD = transaction(sc, "deposit", customerName);
            }
            if (!successW){
                successW = transaction(sc, "withdraw", customerName);
            }
            if (!successT){
                successT = transaction(sc, "withdraw", customerName) && transaction(sc, "deposit", customerName);                     
            }
            if (!successP){
                successP = transaction(sc, "withdraw", customerName) && transaction(sc, "deposit", customerName2);
            }
        }
    }

    private static boolean transaction(Scanner sc, String transaction, String customerName){
        System.out.printf("What account would you like to %s  into, include the type or the account number (exit (e) or main menu (m) or user menu (u))\n", transaction); //get account number or account type to perform transaction for or action to perform
        String type = sc.next();
        switch (type){
            case("e"):
            case("exit"):
                exit = true;
                return true; //transaction was not unsuccessful
            case("m"):
            case("main menu"):
                mainMenu = true;
                return true; //transaction was not unsuccessful
            default:
                switch (customerList.get(customerName).getAccounts().get(type.toLowerCase())){ //check the dictionary for type 

                    case(null): //type is not the accountType, it could be an account number or not correct input
                        type = customerList.get(customerName).findAccountType(Integer.parseInt(type)); //find the account type

                        if (type == null){ //if there was not an account with that number, it is an invalid account
                            System.out.println("Account wasn't found please try again or return to main menu");
                            return false;
                        }else{ //there was an account with that number, proceed with the transaction
                            return transactionHelper(sc, transaction, customerName, type);
                        }

                    default: //there is an account for customerName of type accountType
                        return transactionHelper(sc, transaction, customerName, type);
                    }
                break;
        }
    }

    private static boolean transactionHelper(Scanner sc, String transaction, String customerName, String type){
        System.out.printf("How much would you like to %s into the account\n", transaction); //gets the amount for the transaction
        double amount = sc.nextDouble();
        if (transaction.equals("deposit")){ //if deposit, then run deposit, which will check if it is possible
            return customerList.get(customerName).deposit(type, amount);
        }else{ //else it must be withdraw which will check if it is possible
            return customerList.get(customerName).withdraw(type, amount);
        }
    }


    private static int typeOfUser(Scanner sc){
        System.out.println("Will the following transaction be from a manager (m) or a regular user (r)? (exit)");
        String input = sc.next();
        switch (input){
            case("exit"):
                exit = true;
                return -1;
            case("m"):
            case("manager"):
                return 0;
            case("u"):
            case("user"):
            default:
                System.out.println("Please input the correct term");
                return (typeOfUser(sc));
        }
    }

    private static String getCustomer(Scanner sc){
        System.out.println("Please provide your first and last name  (exit to quit or main menu to return to the main menu)");
        String name = sc.next(); //get name of customer or action to perform
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
        }

        if (customerList.get(name) == null){
            System.out.println("Cannot find user in the database\nEnsure that the spelling is correct or that the user is in the database");
            return getCustomer(sc, customerList);
        }

        System.out.println("Welcome, " + customerList.get(name).getName()); //greats customer with full name
        return name;
    }
    
            default:
                if (customerList.get(name) == null){ //if the customer doesn't exist rerun the function
                    System.out.println("Cannot find user in the database\nEnsure that the spelling is correct or that the user is in the database");
                    return getCustomer(sc);
                }
                else{ //else customer does exist in the bank
                    System.out.println("Welcome, " + customerList.get(name).getName()); //greats customer with full name
                    return name;
                }
        }
    }

    static List<Dictionary<?,Customer>> listOfCustomersFromCSV(String filePath){
        List<Dictionary<?,Customer>> customerList = new ArrayList<>();
        Dictionary<String,Customer> customerNameList = new Hashtable<>();
        Dictionary<Integer,Customer> customerAccountNumberList = new Hashtable<>();
    /*
     * converts the entries given in the CSV to a List of "Customer" building each "Customer" object and their 3 subclass "Account" objects.
     * @param filePath String that shows the location of the file. Put as a parameter for flexibility if needed in a future project.
     * @return List<Map<Object, Object>> the list of all "Customer" objects fully constructed with all their information and their accounts created as well.The diffrent maps are for quickly looking up the customer based on their name or on their account number
     */
    private static Dictionary <?, Customer> listOfCustomersFromCSV(String filePath){
        List<Map<?,Customer>> customerList = new ArrayList<>();
        Map<String,Customer> customerNameList = new HashMap<>();
        Map<Integer,Customer> customerAccountNumberList = new HashMap<>();
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
            }
        }
        //catch if reading failed
        catch(IOException e){
            e.printStackTrace();
        }
        //return all customers read 
        customerList.add(customerAccountNumberList);
        customerList.add(customerNameList);
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
            //Update each customer one by one

            // Get the Enumeration of Customers from the second Dictionary
            Enumeration<Customer> customers = customerList.get(1).elements();
    
            // Iterate over each Customer in the second Dictionary
            while (customers.hasMoreElements()) {
                Customer currentAccountHolder = customers.nextElement();
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
