package com.example.decafe.datatypes;

import javafx.scene.image.ImageView;

import java.util.Random;

public class Order {
    private String product;
    private ImageView orderLabelImage;
    private int chairNumberOfCustomer;

    public Order(ImageView orderLabelImage, int chairNumberOfCustomer) {
        this.product = getRandomProduct();
        this.orderLabelImage = orderLabelImage;
        this.chairNumberOfCustomer = chairNumberOfCustomer;
    }
    public String getProduct() {
        return product;
    }
    public ImageView getOrderLabelImage() {
        return orderLabelImage;
    }
    public int getChair() { return chairNumberOfCustomer;}

    public String getRandomProduct() {
        Random random = new Random();
        int number = random.nextInt(2);
        String[] possibleOrders = {"cake", "coffee"};
        return possibleOrders[number];
    }
}
