package com.springfx.config;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SpringFXMLLoader {

    private static final Logger log = Logger.getLogger(SpringFXMLLoader.class.getName());

    private final ApplicationContext context;

    public SpringFXMLLoader(ApplicationContext context) {
        this.context = context;
    }

    public Parent load(String fxmlPath, ResourceBundle resourceBundle) throws IOException {
        log.config("Setting up Spring FXML loader");
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(param -> context.getBean(param));
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}
