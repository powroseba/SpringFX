package com.springfx;

import com.springfx.config.ApplicationBeanConfigurer;
import com.springfx.config.ApplicationContextConfigurer;
import com.springfx.scenes.FXScene;
import com.springfx.scenes.StageManagerImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.logging.Logger;

public class SpringFXRunner extends Application {

    private static final Logger log = Logger.getLogger(SpringFXRunner.class.getName());

    private ConfigurableApplicationContext applicationContext;
    private static Class FX_RUNNER_CLASS;
    private static FXScene MAIN_SCENE;

    public static void run(Class runnerClass, FXScene mainScene, String[] args) throws IOException {
        log.info("Running SpringFX application!");
        validateParameters(runnerClass, mainScene);
        FX_RUNNER_CLASS = runnerClass;
        MAIN_SCENE = mainScene;
        Application.launch(SpringFXRunner.class, args);
    }

    private static void validateParameters(Class runnerClass, FXScene mainScene) throws IOException {
        if (runnerClass == null) throw new IOException("Runner class cannot be null!");
        if (mainScene == null) throw new IOException("Main scene cannot be null!");
        if (mainScene.getFxmlFilePath().isEmpty() || mainScene.getTitle().isEmpty())
            throw new IOException("Scene file path or title cannot be empty String!");
    }

    @Override
    public void init() {
        log.config("Setting up context");
        applicationContext = ApplicationContextConfigurer.configure(this, FX_RUNNER_CLASS);
        log.config("Setting up initialized");
    }

    @Override
    public void start(Stage stage) {
        ApplicationBeanConfigurer.configure(applicationContext, stage);
        StageManagerImpl stageManagerBean = applicationContext.getBean(StageManagerImpl.class);
        log.config("Setting up main scene for stage manager");
        stageManagerBean.setMainScene(MAIN_SCENE);
        stageManagerBean.displayInitialScene();
    }

    @Override
    public void stop() {
        log.info("Stopping application");
        applicationContext.close();
        Platform.exit();
    }
}
