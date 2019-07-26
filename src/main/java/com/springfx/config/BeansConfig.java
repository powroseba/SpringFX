package com.springfx.config;

import com.springfx.config.scenes.StageManager;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Configuration
public class BeansConfig {

    private ApplicationContext context;

    public BeansConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public SpringFXMLLoader springFXMLLoader() {
        return new SpringFXMLLoader(context);
    }

    @Bean
    @Lazy(value = true)
    public StageManager stageManager(Stage stage, SpringFXMLLoader springFXMLLoader) throws IOException {
        return new StageManager(stage, springFXMLLoader);
    }
}
