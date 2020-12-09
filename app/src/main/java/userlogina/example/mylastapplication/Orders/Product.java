package userlogina.example.mylastapplication.Orders;

public class Product {
    public enum types{
         icecreame;
    }
    private String id="";
    private String name="";
    private double price;
    private int amount;
    private types type;

    public Product(){

    }
    public Product(String id, String name, double price, int stocks,types type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = stocks;
        this.type=type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString(){
        return this.getName();
    }

    public types getType() { return type; }

    public void setType(types type) { this.type = type; }
}
