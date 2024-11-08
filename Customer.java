import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.Scanner;



/**
 * The Customer class represents a customer with multiple accounts.
 * It extends the Person class and provides functionality to manage
 * accounts, such as deposits, withdrawals, transfers, and viewing balances.
 * 
 * @author Hannah Ayala
 */
public class Customer extends Person{
    private Dictionary <String, Account> accounts;
    private int id;
    private int creditScore;

    /**
     * Default constructor for the Customer class.
     * Initializes an empty hashtable of accounts and sets the id to -1.
     */
    public Customer(){
        accounts = new Hashtable<>();
        id = -1;
    }

    /**
     * Constructs a Customer with specified account information and personal details.
     * 
     * @param accounts The dictionary of the customer's accounts.
     * @param id The unique ID of the customer.
     * @param firstName The customer's first name.
     * @param lastName The customer's last name.
     * @param dob The customer's date of birth.
     * @param address The customer's address.
     * @param phone The customer's phone number.
     */
    public Customer(Dictionary <String, Account> accounts, int id, String firstName, String lastName, String dob, String address, String phone, int creditScore){
        super(firstName, lastName, dob, address, phone);
        this.accounts = accounts;
        this.id = id;
        this.creditScore = creditScore;
    }

    /**
     * Sets the customer's accounts.
     * 
     * @param accounts The dictionary of the customer's accounts.
     */
    public void setAccounts(Dictionary <String, Account> accounts){
        this.accounts = accounts;
    }

    /**
     * Sets the customer's unique ID.
     * 
     * @param id The unique ID of the customer.
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Sets the customer's credit score.
     * 
     * @param creditScore The credit score of the customer.
     */
    public void setCreditScore(int creditScore){
        this.creditScore = creditScore;
    }

    /**
     * Returns the customer's accounts.
     * 
     * @return A dictionary of the customer's accounts.
     */
    public Dictionary<String, Account> getAccounts(){
        return accounts;
    } 

    /**
     * Returns the customer's unique ID.
     * 
     * @return The unique ID of the customer.
     */
    public int getId(){
        return id;
    }

    
    /**
     * Returns the customer's credit score.
     * 
     * @return The credit score of the customer.
     */
    public int getCreditScore(){
        return creditScore;
    }

    /**
     * Finds the type of account based on the account number.
     * 
     * @param number The account number to find.
     * @return The type of the account as a string, or null if not found.
     */
    public String findAccountType(int number){  
        Enumeration<String> keys = accounts.keys();
        while(keys.hasMoreElements()) { //traverse through dictionary
            String key = (String) keys.nextElement();
            if (accounts.get(key).getAccountNumber() == number) return key; //if an account number matches then return the type
        }
        return null;
    }

    /**
    * Returns the balance of the desired account. If the customer has more than one account,
    * it prompts the user for the account type or number.
    * 
    * @param f The file object used for logging.
    * @return The balance of the specified account or -1, -2, -3 for exit codes.
    */
    public int getBalance(Files f){
        System.out.println("Please specify which type of account(checking, saving, or credit) or account number you would like to get the balance of (exit (e) or main menu (m) or user menu (u))");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(input.equals("")) input = sc.nextLine();
        switch (input.toLowerCase()){ //input could be account number, account type, action, or invalid
            case("e"):
            case("exit"):
                return -1;
            case("m"):
            case("main menu"):
                return -2;
            case("u"):
            case("user"):
            case("user menu"): //deal with the actions
                return -3;
            case ("checking"):
            case ("saving"):
            case ("credit"): //if its a type then get balance
                    getBalance(input.toLowerCase(), f); 
                    break;
            default: //not an action or account type, must be either an account number or invalid input
                try {
                    String type = findAccountType(Integer.parseInt(input)); //if not an int, throw error
                    if (type == null) throw new Exception(); //if int but not an account of this customer, throw error
                    return getBalance(type, f); //is an account of this customer
                } catch (Exception e) {
                    System.out.println("No such account found");
                    return getBalance(f); //try again or give the option to leave
                }
        }  
        return 0;   
    }
    
    /**
     * Returns the balance of the account of the specified type.
     * 
     * @param accountType The type of the account.
     * @param f The file object used for logging.
     * @return The balance of the account.
     */
    public int getBalance(String accountType, Files f){
        accounts.get(accountType).displayBalance(f);
        return 0;
    }


    /**
     * Deposits or withdraws money into/from the specified account.
     * 
     * @param accountType The type of the account.
     * @param amount The amount to deposit (positive) or withdraw (negative).
     * @param f The file object used for logging.
     * @return true if the transaction was successful, false otherwise.
     */
    public boolean deposit(String accountType, double amount, Files f){
        if (accounts.get(accountType)==null){ //if there is no account of that type
            System.out.println("No account found.");
            return false;
        }
    
        if (amount>=0 && !accounts.get(accountType).canDeposit(amount)){ //if deposit and you cant deposit
            System.out.printf("Cannot deposit " + amount + " into account\n");
            return false;
        }else if (amount<0 && !accounts.get(accountType).canWithdraw(amount)){ //if withdrawl and you cant withdraw
            System.out.printf("Cannot withdrawl " + -amount + " from account\n");
            return false;
        }

        //else proceed with the transaction
        accounts.get(accountType).changeBalance(amount, f);
        return true;
    }


