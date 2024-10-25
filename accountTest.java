public class accountTest {
   
    int accountNumber;
    double balance;
    double max;
    Customer holder;
    Account testCreditObject = new Account();
    // constructor to make dummy account
    accountTest(){
        this.accountNumber = 0000;
        this.balance = 0;
        Customer emptyCustomer = new Customer();
        this.holder = emptyCustomer;
        Account testCreditObject = new Account(accountNumber, balance, holder);
    }
 
    /*
     * the main account testing method that will be called to commense the test, if account was bigger and needed more test this would be wear all the test methods for account would be called
     * @return boolean, shows if you passed all the test(true) or failed(false) any of the test
     */
    public boolean testAccount(){
        System.out.println("Account");
        if(!testChangeBalanceAccount()){
            return false;
        }
        return true;
    }
    /*
     * test the Change Balance function in Credit
     * This method also inlcudes the canDeposit method
     * each has a SOP to indeicate that, that specific test failed
     * @return returns wether the test passed(true) or failed(false)
     */
    private boolean testChangeBalanceAccount(){
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
        if(testCreditObject.changeBalance(-0.5)){
            System.out.println("failed amount -0.5 change balance");
            return false;
        }    
        if(!testCreditObject.changeBalance(0.5)){
            System.out.println("failed amount 0.5 change balance");
            return false;
        }    
        //try positive values
        if(testCreditObject.changeBalance(-1)){
            System.out.println("failed amount -1 change balance");
            return false;
        }    
        if(!testCreditObject.changeBalance(1)){
            System.out.println("failed amount 1 change balance");
            return false;
        }    
        return true;
    }
 
}
 
