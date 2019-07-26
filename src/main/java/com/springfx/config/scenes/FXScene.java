package com.springfx.config.scenes;

/**
 * Every scene should implement this methods to give {@link StageManager} possibility to switch scenes
 */
public interface FXScene {

    String getTitle();

    /**
     * Method to return path to template file
     * @return path to template file
     */
    String getFXMLFilePath();
}
