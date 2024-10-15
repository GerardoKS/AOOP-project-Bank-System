import Customer;

public class Account {
    protected int account_number;
    protected double balance;
    protected Customer account_holder;

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
        double checkIfValidBalance = balance + amount;
        if(checkIfValidBalance < 0){
            return false;
        } 
        balance = checkIfValidBalance;
        return true;
    }
    public void display_account(){
        System.out.println("The account number for "+ account_holder + "is " + account_number);
    }
}
