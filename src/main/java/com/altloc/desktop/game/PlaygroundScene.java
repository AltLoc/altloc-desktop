package com.altloc.desktop.game;

import javafx.animation.AnimationTimer;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;

public class PlaygroundScene {
    private final Group world;
    private final PerspectiveCamera camera;
    private Sphere player;
    private Box ground;
    private double playerX = 0, playerZ = 0;
    private double groundSize = 200;

    // Конструктор без параметров
    public PlaygroundScene() {
        this.world = new Group();
        this.camera = new PerspectiveCamera(true);

        // Создаем игрока и землю
        createPlayer();
        createGround();

        // Создаем и возвращаем сцену
        Scene scene = new Scene(world, 800, 600, true);
        scene.setOnKeyPressed(this::handleKeyInput);

        // Запускаем игровой цикл
        startGameLoop();
    }

    // Метод для получения мира
    public Group getWorld() {
        return world;
    }

    private void createPlayer() {
        player = new Sphere(10);
        PhongMaterial material = new PhongMaterial(Color.RED);
        player.setMaterial(material);
        player.setTranslateY(10);
        world.getChildren().add(player);
    }

    private void createGround() {
        ground = new Box(groundSize, 1, groundSize);
        PhongMaterial material = new PhongMaterial(Color.GREEN);
        ground.setMaterial(material);
        ground.setTranslateY(0);
        world.getChildren().add(ground);
    }

    private void expandGround() {
        if (Math.abs(playerX) > groundSize / 2 - 50 || Math.abs(playerZ) > groundSize / 2 - 50) {
            groundSize += 100;
            ground.setWidth(groundSize);
            ground.setDepth(groundSize);
        }
    }

    private void handleKeyInput(KeyEvent event) {
        switch (event.getCode()) {
            case W -> playerZ -= 10;
            case S -> playerZ += 10;
            case A -> playerX -= 10;
            case D -> playerX += 10;
            default -> {
                // Do nothing
            }
        }
        player.setTranslateX(playerX);
        player.setTranslateZ(playerZ);
    }

    private void startGameLoop() {
        // Запускаем игровой цикл
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                expandGround();
            }
        };
        timer.start();
    }

    public Camera getCamera() {
        return camera;
    }
}
