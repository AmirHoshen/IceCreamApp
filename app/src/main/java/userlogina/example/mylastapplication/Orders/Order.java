package userlogina.example.mylastapplication.Orders;

import java.util.ArrayList;
import java.util.List;

public class Order {

        private String order_ID;
        private String customer_name;
        private String customer_phone;
        private Double total_Price;
        private List<Product> Products = new ArrayList<>();
        private boolean takeAway;
        private String status_order;
        private String Date;

        public Order(){}

        public Order(String order_ID, String customer_name, String customer_phone, Double total_Price, ArrayList<Product> Products, boolean takeAway, String status_order, String Date){
            this.order_ID = order_ID;
            this.customer_name = customer_name;
            this.customer_phone = customer_phone;
            this.total_Price = total_Price;
            this.Products = Products;
            this.takeAway = takeAway;
            this.status_order = status_order;
            this.Date = Date;
        }


        public String getOrderID() { return order_ID; }

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

        public void setCustomerPhone(String customer_phone) { this.customer_phone = customer_phone;
        }

        public Double getPrice() {
            return total_Price;
        }

        public void setPrice(Double price) {
            this.total_Price = total_Price;
        }

        public List<Product> getProducts() {
        return Products;
    }

        public void setProducts(List<Product> Products) {
        this.Products = Products;
    }

        public String toString(){
            String s = Date+ "\n"+ getCustomerName()+"\n"+getCustomerPhone()+"\nTake Away : "+takeAway+"\n"+"\nStatus: "+getStatus()+"\n"+getProducts().toString()+"\n"+getPrice()+"\n";
            return s;
        }

        public boolean isTakeAway() {
            return takeAway;
        }

        public void setTakeAway(boolean takeAway) {
            takeAway = takeAway;
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
    }
