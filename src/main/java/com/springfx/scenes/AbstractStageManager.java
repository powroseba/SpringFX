package com.springfx.scenes;

import com.springfx.laoder.SpringFxmlLoader;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

abstract class AbstractStageManager implements StageManager {

    private static final Logger log = Logger.getLogger(StageManagerImpl.class.getName());

    private final Stage stage;
    private final SpringFxmlLoader fxmlLoader;

    public AbstractStageManager(Stage stage, SpringFxmlLoader fxmlLoader) {
        this.stage = stage;
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Method to close application
     */
    public void closeApplication() {
        Platform.exit();
    }

    /**
     * Method to show specific scene
     * @param rootNode main root node of all scenes in application
     * @param fxScene scene information
     */
    final void show(final Parent rootNode, FXScene fxScene) {
        Scene scene = prepareScene(rootNode);

        stage.setTitle(fxScene.getTitle());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(fxScene.isResizable());
        stage.centerOnScreen();

        try {
            stage.toFront();
            stage.show();
        } catch (Exception e) {
            logAndExit("There was a problem in showing scene with title : " + fxScene.getTitle(), e);
        }
    }

    /**
     * Method to load parent of scenes with scenes defined in fxml file which exist in path passed in fxmlFilePath parameter
     * @param fxmlFilePath path to fxml file of scene
     * @param resourceBundle resource with translations
     * @return
     */
    final Parent loadViewNodeHierarchy(String fxmlFilePath, ResourceBundle resourceBundle) {
        log.config("loading parent of scenes with scene passed in defined fxml file");
        Parent globalSceneParent = null;
        try {
            globalSceneParent = fxmlLoader.load(fxmlFilePath, resourceBundle);
            Objects.requireNonNull(globalSceneParent, "A loaded scenes parent cannot be null, check fxml path");
        } catch (Exception e) {
            logAndExit("There was a problem to load fxml file in path : " + fxmlFilePath, e);
        }
        return globalSceneParent;
    }

    /**
     * Method which is preparing scene defined in globalSceneParent
     * @param globalSceneParent global scene instance
     * @return prepared scene to show
     */
    private Scene prepareScene(Parent globalSceneParent) {
        Scene scene = stage.getScene();

        if (scene == null) {
            scene = new Scene(globalSceneParent);
        }
        scene.setRoot(globalSceneParent);
        return scene;
    }

    /**
     * Method which log the problem with application and close Platform of FX application
     * @param message of exception
     * @param exception instance
     */
    private void logAndExit(String message, Exception exception) {
        log.exiting(getClass().getSimpleName(), message, exception);
        closeApplication();
    }

//    Stage prepareStage(FXScene scene, Stage stage) {
//        stage.setTitle(scene.getTitle());
//        stage.setResizable(scene.isResizable());
//        stage.setMaxHeight(scene.getMaxHeight());
//        stage.setMinHeight(scene.getMin);
//    }

    Stage getStage() {
        return stage;
    }

    SpringFxmlLoader getFxmlLoader() {
        return fxmlLoader;
    }
}
