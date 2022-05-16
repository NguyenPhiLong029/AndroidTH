package vn.edu.huflit.npl_19dh110839.models;

public class Users {
    String email;
    String firstname;
    String lastname;
    String phone;
    String address;
    String userID;
    double latitude, longitude;

    public Users(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Users(String email, String firstname, String lastname, String phone, String address, String userID,double latitude,double longitude) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.address = address;
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
