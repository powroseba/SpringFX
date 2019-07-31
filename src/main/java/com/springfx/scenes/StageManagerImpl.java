package com.springfx.scenes;

import com.springfx.laoder.SpringFxmlLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class to manage with stage in FX application
 * Class is inject in spring context as singleton to it can be inject by {@code @Autowired } and {@code @Lazy} because
 * stage property is setting after spring context start up
 */
public class StageManagerImpl extends AbstractStageManager {

    private static final Logger log = Logger.getLogger(StageManagerImpl.class.getName());
    private static final HashMap<String, Stage> stageCollection = new HashMap<>();

    private FXScene mainScene;
    private ResourceBundle resourceBundle;

    public StageManagerImpl(Stage stage, SpringFxmlLoader springFXMLLoader) {
        super(stage, springFXMLLoader);
    }

    /**
     * Method to display initial stage passed in {@code mainScene} property
     */
    @Override
    public void displayInitialScene() {
        log.info("Displaying main page");
        stageCollection.put(mainScene.identifier(), getStage());
        getStage().setOnCloseRequest(event -> closeApplication());
        switchScene(mainScene);
    }

    /**
     * Method do change current scene
     * @param scene information
     */
    @Override
    public void switchScene(final FXScene scene) {
        log.info("Switching scene to " + scene.getTitle());
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(scene.getFxmlFilePath(), resourceBundle);
        show(viewRootNodeHierarchy, scene);
    }

    /**
     * Method to show new scene, it use stageCollection to avoid stage duplication in case of {@code isAlwaysNewWindow }
     * property false
     * @param scene parameter which define scene and fxml path to FXScene definition
     */
    @Override
    public void showNewScene(final FXScene scene) {
        log.info("Showing new scene : " + scene.getTitle());
        Stage stage = stageCollection.get(scene.identifier());
        if (stage == null || scene.isAlwaysNewScene()) {
            stage = new Stage();
            Parent sceneParent = loadViewNodeHierarchy(scene.getFxmlFilePath(), resourceBundle);
            stage.setTitle(scene.getTitle());
            stage.setScene(new Scene(sceneParent));
            stageCollection.put(scene.identifier(), stage);
        } else {
            switchScene(scene);
        }
        stage.show();
    }

    /**
     * Mutator to change main scene if it necessary
     * @param mainScene instance
     */
    public void setMainScene(FXScene mainScene) {
        this.mainScene = mainScene;
    }

    /**
     * Mutator to change global resourceBundle
     * @param resourceBundle instance
     */
    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }
}
