package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Item {
    private String symbol;
    private String securityName;
    private ObservableList<Bid> bidList;

    public Item(String symbol, String securityName, double finalPrice) {
        this.symbol = symbol;
        this.securityName = securityName;
        this.bidList = FXCollections.observableArrayList();
        bidList.add(new Bid("Initial Price","Start", finalPrice));
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

    public double getCurrentPrice() {
        return bidList.get(bidList.size()-1).getPrice();
    }

    public synchronized boolean setCurrentPrice(String clientName, String bidTime, double newPrice) {
        if(newPrice > this.getCurrentPrice()) {
            this.bidList.add(new Bid(clientName, bidTime, newPrice));
            return true;
        }
        return false;
    }

    public ObservableList<Bid> getBidList() {
        return bidList;
    }
}
