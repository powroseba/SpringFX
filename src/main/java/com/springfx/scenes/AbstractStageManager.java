package com.springfx.scenes;

import com.springfx.loader.SpringFxmlLoader;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class which contains all root method to manage stages and scenes in application
 */
abstract class AbstractStageManager implements StageManager {

    private static final Logger log = Logger.getLogger(StageManagerImpl.class.getName());

    private final Stage stage;
    private final SpringFxmlLoader fxmlLoader;
    protected FXScene mainScene;

    public AbstractStageManager(Stage stage, SpringFxmlLoader fxmlLoader) {
        this.stage = stage;
        this.fxmlLoader = fxmlLoader;
    }

    /**
     * Method to close application
     */
    public void closeApplication() {
        log.exiting(getClass().getName(), "closeApplication()", "Closing the application!");
        Platform.exit();
    }

    /**
     * Method to show specific scene
     * @param rootNode main root node of all scenes in application
     * @param fxScene scene information
     */
    final void show(final Parent rootNode, FXScene fxScene) {
        log.config("Showing stage with title : " + fxScene.getTitle());
        Scene scene = prepareScene(rootNode);

        stage.setTitle(fxScene.getTitle());
        prepareSizeOfStage(fxScene, stage);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.centerOnScreen();
        stage.setResizable(fxScene.isResizable());

        try {
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
        log.config("Loading parent of scenes with scene passed in defined fxml file");
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

    final void prepareSizeOfStage(FXScene scene, Stage stage) {
        if (scene.isResizable()) {
            stage.setMaxHeight(scene.getMaxHeight());
            stage.setMinHeight(scene.getMinHeight());
            stage.setMaxWidth(scene.getMaxWidth());
            stage.setMinWidth(scene.getMinWidth());
        }
    }

    Stage getStage() {
        return stage;
    }

    SpringFxmlLoader getFxmlLoader() {
        return fxmlLoader;
    }
}
