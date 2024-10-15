public class Customer extends Person{
    private Account[] accounts;

    public Customer(){
        accounts = new Account[3];
    }

    /**
     * sets the accounts of the customer
     * @param accounts
     */
    public void set_accounts(Account[] accounts){
        this.accounts = accounts;
    }

    /**
     * prints and returns the list of accounts of the customer
     * @return list of accounts
     */
    public Account[] get_accounts(){
        return accounts;
    } 

    /**
     * displays all of this customers accounts
     */
    public void display_accounts(){
        if (this.is_empty()): System.out.println("There are no accounts to display"); //no accounts
        else{
            for(Account a: accounts)
                if (a != null)
                    a.display_account();
        }
    }

    /**
     * if customer has only one account, return the balance of their only account
     * else ask for more information, based on which, return the balance of the wanted account
     * @return the balance of the desired account
     */
    public double get_balance(){

    }

    /**
     * returns the balance of the account based on the account number
     * @param account_num the number of the account to get the balance of
     * @return the balance of the account
     */
    public double get_balance(int account_num){

    }

    /**
     * creates a new account of the type type and with the given balance
     * @param balance the balance of the account
     * @param type the type of the new account
     * @return true if it was successful, false otherwise
     */
    public boolean add_account(double balance, String type){

    }

    /**
     * deposits money into the specified account number
     * @param account account number to deposit into
     * @param amount amount to be deposited
     * @return true if successful, false otherwise
     */
    public boolean deposit(int account, double amount){

    }

    /**
     * withdraws the amount frm the specified account number
     * @param account account number to withrow from
     * @param amount amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(int account, double amount){

    }

    /**
     * withdraws amount from the source account and deposits amoung into the
     * destination account
     * @param source account number of the source account
     * @param destination account number of the destination account
     * @param amount amount to be deposited
     * @return true if successful, false otherwise
     */
    public boolean transfer(int source , int destination, double amount){

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
    public boolean pay(Customer customer, int source, int destination, double amount ){

    }

    /**
     * checks if the customer has at least one account
     * @return true if all of the accounts are null, false if it contains an account
     */
    public boolean is_empty(){

    }
}
