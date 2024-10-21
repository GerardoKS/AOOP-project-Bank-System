public class Account{
    protected int account_number;
    protected double balance;
    protected Customer account_holder;

    public Account(){}

    public Account(int account_number, double balance, Customer holder){
        this.account_number = account_number;
        this.balance = balance;
        this.account_holder = holder;
    }
 
    public void set_account_number(int num){
        this.account_number = num;
    }
    public void set_balance(double balance){
        this.balance = balance;
    }
    public void set_account_holder(Customer holder){
        this.account_holder = holder;
    }
        
    public int get_account_number(){
        return account_number;
    }
    public double get_balance(){
        return balance;
    }
    public Customer get_account_holder(){
        return account_holder;
    }

    // amount is positive it is a deposit
    // amount is negative if it is a transfer or withdrawl
    public boolean change_balance(double amount){
        if(can_withdraw(amount)){
            balance += amount;
            return true;
        }
        return false;
    }

    public void display_account(){
        System.out.println("The account number for "+ account_holder + "is " + account_number + "with a balance of: " + balance);
    }

    public boolean can_withdraw(double amount){
        double checkIfValidBalance = balance + amount;
        if(checkIfValidBalance < 0){
            return false;
        } 
        return true;
    }
}
