package com.example.decafe.datatypes;

import com.example.decafe.utility.ImageService;
import com.example.decafe.utility.MusicFileBuilder;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import java.io.*;
import java.util.*;
import static com.example.decafe.utility.ImageService.createImage;


public class Customer {
    private Order order;
    public ImageView customerImage;
    private final Timer sixtySecondsWaitingTimer = new Timer();
    private static Timer generalPurposeTimer; //timer for leaving, spawning
    private ImageView customerMoodImage;
    private ImageView customerMoneyImage;
    private boolean alreadyOrderedBool;
    private boolean greenCustomerSmileyBool;
    private boolean yellowCustomerSmileyBool;
    private boolean redCustomerSmileyBool;
    private boolean leftUnhappyBool = true;
    public static List<Customer> customersInCoffeeShopList = new ArrayList<>();
    public static List<Customer> allCustomersList = new ArrayList<>();
    public static List<Integer> freeChairsNumbersList;
    public static ImageView[] customerImages;
    private static int freeSeatChosen = 0;
    public static ImageView[] smileyImages;
    public static ImageView[] orderLabels;
    public static ImageView[] coinImages;

    public Customer(){}
    Customer(Order order, ImageView customerImage, boolean alreadyOrderedBool, ImageView customerMoodImage, ImageView customerMoneyImage) {
        this.order = order;
        this.customerImage = customerImage;
        this.alreadyOrderedBool = alreadyOrderedBool;
        this.customerMoodImage = customerMoodImage;
        this.customerMoneyImage = customerMoneyImage;
    }

    public static Timer getGeneralPurposeTimer() { return generalPurposeTimer; }
    public Timer getSixtySecondsTimer() { return sixtySecondsWaitingTimer; }
    public boolean customerIsHappy() { return greenCustomerSmileyBool;}
    public boolean customerIsAngry() { return redCustomerSmileyBool; }
    public boolean customerIsAnnoyed() { return yellowCustomerSmileyBool; }
    public boolean hasAlreadyOrdered() { return this.alreadyOrderedBool;}
    public Order getOrder() { return order;}
    public ImageView getCoinImage() { return customerMoneyImage;}
    public void setOrder(Order order) { this.order = order; }
    public static void setGeneralPurposeTimer(Timer generalPurposeTimer) { Customer.generalPurposeTimer = generalPurposeTimer;}
    public void setAlreadyOrdered(boolean alreadyOrdered) { this.alreadyOrderedBool = alreadyOrdered; }


    public static void addFreeSeatAfterCustomerLeft(int chairLeft) { Customer.freeChairsNumbersList.add(chairLeft); }

    public static ImageView getCustomerImage(ImageView customer, ImageView[] searchArray ){
        return ImageService.getImage(customer, customerImages, searchArray);
    }

    public static ImageView getOrderLabelImage(ImageView customer) {
        return ImageService.getImage(customer, customerImages, orderLabels);
    }
    
    public static ImageView getRandomCustomerPicture(){
        Random random = new Random();
        int index = freeChairsNumbersList.get(random.nextInt(freeChairsNumbersList.size()));
        freeSeatChosen = index;
        if (!freeChairsNumbersList.contains(index)) { 
            getRandomCustomerPicture();
        }
        freeChairsNumbersList.remove(Integer.valueOf(index)); 
        return customerImages[index];
    }
    
    public static void spawnCustomers(){
        if (customersInCoffeeShopList.size() < 3 && !freeChairsNumbersList.isEmpty()) {
            ImageView customerImage = getRandomCustomerPicture();
            customerImage.setVisible(true);
            ImageView orderLabelImage = getOrderLabelImage(customerImage);
            ImageView moodImage = getCustomerImage(customerImage, smileyImages);
            ImageView coinImage = getCustomerImage(customerImage, coinImages);
            Order order = new Order(orderLabelImage, freeSeatChosen);
            Customer customer = new Customer(order, customerImage, false, moodImage, coinImage);
            customersInCoffeeShopList.add(customer);
            allCustomersList.add(customer);
            String musicFile = MusicFileBuilder.buildMusicFile("doorBell.mp3");
            AudioClip doorBell = new AudioClip(new File(musicFile).toURI().toString());
            doorBell.play();
            customer.customerWaitingTimeBeforeLeave();
        }
    }

