package com.springfx;

import com.springfx.config.SpringFXMLLoader;
import com.springfx.config.scenes.FXScene;
import com.springfx.config.scenes.StageManager;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SpringFXRunner extends Application {

    private static final Logger log = Logger.getLogger(SpringFXRunner.class.getName());

    private ConfigurableApplicationContext springContext;
    private static Class FX_RUNNER_CLASS;
    private static FXScene MAIN_SCENE;

    public static void run(Class runnerClass, FXScene mainScene, String[] args) throws IOException {
        log.info("Running SpringFX application!");
        validateParameters(runnerClass, mainScene);
        if (FX_RUNNER_CLASS == null) {
            FX_RUNNER_CLASS = runnerClass;
        }
        if (MAIN_SCENE == null) {
            MAIN_SCENE = mainScene;
        }
        Application.launch(SpringFXRunner.class, args);
    }

    private static void validateParameters(Class runnerClass, FXScene mainScene) throws IOException {
        if (runnerClass == null) throw new IOException("Runner class cannot be null!");
        if (mainScene == null) throw new IOException("Main scene cannot be null!");
        if (mainScene.getFXMLFilePath().isEmpty() || mainScene.getTitle().isEmpty())
            throw new IOException("Scene file path or title cannot be empty String!");
    }

    @Override
    public void init() {
        log.config("Setting up context");
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext -> {
            genericApplicationContext.registerBean(Application.class, () -> SpringFXRunner.this);
            genericApplicationContext.registerBean(Parameters.class, this::getParameters);
            genericApplicationContext.registerBean(HostServices.class, this::getHostServices);
        };

        springContext = new SpringApplicationBuilder()
                .initializers(initializer)
                .sources(FX_RUNNER_CLASS)
                .run(getParameters().getRaw().toArray(new String[0]));
        log.config("Setting up initialized");
    }

    @Override
    public void start(Stage stage) {
        SpringFXMLLoader loader = new SpringFXMLLoader(springContext);
        ConfigurableListableBeanFactory beanFactory = springContext.getBeanFactory();
        beanFactory.registerSingleton(loader.getClass().getCanonicalName(), loader);
        StageManager stageManager = new StageManager(stage, loader);
        beanFactory.registerSingleton(stageManager.getClass().getCanonicalName(), stageManager);
        StageManager stageManagerBean = springContext.getBean(StageManager.class);
        log.config("Setting up stage manager ");
        setResourceBundle(stageManagerBean);
        stageManagerBean.setMainScene(MAIN_SCENE);
        stageManagerBean.displayInitialScene();
    }

    private void setResourceBundle(StageManager stageManager) {
        try {
            stageManager.setResourceBundle(springContext.getBean(ResourceBundle.class));
            log.config("Resource bundle setup");
        } catch (NoSuchBeanDefinitionException e) {
            log.info("No Resource bundle defined!");
        }
    }

    @Override
    public void stop() throws Exception {
        log.info("Stopping application");
        springContext.close();
        Platform.exit();
    }
}
