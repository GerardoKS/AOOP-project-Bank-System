package src;

/**
 * Checking class extended from Account class to represent Checking Accounts
 * @author Gerardo Sillas
 */
public class Checking extends Account{
    public Checking(){}
    //constuctor that sets all the attributes
    public Checking(int accountNumber, double balance, Customer holder){
        super(accountNumber,balance, holder);
    }
}