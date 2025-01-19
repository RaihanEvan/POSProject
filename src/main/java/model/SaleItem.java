package model;

public class SaleItem {
    private String itemName;
    private int quantity;
    private double price;

    public SaleItem(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItemName() { return itemName; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getTotalPrice() { return quantity * price; }

//    @Override
//    public String toString() {
//        return itemName + "," + quantity + "," + price;
//    }
}
