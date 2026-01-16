package com.example.decafe.datatypes;

// Class to handle Methods used to change the Image and movement speed of waiter
public class Player {
    private final String imageWaiterWithoutProductInHands;
    private final String imageWaiterWithCoffeeInHands;
    private final String imageWaiterWithCakeInHands;
    private String productInWaiterHands;
    private int movementSpeedOfWaiter;

    // Constructor
    public Player(String filenameImageWithoutProduct, String filenameImageWithCake, String filenameImageWithCoffee, int movement) {
        this.imageWaiterWithoutProductInHands = filenameImageWithoutProduct;
        this.imageWaiterWithCakeInHands =  filenameImageWithCake;
        this.imageWaiterWithCoffeeInHands = filenameImageWithCoffee;
        this.productInWaiterHands = "none";
        this.movementSpeedOfWaiter = movement;
    }

    //Getter
    public String getProductInHand() {
        return productInWaiterHands;
    }

    public String getFilenameImageWithoutProduct() {
        return imageWaiterWithoutProductInHands;
    }

    public String getFilenameImageWithCake() {
        return imageWaiterWithCakeInHands;
    }

    public String getFilenameImageWithCoffee() {
        return imageWaiterWithCoffeeInHands;
    }

    public int getMovement() {
        return movementSpeedOfWaiter;
    }

    //Setter
    public void setProductInHand(String productInHand) {
        this.productInWaiterHands = productInHand;
    }

    public void setMovement(int movement) {
        this.movementSpeedOfWaiter = movement;
    }
}

