package com.springfx.scenes;

/**
 * Stage manager interface of method to manage scenes
 */
public interface StageManager {

    void displayInitialScene();

    void switchScene(final FXScene scene);

    void showNewScene(final FXScene scene);

    void closeApplication();

}
