package com.example.decafe.datatypes;

import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;

public class Game {
    private final Machine coffeeMachine;
    private final Machine cakeMachine;
    private final Upgrade coffeeMachineUpgrade;
    private final Upgrade cakeMachineUpgrade;
    private final Upgrade waiterSpeedUpgrade;
    private int coinBudget;
    private final String imageThreeCoinsEarned;
    private final String imageFourCoinsEarned;
    private final String imageDollarEarned;

    // Constructor
    public Game(ImageView upgradeCoffee, ImageView upgradeCake, ImageView upgradeWaiter){
        this.coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        this.cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");
        this.coffeeMachineUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png",  upgradeCoffee);
        this.cakeMachineUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", upgradeCake);
        this.waiterSpeedUpgrade = new Upgrade(40, false, "upgradeSkates.png", "upgradeSkatesUsed.png",  upgradeWaiter);
        this.coinBudget = 0;
        this.imageDollarEarned = "5coins.png";
        this.imageFourCoinsEarned = "4coins.png";
        this.imageThreeCoinsEarned = "3coins.png";
    }

    public Machine getCakeMachine() { return cakeMachine;}
    public Machine getCoffeeMachine() { return coffeeMachine; }
    public Upgrade getCakeUpgrade() { return cakeMachineUpgrade; }
    public Upgrade getCoffeeUpgrade() { return coffeeMachineUpgrade; }
    public Upgrade getPlayerUpgrade() { return waiterSpeedUpgrade; }
    public String getFilenameImageThreeCoins() { return imageThreeCoinsEarned; }
    public String getFilenameImageFourCoins() { return imageFourCoinsEarned; }
    public String getFilenameImageDollar() { return imageDollarEarned; }
    public int getCoinsEarned() { return coinBudget; }

    public void doUpgrade(String type, Waiter waiter) throws FileNotFoundException {
        switch (type) {
            case "coffee" -> {
                coinBudget = coffeeMachineUpgrade.payForUpgrade(coinBudget);
                coffeeMachine.setTimeForProduct(2);
            }
            case "cake" -> {
                coinBudget = cakeMachineUpgrade.payForUpgrade(coinBudget);
                cakeMachine.setTimeForProduct(2);
            }
            case "waiter" -> {
                coinBudget = waiterSpeedUpgrade.payForUpgrade(coinBudget);
                waiter.setMovement(6);
            }
        }
    }

    public void addCoinsAfterCustomer(Customer customer){
        if (customer.customerIsHappy()){
            this.coinBudget += 7;
        } else if (customer.customerIsAnnoyed()){
            this.coinBudget += 5;
        }else if (customer.customerIsAngry()){
            this.coinBudget += 3;
        }
    }
}
