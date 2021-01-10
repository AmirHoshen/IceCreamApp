package userlogina.example.mylastapplication.Orders;

public class Upload {
    private String falvor;
    private String Description;
    private double Price;
    private String ImageUrl;
    private String Tag;



    public String getDescription() {
        return Description;
    }

    public void setDescription(String mDescription) {
        this.Description = mDescription;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double mPrice) {
        this.Price = mPrice;
    }


    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String description, double price,  String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }

        falvor = name;
        Description = description;
        Price = price;
        ImageUrl = imageUrl;
    }

    public String getFalvor() {
        return falvor;
    }

    public void setFalvor(String name) {
        falvor = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}