package com.example.decafe.datatypes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.decafe.utility.ImageService.createImage;

public class Machine {
    private int timeForProduct;
    private Boolean produced;
    private final String imageOfMachineWithoutProduct;
    private final String imageOfMachineWithProduct;
    private final String machineProductType;

    public Machine(int timeForProduct, String imageOfMachineWithProduct, String imageOfMachineWithoutProduct, String machineProductType){
        this.timeForProduct = timeForProduct;
        this.produced = false;
        this.imageOfMachineWithProduct = imageOfMachineWithProduct;
        this.imageOfMachineWithoutProduct = imageOfMachineWithoutProduct;
        this.machineProductType = machineProductType;
    }

    public int getDuration() { return timeForProduct; }
    public Boolean getProduced() { return produced; }
    public void setTimeForProduct(int timeForProduct) { this.timeForProduct = timeForProduct; }
    public void setProduced(Boolean produced){ this.produced = produced; }

    public void machineProgressBarAnimation(Timer productionTimer, ImageView machineImageView, ProgressBar machineProgressBar, Image imageProductProduced){
        machineImageView.setDisable(true);
        machineProgressBar.setVisible(true);
        Timeline task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(machineProgressBar.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(this.getDuration()),
                        new KeyValue(machineProgressBar.progressProperty(), 1)
                )
        );
        int maxStatus = 12;
        IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);
        Timeline timelineBar = new Timeline(
                new KeyFrame(
                        Duration.millis(300),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );
        timelineBar.setCycleCount(Timeline.INDEFINITE);
        timelineBar.play();
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {
            int statusNew = statusNewNumber.intValue();
            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);
            machineProgressBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
        });
        task.playFromStart();

        productionTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        machineImageView.setImage(imageProductProduced);
                        machineImageView.setDisable(false);
                        task.stop();
                        timelineBar.stop();
                        productionTimer.cancel();
                    }
                },
                this.timeForProduct * 1000L
        );
    }

//    public void displayProduct (ImageView waiterImageView, ImageView machineImageView, Waiter waiter, ProgressBar machineProgressBar) throws FileNotFoundException {
//        Timer productionTimer = new Timer();
//        String imageMachine = this.imageOfMachineWithProduct;
//        String waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithoutProduct();
//        boolean gotProduced = false;
//
//        if (!this.produced && waiter.getProductInHand().equals("none")) {
//            this.setProduced(true);
//            gotProduced = true;
//        } else if (!this.produced && waiter.getProductInHand().equals("coffee")) {
//            this.setProduced(true);
//            gotProduced = true;
//            waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCoffee();
//        } else if (!this.produced && waiter.getProductInHand().equals("cake")) {
//            this.setProduced(true);
//            gotProduced = true;
//            // Change Image to waiter with Cake (since it's not the same as the default)
//            waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCake();
//        } else { // If something was already produced
//            if (waiter.getProductInHand().equals("none")){ // And the waiter hasn't anything in his hands
//                // Set produced boolean to false since nothing was produces
//                this.setProduced(false);
//                // Change Image to Machine without product (since product was taken)
//                imageMachine = this.imageOfMachineWithoutProduct;
//                // Set the product the player obtain to whatever type the machine is
//                waiter.setProductInHand(this.machineProductType);
//                if (this.machineProductType.equals("coffee")){ // If the type of the machine is coffee
//                    // Change the images of the waiter, so he holds coffee
//                    waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCoffee();
//                } else { // If the type of the machine is cake
//                    // Change the images of the waiter, so he holds cake
//                    waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCake();
//                }
//            } else { // If coffee Brew has something in his hands (so he can't pick up the product produced)
//                    // Keep the picture the same
//                    if (waiter.getProductInHand().equals("coffee")){ // So if he holds coffee at the moment
//                        // Set Image to waiter with coffee
//                        waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCoffee();
//                    } else { // If he holds cake at the moment
//                        // Set Image to waiter with cake
//                        waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCake();
//                    }
//            }
//        }
//        // Set the waiter Image to whatever images was chosen above
//        waiterImageView.setImage(createImage(waiterFilenameImageWithoutProduct));
//
//        if (gotProduced) { // If something was produced
//            // Animate the progress bar and wait till the product gets produced
//            machineProgressBarAnimation(productionTimer, machineImageView, machineProgressBar, createImage(imageMachine));
//        } else { // If nothing was produced
//            // Make the progress bar visible if something was produced and if not so set it invisible
//            machineProgressBar.setVisible(this.getProduced());
//            // Set the machine Image to whatever images was chosen above
//            machineImageView.setImage(createImage(imageMachine));
//        }
//    }

    public void displayProduct(ImageView waiterImageView, ImageView machineImageView, Waiter waiter, ProgressBar machineProgressBar) throws FileNotFoundException {
        Timer productionTimer = new Timer();
        String imageMachine = this.imageOfMachineWithProduct;
        String waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithoutProduct();
        boolean gotProduced = false;

        if (!this.produced) {
            this.setProduced(true);
            gotProduced = true;
            if (waiter.getProductInHand().equals("coffee")) {
                waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCoffee();
            } else if (waiter.getProductInHand().equals("cake")) {
                waiterFilenameImageWithoutProduct = waiter.getFilenameImageWithCake();
            }
        } else if (waiter.getProductInHand().equals("none")) {
            this.setProduced(false);
            imageMachine = this.imageOfMachineWithoutProduct;
            waiter.setProductInHand(this.machineProductType);
            waiterFilenameImageWithoutProduct = this.machineProductType.equals("coffee")
                    ? waiter.getFilenameImageWithCoffee()
                    : waiter.getFilenameImageWithCake();
        } else {
            waiterFilenameImageWithoutProduct = waiter.getProductInHand().equals("coffee")
                    ? waiter.getFilenameImageWithCoffee()
                    : waiter.getFilenameImageWithCake();
        }
        waiterImageView.setImage(createImage(waiterFilenameImageWithoutProduct));
        if (gotProduced) {
            machineProgressBarAnimation(productionTimer, machineImageView, machineProgressBar, createImage(imageMachine));
        } else {
            machineProgressBar.setVisible(this.getProduced());
            machineImageView.setImage(createImage(imageMachine));
        }
    }


}
