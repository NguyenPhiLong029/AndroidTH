package vn.edu.huflit.npl_19dh110839.models;

import java.util.List;

public class OrderFinished {
    public String orderID;
    public String orderDate;
    public String orderSum;
    int orderStatus;
    String userUID;
    List<FoodBasket> foodBaskets;


    public OrderFinished() {
    }

    public OrderFinished(String orderID, String orderDate, String orderSum, int orderStatus, String userUID, List<FoodBasket> foodBaskets) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.orderSum = orderSum;
        this.orderStatus = orderStatus;
        this.userUID = userUID;
        this.foodBaskets = foodBaskets;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderSum() {
        return orderSum;
    }

    public void setOrderSum(String orderSum) {
        this.orderSum = orderSum;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public List<FoodBasket> getFoodBaskets() {
        return foodBaskets;
    }

    public void setFoodBaskets(List<FoodBasket> foodBaskets) {
        this.foodBaskets = foodBaskets;
    }
}

