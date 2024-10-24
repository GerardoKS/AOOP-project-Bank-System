import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Dictionary;
import java.util.Scanner;


public class Customer extends Person{
    private Dictionary <String, Account> accounts;
    private int id;

    public Customer(){
        accounts = new Hashtable<>();
        accounts.put("saving", null);
        accounts.put("checking", null);
        accounts.put("credit", null);
        id = -1;
    }

    public Customer(Dictionary <String, Account> accounts, int id, String firstName, String lastName, String dob, String address, String phone){
        super(firstName, lastName, dob, address, phone);
        this.accounts = accounts;
        this.id = id;
    }
    /**
     * sets the accounts of the customer
     * @param accounts
     */
    public void setAccounts(Dictionary <String, Account> accounts){
        this.accounts = accounts;
    }

    public void setId(int id){
        this.id = id;
    }

    /**
     * prints and returns the list of accounts of the customer
     * @return list of accounts
     */
    public Dictionary<String, Account> getAccounts(){
        return accounts;
    } 

    public int getId(){
        return id;
    }


    /**
     * 
     * @param number the account number to find
     * @return the index position the account is in accounts
     */
    public String findAccountType(int number){  
        Enumeration<String> keys = accounts.keys();
        while(keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (accounts.get(key).getAccountNumber() == number) return key;
        }
        return null;
    }

    /**
     * displays all of this customers accounts
     */
    public void displayAccounts(){
        Enumeration<String> keys = accounts.keys();
        while(keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (accounts.get(key) != null){
                System.out.printf("Account of type %s balance: ", key);
                accounts.get(key).displayAccount();
            }
        }
    }

    /**
     * if customer has only one account, return the balance of their only account
     * else ask for more information, based on which, return the balance of the wanted account
     * @return the balance of the desired account
     */
    public double getBalance(){
        switch (this.numberOfAccounts()){ //count the number of accounts
            case(0): //if there are no accounts
                System.out.println("There are no accounts under this customer.");
                return 0.0; //return 0.0
            case(1): //if there is only one account, print it
                Enumeration<String> keys = accounts.keys();
                while(keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    if (accounts.get(key) != null) return getBalance(key);
                }
                break;
            default: //there are more than one accounts
                System.out.println("There are more than one accounts to this customer's name please specify which type of account(checking, saving, or credit) or account number you would like to get the balance of (exit or main menu)");
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                switch (input.toLowerCase()){ //inut could be account number or account type or action
                    case("e"):
                    case("exit"):
                        return -1.0;
                    case("m"):
                    case("main menu"):
                        return -2.0;
                    case ("checking"):
                        return getBalance("checking");
                    case ("saving"):
                        return getBalance("saving");
                    case ("credit"):
                        return getBalance("credit");
                    default: //not an action or account type, must be either an account number or invalid input
                        keys = accounts.keys();
                        while(keys.hasMoreElements()) {
                            String key = (String) keys.nextElement();
                            if (accounts.get(key).getAccountNumber() == Integer.parseInt(input)) return getBalance(key); //there was a matching account for the account number
                        }
                        System.out.println("Couldn't find account of that type or with that account number\nMake sure you are only including the type or account number not both.\n"); //invalid input
                        return getBalance();
                }     
        }
        return 0.0;
 
    }

    /**
     * returns the balance of the account based on the account number
     * @param account_num the number of the account to get the balance of
     * @return the balance of the account
     */
    public double getBalance(String accountType){
        System.out.printf("Balance %d", accounts.get(accountType).getBalance());
        //accounts.get(accountType).displayBalance());  //IF TIME MAKE IT DISPLAY NEATLY
        return (accounts.get(accountType).getBalance());
    }

    /**
     * creates a new account of the type type and with the given balance
     * @param balance the balance of the account
     * @param type the type of the new account
     * @return true if it was successful, false otherwise
     */
    public boolean add_account(String type, Account account){
        if (accounts.get(type) != null){ //if there is already an account with that type
            System.out.printf("Account of type %s already exists.\n", type);
            return false;
        }
        //else create the account
        accounts.put(type, account);
        return true;
    }


