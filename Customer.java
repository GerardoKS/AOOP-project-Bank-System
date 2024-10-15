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
     * 
     * @param number the account number to find
     * @return the index position the account is in accounts
     */
    private int find_account_index(int number){
        for(int i = 0; i<3; i++){
            if (accounts[i].get_account_number() == number)
                return i;
        }
        return -1;
    }

    /**
     * displays all of this customers accounts
     */
    public void display_accounts(){
        if (this.number_of_accounts() == 0): System.out.println("There are no accounts to display"); //no accounts
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
        if (this.number_of_accounts() == 1){ //if just one then find that account and return the balance
            for(Account a: accounts)
                if (a != null): return get_balance(a.get_account_number());
        }else if (this.number_of_accounts() == 0){ //if no accounts display message
            System.out.println("There is no accounts under this customer.");
            return 0;
        }else{ //if multiple accounts ask for more information
            System.out.println("There are more than one accounts to this customer's name please specify which account you would like to get the balance of");
            //get user input on the account to get the balance off
        }
    }

    /**
     * returns the balance of the account based on the account number
     * @param account_num the number of the account to get the balance of
     * @return the balance of the account
     */
    public double get_balance(int account_num){
        int index = find_account_index(account_num);
        if (index != -1)
            return (accounts[index].get_balance());
        System.out.println("No account with such account number was found under this customer");
        return 0;
    }

    /**
     * creates a new account of the type type and with the given balance
     * @param balance the balance of the account
     * @param type the type of the new account
     * @return true if it was successful, false otherwise
     */
    public boolean add_account(double balance, String type){
        if (type.equals("Checking") && accounts[0] == null){
            Account checking = new Checking(balance);
            accounts[0] = checking;
            return true;
        }else if (type.equals("Saving") && accounts[1] == null){
            Account savings = new Savings(balance);
            accounts[1] = savings;
            return true;
        }else if (type.equals("Credit") && accounts[2] != null){
            Account credit = new Credit(balance);
            accounts[2] = credit;
            return true;
        }
        System.out.printf("Account of type %s already exists.\n", type);
        return false;
    }
    
    /**
     * 
     * @param type type of account to create
     * @return true if successful, false otherwise
     */
    public boolean add_account(String type){
        return this.add_account(0, type);
    }


    /**
     * deposits money into the specified account number
     * @param account account number to deposit into
     * @param amount amount to be deposited
     * @return true if successful, false otherwise
     */
    public boolean deposit(int account, double amount){
        int index = find_account_index(account);
        if (index == -1){
            System.out.println("No account of such account number found.");
            return false;
        }

        if (accounts[index].can_deposit(amount)){
                    accounts[index].change_balance(amount);
                    return true;
                }
    }

    /**
     * withdraws the amount frm the specified account number
     * @param account account number to withrow from
     * @param amount amount to withdraw
     * @return true if successful, false otherwise
     */
    public boolean withdraw(int account, double amount){
        return deposit(account, -amount);
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
        int index_source = find_account_index(source);
        int index_dest = find_account_index(destination);

        if (index_source == -1 || index_dest == -1){
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts[index_source].can_withdraw(amount) && accounts[index_dest].can_deposit(amount)){
            accounts[index_source].change_balance(-amount);
            accounts[index_dest].change_balance(amount);
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
    public boolean pay(Customer customer, int source, int destination, double amount ){
        int index_source = find_account_index(source);
        int index_dest = customer.find_account_index(destination);

        if (index_source == -1 || index_dest == -1){
            System.out.println("One or both of the specified account numbers do not exist under this customer.");
            return false;
        }
        if (accounts[index_source].can_withdraw(amount) && customer.accounts[index_dest].can_deposit(amount)){
            accounts[index_source].change_balance(-amount);
            customer.accounts[index_dest].change_balance(amount);
            return true;
        }
        
        System.out.println("One or both transactions cannot be performed.");
        return false;

    }

    /**
     * returns the number of active accounts
     * @return number of non-null accounts
     */
    public int number_of_accounts(){
        int count = 0;
        for(Account a: accounts)
            if (a != null) count++;
        return count;
    }
}
