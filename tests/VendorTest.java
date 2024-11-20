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

    @Test //Unit test to validate that you can empty the vendor’s inventory.
    public void emptyVendorInventoryTest(){
        ven.addMoney(10.00);

        //buy candy 5 times
        for (int i = 0; i < 5; i++){
            ven.select("Candy");
        }

        Assertions.assertEquals(0, ven.getStock("Candy")); //check that stock is empty

        ven.select("Candy");
        Assertions.assertEquals(0, ven.getStock("Candy")); //check that stock doesn't decrease bellow 0
    }

    @Test //As a User, I would like to restock items on a vendor so players can buy from him later
    public void restockTest(){

        ven.restockItems("Candy", 10);
        Assertions.assertEquals(15, ven.getStock("Candy"));
    }

}