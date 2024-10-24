import java.util.*;

public class Customer extends Person{
    private Dictionary <String, Account> accounts;
    private int id;

    public Customer(){
        Dictionary <String, Account>accounts = new Hashtable<>();
        Account a = new Account();
        accounts.put("saving", a);
        accounts.put("checking", a);
        accounts.put("credit", a);
        id = -1;
    }

    public Customer(Dictionary accounts, int id, String firstName, String lastName, String dob, String address, String phone){
        super(firstName, lastName, dob, address, phone);
        this.accounts = accounts;
        this.id = id;
    }
    /**
     * sets the accounts of the customer
     * @param accounts
     */
    public void setAccounts(Dictionary accounts){
        this.accounts = accounts;
    }

    public void setId(int id){
        this.id = id;
    }

    /**
     * prints and returns the list of accounts of the customer
     * @return list of accounts
     */
    public Dictionary getAccounts(){
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
    private String findAccountType(int number){
        Enumeration keys = accounts.keys();
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
        Enumeration keys = accounts.keys();
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
        switch (this.numberOfAccounts()){
            case(0):
                System.out.println("There is no accounts under this customer.");
                return 0;
            case(1):
                Enumeration keys = accounts.keys();
                while(keys.hasMoreElements()) {
                    String key = (String) keys.nextElement();
                    if (accounts.get(key) != null) return getBalance(key);
                }
                break;
            default:
                System.out.println("There are more than one accounts to this customer's name please specify which type of account(checking, saving, or credit) or account number you would like to get the balance of (exit or main menu)");
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                switch (input.toLowerCase()){
                    case("e"):
                    case("exit"):
                        return -1;
                    case("m"):
                    case("main menu"):
                        return -2;
                    case ("checking"):
                        return getBalance("checking");
                    case ("saving"):
                        return getBalance("saving");
                    case ("credit"):
                        return getBalance("credit");
                    default:
                        keys = accounts.keys();
                        while(keys.hasMoreElements()) {
                            String key = (String) keys.nextElement();
                            if (accounts.get(key).getAccountNumber() == Integer.parseInt(input)) return getBalance(key);
                        }
                        System.out.println("Couldn't find account of that type or with that account number\nMake sure you are only including the type or account number not both.");
                        return getBalance();
                }

                
        }
 
    }

    /**
     * returns the balance of the account based on the account number
     * @param account_num the number of the account to get the balance of
     * @return the balance of the account
     */
    public double getBalance(String accountType){
        accounts.get(accountType).displayBalance();
        return (accounts.get(accountType).getBalance());
    }

    /**
     * creates a new account of the type type and with the given balance
     * @param balance the balance of the account
     * @param type the type of the new account
     * @return true if it was successful, false otherwise
     */
    public boolean add_account(String type, Account account){
        if (accounts.get(type) != null){
            System.out.printf("Account of type %s already exists.\n", type);
            return false;
        }
        
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
        if (accounts.get(accountType)==null){
            System.out.println("No account of such account number found.");
            return false;
        }
    
        if (accountType == "credit" && !accounts.get(accountType).canDeposit(amount)){
            System.out.printf("Cannot deposit %d into account of type credit", amount);
            return false;
        }
        accounts.get(accountType).changeBalance(amount);
        return true;
    }

    public boolean deposit(int accountNumber, double amount){
        String type = findAccountType(accountNumber);
        switch (type){
            case (null):
                System.out.println("Account of account number %d cannot be found in this customer please try again");
                return false;
            default:
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
    public boolean transfer(int source , int dest, double amount){
        String sourceType = findAccountType(source);
        String destType = findAccountType(dest);
        return transfer(sourceType, destType, amount);
        

    }

    public boolean transfer(String source, String dest, double amount){
        if (source == null || dest == null){
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts.get(source).canWithdrawal(amount) && accounts.get(dest).canDeposit(amount)){
            accounts.get(source).changeBalance(-amount);
            accounts.get(dest).changeBalance(amount);
            return true;
        }
        
        System.out.println("One or both transactions cannot be performed.");
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
    public boolean pay(Customer customer, int source, int dest, double amount ){
        String sourceType = findAccountType(source);
        String destType = customer.findAccountType(dest);
        return pay(customer, sourceType, destType, amount);        
    }

    public boolean pay(Customer customer, String source, String dest, double amount){
        if (source == null || dest == null){
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts.get(source).canWithdrawal(amount) && customer.accounts.get(dest).canDeposit(amount)){
            accounts.get(source).changeBalance(-amount);
            customer.accounts.get(dest).changeBalance(amount);
            return true;
        }
        
        System.out.println("One or both transactions cannot be performed.");
        return false;
    }

    /**
     * returns the number of active accounts
     * @return number of non-null accounts
     */
    public int numberOfAccounts(){
        int count = 0;
        Enumeration keys = accounts.keys();      
        while(keys.hasMoreElements()) {
            String str = (String)keys.nextElement();
            if (accounts.get(str) != null) count++;
        }  
        return count;
    }
}
