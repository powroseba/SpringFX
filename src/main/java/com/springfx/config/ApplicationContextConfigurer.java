package com.springfx.config;

import com.springfx.SpringFXRunner;
import javafx.application.Application;
import javafx.application.HostServices;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class ApplicationContextConfigurer {

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
