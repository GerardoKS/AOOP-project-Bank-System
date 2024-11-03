import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
/**
 * This class is to test if the methods in Account work properly
 * The methods with returns in Accounts are canDeposit, CanWithdraw, changeBalance and toString, .
 * The changeBalance contains canDeposit and CanWithdraw
 * @author Gerardo Sillas
 */
public class accountTest {
    Account testAccount;
    Customer testCustomer;
    Files testFile = new Files();
    @BeforeEach
    void setUp(){
        testCustomer = new Customer();
        testAccount = new Account(1000,0,testCustomer);
    }
    @AfterEach
    void tearDown(){
        testCustomer = null;
        testAccount = null;
    }
 

    /**
     * test the changeBalance function in Account to see if withdraw is to large, will it fail
     */
    @Test
    void testChangeBalanceNegativeInfinity(){
         //try a condition that should always fail
        assertFalse(testAccount.changeBalance(Double.NEGATIVE_INFINITY, testFile));
    }

    /**
     * test the changeBalance function in Account to see if withdraw is nothing, will it pass
     */
    @Test
    void testChangeBalanceZero(){
       //try 0
        assertTrue(testAccount.changeBalance(0, testFile));
    }   
    
    /**
     * test the changeBalance function in Account to see if withdraw of a decimal valued number with sufficient funds, will pass
     */
    @Test
    void testChangeBalanceDecimalValuesPass(){
        //try decimal values
        testAccount.setBalance(0.5);
        assertTrue(testAccount.changeBalance(-0.5, testFile));
    }   
    
    /**
     * test the changeBalance function in Account to see if withdraw of a decimal valued number without sufficient funds, will fail
     */
    @Test
    void testChangeBalanceDecimalValuesFail(){
        //try decimal values
        //balance == 0
        assertFalse(testAccount.changeBalance(-0.5, testFile));
    }    
    
    /**
     * test the changeBalance function in Account to see if withdraw of a whole number with sufficient funds, will pass
     */
    @Test
    void testChangeBalanceWholeValuePass(){
        //try decimal values
        testAccount.setBalance(1);
        assertTrue(testAccount.changeBalance(-1, testFile));
    }    

    /**
     * test the changeBalance function in Account to see if withdraw of a whole number without sufficient funds, will fail
     */
    @Test
    void testChangeBalanceWholeValueFail(){
        //try decimal values
        //balance == 0
        assertFalse(testAccount.changeBalance(-1, testFile));
    } 

    /**
     * test the changeBalance function in Account to see if deposits will pass
     */
    @Test
    void testChangeBalanceDeposit(){
        //try decimal values
        assertTrue(testAccount.changeBalance(1, testFile));
    } 

    /**
     * test the toString function in Account to see if the String it returns is correct
     */
    @Test
    void testToString(){
        testCustomer.setFirstName("Donald");
        testCustomer.setLastName("Duck");
        assertEquals("Account number: " + 1000 + "\nAccount holder: " + "Donald Duck" + "\nAccount balance: " + 0.0, testAccount.toString());
    }
 
}
 
