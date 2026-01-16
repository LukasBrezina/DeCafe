package com.example.decafe.datatypes;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import java.io.*;
import java.util.*;

import static com.example.decafe.utility.ImageService.createImage;


public class Customer {
//    private String order; //The order of the customer
//    private ImageView customer; //picture of the customer
//    private ImageView orderLabel; //label that displays order
//    private int chair; //number of chair the customer is sitting
    private Order order;
    public ImageView customerImage; //picture of the customer
    private Timer sixtySecondsTimer = new Timer(); //timer for the 60 seconds waiting time
    private static Timer controllerTimer; //timer for leaving, spawning
    private ImageView smiley; //picture of smiley for the mood of the customer
    private ImageView coinImage; //picture of the money the customer is leaving behind

    private boolean alreadyOrdered; //boolean to see if the customer has already ordered
    private boolean green; //boolean for smiley
    private boolean yellow; //boolean for smiley
    private boolean red; //boolean for smiley

    private boolean leftUnhappy = true; //boolean to see if customer has left unhappy (received wrong order or left after 60 secs)

    public static List<Customer> customersInCoffeeShop = new ArrayList<>(); //list with all customers that are in the café
    public static List<Customer> allCustomers = new ArrayList<>(); //list with all customers ever created
    public static List<Integer> freeChairs; //integer list with all chair numbers
    public static ImageView[] customerImages; //array with all customer pictures
    private static int freeSeatChosen = 0;
    public static ImageView[] smileyImages; //image for smiley
    public static ImageView[] orderLabels; //label for order
    public static ImageView[] coinImages; //image for coins

    // Constructors
    public Customer(){}
    Customer(Order order, ImageView customerImage, boolean alreadyOrdered, ImageView smiley, ImageView coinImage) {
        this.order = order;
        this.customerImage = customerImage;
        this.alreadyOrdered = alreadyOrdered;
        this.smiley = smiley;
        this.coinImage = coinImage;
    }
    // Getter
    public static Timer getControllerTimer() {
        return controllerTimer;
    }

    public Timer getSixtySecondsTimer() {
        return sixtySecondsTimer;
    }

    public static void addFreeSeat(int chairLeft) { //add chair number to the list when customer has left
        Customer.freeChairs.add(chairLeft);
    }

    public boolean isGreen() { //to see if the color of the smiley
        return green;
    }

    public boolean isRed() { //to see if the color of the smiley
        return red;
    }

    public boolean isYellow() { //to see if the color of the smiley
        return yellow;
    }

    public boolean isAlreadyOrdered() { //return if the customer has already ordered or not
        return this.alreadyOrdered;
    }

    public Order getOrder() { //returns the order of the customer
        return order;
    }

//    public String getRandomOrder() { //returns random order
//
//        Random random = new Random();
//        int number = random.nextInt(2);
//
//        switch (number) {
//            case 0 -> order = "cake";
//            case 1 -> order = "coffee";
//        }
//
//        return order;
//    }

    public ImageView getCoinImage() { //returns the image of the coin
        return coinImage;
    }

    // Setter
    public void setOrder(Order order) { //sets the order of the customer
        this.order = order;
    }

    public static void setControllerTimer(Timer controllerTimer) { //sets the timer
        Customer.controllerTimer = controllerTimer;
    }

    public void setAlreadyOrdered(boolean alreadyOrdered) { //sets if the customer has already ordered or not
        this.alreadyOrderedBool = alreadyOrdered;
    }

    // Method used to create an Image Object
//    public Image createImage(String filename) throws FileNotFoundException {
//        File f = new File(""); // Get filepath of project
//        // Get path to certain Image
//        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
//        InputStream stream = new FileInputStream(filePath); // Convert path into stream
//        return new Image(stream); // Convert stream to Image and return it
//    }

    //Returns the appropriate image for the customer
    public static ImageView getImage(ImageView customer, ImageView[] searchArray ){
        ImageView wantedImage = new ImageView();

        if (customerImages[0].equals(customer)) {
            wantedImage = searchArray[0];
        } else if (customerImages[1].equals(customer)) {
            wantedImage = searchArray[1];
        } else if (customerImages[2].equals(customer)) {
            wantedImage = searchArray[2];
        } else if (customerImages[3].equals(customer)) {
            wantedImage = searchArray[3];
        } else if (customerImages[4].equals(customer)) {
            wantedImage = searchArray[4];
        } else if (customerImages[5].equals(customer)) {
            wantedImage = searchArray[5];
        } else if (customerImages[6].equals(customer)) {
            wantedImage = searchArray[6];
        }

        return wantedImage;
    }

    //Returns the appropriate label for the customer
    public static ImageView getLabel(ImageView customer) {

        ImageView customerOrder = new ImageView();

        if (customerImages[0].equals(customer)) {
            customerOrder = orderLabels[0];
        } else if (customerImages[1].equals(customer)) {
            customerOrder = orderLabels[1];
        } else if (customerImages[2].equals(customer)) {
            customerOrder = orderLabels[2];
        } else if (customerImages[3].equals(customer)) {
            customerOrder = orderLabels[3];
        } else if (customerImages[4].equals(customer)) {
            customerOrder = orderLabels[4];
        } else if (customerImages[5].equals(customer)) {
            customerOrder = orderLabels[5];
        } else if (customerImages[6].equals(customer)) {
            customerOrder = orderLabels[6];
        }

        return customerOrder;

    }

