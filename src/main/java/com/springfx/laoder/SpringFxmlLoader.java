package com.springfx.laoder;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class SpringFxmlLoader {

    private static final Logger log = Logger.getLogger(SpringFxmlLoader.class.getName());

    private final ApplicationContext context;

    public SpringFxmlLoader(ApplicationContext context) {
        this.context = context;
    }

    public Parent load(String fxmlPath, ResourceBundle resourceBundle) throws IOException {
        log.config("Setting up Spring FXML loader");
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}
