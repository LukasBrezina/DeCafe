package com.example.decafe.datatypes;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Order {
    private String product;
    private ImageView orderLabel;
    private int chairNumberOfCustomer;

    public Order(ImageView orderLabel, int chairNumberOfCustomer) {
        this.product = getRandomProduct();
        this.orderLabel = orderLabel;
        this.chairNumberOfCustomer = chairNumberOfCustomer;
    }
    public String getProduct() {
        return product;
    }
    public ImageView getOrderLabelImage() {
        return orderLabel;
    }
    public int getChair() {
        return chairNumberOfCustomer;
    }

    public String getRandomProduct() { //returns random order

        Random random = new Random();
        int number = random.nextInt(2);
        String[] possibleOrders = {"cake", "coffee"};
        return possibleOrders[number];
    }
}
