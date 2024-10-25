/*
 * This class is to test if the methods in credit work properly
 * @author Gerardo Sillas
 */
public class creditTest {
    int accountNumber;
    double balance;
    double max;
    Customer holder;
    Credit testCreditObject = new Credit();
 
    creditTest(){
        this.accountNumber = 0000;
        this.balance = 0;
        this.max = 2;
        Customer emptyCustomer = new Customer();
        this.holder = emptyCustomer;
        Credit testCreditObject = new Credit(accountNumber, balance, holder, max);
    }
 
    /*
     * the main credit testing method that will be called to commense the test, if credit was bigger and needed more test this would be wear all the test methods for account would be called
     * @return boolean, shows if you passed all the test(true) or failed(false) any of the test
     */
    public boolean testCredit(){
        System.out.println("Credit");
        testCreditObject.setMax(2);
        if(!testChangeBalance()){
            return false;
        }
        return true;
    }
    /*
     * test the Change Balance function in Credit
     * This method also inlcudes the canDeposit method
     * Each test case has an SOP to indicate that that, that taste case failed.
     * @return returns wether the test passed(true) or failed(false)
     */
    private boolean testChangeBalance(){
        testCreditObject.setBalance(0);
        //try a condition that should always fail
        if(testCreditObject.changeBalance(Double.NEGATIVE_INFINITY)){
            System.out.println("failed amount neg inf change balance");
            return false;
        }    
        //try 0
        if(!testCreditObject.changeBalance(0)){
            System.out.println("failed amount 0 change balance");
            return false;
        }    
        //try valeus with decimals
        if(!testCreditObject.changeBalance(-0.5)){
            System.out.println("failed amount -0.5 change balance");
            return false;
        }    
        //pay more that you owe test for values with decimals
        testCreditObject.setBalance(0);
        if(testCreditObject.changeBalance(0.5)){
            System.out.println("failed amount 0.5 change balance");
            return false;
        }    
        //try positive values
        if(!testCreditObject.changeBalance(-1)){
            System.out.println("failed amount -1 change balance");
            return false;
        }    
        //pay more than you owe test for no decimal numbers
        testCreditObject.setBalance(0);
        if(testCreditObject.changeBalance(1)){
            System.out.println("failed amount 1 change balance");
            return false;
        }    
        //if they pay off all their debt
        testCreditObject.setBalance(-1);
        if(!testCreditObject.changeBalance(1)){
            System.out.println("failed amount 1 balance -1 change balance");
            return false;
        }
        //exceed max
        testCreditObject.setBalance(-1);
        if(testCreditObject.changeBalance(-2)){
            System.out.println("failed amount -2 balance -1 change balance");
            return false;
        }  
        //reach max
        testCreditObject.setBalance(-1.5);
        if(!testCreditObject.changeBalance(-0.5)){
            System.out.println("failed amount -0.5 balance -1.5 change balance");
            return false;
        }  
        //exceed max with decimal value
        testCreditObject.setBalance(-2);
        if(testCreditObject.changeBalance(-0.5)){
            System.out.println("failed amount -0.5 balance -2 change balance");
            return false;
        }  
        return true;
    }
}
