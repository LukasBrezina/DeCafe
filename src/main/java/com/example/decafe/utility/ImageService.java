package com.example.decafe.utility;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageService {

    public static Image createImage(String filename) throws FileNotFoundException {
        String filePath = new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }


    public static ImageView getImage(ImageView customer, ImageView[] customerImages, ImageView[] searchArray ){
        for (int i = 0; i < customerImages.length; i++) {
            if (customerImages[i].equals(customer)) {
                return searchArray[i];
            }
        }
        return null;
    }

}
