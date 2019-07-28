package com.springfx.config;

import com.springfx.config.scenes.FXScene;
import com.springfx.config.scenes.StageManager;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;

public abstract class FXController implements Initializable {

    @Autowired
    private ApplicationContext context;
    @Lazy
    @Autowired
    private StageManager stageManager;

    protected void switchScene(FXScene scene) {
        stageManager.switchScene(scene);
    }

}
