package userlogina.example.mylastapplication;


import java.util.ArrayList;

import userlogina.example.mylastapplication.Orders.Order;

public class Business {

    String businessOwnerName, businessName, email, phone , password;
    ArrayList<Order> Orders;

    public Business(){}

    public Business(String businessOwnerName, String businessName, String email, String phone, String password,ArrayList<Order> orders) {
        this.businessOwnerName = businessOwnerName;
        this.businessName = businessName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.Orders = orders;
    }

    public String getBusinessOwnerName() {
        return businessOwnerName;
    }

    public void setBusinessOwnerName(String businessOwnerName) {
        this.businessOwnerName = businessOwnerName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
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

    public ArrayList<Order> getOrders() {
        return Orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        Orders = orders;
    }
}
