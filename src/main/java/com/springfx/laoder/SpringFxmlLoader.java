package com.springfx.laoder;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Class which load fxml file and create from this parent to stage
 */
public class SpringFxmlLoader {

    private static final Logger log = Logger.getLogger(SpringFxmlLoader.class.getName());

    private final ApplicationContext context;

    public SpringFxmlLoader(ApplicationContext context) {
        this.context = context;
    }

    /**
     * Method is using {@link FXMLLoader } to load fxml file defined in {@code fxmlPath } parameter
     * @param fxmlPath path to fxml file
     * @param resourceBundle optional resource bundle for controller
     * @return parent for stage created by {@link FXMLLoader }
     * @throws IOException exception in case of lack of fxml file
     */
    public Parent load(String fxmlPath, ResourceBundle resourceBundle) throws IOException {
        log.config("Setting up Spring FXML loader");
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(context::getBean);
        loader.setResources(resourceBundle);
        loader.setLocation(getClass().getResource(fxmlPath));
        return loader.load();
    }
}
