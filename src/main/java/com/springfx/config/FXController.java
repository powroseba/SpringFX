package com.springfx.config;

import com.springfx.config.scenes.FXScene;
import com.springfx.config.scenes.StageManager;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public abstract class FXController implements Initializable {

    @Autowired
    private ApplicationContext context;

    protected void switchScene(FXScene scene) {
        StageManager stageManager = context.getBean(StageManager.class);
        stageManager.switchScene(scene);
    }

}
