package com.example.decafe;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// Class used to start the JavaFX Application
public class Application extends javafx.application.Application {

    public static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Application.stage = stage;
        stage.getIcons().add(new Image("file:src/main/resources/com/example/decafe/mugTabPic.png"));
        stage.setTitle("DeCafé");
        Application.stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}