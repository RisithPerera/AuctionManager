package model;

import java.util.ArrayList;

public class Item {
    private String symbol;
    private String securityName;
    private double price;
    private ArrayList<Bid> bidList;

    public Item(String symbol, String securityName, double price) {
        this.symbol = symbol;
        this.securityName = securityName;
        this.price = price;
        this.bidList = new ArrayList<>();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getSecurityName() {
        return securityName;
    }

    public void setSecurityName(String securityName) {
        this.securityName = securityName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ArrayList<Bid> getBidList() {
        return bidList;
    }
}
