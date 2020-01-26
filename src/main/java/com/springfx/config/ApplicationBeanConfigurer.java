package com.springfx.config;

import com.springfx.loader.SpringFxmlLoader;
import com.springfx.scenes.StageManagerImpl;
import javafx.stage.Stage;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class to configure all necessary beans which are used in whole runtime of application to manage scenes and stages
 */
public class ApplicationBeanConfigurer {

    private static final Logger log = Logger.getLogger(ApplicationBeanConfigurer.class.getName());

    /**
     * Configuration method of {@link SpringFxmlLoader } bean and {@link com.springfx.scenes.StageManager } bean
     * with optional resource bundle
     *
     * @param applicationContext spring context to register beans
     * @param stage              primary stage of fx application to register it in {@link com.springfx.scenes.StageManager }
     */
    public static void configure(ConfigurableApplicationContext applicationContext, Stage stage) {
        log.config("Setting up application beans");
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
            log.config("No Resource bundle defined!");
        }
    }
}
