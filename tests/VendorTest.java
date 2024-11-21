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

        ven.restockItems("Candy", 0, 10);
        Assertions.assertEquals(15, ven.getStock("Candy"));
    }

    /*
    As a User, I would like items added to the vendor’s inventory when restocking if they were
    unavailable so that the Vendor’s inventory can change over time
     */
    @Test
    public void addItemsToVendorInventoryTest(){
        Assertions.assertEquals(-1, ven.getStock("Soda")); //soda isn't in inventory
        ven.restockItems("Soda", 1.50, 10); //restocknew item
        Assertions.assertEquals(10, ven.getStock("Soda"));

        //check if you can buy soda
        ven.addMoney(2.00);
        ven.select("Soda");
        Assertions.assertEquals(9, ven.getStock("Soda")); //check stock decreases after buying it

    }

    /*
    As a User, I would like to change the Name of an item at a vendor, so it is easy to manage
    the vendor-available items
     */
    @Test
    public void changeItemNameTest(){
        String changedName = ven.changeItemName("Candy", "Pudding");
        Assertions.assertEquals("Item name changed", changedName);
        Assertions.assertEquals(-1, ven.getStock("Candy")); //chek that old name isn't there
        Assertions.assertEquals(5, ven.getStock("Pudding")); //check new item is there

        //try to change name to empty string
        changedName = ven.changeItemName("Pudding", "");
        Assertions.assertEquals("New name can't be empty or null", changedName);

        //try to change name to an already existing item
        changedName = ven.changeItemName("Pudding", "Gum");
        Assertions.assertEquals("An item with the new name already exists", changedName);

        //try to change name of non existing item
        changedName = ven.changeItemName("Yummy", "Yucky");
        Assertions.assertEquals("item to change is not found in stock", changedName);
    }

}