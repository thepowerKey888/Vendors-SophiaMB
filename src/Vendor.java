import java.util.HashMap;
import java.util.Map;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vendor {
    private static HashMap<String, Item> Stock = new HashMap<String,Item>();
    private double balance;
    private static HashMap<String, HashMap<String, Item>> vendors = new HashMap<>();


    HashMap<String, Integer> allItems = new HashMap<>();

    Vendor(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(.5, numGum));
        this.balance = 0;
    }

    /*
    As a User, I would like for the vendor system to manage
    and print the inventory of 5 different vendors so that
    I can have multiple vendors available
     */
    /**
     * Adds a new vendor with initial stock of candy and gum.
     * @param vendorName name of vendor
     * @param numCandy initial stock of candy
     * @param numGum initial stock of gum
     */
    public void addVendor(String vendorName, int numCandy, int numGum){
        HashMap<String, Item> stock = new HashMap<>();
        stock.put("Candy", new Item(1.25, numCandy));
        stock.put("Gum", new Item(0.50, numGum));
        vendors.put(vendorName, stock);
    }

    /**
     * Check the stock of the items in vendor
     * @param name
     */
    public int getStock(String name){
        if (Stock.containsKey(name)) {
            return Stock.get(name).stock;
        } else {
            return -1; //return -1 to show the item is not found
        }
    }

    /**
     * Gets the stock of an item for a specific vendor.
     * @param vendorName the name of the vendor
     * @param itemName the name of the item
     * @return stock amount (-1 if no stock)
     */
    public int getStockOneVendor(String vendorName, String itemName){
        if(vendors.containsKey(vendorName)){
            HashMap<String, Item> stock = vendors.get(vendorName);
            if(stock.containsKey(itemName)){
                return stock.get(itemName).stock;
            }
        }
        return -1; //if vendor or item is not found
    }

    /**
     * Prints the inventory of all vendors and records the items each vendor has in stock.
     */
    public void printAllInventories(){

        //loop through vendors
        for(String vendorName : vendors.keySet()){
            System.out.println("vendor: " + vendorName);
            HashMap<String, Item> stock = vendors.get(vendorName);

            //add items to hashmap
            for(String itemName : stock.keySet()){
                allItems.putIfAbsent(itemName, 0);
                System.out.println(itemName + " stock: " + stock.get(itemName).stock);
            }
            System.out.println("");
        }

        //print unique items that have been encountered across all vendors
//        System.out.println("All Unique Items:");
//        for (String item : allItems.keySet()) {
//            System.out.println(item + " has been encountered.");
//        }
    }

    /**
     * restocks items for a specific vendor
     * @param vendorName name of vendor
     * @param itemName name of the item
     * @param amount to restock
     */
    public void restockItems(String vendorName, String itemName, int amount){
        if(vendors.containsKey(vendorName)){
            HashMap<String, Item> stock = vendors.get(vendorName);
            if(stock.containsKey(itemName)){
                stock.get(itemName).restock(amount);
            } else {
                // Add the item if it doesn't exist
                stock.put(itemName, new Item(1.0, amount)); // Assuming a default price of 1.0 for new items
            }
        }
    }

    /** resets the Balance to 0 */
    void resetBalance () {
        this.balance = 0;
    }

    /** returns the current balance */
    double getBalance () {
        return this.balance;
    }

    /** adds money to the machine's balance
     * @param amt how much money to add
     * */
    void addMoney (double amt) {
        this.balance = this.balance + amt;
    }

    /**
     * Attempt to purchase the named item from a vendor. A message is returned if
     * the balance isn't sufficient to cover the item cost.
     *
     * @param itemName The name of the item to purchase ("Candy" or "Gum")
     * @param vendorName The vendor from which to purchase the item
     */
    void select(String vendorName, String itemName) {
        if (vendors.containsKey(vendorName)) {
            HashMap<String, Item> stock = vendors.get(vendorName);
            if (stock.containsKey(itemName)) {
                Item item = stock.get(itemName);

                if (item.stock <= 0) {
                    System.out.println("Item no longer available");
                } else if (balance >= item.price) {
                    item.purchase(1);
                    this.balance = this.balance - item.price;
                    System.out.println("Purchased " + itemName + " from " + vendorName);

                    //update allItems hashmap item value
                    if (allItems.containsKey(itemName)) {
                        allItems.put(itemName, allItems.get(itemName) + 1);
                    } else {
                        allItems.put(itemName, 1);  //if the item doesn't exist set its count to 1
                    }
                } else {
                    System.out.println("Gimme more money");
                }
            } else {
                System.out.println("Sorry, vendor doesn't have that item");
            }
        } else {
            System.out.println("Sorry, vendor " + vendorName + " not found");
        }
    }


    /**
     * Change the name of an item in the stock and update the allItems hashmap.
     * @param curName current name of the item
     * @param newName new name to change
     * @return a message indicating the result of the operation
     */
    public String changeItemName(String vendorName, String curName, String newName) {
        // Check if the new name is valid
        if (newName == null || newName.trim().isEmpty()) {
            return "New name can't be empty or null";
        }

        // Check if the vendor exists
        if (!vendors.containsKey(vendorName)) {
            return "Vendor not found";
        }

        // Get the vendor's stock
        HashMap<String, Item> stock = vendors.get(vendorName);

        // Check if the item exists in the vendor's stock
        if (!stock.containsKey(curName)) {
            return "Item to change is not found in the vendor's stock";
        }

        // Check if the new name already exists in the vendor's stock
        if (stock.containsKey(newName)) {
            return "An item with the new name already exists in the vendor's stock";
        }

        // Change the item name in the vendor's stock
        Item item = stock.remove(curName);
        stock.put(newName, item);

        // Update the allItems map (if needed)
        if (allItems.containsKey(curName)) {
            Integer value = allItems.remove(curName);
            allItems.put(newName, value);
        }

        return "Item name changed";
    }



    /**
     * Removes an item from a vendor's inventory if it is not available
     * and handles the case where itemName is null or the item does not exist in the vendor
     * @param vendorName name of vendor
     * @param itemName name of item to remove
     * @return message result
     */
    public String removeUnavailableItem(String vendorName, String itemName){
        if(vendorName == null || itemName == null || vendorName.trim().isEmpty() || itemName.trim().isEmpty()){
            return "Vendor name or item name cannot be null or empty";
        }

        if(!vendors.containsKey(vendorName)){
            return "Vendor not found";
        }

        HashMap<String, Item> stock = vendors.get(vendorName);

        if(!stock.containsKey(itemName)){
            return "Item not found in vendor's inventory";
        }

        Item item = stock.get(itemName);
        if(item.stock == 0){
            stock.remove(itemName);
            return "Item removed from vendor's inventory";
        } else{
            return "Item is still in stock";
        }
    }

    /**
     * Gets the keys and values of the allItems hashmap, representing the purchases made by the customer.
     * Displays the most popular item (the one with the highest purchase count).
     *
     * @return A string representing the customer purchases and their counts, along with the most popular item
     */
    public String getCustomerPurchases() {
        printAllInventories(); //gets all the items
        StringBuilder purchases = new StringBuilder("Customer Purchases:\n");
        String mostPopularItem = "";
        int maxPurchaseCount = 0;

        for (Map.Entry<String, Integer> entry : allItems.entrySet()) {
            String itemName = entry.getKey();
            Integer purchaseCount = entry.getValue();
            purchases.append(itemName + ": " + purchaseCount + " times\n");

            if (purchaseCount > maxPurchaseCount) {
                mostPopularItem = itemName;
                maxPurchaseCount = purchaseCount;
            }
        }
        if (!mostPopularItem.isEmpty()) {
            purchases.append("\nMost Popular Item: " + mostPopularItem + " with " + maxPurchaseCount + " purchases.");
        }

        return purchases.toString();
    }
}

class Examples {
}

