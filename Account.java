/*
 * Account class used to be a base for the three account sub categories saving, checking, and credit, used to reduce redudancy through inheritance and polymorphosm
 * @author Gerardo Sillas
 */



public class Account{
    /*
     * account_number, balance and account_holder are all base information needed to have an account not including account specific requirements
     */
    protected int accountNumber;
    protected double balance;
    protected Customer accountHolder;
 
    public Account(){}
 
    //constructor that takes in all the attributes and sets them
    public Account(int accountNumber, double balance, Customer holder){
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountHolder = holder;
    }
 
    //setters
    public void setAccountNumber(int num){
        this.accountNumber = num;
    }
    public void setBalance(double balance){
        this.balance = balance;
    }
    public void setAccountHolder(Customer holder){
        this.accountHolder = holder;
    }
     
    //getters
    public int getAccountNumber(){
        return accountNumber;
    }
    public double getBalance(){
        return balance;
    }
    public Customer getAccountHolder(){
        return accountHolder;
    }
 
    /*
     * Changes the balance based on the amount given
     * @param amount is positive if it is for a deposit or negative if withdrawl
     * @return  wether the transation was successful
     */
 
     public boolean changeBalance(double amount, Files f){
        String output = "";
        if(amount < 0  && canWithdraw(amount)){
            balance += amount;
            output = "Withdrew " + -amount + " from " + accountNumber + " account";
            System.out.println(output);
            f.writeFile(accountHolder.getName() + " " + output);
            return true;
        }
        else if(amount >= 0 && canDeposit(amount)){
            balance += amount;
            output = "Deposited " + amount + " into " + accountNumber + " account";
            System.out.println(output);
            f.writeFile(accountHolder.getName() + " " + output);
            return true;
        }
        System.out.println("Cannot perform transaction");
        return false;
    }
    /*
     * displays account holder first and last name, account number, and balance
     */
    public void displayAccount(Files f){
        System.out.println("The account number for "+ accountHolder.getFirstName() + " " + accountHolder.getLastName() + "is " + accountNumber + "with a balance of: " + balance);
        f.writeFile(accountHolder.getName() + " displayed "  + accountNumber + " account");
    }
 
    /*
     * displays balance for the account with the account number ...
     */
    public void displayBalance(Files f){
        System.out.println("The balance for account number: " + accountNumber + " is: $" + balance);
        f.writeFile(accountHolder.getName() + " displayed balance "  + accountNumber + " account");
    }
 
    /*
     * Checks if the withdrawl should be approved based on the uses balance
     * @peram amount amount that is wan ted to be withdrawn
     * @return boolean returning true if the customer has eenough money in their balance to 2
     */
    public boolean canWithdraw(double amount){
        double checkIfValidBalance = balance + amount;
        if(checkIfValidBalance < 0){
            return false;
        }
        return true;
    }
    /*
     * The Deposit should always be true for this method since you dont have a max amount of money you can have
     */
    public boolean canDeposit(double amount){
        return true;
    }

    public String toString(){
        return("Account number: " + accountNumber + "\nAccount holder: " + accountHolder.getName() + "\nAccount balance: " + balance);
    }
}
