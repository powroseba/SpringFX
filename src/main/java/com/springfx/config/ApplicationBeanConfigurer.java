package com.springfx.config;

import com.springfx.laoder.SpringFxmlLoader;
import com.springfx.scenes.StageManagerImpl;
import javafx.stage.Stage;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ApplicationBeanConfigurer {

    private static final Logger log = Logger.getLogger(ApplicationBeanConfigurer.class.getName());

    public static void configure(ConfigurableApplicationContext applicationContext, Stage stage) {
        SpringFxmlLoader loader = new SpringFxmlLoader(applicationContext);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton(loader.getClass().getCanonicalName(), loader);
        StageManagerImpl stageManager = new StageManagerImpl(stage, loader);
        beanFactory.registerSingleton(stageManager.getClass().getCanonicalName(), stageManager);
        setResourceBundle(stageManager, applicationContext);
    }

    private static void setResourceBundle(StageManagerImpl stageManager, ConfigurableApplicationContext applicationContext) {
        try {
            stageManager.setResourceBundle(applicationContext.getBean(ResourceBundle.class));
            log.config("Resource bundle setup");
        } catch (NoSuchBeanDefinitionException e) {
            log.info("No Resource bundle defined!");
        }
    }
}