    public void startTimerSpawn(int duration, Timer controllerTimer){
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Customer.spawnCustomers();
                        controllerTimer.purge();
                    }
                },
                duration * 1000L
        );
    }

    public void startTimerLeave (Customer customer){
        this.order.getOrderLabelImage().setVisible(false);
        this.customerMoodImage.setVisible(false);
        generalPurposeTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            customerLeaves(customer.getCustomerImage(customer.customerImage, customerImages));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        generalPurposeTimer.purge();
                    }
                },
                1000
        );
        this.sixtySecondsWaitingTimer.cancel();
    }

    public void customerWaitingTimeBeforeLeave()  {
        Customer customer = this;
        TimerTask timerTask = new TimerTask() {
            int seconds = 60;
            @Override
            public void run() {
                seconds --;
                if (seconds == 59){
                    customerMoodImage.setVisible(true);
                    try {
                        customerMoodImage.setImage(createImage("smileygreen.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    greenCustomerSmileyBool = true;
                    yellowCustomerSmileyBool = false;
                    redCustomerSmileyBool = false;
                }else if (seconds == 30){
                    customerMoodImage.setVisible(true);
                    try {
                        customerMoodImage.setImage(createImage("smileyyellow.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    greenCustomerSmileyBool = false;
                    yellowCustomerSmileyBool = true;
                    redCustomerSmileyBool = false;
                }else if (seconds == 15){
                    customerMoodImage.setVisible(true);
                    try {
                        customerMoodImage.setImage(createImage("smileyred.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    greenCustomerSmileyBool = false;
                    yellowCustomerSmileyBool = false;
                    redCustomerSmileyBool = true;
                }
                else if (seconds == 0){
                    startTimerLeave(customer);
                }
            }
        };

        this.sixtySecondsWaitingTimer.schedule(timerTask, 0, 1000);
    }

    public void displayOrderOfCustomer(Order newOrder) throws FileNotFoundException {
        setOrder(newOrder);
        String product = order.getProduct();
        if (product != null && (product.equals("cake") || product.equals("coffee"))) {
            order.getOrderLabelImage().setVisible(true);
            int chair = order.getChair();
            String imageFile = switch (chair) {
                case 0, 1, 4, 6 -> product.equals("cake") ? "bubbleCakeTopLeft.png" : "bubbleCoffeeTopLeft.png";
                case 2, 3       -> product.equals("cake") ? "bubbleCakeTopRight.png" : "bubbleCoffeeTopRight.png";
                case 5          -> product.equals("cake") ? "bubbleCakeBottomRight.png" : "bubbleCoffeeBottomRight.png";
                default         -> "";
            };
            if (!imageFile.isEmpty()) {
                order.getOrderLabelImage().setImage(createImage(imageFile));
            }
        }
    }

    public boolean checkOrder(Waiter waiter, Customer customer, ImageView waiterImage) throws FileNotFoundException{
        waiterImage.setImage(createImage(waiter.getFilenameImageWithoutProduct()));
        if (waiter.getProductInHand().equals(customer.getOrder().getProduct())) {
            waiter.setProductInHand("none");
            this.leftUnhappyBool = false;
            startTimerLeave(this);
            return true;
        } else {
            waiter.setProductInHand("none");
            startTimerLeave(this);
            return false;
        }
    }

    public static void deactivateCustomer(Customer customer) throws FileNotFoundException {
        customer.customerMoneyImage.setVisible(false);
        customer.customerMoneyImage.setDisable(true);
        freeChairsNumbersList.add(customer.order.getChair());
        customer.startTimerSpawn(5, generalPurposeTimer);
    }

    public void customerLeaves(ImageView customerImage) throws FileNotFoundException {
        customerImage.setVisible(false);
        customersInCoffeeShopList.removeIf(customer -> customer.customerImage.equals(customerImage));
        this.customerMoneyImage.setVisible(true);
        this.customerMoneyImage.setDisable(false);

        if (this.leftUnhappyBool){
            String wrongChoiceSound = MusicFileBuilder.buildMusicFile("wrongChoice.mp3");
            AudioClip wrongOrder = new AudioClip(new File(wrongChoiceSound).toURI().toString());
            wrongOrder.play();
            this.customerMoneyImage.setImage(createImage("coin.png"));
            this.customerMoneyImage.setOnMouseClicked(event -> {
                try {
                    deactivateCustomer(this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            String correctChoiceSound = MusicFileBuilder.buildMusicFile("rightChoice.mp3");
            AudioClip rightOrder = new AudioClip(new File(correctChoiceSound).toURI().toString());
            rightOrder.play();
        }
    }
}






