package com.example.decafe.datatypes;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileNotFoundException;

import static com.example.decafe.utility.ImageService.createImage;

//Class that is used mainly to control certain assets of the Game like Machines, Upgrades and the Coin Score
public class Game {
    private final Machine coffeeMachine; // A Machine Object used to make Coffee
    private final Machine cakeMachine; // A Machine Object used to make Cake
    private final Upgrade coffeeUpgrade; // An Upgrade Object used to upgrade the Coffee Machine
    private final Upgrade cakeUpgrade; // An Upgrade Object used to upgrade the Cake Machine
    private final Upgrade playerUpgrade; // An Upgrade Object used to make the Player faster
    private int coinsEarned; // The amount of Coins earned/used in the Game - 0 at the beginning
    private final String filenameImageThreeCoins; // Image of small amount of money earned
    private final String filenameImageFourCoins; // Image of normal amount of money earned
    private final String filenameImageDollar; // Images of huge amount of money earned

    // Constructor
    public Game(ImageView upgradeCoffee, ImageView upgradeCake, ImageView upgradePlayer){
        this.coffeeMachine = new Machine(5, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
        this.cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");
        this.coffeeUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png",  upgradeCoffee);
        this.cakeUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", upgradeCake);
        this.playerUpgrade = new Upgrade(40, false, "upgradeSkates.png", "upgradeSkatesUsed.png",  upgradePlayer);
        this.coinsEarned = 0;
        this.filenameImageDollar = "5coins.png";
        this.filenameImageFourCoins = "4coins.png";
        this.filenameImageThreeCoins = "3coins.png";
    }

    // Getter
    public Machine getCakeMachine() {
        return cakeMachine;
    }

    public Machine getCoffeeMachine() {
        return coffeeMachine;
    }

    public Upgrade getCakeUpgrade() {
        return cakeUpgrade;
    }

    public Upgrade getCoffeeUpgrade() {
        return coffeeUpgrade;
    }

    public Upgrade getPlayerUpgrade() {
        return playerUpgrade;
    }

    public String getFilenameImageThreeCoins() {
        return filenameImageThreeCoins;
    }

    public String getFilenameImageFourCoins() {
        return filenameImageFourCoins;
    }

    public String getFilenameImageDollar() {
        return filenameImageDollar;
    }

    public int getCoinsEarned() { return coinsEarned; }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public void addToCoinsEarned (int coins) {
        this.coinsEarned += coins;
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
//    public void doUpgrade(String type, Player player) throws FileNotFoundException {
//        switch (type) {
//            case "coffee" -> {
//                coinsEarned = coffeeUpgrade.doUpgrades(coinsEarned);
//                coffeeMachine.setDuration(2);
//            }
//            case "cake" -> {
//                coinsEarned = cakeUpgrade.doUpgrades(coinsEarned);
//                cakeMachine.setDuration(2);
//            }
//            case "player" -> {
//                coinsEarned = playerUpgrade.doUpgrades(coinsEarned);
//                player.setMovement(6);
//            }
//        }
//    }

    public void addCoinsAfterCustomer(Customer customer){
        if (customer.isGreen()){
            this.coinsEarned += 7;
        } else if (customer.isYellow()){
            this.coinsEarned += 5;
        }else if (customer.isRed()){
            this.coinsEarned += 3;
        }
    }
}
