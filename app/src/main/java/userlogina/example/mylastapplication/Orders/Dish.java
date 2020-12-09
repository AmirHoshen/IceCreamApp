package userlogina.example.mylastapplication.Orders;

public class Dish {
    private String falvor;
    private String description;
    private double price;
    private double amount;


    public Dish(){
    }

    public Dish(String falvor, String description, double price, double amount) {
        this.falvor = falvor;
        this.description = description;
        this.price = price;
        this.amount = amount;
    }

    public String getFalvor() {
        return falvor;
    }

    public void setFalvor(String falvor) {
        this.falvor = falvor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
