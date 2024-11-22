import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vendor {
    private static HashMap<String, Item> Stock = new HashMap<String,Item>();
    private static HashMap<String, String> itemDescriptions = new HashMap<String,String>();
    private double balance;
    private static HashMap<String, HashMap<String, Item>> vendors = new HashMap<>();

    private static HashMap<String, Double> discounts = new HashMap<>();


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
        System.out.println("   - Item: " + itemName + " not found in " +vendorName );
        return -1; //if vendor or item is not found
    }

    /**
     * Prints the inventory of all vendors and records the items each vendor has in stock.
     * Also determines bestseller
     */
    public void printAllInventories() {
        int bestsellerThreshold = 5;


        for (String vendorName : vendors.keySet()) {
            System.out.println("   - Vendor: " + vendorName);
            HashMap<String, Item> stock = vendors.get(vendorName);


            for (String itemName : stock.keySet()) {
                int purchaseCount = allItems.getOrDefault(itemName, 0); // Get purchase count from allItems
                String itemInfo = "      " + itemName + " stock: " + stock.get(itemName).stock;

                //check if bestseller
                if (purchaseCount >= bestsellerThreshold) {
                    itemInfo += " (Bestseller)";
                }

                System.out.println(itemInfo);
            }
        }
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
                System.out.println("   - Restocked " + itemName );
            } else {
                //add item if it doesn't exist
                stock.put(itemName, new Item(1.0, amount)); //assuming a default price of 1.0 for new items
                System.out.println("   - Added new item " + itemName + "!");
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
     * the balance isn't sufficient to cover the item cost. Discounts are applied
     * if the discount name is provided and valid.
     *
     * @param itemName    The name of the item to purchase ("Candy" or "Gum")
     * @param vendorName  The vendor from which to purchase the item
     * @param discountName The name of the discount to apply (e.g., "christmasSpecial"), or "" for no discount
     */
    void select(String vendorName, String itemName, String discountName) {
        getDiscounts();
        if (vendors.containsKey(vendorName)) {
            HashMap<String, Item> stock = vendors.get(vendorName);
            if (stock.containsKey(itemName)) {
                Item item = stock.get(itemName);

                double finalPrice = item.price;

                //apply discount if valid
                if (!discountName.isEmpty() && discounts.containsKey(discountName)) {
                    double discountPercentage = discounts.get(discountName);
                    finalPrice -= (finalPrice * discountPercentage / 100);
                    finalPrice = Math.round(finalPrice * 100.0) / 100.0;
                    System.out.println("   - " + discountName + " discount applied: -" + discountPercentage + "%");
                } else if (discountName.isEmpty()) {
                    System.out.println("   - No discount applied.");
                } else {
                    System.out.println("   - Invalid discount name: No discount applied.");
                }

                finalPrice = Math.max(finalPrice, 0); //check price doesn't go bellow 0

                //check item is available and balance is sufficient
                if (item.stock <= 0) {
                    System.out.println("   - Item no longer available");
                } else if (balance >= finalPrice) {
                    item.purchase(1);
                    this.balance -= finalPrice;
                    System.out.println("   - Purchased " + itemName + " from " + vendorName);

                    allItems.put(itemName, allItems.getOrDefault(itemName, 0) + 1);
                } else {
                    System.out.println("Gimme more money");
                }
            } else {
                System.out.println("Sorry, vendor doesn't have that item.");
            }
        } else {
            System.out.println("Sorry, vendor " + vendorName + " not found.");
        }
    }


    /**
     * Change the name of an item in the stock and update the allItems hashmap.
     * @param curName current name of the item
     * @param newName new name to change
     * @return a message indicating the result of the operation
     */
    public String changeItemName(String vendorName, String curName, String newName) {
        //check if the new name is valid
        if (newName == null || newName.trim().isEmpty()) {
            System.out.println("   - New name can't be empty or null");
            return "New name can't be empty or null";
        }
        //check if the vendor exists
        if (!vendors.containsKey(vendorName)) {
            System.out.println("   - Vendor not found");
            return "Vendor not found";
        }
        HashMap<String, Item> stock = vendors.get(vendorName);
        //check if the item exists in vendor's stock
        if (!stock.containsKey(curName)) {
            System.out.println("   - Item to change is not found in the vendor's stock");
            return "Item to change is not found in the vendor's stock";
        }
        //check if new name already exists in  vendor's stock
        if (stock.containsKey(newName)) {
            System.out.println("   - An item with the new name already exists in the vendor's stock");
            return "An item with the new name already exists in the vendor's stock";
        }

        //change item name
        Item item = stock.remove(curName);
        stock.put(newName, item);

        //update the allItems map
        if (allItems.containsKey(curName)) {
            Integer value = allItems.remove(curName);
            allItems.put(newName, value);
        }
        System.out.println("   - Item name changed");
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
            System.out.println("   - Vendor name or item name cannot be null or empty");
            return "Vendor name or item name cannot be null or empty";
        }

        if(!vendors.containsKey(vendorName)){
            System.out.println("   - Vendor not found");
            return "Vendor not found";
        }

        HashMap<String, Item> stock = vendors.get(vendorName);

        if(!stock.containsKey(itemName)){
            System.out.println("   - Item not found in vendor's inventory");
            return "Item not found in vendor's inventory";
        }

        Item item = stock.get(itemName);
        if(item.stock == 0){
            stock.remove(itemName);
            allItems.remove(itemName); //remove from item list
            itemDescriptions.remove(itemName); //removes description for the item
            System.out.println("   - Item removed from vendor's inventory");
            return "Item removed from vendor's inventory";
        } else{
            System.out.println("   - Item is still in stock");
            return "Item is still in stock";
        }
    }

    /**
     * Gets the keys and values of the allItems hashmap, representing the purchases made by the customer.
     * Collects all items from each vendor's inventory, ensuring each unique item is only added once.
     * Displays the most popular item (the one with the highest purchase count).
     *
     * @return A string representing the customer purchases and their counts, along with the most popular item
     */
    public String getCustomerPurchases() {
        int bestsellerThreshold = 5;

        //get items from vendors
        for (String vendorName : vendors.keySet()) {
            HashMap<String, Item> stock = vendors.get(vendorName);

            //add items to allItems
            for (String itemName : stock.keySet()) {
                allItems.putIfAbsent(itemName, 0); // Add item with value 0 if not present
            }
        }

        StringBuilder purchases = new StringBuilder("   - Customer Purchases:\n");
        List<Map.Entry<String, Integer>> sortedItems = new ArrayList<>(allItems.entrySet());

        sortedItems.sort(Comparator.comparing(Map.Entry<String, Integer>::getValue).reversed());

        String mostPopularItem = "";
        int maxPurchaseCount = 0;

        for (Map.Entry<String, Integer> entry : sortedItems) {
            String itemName = entry.getKey();
            Integer purchaseCount = entry.getValue();

            //bestseller if the purchase count exceeds the threshold
            if (purchaseCount >= bestsellerThreshold) {
                purchases.append("      - " + itemName).append(": ").append(purchaseCount).append(" times (Bestseller)\n");
            } else {
                purchases.append("      - " + itemName).append(": ").append(purchaseCount).append(" times\n");
            }

            //track most popular item
            if (purchaseCount > maxPurchaseCount) {
                mostPopularItem = itemName;
                maxPurchaseCount = purchaseCount;
            }
        }


        if (!mostPopularItem.isEmpty()) {
            purchases.append("\n   - Most Popular Item: ").append(mostPopularItem).append(" with ").append(maxPurchaseCount).append(" purchases.\n");
        }

        //top 3 trending items
        int topN = Math.min(3, sortedItems.size());
        if (topN > 0) {
            purchases.append("\n   - Trending Items:\n");
            for (int i = 0; i < topN; i++) {
                Map.Entry<String, Integer> trendingItem = sortedItems.get(i);
                purchases.append("      - ").append(trendingItem.getKey()).append(" (").append(trendingItem.getValue()).append(" purchases)\n");
            }
        } else {
            purchases.append("   - No items available to display.\n");
        }

        return purchases.toString();
    }

    /**
     * Adds an item description to the itemDescriptions HashMap.
     * If the item already exists, the description is updated.
     * @param itemName the name of the item
     * @param description the description of the item
     */
    public void addItemDescription(String itemName, String description) {
        itemDescriptions.put(itemName, description);
        System.out.println("   - Description for item '" + itemName + "' has been added/updated.");
    }

    /**
     * Retrieves the description of an item by its name.
     * If the item does not exist in the itemDescriptions map, it returns a message indicating the item is not found.
     * @param itemName the name of the item
     * @return the description of the item, or a message if the item is not found
     */
    public String getDescription(String itemName) {
        if (itemDescriptions.containsKey(itemName)) {
            System.out.println("   - Description for " + itemName + ":\n"+
                    "      "+itemDescriptions.get(itemName));
            return itemDescriptions.get(itemName);
        } else {
            System.out.println("   - Description not found.");
            return "Description not found.";
        }
    }

    public void getDiscounts() {

        discounts.put("christmasSpecial", 25.0);
        discounts.put("springSale", 15.0);
        discounts.put("summerClearance", 20.0);
        discounts.put("happyBirthdayDeal", 10.0);
        discounts.put("loyaltyReward", 5.0);
        discounts.put("newCustomerBonus", 30.0);

        System.out.println("Available Discounts:");
        for (Map.Entry<String, Double> discount : discounts.entrySet()) {
            System.out.println("   - " + discount.getKey() + ": " + discount.getValue() + "% off");
        }
    }

}

class Examples {
}

