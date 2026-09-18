package rs.ac.metropolitan.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import rs.ac.metropolitan.project.di.ApplicationContext;
import rs.ac.metropolitan.project.di.RepositoryModule;
import rs.ac.metropolitan.project.di.WeatherModule;

import java.io.IOException;

/**
 * Agency application
 */
public class AgencyApplication extends Application {
    private AgencyController controller;

    /**
     * Main method
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * Starts the application
     *
     * @param stage stage to start the application on
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void start(Stage stage) throws IOException {
        var context = new ApplicationContext(
                new RepositoryModule(),
                new WeatherModule()
        );

        var fxmlLoader = new FXMLLoader(AgencyApplication.class.getResource("agency-view.fxml"));
        fxmlLoader.setControllerFactory(context::getInstance);
        var scene = new Scene(fxmlLoader.load(), 1024, 768);
        controller = fxmlLoader.getController();

        stage.setTitle("Agency");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Stops the application
     */
    @Override
    public void stop() {
        if (controller != null) {
            controller.stop();
        }
    }
}