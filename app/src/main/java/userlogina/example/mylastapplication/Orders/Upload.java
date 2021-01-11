package userlogina.example.mylastapplication.Orders;

public class Upload {
    private String falvor;
    private String Description;
    private double Price;
    private String ImageUrl;
    private String Tag;

    public Upload() {
        //empty constructor needed
    }


    public Upload(String falvor, String description, double price, String imageUrl, String tag) {
        if (falvor.trim().equals("")) {
            falvor = "No Name";
        }
        this.falvor = falvor;
        Description = description;
        Price = price;
        ImageUrl = imageUrl;
        Tag = tag;
    }

    public String getFalvor() {
        return falvor;
    }

    public void setFalvor(String falvor) {
        this.falvor = falvor;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }
}