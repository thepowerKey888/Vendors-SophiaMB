import java.util.HashMap;


/**
 * Class for a Vending Machine.  Contains a hashtable mapping item names to item data, as
 * well as the current balance of money that has been deposited into the machine.
 */
class Vendor {
    private static HashMap<String, Item> Stock = new HashMap<String,Item>();
    private double balance;


    Vendor(int numCandy, int numGum) {
        Stock.put("Candy", new Item(1.25, numCandy));
        Stock.put("Gum", new Item(.5, numGum));
        this.balance = 0;
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

    /** attempt to purchase named item.  Message returned if
     * the balance isn't sufficient to cover the item cost.
     *
     * @param name The name of the item to purchase ("Candy" or "Gum")
     */
    void select (String name) {
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            if(item.stock <= 0){
                System.out.println("Item no longer available");
            }

            else if (balance >= item.price) {
                item.purchase(1);
                this.balance = this.balance - item.price;
            }


            else
                System.out.println("Gimme more money");
        }
        else System.out.println("Sorry, don't know that item");
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

    /** resets the Stock to given amount and adds new items
     * @param name
     * @param amount
     * @param price */
    public void restockItems(String name, double price, int amount){
        if (Stock.containsKey(name)) {
            Item item = Stock.get(name);
            item.restock(amount);
        } else {
            Stock.put(name, new Item(price, amount));
            System.out.println("Added new item: " + name);
        }
    }

    /**change name of an item in the stock.
     * @param curName current name of item
     * @param newName new name to change
     */
    public String changeItemName(String curName, String newName){
        if(newName == null || newName.trim().isEmpty()){
            return "New name can't be empty or null";
        }
        if(!Stock.containsKey(curName)){
            return "item to change is not found in stock";
        }
        if(Stock.containsKey(newName)){
            return "An item with the new name already exists";
        }

        //changes the name
        Item item = Stock.remove(curName);
        Stock.put(newName, item);
        return "Item name changed";
    }
}

class Examples {
}

