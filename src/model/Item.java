package model;

import java.util.ArrayList;

public class Item {
    private String securityName;
    private double finalPrice;
    private ArrayList<Bid> bidList;

    public Item(String securityName, double finalPrice) {
        this.securityName = securityName;
        this.finalPrice = finalPrice;
        this.bidList = new ArrayList<>();
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public ArrayList<Bid> getBidList() {
        return bidList;
    }
}