    //Returns random customer picture
    public static ImageView getRandomPic(){
        Random random = new Random();
        int index = freeChairs.get(random.nextInt(freeChairs.size()));
        freeSeatChosen = index;

        if (!freeChairs.contains(index)) { //when the customer is already visible make new random number
            getRandomPic();
        }

        freeChairs.remove(Integer.valueOf(index)); //remove the number from the number list of chairs so there are no duplicates

        return customerImages[index];
    }

    //Methode to spawn customers
    public static void spawnCustomers(){
        if (customersInCoffeeShop.size() < 3 && freeChairs.size() != 0) { //spawn a new customer this when under 3 customers are in the café
            ImageView customerImage = getRandomPic(); //get random picture from Array
            customerImage.setVisible(true); //make this picture visible

            ImageView customerLabel = getLabel(customerImage); //get the label for the customer
            ImageView smiley = getImage(customerImage, smileyImages); //gets the smiley picture for the customer
            ImageView coin = getImage(customerImage, coinImages); //gets the coin picture for the customer

            Order order = new Order(customerLabel, freeSeatChosen); //make new order object
            Customer customer = new Customer(order, customerImage, false, smiley, coin); //make new customer object
//            Customer customer = new Customer(customerLabel, customerImage, freeSeatChosen, smiley, coin); //make new customer object
            customersInCoffeeShop.add(customer); //to check if not more than 3 customers are in the store
            allCustomers.add(customer); //to stop all timers that are still alive even after customer has left
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "doorBell.mp3";
            AudioClip doorBell = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer doorBell = new MediaPlayer(sound);
            doorBell.play();
            customer.customerWaitingTimeBeforeLeave(); //place customer in the waitingTime of  60 seconds
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
        this.smiley.setVisible(false);
        controllerTimer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            customerLeaves(customer.getImage(customer.customerImage, customerImages));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        controllerTimer.purge();
                    }
                },
                1000
        );
        this.sixtySecondsTimer.cancel(); //cancel the 60 seconds when customer left
    }

    public void customerWaitingTimeBeforeLeave()  {
        Customer customer = this;
        TimerTask timerTask = new TimerTask() {
            int seconds = 60;
            @Override
            public void run() {
                seconds --;
                if (seconds == 59){ //set green smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileygreen.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = true;
                    yellow = false;
                    red = false;
                }else if (seconds == 30){ //set yellow smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyyellow.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = true;
                    red = false;
                }else if (seconds == 15){ //set red smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyred.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = false;
                    red = true;
                }
                else if (seconds == 0){ //when the timer has finished - customer leaves
                    startTimerLeave(customer);
                }
            }
        };

        this.sixtySecondsTimer.schedule(timerTask, 0, 1000);//it should call this methode every second

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


    //Methode to check if the order is right or wrong
    public boolean checkOrder(Player player, Customer customer, ImageView waiterImage) throws FileNotFoundException{
        waiterImage.setImage(createImage(player.getFilenameImageWithoutProduct())); //set player without order
        System.out.println(player.getProductInHand());
        System.out.println(customer.getOrder().getProduct());
        if (player.getProductInHand().equals(customer.getOrder().getProduct())) { //if player has the right order
            player.setProductInHand("none"); // change product hold by player to none
            this.leftUnhappyBool = false;
            startTimerLeave(this); // start timer to leave the coffee shop (true - it was the right order)
            return true;
        } else {
            player.setProductInHand("none"); // change product hold by player to none
            startTimerLeave(this);  // start timer to leave the coffee shop (false - it was the wrong order)
            return false;
        }
    }

    //when the customer leaves after 60 seconds without being served or received wrong order
    public static void deactivateCustomer(Customer customer) throws FileNotFoundException {
        customer.customerMoneyImage.setVisible(false);
        customer.customerMoneyImage.setDisable(true);
        freeChairsNumbersList.add(customer.order.getChair());
        customer.startTimerSpawn(5, controllerTimer);
    }

    //Methode for when the customer leaves
    public void customerLeaves(ImageView customerImage) throws FileNotFoundException {
        customerImage.setVisible(false);
        customersInCoffeeShop.removeIf(customer -> customer.customerImage.equals(customerImage)); //remove customer from customerList
        this.coinImage.setVisible(true);
        this.coinImage.setDisable(false);
        if (this.leftUnhappy){ //when customer leaves after 60 seconds or received wrong order
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "wrongChoice.mp3";
            AudioClip wrongOrder = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer collectMoney = new MediaPlayer(sound);
            wrongOrder.play();
            this.coinImage.setImage(createImage("coin.png")); // set coin Image to empty plate
            this.coinImage.setOnMouseClicked(event1 -> { // set click event to this
                try {
                    deactivateCustomer(this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } else {
            File f = new File("");
            String musicFile = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "rightChoice.mp3";
            AudioClip rightOrder = new AudioClip(new File(musicFile).toURI().toString());
            //MediaPlayer collectMoney = new MediaPlayer(sound);
            rightOrder.play();
        }
    }
}






