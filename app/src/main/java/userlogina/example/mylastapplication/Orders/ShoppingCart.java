package userlogina.example.mylastapplication.Orders;

import java.util.ArrayList;

public class ShoppingCart {

    ArrayList<Product> order_dish;
    int num_of_products;
    double total_price;

    public ShoppingCart(){}

    public ShoppingCart(ArrayList<Product> order_products, int num_of_products) {
        this.order_dish = order_products;
        this.num_of_products = num_of_products;
    }

    public ArrayList<Product> getOrder_dish() {
        return order_dish;
    }

    public void setOrder_dish(ArrayList<Product> order_dish) {
        this.order_dish = order_dish;
    }

    public int getNum_of_products() {
        return num_of_products;
    }

    public void setNum_of_products(int num_of_products) {
        this.num_of_products = num_of_products;
    }
}
