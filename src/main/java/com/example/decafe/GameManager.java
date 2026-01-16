package com.example.decafe;

import com.example.decafe.datatypes.Customer;
import com.example.decafe.datatypes.Game;
import com.example.decafe.datatypes.Waiter;
import com.example.decafe.datatypes.Upgrade;
import com.example.decafe.utility.MusicFileBuilder;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.net.URL;
import java.util.*;

import static com.example.decafe.utility.ImageService.createImage;

public class GameManager implements Initializable {

    // FXML Elements
    public ImageView startButton;
    public ImageView startQuitButton;
    public ImageView waiterImageView;
    public Label coinsEarnedLabel;
    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();
    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);
    public ImageView coffeeMachineImageView;
    public ImageView cakeMachineImageView;
    public ImageView trashcanImageView;
    public ProgressBar progressBarCoffee;
    public ProgressBar progressBarCake;
    public ImageView upgradeCoffeeImageView;
    public ImageView upgradeCakeImageView;
    public ImageView upgradePlayerImageView;
    public Label table1;
    public Label table2;
    public Label table3;
    public Label table4;
    public Label plantsAbove;
    public Label countRight;
    public Label countBelow;
    public Label customerTop1;
    public Label customerTop2;
    public Label customerTop3;
    public Label customerTop4;
    public Label customerBot1;
    public Label customerBot2;
    public Label customerBot3;
    public Label plant;
    public Label edgeBot;
    public Label edgeTop;
    public Label edgeLeft;
    public Label edgeRight;
    public ImageView smileyFirst;
    public ImageView smileySecond;
    public ImageView smileyThird;
    public ImageView smileyFourth;
    public ImageView smileyFifth;
    public ImageView smileySixth;
    public ImageView smileySeventh;
    public ImageView coinFirst;
    public ImageView coinSecond;
    public ImageView coinThird;
    public ImageView coinFourth;
    public ImageView coinFifth;
    public ImageView coinSixth;
    public ImageView coinSeventh;
    public ImageView orderlabel1 = new ImageView();
    public ImageView orderlabel2 = new ImageView();
    public ImageView orderlabel3 = new ImageView();
    public ImageView orderlabel4 = new ImageView();
    public ImageView orderlabel5 = new ImageView();
    public ImageView orderlabel6 = new ImageView();
    public ImageView orderlabel7 = new ImageView();
    public ImageView first;
    public ImageView second;
    public ImageView third;
    public ImageView fourth;
    public ImageView fifth;
    public ImageView sixth;
    public ImageView seventh;
    public ImageView gameStartButton;
    public ImageView cofiBrewImage;
    public ImageView playAgainImage;
    public ImageView backToStartImage;
    public Label labelCredits;
    public ImageView endScreenBackground;
    public ImageView quitEndScreenImage;

    Waiter waiter = new Waiter("CofiBrewUp.png", "CofiBrewCakeLeft.png", "CofiBrewCoffeeLeft.png", 4);
    Game game;
    Label[] collisions;
    public Timer controllerTimer = new Timer();
    String musicFile = MusicFileBuilder.buildMusicFile("backgroundmusic.mp3");
    public AudioClip backgroundMusic = new AudioClip(new File(musicFile).toURI().toString());

    public void loadScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load());
        Application.stage.setScene(scene);
        Application.stage.show();
    }

    public void switchToEndScreen() throws IOException {
        backgroundMusic.stop();
        loadScene("endScreen.fxml");
    }

    public void switchToStartScreen() throws IOException {
        loadScene("startScreen.fxml");
    }

    public void switchToGameScreen() throws IOException {
        loadScene("gameScreen.fxml");
        if (Customer.customerImages[0] != null) {
            Customer customer = new Customer();
            customer.startTimerSpawn(1, Customer.getGeneralPurposeTimer());
            customer.startTimerSpawn(5, Customer.getGeneralPurposeTimer());
            customer.startTimerSpawn(10, Customer.getGeneralPurposeTimer());
            Customer.allCustomersList.add(customer);
        }
        backgroundMusic.setCycleCount(AudioClip.INDEFINITE);
        backgroundMusic.play();
    }

    public void switchToInstructions() throws IOException {
        loadScene("Instructions.fxml");
    }

    // FXML Events
    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(true);
            case A -> aPressed.set(true);
            case S -> sPressed.set(true);
            case D -> dPressed.set(true);
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(false);
            case A -> aPressed.set(false);
            case S -> sPressed.set(false);
            case D -> dPressed.set(false);
        }
    }

    private void updateWaiterImage(String movement) {
        try {
            String product = waiter.getProductInHand();
            if (product.equals("none")) {
                product = "";
            }
            String imageName = "CofiBrew" + capitalizeFirstLetter(product) + capitalizeFirstLetter(movement) + ".png";
            waiterImageView.setImage(createImage(imageName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }



    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            int movementVariable = waiter.getMovement();
            double move = movementVariable;
            String movement = "none";
            // if two keys are pressed at once and player moves diagonally - correct diagonal speed
            if (wPressed.get() && aPressed.get() || wPressed.get() && dPressed.get() ||
                    sPressed.get() && aPressed.get() || sPressed.get() && dPressed.get())
                move -= movementVariable - Math.sqrt(Math.pow(movementVariable, 2) / 2);

            double xMove = 0;
            double yMove = 0;

            if (wPressed.get()) {
                yMove = -move; // negative move because otherwise waiter would move down
                movement = "up";
            }

            if (sPressed.get()) {
                yMove = move;
                movement = "down";
            }

            if (aPressed.get()) {
                xMove = -move;
               movement = "left";
            }

            if (dPressed.get()) {
                xMove = move;
                movement = "right";
            }

            waiterImageView.setLayoutX(waiterImageView.getLayoutX() + xMove);
            waiterImageView.setLayoutY(waiterImageView.getLayoutY() + yMove);

            if (checkIfWaiterCollidesWithLabelOfCustomer(waiterImageView)) {
                waiterImageView.setLayoutX(waiterImageView.getLayoutX() - xMove);
                waiterImageView.setLayoutY(waiterImageView.getLayoutY() - yMove);
            } else {
                updateWaiterImage(movement);
            }
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyPressed.addListener((((observableValue, aBoolean, t1) -> {
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        })));
        collisions = new Label[]{plant, plantsAbove, customerBot1, customerBot2, customerBot3, customerTop1, customerTop2, customerTop3, customerTop4, table1, table2, table3, table4, edgeBot, edgeLeft, edgeRight, edgeTop, countRight, countBelow};
        Customer.customerImages = new ImageView[]{first, second, third, fourth, fifth, sixth, seventh};
        Customer.smileyImages = new ImageView[]{smileyFirst, smileySecond, smileyThird, smileyFourth, smileyFifth, smileySixth, smileySeventh};
        Customer.orderLabels = new ImageView[]{orderlabel1, orderlabel2, orderlabel3, orderlabel4, orderlabel5, orderlabel6, orderlabel7};
        Customer.coinImages = new ImageView[]{coinFirst, coinSecond, coinThird, coinFourth, coinFifth, coinSixth, coinSeventh};
        Customer.freeChairsNumbersList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6));
        Customer.setGeneralPurposeTimer(controllerTimer);
        game = new Game(upgradeCoffeeImageView, upgradeCakeImageView, upgradePlayerImageView);
    }

    public void changeStartCoffeeImage() throws FileNotFoundException {
        startButton.setImage(createImage("startCoffeeHot.png"));
    }

    public void changeStartCoffeeImageBack() throws FileNotFoundException {
        startButton.setImage(createImage("startCoffee.png"));
    }

    public void changeQuitStartScreen() throws FileNotFoundException {
        startQuitButton.setImage(createImage("quitEndScreenBrighter.png"));
    }

    public void changeQuitStartScreenBack() throws FileNotFoundException {
        startQuitButton.setImage(createImage("quitEndScreen.png"));
    }

    public void changeStartImage() throws FileNotFoundException {
        gameStartButton.setImage(createImage("instructionsGotIt.png"));
    }

    public void changeStartImageBack() throws FileNotFoundException {
        gameStartButton.setImage(createImage("instructionsGotItBrighter.png"));
    }

    public void changePlayAgain() throws FileNotFoundException {
        playAgainImage.setImage(createImage("playAgainBrighter.png"));
    }

    public void changePlayAgainBack() throws FileNotFoundException {
        playAgainImage.setImage(createImage("playAgain.png"));
    }

    public void changeBackToStartMenu() throws FileNotFoundException {
        backToStartImage.setImage(createImage("backToStartMenuBrighter.png"));
    }

    public void changeBackToStartMenuBack() throws FileNotFoundException {
        backToStartImage.setImage(createImage("backToStartMenu.png"));
    }

    public void changeQuitEndScreen() throws FileNotFoundException {
        quitEndScreenImage.setImage(createImage("quitEndScreenBrighter.png"));
    }

    public void changeQuitEndScreenBack() throws FileNotFoundException {
        quitEndScreenImage.setImage(createImage("quitEndScreen.png"));
    }

    public void showCoffee() throws FileNotFoundException {
        if (waiterImageView.getBoundsInParent().intersects(coffeeMachineImageView.getBoundsInParent())) {
            game.getCoffeeMachine().displayProduct(waiterImageView, coffeeMachineImageView, waiter, progressBarCoffee);
            String musicFile = MusicFileBuilder.buildMusicFile("test_sound.wav");
            AudioClip coffeeSound = new AudioClip(new File(musicFile).toURI().toString());
            coffeeSound.play();
        }
    }

    public void showCake() throws FileNotFoundException {
        if (waiterImageView.getBoundsInParent().intersects(cakeMachineImageView.getBoundsInParent())) {
            game.getCakeMachine().displayProduct(waiterImageView, cakeMachineImageView, waiter, progressBarCake);
            String musicFile = MusicFileBuilder.buildMusicFile("test_sound.wav");
            AudioClip cakeSound = new AudioClip(new File(musicFile).toURI().toString());
            cakeSound.play();
        }
    }

    public void trashItem() throws FileNotFoundException {
        if (waiter.getProductInHand().equals("coffee") || waiter.getProductInHand().equals("cake")) {
            String musicFile = MusicFileBuilder.buildMusicFile("trashSound.mp3");
            AudioClip trashSound = new AudioClip(new File(musicFile).toURI().toString());
            trashSound.play();
            waiterImageView.setImage(createImage(waiter.getFilenameImageWithoutProduct()));
            waiter.setProductInHand("none");
        }
    }

    public Customer findCustomer(List<Customer> customerList, ImageView customerImageView) {
        for (Customer customer : customerList) {
            if (customer.getCustomerImage(customer.customerImage, customer.customerImages).equals(customerImageView)) {
                return customer;
            }
        }
        return null;
    }

    public void displayPerson(MouseEvent event) throws IOException {
        ImageView customerImageView = (ImageView) event.getSource();
        Customer customer = findCustomer(Customer.customersInCoffeeShopList, customerImageView);

        if (!customer.hasAlreadyOrdered()) {
            customer.setAlreadyOrdered(!customer.hasAlreadyOrdered());
            customer.displayOrderOfCustomer(customer.getOrder());
        } else {
            if (customerImageView.getBoundsInParent().intersects(waiterImageView.getBoundsInParent())) {
                try {
                    customer.startTimerSpawn(5, Customer.getGeneralPurposeTimer());
                } catch (NullPointerException e) {
                    switchToEndScreen();
                }
                if (customer.checkOrder(waiter, customer, waiterImageView)) {
                    String moneyImage = "";
                    if (customer.customerIsHappy()) {
                        moneyImage = game.getFilenameImageDollar();
                    } else if (customer.customerIsAnnoyed()) {
                        moneyImage = game.getFilenameImageFourCoins();
                    } else if (customer.customerIsAngry()) {
                        moneyImage = game.getFilenameImageThreeCoins();
                    }
                    customer.getCoinImage().setImage(createImage(moneyImage));
                    customer.getCoinImage().setOnMouseClicked(event1 -> {
                        try {
                            getMoney(event1, customer);
                        } catch (IOException e) {
                            try {
                                switchToEndScreen();
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                }
            }
        }
    }

    public void checkUpgradePossible(Upgrade upgrade) throws FileNotFoundException {
        upgrade.checkIfUpgradeIsPossible(upgrade, game);
    }

    public void doUpgrade(MouseEvent e) throws FileNotFoundException {
        game.doUpgrade(((ImageView) e.getSource()).getId(), waiter);
        coinsEarnedLabel.setText(String.valueOf(game.getCoinsEarned()));
        String musicFile = MusicFileBuilder.buildMusicFile("upgradeSound.wav");
        AudioClip getUpgrade = new AudioClip(new File(musicFile).toURI().toString());
        getUpgrade.play();

        checkUpgradePossible(game.getCoffeeUpgrade());
        checkUpgradePossible(game.getCakeUpgrade());
        checkUpgradePossible(game.getPlayerUpgrade());
    }

    public boolean checkIfWaiterCollidesWithLabelOfCustomer(ImageView waiter) {
        for (Label collision : collisions) {
            if (waiter.getBoundsInParent().intersects(collision.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public void getMoney(MouseEvent e, Customer customer) throws IOException {
        String musicFile = MusicFileBuilder.buildMusicFile("coinsSound.wav");
        AudioClip collectMoney = new AudioClip(new File(musicFile).toURI().toString());
        collectMoney.play();
        arrangeChairs(e, customer);

        if (game.getCoinsEarned() < 80) {
            checkUpgradePossible(game.getCoffeeUpgrade());
            checkUpgradePossible(game.getCakeUpgrade());
            checkUpgradePossible(game.getPlayerUpgrade());
            coinsEarnedLabel.setText(String.valueOf(game.getCoinsEarned()));
            try {
                customer.startTimerSpawn(5, Customer.getGeneralPurposeTimer());
            } catch (NullPointerException y) {
                switchToEndScreen();
            }
        } else {
            // Game won
            stopTimers();
            switchToEndScreen();
        }
    }

    public void arrangeChairs(MouseEvent e, Customer customer) {
        Customer.addFreeSeatAfterCustomerLeft(customer.getOrder().getChair());
        game.addCoinsAfterCustomer(customer);
        ((ImageView) e.getSource()).setVisible(false);
        ((ImageView) e.getSource()).setDisable(true);
    }

    public void stopTimers() {
        for (Customer customer : Customer.allCustomersList) {
            if (customer.getSixtySecondsTimer() != null) {
                customer.getSixtySecondsTimer().cancel();
            }
        }
        Customer.allCustomersList.clear();
        Customer.customersInCoffeeShopList.clear();
        Customer.freeChairsNumbersList.clear();
        waiter.setProductInHand("none");
        controllerTimer.cancel();
        Customer.getGeneralPurposeTimer().cancel();
    }
    
    public void endGameQuick() {
        stopTimers();
        backgroundMusic.stop();
        Platform.exit();
        System.exit(0);
    }

    public void endGame() {
        Platform.exit();
        backgroundMusic.stop();
        System.exit(0);
    }
}
