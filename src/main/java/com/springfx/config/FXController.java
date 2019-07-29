package com.springfx.config;

import com.springfx.config.scenes.FXScene;
import com.springfx.config.scenes.StageManager;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public abstract class FXController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    protected void switchScene(FXScene scene) {
        stageManager.switchScene(scene);
    }

    protected void showNewScene(FXScene scene) {
        stageManager.showNewScene(scene);
    }

}
