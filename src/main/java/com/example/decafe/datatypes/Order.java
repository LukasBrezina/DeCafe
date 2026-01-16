package com.example.decafe.datatypes;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Order {
    private String product; //The order of the customer
    private ImageView orderLabelImage; //label that displays order
    private int chair; //number of chair the customer is sitting

    public Order(ImageView orderLabel, int chairNumberOfCustomer) {
        this.product = getRandomProduct();
        this.orderLabelImage = orderLabel;
        this.chair = chair;
    }
    public String getProduct() {
        return product;
    }
    public ImageView getOrderLabelImage() {
        return orderLabelImage;
    }
    public int getChair() {
        return chair;
    }

    public String getRandomProduct() { //returns random order

        Random random = new Random();
        int number = random.nextInt(2);
        String[] possibleOrders = {"cake", "coffee"};
        return possibleOrders[number];
    }
}
