package userlogina.example.mylastapplication.Orders;

import java.util.ArrayList;

public class ShoppingCart {

    ArrayList<Product> order_products;
    int num_of_products;

    public ShoppingCart(){}

    public ShoppingCart(ArrayList<Product> order_products, int num_of_products) {
        this.order_products = order_products;
        this.num_of_products = num_of_products;
    }

    public ArrayList<Product> getOrder_products() {
        return order_products;
    }

    public void setOrder_products(ArrayList<Product> order_products) {
        this.order_products = order_products;
    }

    public int getNum_of_products() {
        return num_of_products;
    }

    public void setNum_of_products(int num_of_products) {
        this.num_of_products = num_of_products;
    }
}
