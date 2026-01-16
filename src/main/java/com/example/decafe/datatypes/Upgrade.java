package com.example.decafe.datatypes;


import javafx.scene.image.ImageView;
import java.io.FileNotFoundException;
import static com.example.decafe.utility.ImageService.createImage;

public class Upgrade {
    private final int coinsNeededForUpgrade;
    private boolean upgradeAlreadyHappened;
    private final String filenameUpgradeNotUsed;
    private final String filenameUpgradeUsed;
    private final ImageView upgradeImageView;

    Upgrade(int coinsNeeded, boolean alreadyUsedOnce, String filenameUpgradeNotUsed, String filenameUpgradeUsed, ImageView upgradeImageView){
        this.coinsNeededForUpgrade = coinsNeeded;
        this.upgradeAlreadyHappened = alreadyUsedOnce;
        this.filenameUpgradeNotUsed = filenameUpgradeNotUsed;
        this.filenameUpgradeUsed = filenameUpgradeUsed;
        this.upgradeImageView = upgradeImageView;
    }

    public boolean isAlreadyUsedOnce() { return upgradeAlreadyHappened; }
    public int getCoinsNeeded() { return coinsNeededForUpgrade; }
    public String getFilenameUpgradeUsed() { return filenameUpgradeUsed; }
    public String getFilenameUpgradeNotUsed() { return filenameUpgradeNotUsed; }
    public ImageView getUpgradeImageView() { return upgradeImageView; }
    public void setAlreadyUsedOnce(boolean alreadyUsedOnce) {this.upgradeAlreadyHappened = alreadyUsedOnce;}

    public int payForUpgrade(int coinBudget) throws FileNotFoundException {
        this.upgradeImageView.setImage(createImage(this.filenameUpgradeUsed));
        this.upgradeImageView.setDisable(true);
        this.setAlreadyUsedOnce(true);
        coinBudget -= this.getCoinsNeeded();
        return coinBudget;
    }

    public void checkIfUpgradeIsPossible(Upgrade upgrade, Game game) throws FileNotFoundException {
        if (!upgrade.isAlreadyUsedOnce() && game.getCoinsEarned() >= upgrade.getCoinsNeeded()){
            upgrade.getUpgradeImageView().setDisable(false);
            upgrade.getUpgradeImageView().setImage(createImage(upgrade.getFilenameUpgradeNotUsed()));
        } else {
            upgrade.getUpgradeImageView().setDisable(true);
            upgrade.getUpgradeImageView().setImage(createImage(upgrade.getFilenameUpgradeUsed()));
        }
    }

}
