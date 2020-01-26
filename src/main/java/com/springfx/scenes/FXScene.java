package com.springfx.scenes;

import org.springframework.lang.NonNull;

import java.util.UUID;

/**
 * Every scene should implement this methods to give {@link StageManagerImpl } possibility to switch scenes
 */
public interface FXScene {

    String IDENTIFIER_DELIMITER = ":";
    String IDENTIFIER_NAME_DELIMITER = "__";

    @NonNull
    String getTitle();

    /**
     * Method to return path to template file
     * @return path to template file
     */
    @NonNull
    String getFxmlFilePath();

    /**
     * Always new scene define that in every show operation of specific scene,
     * that should be show in new window or always in the same window
     * @return true if scene should be always new scene
     */
    boolean isAlwaysNewScene();

    boolean isResizable();
    int getMaxWidth();
    int getMaxHeight();
    int getMinWidth();
    int getMinHeight();

    /**
     * Method to generate identifier, unique for scene with true in {@code isAlwaysNewScene() }
     * @return identifier to inject in stage collection
     */
    default String identifier() {
        String randomSuffixInCaseOfAlwaysNewScene = "";
        if (isAlwaysNewScene()) {
            randomSuffixInCaseOfAlwaysNewScene = UUID.randomUUID().toString();
        }
        return getTitle() + IDENTIFIER_NAME_DELIMITER + randomSuffixInCaseOfAlwaysNewScene + IDENTIFIER_DELIMITER + getFxmlFilePath();
    }
}
