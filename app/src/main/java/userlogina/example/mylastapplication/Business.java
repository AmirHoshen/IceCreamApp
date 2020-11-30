package userlogina.example.mylastapplication;

public class Business {

    String businessOwnerName, businessName, email, phone , password;

    public Business(){}

    public Business(String businessOwnerName, String businessName, String email, String phone, String password) {
        this.businessOwnerName = businessOwnerName;
        this.businessName = businessName;
        this.email = email;
        this.phone = phone;
        this.password = password;
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
}
