package userlogina.example.mylastapplication;

import java.util.ArrayList;

import userlogina.example.mylastapplication.Orders.Order;
import userlogina.example.mylastapplication.Orders.ShoppingCart;

public class User {
    public String fullName, email, phone , password;
    public ShoppingCart shoppingCart;
    //private ArrayList<Order> Orders;

    public User(){}

    public User(String fullName, String email, String phone, String password){
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.shoppingCart = null;
        //this.Orders = new ArrayList<>();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

//    public ArrayList<Order> getOrders() {
//        return Orders;
//    }
//
//    public void setOrders(ArrayList<Order> orders) {
//        Orders = orders;
//    }
}
