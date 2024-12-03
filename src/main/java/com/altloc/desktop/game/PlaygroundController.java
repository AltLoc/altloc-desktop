package com.altloc.desktop.game;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;

public class PlaygroundController {

    @FXML
    private Pane sceneContainer;

    public void initialize() {
        // Create a new Group for the scene
        Group subSceneRoot = new Group();

        // Initialize PlaygroundScene with this new Group
        PlaygroundScene playgroundScene = new PlaygroundScene();

        // Create the SubScene with the separate root
        SubScene subScene = new SubScene(subSceneRoot, 800, 600, true, SceneAntialiasing.BALANCED);

        // Set the camera from PlaygroundScene to the SubScene
        subScene.setCamera(playgroundScene.getCamera());

        // Add the SubScene to the sceneContainer (your FXML pane)
        sceneContainer.getChildren().add(subScene);

        // Add the world and other objects (like player, ground) to the subSceneRoot
        subSceneRoot.getChildren().add(playgroundScene.getWorld());

        // // Add event listener for key inputs to control the player movement
        // subScene.setOnKeyPressed(event -> playgroundScene.handleKeyInput(event)); //
        // Handle key events
    }
}
