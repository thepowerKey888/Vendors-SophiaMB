import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VendorTest {

    static Vendor ven;

    @BeforeEach
    public void setUp(){

        ven = new Vendor(5, 5);

        ven.addVendor("Ven1", 5, 10);
        ven.addVendor("Ven2", 8, 15);
        ven.addVendor("Ven3", 20, 5);
        ven.addVendor("Ven4", 18, 1);
        ven.addVendor("Ven5", 30, 4);

    }
    /*
    As a User, I would like for the vendor system to manage and print the inventory of 5
    different vendors so that I can have multiple vendors available
     */
    @Test void testVendorInventory(){

        //check stock of ven1
        Assertions.assertEquals(5, ven.getStockOneVendor("Ven1", "Candy"));
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven1", "Gum"));

        //restock Ven1 candy
        ven.restockItems("Ven1", "Candy", 10);
        Assertions.assertEquals(15, ven.getStockOneVendor("Ven1", "Candy"));

        //check stock of Ven2
        Assertions.assertEquals(8, ven.getStockOneVendor("Ven2", "Candy"));
        Assertions.assertEquals(15, ven.getStockOneVendor("Ven2", "Gum"));

        //print all inventories
        ven.printAllInventories();
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
        ven.select("Ven1", "Candy");
        System.out.println(ven.getStock("Candy"));
        System.out.println(ven.getBalance());
        Assertions.assertEquals(4, ven.getStockOneVendor("Ven1","Candy")); //check that stock decreases
        Assertions.assertEquals(3.75, ven.getBalance()); //check that balance was reduced correctly
    }

    @Test //Unit test to validate that you can empty the vendor’s inventory.
    public void emptyVendorInventoryTest(){
        ven.addMoney(10.00);

        //buy candy 5 times
        for (int i = 0; i < 5; i++){
            ven.select("Ven1", "Candy");
        }

        Assertions.assertEquals(0, ven.getStockOneVendor("Ven1","Candy")); //check that stock is empty

        ven.select("Ven1", "Candy");
        Assertions.assertEquals(0, ven.getStockOneVendor("Ven1","Candy")); //check that stock doesn't decrease bellow 0
    }

    @Test //As a User, I would like to restock items on a vendor so players can buy from him later
    public void restockTest(){

        ven.restockItems("Ven1","Candy",  10);
        Assertions.assertEquals(15, ven.getStockOneVendor("Ven1","Candy"));
    }

    /*
    As a User, I would like items added to the vendor’s inventory when restocking if they were
    unavailable so that the Vendor’s inventory can change over time
     */
    @Test
    public void addItemsToVendorInventoryTest(){
        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven1", "Soda")); //soda isn't in inventory
        ven.restockItems("Ven1", "Soda",  10); //restocknew item
        System.out.println(ven.getStockOneVendor("Ven1","Soda"));
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven1","Soda"));

        //check if you can buy soda
        ven.addMoney(2.00);
        ven.select("Ven1", "Soda");
        Assertions.assertEquals(9, ven.getStockOneVendor("Ven1", "Soda")); //check stock decreases after buying it

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

        //try to change name of non-existing item
        changedName = ven.changeItemName("Yummy", "Yucky");
        Assertions.assertEquals("item to change is not found in stock", changedName);
    }

    /*
    As a User, I would like to remove an item from the vendor’s
    inventory if it is discontinued or no longer available.
     */
    @Test
    public void removeUnavailableItemTest(){
        //adding money and buying all candy from ven1
        ven.addMoney(10.00);
        for(int i = 0; i < 5; i++){
            ven.select("Ven1", "Candy");
        }

        //testing the out-of stock items
         String result = ven.removeUnavailableItem("Ven1", "Candy");
        System.out.println(ven.getStockOneVendor("Ven1", "Candy"));
         System.out.println(result);
         Assertions.assertEquals("Item removed from vendor's inventory", result);
         Assertions.assertEquals(-1, ven.getStockOneVendor("Ven1", "Candy")); //check item isn't in inventory

         //test with item still in stock
          result = ven.removeUnavailableItem("Ven1", "Gum");
          Assertions.assertEquals("Item is still in stock", result);

         //test removal with null itemName
         result = ven.removeUnavailableItem("Ven1", null);
         Assertions.assertEquals("Vendor name or item name cannot be null or empty", result);

         //test removal for a vendor that doesn't exist
         result = ven.removeUnavailableItem("Ven6", "Candy");
         Assertions.assertEquals("Vendor not found", result);

        ////test removal for item that doesn't existi
        result = ven.removeUnavailableItem("Ven1", "Chilly");
        Assertions.assertEquals("Item not found in vendor's inventory", result);
    }


}