    /**
     * deposits money into the specified account number
     * @param account account number to deposit into
     * @param amount amount to be deposited
     * @return true if successful, false otherwise
     */
    public boolean deposit(String accountType, double amount){
        if (accounts.get(accountType)==null){ //if there is no account of that type
            System.out.println("No account of such account number found.");
            return false;
        }
    
        if (amount>=0 && accountType == "credit" && !accounts.get(accountType).canDeposit(amount)){ //if it is a deposit for credit, check if you can deposit, if not then return false
            System.out.printf("Cannot deposit %d into account of type credit\n", amount);
            return false;
        }else if (amount<0 && !accounts.get(accountType).canWithdrawal(amount)){ //if we are withdrawing check if we can withdrawal
            System.out.printf("Cannot withdrawl %d from account of type %s\n", amount, accountType);
        }

        //else proceed with the transaction
        accounts.get(accountType).changeBalance(amount);
        return true;
    }


    //MIGHT BE COMPLETELY USELESS BECAUSE OF THE MAIN FILE
    public boolean deposit(int accountNumber, double amount){
        String type = findAccountType(accountNumber); //find the account type for that number
        switch (type){
            case (null): //if it doesn't exist then return false
                System.out.println("Account of account number %d cannot be found in this customer please try again");
                return false;
            default: //else deposit into that accounttype
                return deposit(type, amount);

        }
    }

    /**
     * withdraws the amount frm the specified account number
     * @param account account number to withrow from
     * @param amount amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdrawal(String accountType, double amount){
        return deposit(accountType, -amount);
    }

    public boolean withdrawal(int accountNumber, double amount){
        return deposit(accountNumber, -amount);
    }

    /**
     * withdraws amount from the source account and deposits amoung into the
     * destination account
     * @param source account number of the source account
     * @param destination account number of the destination account
     * @param amount amount to be deposited
     * @return true if successful, false otherwise
     */
    //MIGHT BE COMPLETELY USELESS BECAUSE OF THE MAIN
    public boolean transfer(int source , int dest, double amount){
        String sourceType = findAccountType(source);
        String destType = findAccountType(dest);
        return transfer(sourceType, destType, amount);
    }

    public boolean transfer(String source, String dest, double amount){
        if (accounts.get(source) == null || accounts.get(dest) == null){ //if either account doesn't exist
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts.get(source).canWithdrawal(amount) && accounts.get(dest).canDeposit(amount)){ //if you can deposit and you can withdrawal proceed with the transaction
            accounts.get(source).changeBalance(-amount); //withdrawal from the source account
            accounts.get(dest).changeBalance(amount); //deposit into the dest account
            return true;
        }
        
        System.out.println("One or both transactions cannot be performed."); //means either or both transactions couldn't be performed
        return false;
    }

    /**
     * withdraws amount from the current custmer source account and 
     * deposits amount into the param customer destination account number
     * @param customer customer owner of destination account
     * @param source account number of the source account
     * @param destination account number of the destination account
     * @param amount amount to be paid
     * @return true if successful, false otherwise
     */

    //MIGHT BE COMPLETELY USELESS BECAUSE OF MAIN
    public boolean pay(Customer customer, int source, int dest, double amount ){
        String sourceType = findAccountType(source);
        String destType = customer.findAccountType(dest);
        return pay(customer, sourceType, destType, amount);        
    }


    public boolean pay(Customer customer, String source, String dest, double amount){
        if (accounts.get(source) == null || customer.accounts.get(dest) == null){ //if either of the accounts don't exist
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts.get(source).canWithdrawal(amount) && (Credit) customer.accounts.get(dest).canDeposit(amount)){ //if both actions can be performed
            accounts.get(source).changeBalance(-amount); //withdrawal from the source account
            customer.accounts.get(dest).changeBalance(amount); //deposit into the dest account of customer
            return true;
        }
        
        //else either or both transactions cannot be performed
        System.out.println("One or both transactions cannot be performed.");
        return false;
    }

    /**
     * returns the number of active accounts
     * @return number of non-null accounts
     */
    public int numberOfAccounts(){
        int count = 0;
        Enumeration<String> keys = accounts.keys();      
        while(keys.hasMoreElements()) {
            String str = (String)keys.nextElement();
            if (accounts.get(str) != null) count++;
        }  
        return count;
    }
}
