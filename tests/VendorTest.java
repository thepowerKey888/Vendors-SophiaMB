import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {

    static Vendor ven;

    @BeforeEach
    public void setUp(){
        ven = new Vendor(1, 2);
    }
    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }

    @Test //JUnit test case to validate that you can add money to a vendor
    public void addMoneyTest(){
        ven.addMoney(10);
        Assertions.assertEquals(10, ven.getBalance());
    }



}