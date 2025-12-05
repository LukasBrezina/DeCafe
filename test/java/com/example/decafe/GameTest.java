package com.example.decafe;

import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameTest {
    private Game game;
    public ImageView upgradeCoffeeImageView;
    public ImageView upgradeCakeImageView;
    public ImageView upgradePlayerImageView;

    @BeforeEach
    void setUp() {
        game = new Game(upgradeCoffeeImageView, upgradeCakeImageView, upgradePlayerImageView);
    }

    @Test
    void getCoinsEarned() {
        assertEquals(0, game.getCoinsEarned());
    }
}