
/*
 * Credit class that is extended off Account class to represent Credit Accounts
 * @author Gerardo Sillas
 */

public class Credit extends Account{
    /*
     * represents the limit to how much the balance can be without being declined
     */
    private double max;
    
    public Credit(){
        super();
    }
    
    //constuctor that sets all the atttributes including max
    public Credit(int accountNumber, double balance, Customer holder, double max){
        super(accountNumber,balance, holder);
        this.max = max;
    }

    //max setter
    public void setMax(double max){
        this.max = max;
    }
    //max getter
    public double getMax(){
        return max;
    }

    /*
     * changes the balance based on the amount given
     * @param amount positive double that shows how much money is tryying to be charged to the credit account
     * @return boolean that shows wether the charge went through
     */
    public boolean changeBalance(double amount){
        if(canDeposit(amount)){
            balance += amount;
            return true;
        }
        return false;
    }
    /*
     * prints the account holder first name, last name, account number, max, and balance 
     */
    public void displayAccount(){
        System.out.println("The account number for "+ accountHolder.getFirstName() + " " + accountHolder.getLastName() + "is " + accountNumber + "and this account has a max limit of: $" + max + "and has a used: $"+ balance);
    }

    /*
     * checks if you the amount will put the users credit total charge past the max amount 
     * @param amount positive double that shows how much money is trying to be charged to the credit account
     * @return boolean that tells if the sun of balance and amount will exceeed the max 
     */
    public boolean canWithdraw(double amount){
        if(((balance - amount)*-1) > max){
            return false;
        }
        return true;
    }
    public boolean canDeposit(double amount){
        if((balance + amount) <= 0){
            return true;
        }
        return false;
    }


}
