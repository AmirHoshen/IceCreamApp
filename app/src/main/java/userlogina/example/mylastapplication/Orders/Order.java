package userlogina.example.mylastapplication.Orders;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private String order_ID;
    private String customer_name;
    private String customer_phone;
    private Double total_Price;
    private List<Dish> Dishes = new ArrayList<>();
    private String status_order;
    private String address;
    private String Date;
    //private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public Order() {
    }

    public Order(String order_ID, String customer_name, String customer_phone,String address, ArrayList<Dish> dishes,Double total_Price, String status_order, String Date) {
        this.order_ID = order_ID;
        this.customer_name = customer_name;
        this.customer_phone = customer_phone;
        this.address = address;
        this.Dishes = dishes;
        this.total_Price = total_Price;
        this.status_order = status_order;
        this.Date = Date;
    }

    public String getOrderID() {
        return order_ID;
    }

    public void setOrderID(String orderID) {
        this.order_ID = order_ID;
    }

    public String getCustomerName() {
        return customer_name;
    }

    public void setCustomerName(String customerName) {
        this.customer_name = customer_name;
    }

    public String getCustomerPhone() {
        return customer_phone;
    }

    public void setCustomerPhone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public Double getPrice() {
        return total_Price;
    }

    public void setPrice(Double price) {
        this.total_Price = total_Price;
    }

    public List<Dish> getDishes() {
        return Dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.Dishes = dishes;
    }

    public String toString() {
        String s = Date + "\n" + getCustomerName() + "\n" + getCustomerPhone() + "\n" + "\nStatus: " + getStatus() + "\n" + getDishes().toString() + "\n" + getPrice() + "\n";
        return s;
    }

    public String getStatus() {
        return status_order;
    }

    public void setStatus(String status) {
        this.status_order = status_order;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = Date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
