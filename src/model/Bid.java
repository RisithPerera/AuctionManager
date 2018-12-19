package model;

import java.util.ArrayList;

public class Bid {
    private String client;
    private String time;
    private double price;

    public Bid(String client, String time, double price) {
        this.client = client;
        this.time = time;
        this.price = price;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
