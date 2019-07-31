package com.springfx.scenes;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class FXController implements Initializable {

    @Lazy
    @Autowired
    private StageManagerImpl stageManager;

    protected void switchScene(FXScene scene) {
        stageManager.getStage();
        stageManager.switchScene(scene);
    }

    protected void showNewScene(FXScene scene) {
        stageManager.showNewScene(scene);
    }

}