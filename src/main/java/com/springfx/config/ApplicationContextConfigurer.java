package com.springfx.config;

import com.springfx.SpringFXRunner;
import javafx.application.Application;
import javafx.application.HostServices;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

/**
 * Class to configure and tie together spring context and java fx application context
 */
public class ApplicationContextConfigurer {

    /**
     * Method which define java fx application context and build spring context. In next step {@link SpringApplicationBuilder }
     * is tying together two create context so that they can cooperate with each other
     * @param fxRunner java fx application runner class
     * @param runnerClass which is main spring boot class of context
     * @return configured and tied together two context in spring context
     */
    public static ConfigurableApplicationContext configure(SpringFXRunner fxRunner, Class runnerClass) {
        ApplicationContextInitializer<GenericApplicationContext> initializer = genericApplicationContext -> {
            genericApplicationContext.registerBean(Application.class, () -> fxRunner);
            genericApplicationContext.registerBean(Application.Parameters.class, fxRunner::getParameters);
            genericApplicationContext.registerBean(HostServices.class, fxRunner::getHostServices);
        };
        return new SpringApplicationBuilder()
                .initializers(initializer)
                .sources(runnerClass)
                .run(fxRunner.getParameters().getRaw().toArray(new String[0]));
    }
}
