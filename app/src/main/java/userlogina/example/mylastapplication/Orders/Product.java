package userlogina.example.mylastapplication.Orders;

public class Product {
    public enum types{
        Drinks, icecreame;
    }
    private String id="";
    private String name="";
    private double price;
    private int count;
    private types type;

    public Product(){

    }
    public Product(String id, String name, double price, int stocks,types type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = stocks;
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String toString(){
        return this.getName();
    }

    public types getType() { return type; }

    public void setType(types type) { this.type = type; }
}
