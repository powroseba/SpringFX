package com.springfx.scenes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class SceneLoader {

    @Lazy
    @Autowired
    private StageManagerImpl stageManager;

    public void showNewScene(FXScene scene) {
        stageManager.showNewScene(scene);
    }

    public void switchScene(FXScene scene) {
        stageManager.switchScene(scene);
    }
}
