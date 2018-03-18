package han.ica.asd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to startup the app and show the main view.
 */
public class App extends Application {

    /**
     * Main class to start the application and show the main view.
     *
     * @param args arguments used for the application that are given by console.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(Stage window) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        window.setTitle("Exam Plugin App");
        window.setScene(new Scene(root, 600, 400));
        window.show();
    }
}
