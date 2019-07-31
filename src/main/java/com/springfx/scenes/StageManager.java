package com.springfx.scenes;

public interface StageManager {

    void displayInitialScene();

    void switchScene(final FXScene scene);

    void showNewScene(final FXScene scene);

    void closeApplication();

}
