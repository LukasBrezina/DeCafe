package com.example.decafe.datatypes;

import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

//Class that is used mainly to control certain assets of the Game like Machines, Upgrades and the Coin Score
public class Game {
    private final Machine coffeeMachine;
    private final Machine cakeMachine;
    private final Upgrade coffeeMachineUpgrade;
    private final Upgrade cakeMachineUpgrade;
    private final Upgrade playerSpeedUpgrade;
    private int coinBudget;
    private final String imageThreeCoinsEarned;
    private final String imageFourCoinsEarned;
    private final String imageDollarEarned;

    // Constructor
    public Game(ImageView upgradeCoffee, ImageView upgradeCake, ImageView upgradePlayer){
        this.coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        this.cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");
        this.coffeeMachineUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png",  upgradeCoffee);
        this.cakeMachineUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", upgradeCake);
        this.playerSpeedUpgrade = new Upgrade(40, false, "upgradeSkates.png", "upgradeSkatesUsed.png",  upgradePlayer);
        this.coinBudget = 0;
        this.imageDollarEarned = "5coins.png";
        this.imageFourCoinsEarned = "4coins.png";
        this.imageThreeCoinsEarned = "3coins.png";
    }

    // Getter
    public Machine getCakeMachine() {
        return cakeMachine;
    }

    public Machine getCoffeeMachine() {
        return coffeeMachine;
    }

    public Upgrade getCakeUpgrade() {
        return cakeMachineUpgrade;
    }

    public Upgrade getCoffeeUpgrade() {
        return coffeeMachineUpgrade;
    }

    public Upgrade getPlayerUpgrade() {
        return playerSpeedUpgrade;
    }

    public String getFilenameImageThreeCoins() {
        return imageThreeCoinsEarned;
    }

    public String getFilenameImageFourCoins() {
        return imageFourCoinsEarned;
    }

    public String getFilenameImageDollar() {
        return imageDollarEarned;
    }

    public int getCoinsEarned() { return coinBudget; }

    public void setCoinsEarned(int coinsEarned) {
        this.coinBudget = coinsEarned;
    }

    public void addToCoinsEarned (int coins) {
        this.coinBudget += coins;
    }

//    public void checkUpgradePossible(Upgrade upgrade) throws FileNotFoundException {
//        if (!upgrade.isAlreadyUsedOnce() && this.coinsEarned >= upgrade.getCoinsNeeded()){
//            upgrade.getUpgradeImageView().setDisable(false);
//            upgrade.getUpgradeImageView().setImage(createImage(upgrade.getFilenameUpgradeNotUsed()));
//        } else {
//            upgrade.getUpgradeImageView().setDisable(true);
//            upgrade.getUpgradeImageView().setImage(createImage(upgrade.getFilenameUpgradeUsed()));
//        }
//    }
//
    public void doUpgrade(String type, Player player) throws FileNotFoundException {
        switch (type) {
            case "coffee" -> {
                coinBudget = coffeeMachineUpgrade.doUpgrades(coinBudget);
                coffeeMachine.setDuration(2);
            }
            case "cake" -> {
                coinBudget = cakeMachineUpgrade.doUpgrades(coinBudget);
                cakeMachine.setDuration(2);
            }
            case "player" -> {
                coinBudget = playerSpeedUpgrade.doUpgrades(coinBudget);
                player.setMovement(6);
            }
        }
    }

    public void addCoinsAfterCustomer(Customer customer){
        if (customer.isGreen()){
            this.coinBudget += 7;
        } else if (customer.isYellow()){
            this.coinBudget += 5;
        }else if (customer.isRed()){
            this.coinBudget += 3;
        }
    }
}
