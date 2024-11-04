package tst;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    accountTest.class,
    creditTest.class
})
public class TestSuite{
}

