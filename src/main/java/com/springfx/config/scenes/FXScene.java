package com.springfx.config.scenes;

import java.util.List;
import java.util.UUID;

/**
 * Every scene should implement this methods to give {@link StageManager} possibility to switch scenes
 */
public interface FXScene {

    String IDENTIFIER_DELIMITER = ":";

    String getTitle();

    /**
     * Method to return path to template file
     * @return path to template file
     */
    String getFXMLFilePath();

    /**
     * Always new scene define that in every show operation of specific scene,
     * that should be show in new window or always in the same window
     * @return
     */
    boolean isAlwaysNewScene();

    /**
     * Method to return all declared scenes in application
     * @return list of FXScene
     */
    List<FXScene> getAllScenes();

    default String identifier() {
        String randomSuffixInCaseOfAlwaysNewScene = "";
        if (isAlwaysNewScene()) {
            randomSuffixInCaseOfAlwaysNewScene = UUID.randomUUID().toString();
        }
        return getTitle() + randomSuffixInCaseOfAlwaysNewScene + IDENTIFIER_DELIMITER + getFXMLFilePath();
    }
}
