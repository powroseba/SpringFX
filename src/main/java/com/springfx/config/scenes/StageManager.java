package com.springfx.config.scenes;

import com.springfx.config.SpringFXMLLoader;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

public class StageManager {

    private static final Logger log = Logger.getLogger(StageManager.class.getName());

    private final Stage stage;
    private final SpringFXMLLoader springFXMLLoader;
    private static FXScene MAIN_SCENE;
    private static ResourceBundle RESOURCE_BUNDLE;
    private static final HashMap<String, Stage> stageCollection = new HashMap<>();

    public StageManager(Stage stage, SpringFXMLLoader springFXMLLoader) {
        this.stage = stage;
        this.springFXMLLoader = springFXMLLoader;
    }

    public void displayInitialScene() {
        log.info("Displaying main page");
        stageCollection.put(MAIN_SCENE.identifier(), stage);
        switchScene(MAIN_SCENE);
    }

    public void switchScene(final FXScene scene) {
        log.info("Switching scene to " + scene.getTitle());
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(scene.getFXMLFilePath(), RESOURCE_BUNDLE);
        show(viewRootNodeHierarchy, scene.getTitle());
    }

    public void showNewScene(final FXScene scene) {
        log.info("Showing new scene : " + scene.getTitle());
        Optional<FXScene> fxScene = findScene(scene);
        fxScene.ifPresent(passedScene -> {
            Stage stage = stageCollection.get(passedScene.identifier());
            if (stage == null || passedScene.isAlwaysNewScene()) {
                stage = new Stage();
                Parent sceneParent = loadViewNodeHierarchy(passedScene.getFXMLFilePath(), RESOURCE_BUNDLE);
                stage.setTitle(passedScene.getTitle());
                stage.setScene(new Scene(sceneParent));
                stageCollection.put(passedScene.identifier(), stage);
            } else {
                switchScene(scene);
//                return;
            }
            stage.show();
        });
    }


    private Optional<FXScene> findScene(FXScene scene) {
        return scene.getAllScenes().stream()
                .filter(s -> s.getFXMLFilePath().equals(scene.getFXMLFilePath()))
                .findFirst();
    }

    private void show(final Parent rootNode, String title) {
        Scene scene = prepareScene(rootNode);
//        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle(title);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();

        try {
            stage.show();
        } catch (Exception e) {
            logAndExit(title, e);
        }
    }

    private Parent loadViewNodeHierarchy(String fxmlFilePath, ResourceBundle resourceBundle) {
        log.config("loading view node hierarchy");
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath, resourceBundle);
            Objects.requireNonNull(rootNode, "A root FXML node must not be null");
        } catch (Exception e) {
            logAndExit(fxmlFilePath, e);
        }
        return rootNode;
    }

    private Scene prepareScene(Parent rootNode) {
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(rootNode);
        }
        scene.setRoot(rootNode);
        return scene;
    }

    private void logAndExit(String title, Exception e) {
        log.info("Exiting application...");
        Platform.exit();
    }

    public void setMainScene(FXScene mainScene) {
        MAIN_SCENE = mainScene;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        RESOURCE_BUNDLE = resourceBundle;
    }
}
