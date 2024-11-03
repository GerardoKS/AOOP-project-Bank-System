import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
/**
 * This class is to test if the methods in credit work properly
 * @author Gerardo Sillas
 */
public class creditTest {
    Customer testHolder;
    Credit testCreditAccount;
    Files testFile = new Files();
 
    @BeforeEach
    void setup(){
        testHolder = new Customer();
        testCreditAccount = new Credit(3000, 0, testHolder, 0);
    }

    @AfterEach
    void tearDown(){
        testHolder = null;
        testCreditAccount = null;
    }


    /**
     * test the changeBalance function in Credit to see if withdraw is to large, will it fail
     */
    @Test
    void testChangeBalanceNegativeInfinity(){
    //try a condition that should always fail
        assertFalse(testCreditAccount.changeBalance(Double.NEGATIVE_INFINITY, testFile));
    }  

    /**
     * test the changeBalance function in Credit to see if withdraw is nothing, will it pass
     */
    @Test
    void testChangeBalanceZero(){
        //try 0
        assertTrue(testCreditAccount.changeBalance(0, testFile));
    }    

    /**
     * test the changeBalance function in Credit to see if withdraw of a decimal valued number with a diffrence of balance and amount is less than or equal to max, will pass
     */
    @Test
    void testChangeBalanceDecimalValuesPass(){
        //try valeus with decimals
        //balance == 0
        testCreditAccount.setMax(-0.5);
        assertTrue(testCreditAccount.changeBalance(-0.5, testFile));
    }    

    /**
     * test the changeBalance function in Credit to see if withdraw of a decimal valued number with a diffrence of balance and amount is greater than max, will fail
     */
    @Test
    void testChangeBalanceDecimalValuesFail(){
        //try valeus with decimals
        //balance == 0
        //max == 0
        assertFalse(testCreditAccount.changeBalance(-0.5, testFile));
    }    

    /**
     * test the changeBalance function in Credit to see if withdraw of a whole valued number with a diffrence of balance and amount is less than or equal to max, will pass
     */
    @Test
    void testChangeBalanceWholeValuesPass(){
        //change value for whole valued numbers
        testCreditAccount.setMax(-1);
        assertTrue(testCreditAccount.changeBalance(-1, testFile));
    }   

    /**
     * test the changeBalance function in Credit to see if withdraw of a whole valued number with a diffrence of balance and amount is greater than max, will fail
     */
    @Test
    void testChangeBalanceWholeValuesFail(){
        //change balance exceeds max
        //max == 0
        assertFalse(testCreditAccount.changeBalance(-1, testFile));
    }   

    /**
     * test the changeBalance function in Credit to see if paying off debt for a decimal valued amount that is less than or equal to balance, will pass
     */
    @Test
    void testChangeBalanceDecimalValuePayOffCreditDebtPass(){
        //pay off debt on your credit 
        testCreditAccount.setBalance(-0.5);
        assertTrue(testCreditAccount.changeBalance(0.5, testFile));
    }  

    /**
     * test the changeBalance function in Credit to see if paying off debt for a decimal valued amount that is greater than balance, will fail
     */
    @Test
    void testChangeBalanceDecimalValuePayOffCreditDebtFail(){
        //try to pay more than you owe
        //balance == 0
        assertFalse(testCreditAccount.changeBalance(0.5, testFile));
    } 

    /**
     * test the changeBalance function in Credit to see if paying off debt for a whole valued amount that is less than or equal to balance, will pass
     */
    @Test
    void testChangeBalanceWholeValuePayOffCreditDebtPass(){
        //pay off debt on your credit 
        testCreditAccount.setBalance(-1);
        assertTrue(testCreditAccount.changeBalance(1, testFile));
    }  


    /**
     * test the changeBalance function in Credit to see if paying off debt with a whole valued amount that is greater than balance, will fail
     */
    @Test
    void testChangeBalanceWholeValuePayOffCreditDebtFail(){
        //try to pay more than you owe
        //balance == 0
        assertFalse(testCreditAccount.changeBalance(1, testFile));
    } 
}
