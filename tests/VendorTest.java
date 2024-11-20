import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {

    static Vendor ven;

    @BeforeEach
    public void setUp(){
        ven = new Vendor(5, 5);
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

    @Test //JUnit test to validate that you can buy an item from a vendor.
    public void buyItemTest(){
        ven.addMoney(5.00);
        ven.select("Candy");

        Assertions.assertEquals(4, ven.getStock("Candy")); //check that stock decreases
        Assertions.assertEquals(3.75, ven.getBalance()); //check that balance was reduced correctly

    }

}