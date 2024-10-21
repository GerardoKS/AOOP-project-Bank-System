public class Credit extends Account{
    private double max;
    
    public Credit(){
        super();
    }
    

    public Credit(int account_number, double balance, Customer holder, double max){
        super(account_number,balance, holder);
        this.max = max;
    }

    public void set_max(double max){
        this.max = max;
    }
    public double get_max(){
        return max;
    }
    public boolean change_balance(double amount){
        if(can_deposit(amount)){
            balance += amount;
            return true;
        }
        return false;
    }
    public void display_account(){
        System.out.println("The account number for "+ account_holder + "is " + account_number + "and this account has a max of:" + max + "and has a used: "+ balance);
    }
    public boolean can_deposit(double amount){
        if(amount > max){
            return false;
        }
        return true;
    }
}
