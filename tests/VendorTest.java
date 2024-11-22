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

    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }

    @Test //JUnit test case to validate that you can add money to a vendor
    public void addMoneyTest(){
        System.out.println();
        System.out.println("addMoneyTest Executed!");

        ven.addMoney(10);
        Assertions.assertEquals(10, ven.getBalance());

    }

    @Test //JUnit test to validate that you can buy an item from a vendor.
    public void buyItemTest(){
        System.out.println();
        System.out.println("buyItemTest Executed!");

        ven.addMoney(5.00);
        ven.select("Ven1", "Candy", "");
        Assertions.assertEquals(4, ven.getStockOneVendor("Ven1","Candy")); //check that stock decreases
        Assertions.assertEquals(3.75, ven.getBalance()); //check that balance was reduced correctly
    }

    @Test //Unit test to validate that you can empty the vendor’s inventory.
    public void emptyVendorInventoryTest(){

        System.out.println();
        System.out.println("emptyVendorInventoryTest Executed!");

        ven.addMoney(10.00);

        //buy candy 5 times
        for (int i = 0; i < 5; i++){
            ven.select("Ven1", "Candy", "");
        }
        //System.out.println("Check that stock is empty:");
        Assertions.assertEquals(0, ven.getStockOneVendor("Ven1","Candy")); //check that stock is empty
        ven.select("Ven1", "Candy", "");
        Assertions.assertEquals(0, ven.getStockOneVendor("Ven1","Candy")); //check that stock doesn't decrease bellow 0
    }


    @Test //As a User, I would like to restock items on a vendor so players can buy from him later
    public void restockTest(){
        System.out.println();
        System.out.println("restockTest Executed!");

        ven.restockItems("Ven1","Candy",  10);
        Assertions.assertEquals(15, ven.getStockOneVendor("Ven1","Candy"));

        ven.restockItems("Ven5","Marshmellow",  10);
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven5","Marshmellow"));

    }

    /*
    As a User, I would like for the vendor system to manage and print the inventory of 5
    different vendors so that I can have multiple vendors available
     */
    @Test void testVendorInventory(){
        System.out.println();
        System.out.println("testVendorInventory Executed!");

        //check stock of ven1
        Assertions.assertEquals(5, ven.getStockOneVendor("Ven1", "Candy"));
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven1", "Gum"));

        //check stock of Ven2
        Assertions.assertEquals(8, ven.getStockOneVendor("Ven2", "Candy"));
        Assertions.assertEquals(15, ven.getStockOneVendor("Ven2", "Gum"));

        //check stock of Ven3
        Assertions.assertEquals(20, ven.getStockOneVendor("Ven3", "Candy"));
        Assertions.assertEquals(5, ven.getStockOneVendor("Ven3", "Gum"));

        //check stock of Ven4
        Assertions.assertEquals(18, ven.getStockOneVendor("Ven4", "Candy"));
        Assertions.assertEquals(1, ven.getStockOneVendor("Ven4", "Gum"));

        //check stock of Ven5
        Assertions.assertEquals(30, ven.getStockOneVendor("Ven5", "Candy"));
        Assertions.assertEquals(4, ven.getStockOneVendor("Ven5", "Gum"));

        //print all inventories
        ven.printAllInventories();
    }

    /*
    As a User, I would like items added to the vendor’s inventory when restocking if they were
    unavailable so that the Vendor’s inventory can change over time
     */
    @Test
    public void addItemsToVendorInventoryTest(){
        System.out.println();
        System.out.println("addItemsToVendorInventoryTest Executed!");

        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven1", "Soda")); //soda isn't in inventory
        ven.restockItems("Ven1", "Soda",  10); //restock new item
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven1","Soda"));

        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven2", "Milk")); //soda isn't in inventory
        ven.restockItems("Ven2", "Milk",  10); //restock new item
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven2","Milk"));

        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven3", "Cheese")); //soda isn't in inventory
        ven.restockItems("Ven3", "Cheese",  10); //restock new item
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven3","Cheese"));

        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven4", "Ice Cream")); //soda isn't in inventory
        ven.restockItems("Ven4", "Ice Cream",  10); //restock new item
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven4","Ice Cream"));

        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven5", "Jello")); //soda isn't in inventory
        ven.restockItems("Ven5", "Jello",  10); //restock new item
        Assertions.assertEquals(10, ven.getStockOneVendor("Ven5","Jello"));

        System.out.println();
        //check if you can buy the added items
        ven.addMoney(20.00);

        ven.select("Ven1", "Soda", "");
        Assertions.assertEquals(9, ven.getStockOneVendor("Ven1", "Soda")); //check stock decreases after buying it

        ven.select("Ven2", "Milk", "");
        Assertions.assertEquals(9, ven.getStockOneVendor("Ven2", "Milk")); //check stock decreases after buying it

        ven.select("Ven3", "Cheese", "");
        Assertions.assertEquals(9, ven.getStockOneVendor("Ven3", "Cheese")); //check stock decreases after buying it

        ven.select("Ven4", "Ice Cream", "");
        Assertions.assertEquals(9, ven.getStockOneVendor("Ven4", "Ice Cream")); //check stock decreases after buying it

        ven.select("Ven5", "Jello", "");
        Assertions.assertEquals(9, ven.getStockOneVendor("Ven5", "Jello")); //check stock decreases after buying it

    }

    /*
    As a User, I would like to change the Name of an item at a vendor, so it is easy to manage
    the vendor-available items
     */
    @Test
    public void changeItemNameTest(){
        System.out.println();
        System.out.println("changeItemNameTest Executed!");

        String changedName = ven.changeItemName("Ven3","Candy", "Pudding");
        Assertions.assertEquals("Item name changed", changedName);
        Assertions.assertEquals(-1, ven.getStockOneVendor("Ven3","Candy")); //chek that old name isn't there
        Assertions.assertEquals(20, ven.getStockOneVendor("Ven3","Pudding")); //check new item is there


        //try to change name to empty string
        changedName = ven.changeItemName("Ven3","Pudding", "");
        Assertions.assertEquals("New name can't be empty or null", changedName);

        //try to change name to an already existing item
        changedName = ven.changeItemName("Ven3","Pudding", "Gum");
        Assertions.assertEquals("An item with the new name already exists in the vendor's stock", changedName);

        //try to change name of non-existing item
        changedName = ven.changeItemName("Ven3","Yummy", "Yucky");
        Assertions.assertEquals("Item to change is not found in the vendor's stock", changedName);
    }

    /*
    As a User, I would like to remove an item from the vendor’s
    inventory if it is discontinued or no longer available.
     */
    @Test
    public void removeUnavailableItemTest(){
        System.out.println();
        System.out.println("removeUnavailableItemTest Executed!");

        //adding money and buying all candy from ven1
        ven.addMoney(10.00);
        for(int i = 0; i < 5; i++){
            ven.select("Ven1", "Candy", "");
        }

        //testing the out-of stock items
         String result = ven.removeUnavailableItem("Ven1", "Candy");
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

    /*
    As a User, I would like the vendor system to track customer purchases for each item,
    providing insights on popular items and trends.
     */
    @Test
    public void getCustomerPurchasesTest() {
        System.out.println();
        System.out.println("getCustomerPurchasesTest Executed!");

        // Set up test data by simulating some purchases
        ven.addMoney(20.00); // Add money to the balance
        ven.select("Ven1", "Candy", ""); // Purchase Candy
        ven.select("Ven1", "Candy", ""); // Purchase Candy again
        ven.select("Ven2", "Gum", "");   // Purchase Gum
        ven.select("Ven1", "Candy", ""); // Purchase Candy again

        ven.getStockOneVendor("Ven1", "Candy");
        ven.getStockOneVendor("Ven2", "Gum");

        System.out.println();

        String expectedOutput = "   - Customer Purchases:\n" +
                "      - Candy: 3 times\n" +
                "      - Gum: 1 times\n" +
                "\n   - Most Popular Item: Candy with 3 purchases.\n" +
                "\n   - Trending Items:\n" +
                "      - Candy (3 purchases)\n" +
                "      - Gum (1 purchases)\n";

        String result = ven.getCustomerPurchases();
        System.out.println(result);
        Assertions.assertEquals(expectedOutput, result);
    }


    /*
    As a User, I would like to check an item’s description or details before purchasing, so I
    can make informed choices on item benefits and uses
     */
    @Test
    void testAddAndGetItemDescription() {
        System.out.println();
        System.out.println("testAddAndGetItemDescription Executed!");
        ven.addItemDescription("Candy", "A sweet treat made of sugar and chocolate.");
        ven.addItemDescription("Gum", "Chewy and flavorful gum.");

        Assertions.assertEquals("A sweet treat made of sugar and chocolate.", ven.getDescription("Candy"));
        Assertions.assertEquals("Chewy and flavorful gum.", ven.getDescription("Gum"));

        //try to get a description for an item that doesn't exist
        Assertions.assertEquals("Description not found.", ven.getDescription("Chips"));
    }

    /*
    As a User, I would like to apply discounts to specific items or categories within the vendor’s
    inventory, allowing for seasonal sales or promotions
     */
    @Test
    void testApplyDiscounts(){
        System.out.println();
        System.out.println("testApplyDiscounts Executed!");

        ven.addMoney(2.00);
        ven.select("Ven1", "Candy", "christmasSpecial");
        assertEquals(1.06, ven.getBalance());


    }

    /*
    As a User, I would like for certain items to be marked as ”bestsellers” in the vendor’s
    inventory, enabling quicker access to commonly purchased items
     */
    @Test
    void testBestSeller(){
        System.out.println();
        System.out.println("testBestSeller Executed!");

        ven.addMoney(10.00);
        System.out.println(ven.getStockOneVendor("Ven1", "Candy" ));
        for (int i = 0; i < 5; i++) {
            ven.select("Ven1", "Candy", "");
        }

        //check that stock is empty
        Assertions.assertEquals(0, ven.getStockOneVendor("Ven1", "Candy" ));

        String actual_output = ven.getCustomerPurchases();


        String expectedOutput = "   - Customer Purchases:\n" +
                "      - Candy: 5 times (Bestseller)\n" +
                "      - Gum: 0 times\n" + "\n" +
                "   - Most Popular Item: Candy with 5 purchases.\n" + "\n" +
                "   - Trending Items:\n" +
                "      - Candy (5 purchases)\n" +
                "      - Gum (0 purchases)\n";

        Assertions.assertEquals(expectedOutput, actual_output);
    }


}