    /**
     * Deposits or withdraws money into/from the specified account based on the account number.
     * 
     * @param accountNumber The account number.
     * @param amount The amount to deposit or withdraw.
     * @param f The file object used for logging.
     * @return true if the transaction was successful, false otherwise.
     */
    public boolean deposit(int accountNumber, double amount, Files f){
        String type = findAccountType(accountNumber); //find the account type for that number
        if  (type == null){ //if it doesn't exist then return false
            System.out.println("Account cannot be found in this customer please try again");
            return false;
        }else{ //else deposit into that accounttype
            return deposit(type, amount, f);
        }
    }
 
    /**
     * Withdraws money from the specified account.
     * 
     * @param accountType The type of the account.
     * @param amount The amount to withdraw.
     * @param f The file object used for logging.
     * @return true if successful, false otherwise.
     */
    public boolean withdraw(String accountType, double amount, Files f){
        return deposit(accountType, -amount, f);
    }

    /**
     * Withdraws money from the specified account based on the account number.
     * 
     * @param accountNumber The account number.
     * @param amount The amount to withdraw.
     * @param f The file object used for logging.
     * @return true if successful, false otherwise.
     */
    public boolean withdraw(int accountNumber, double amount, Files f){
        return deposit(accountNumber, -amount, f);
    }

    /**
     * Transfers money from one account to another within the same customer.
     * 
     * @param source The source account number.
     * @param dest The destination account number.
     * @param amount The amount to be transferred.
     * @param f The file object used for logging.
     * @return true if successful, false otherwise.
     */
    public boolean transfer(int source , int dest, double amount, Files f){
        String sourceType = findAccountType(source);
        String destType = findAccountType(dest);
        return transfer(sourceType, destType, amount, f);
    }

    /**
     * Transfers money from one account to another within the same customer.
     * 
     * @param source The source account type.
     * @param dest The destination account type.
     * @param amount The amount to be transferred.
     * @param f The file object used for logging.
     * @return true if successful, false otherwise.
     */
    public boolean transfer(String source, String dest, double amount, Files f){
        if (accounts.get(source) == null || accounts.get(dest) == null){ //if either account doesn't exist
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts.get(source).canWithdraw(-amount) && accounts.get(dest).canDeposit(amount)){ //if you can deposit and you can withdraw proceed with the transaction
            String output = "(" + this.getName() + " transfered " + amount + " from " +  source + " account to " + dest + " account)";
            System.out.println(output);
            f.writeFile(output); //log
            accounts.get(source).changeBalance(-amount, f); //withdraw from the source account
            accounts.get(dest).changeBalance(amount, f); //deposit into the dest account
            return true;
        }
    
        System.out.println("One or both transactions cannot be performed."); //means either or both transactions couldn't be performed
        return false;
    }

    /**
     * Transfers a payment from one account of the current customer to another account of a different customer.
     * 
     * @param customer The customer who will receive the payment.
     * @param source The source account number of the current customer.
     * @param dest The destination account number of the receiving customer.
     * @param amount The amount to be transferred.
     * @param f The file object used for logging the transaction.
     * @return true if the payment was successful, false otherwise.
     */
    public boolean pay(Customer customer, int source, int dest, double amount, Files f ){
        String sourceType = findAccountType(source);
        String destType = customer.findAccountType(dest);
        return pay(customer, sourceType, destType, amount, f);        
    }

    /**
     * Transfers a payment from the current customer's account to another account belonging to a different customer.
     * 
     * @param customer The customer receiving the payment.
     * @param source The type of the source account (e.g., "checking", "saving") from which the payment will be made.
     * @param dest The type of the destination account (e.g., "checking", "saving") to which the payment will be transferred.
     * @param amount The amount to be transferred.
     * @param f The file object used for logging the transaction.
     * @return true if the payment was successful, false otherwise.
     */
    public boolean pay(Customer customer, String source, String dest, double amount, Files f){
        if (accounts.get(source) == null || customer.accounts.get(dest) == null){ //if either of the accounts don't exist
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts.get(source).canWithdraw(-amount) && customer.accounts.get(dest).canDeposit(amount)){ //if both actions can be performed
            String output = "(" + this.getName() + " paid " + amount + " from " +  source + " account to " + customer.getName() +  "'s " + dest + " account)";
            System.out.println(output);
            f.writeFile(output);
            accounts.get(source).changeBalance(-amount, f); //withdraw from the source account
            customer.accounts.get(dest).changeBalance(amount, f); //deposit into the dest account of customer
            return true;
        }
        
        //else either or both transactions cannot be performed
        System.out.println("One or both transactions cannot be performed.");
        return false;
    }
}