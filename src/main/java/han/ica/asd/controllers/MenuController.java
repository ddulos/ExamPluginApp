package han.ica.asd.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

import static java.lang.System.out;

/**
 * Controller for the menu view, where the user can choose one of the three views.
 */
public class MenuController {

    @FXML
    public VBox mainPane;

    /**
     * When pressed, the perform exam view will be opened and the user can perform an exam.
     */
    public void performExamTest() throws IOException {
        Stage window = (Stage) mainPane.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/performexam.fxml"));

        Parent root = loader.load();

        Map<String, Object> namespace = loader.getNamespace();

        PerformExamController performExamController = loader.getController();
        performExamController.setupPane(namespace);

        window.setScene(new Scene(root, 600, 400));
    }

    /**
     * Unavailable option - When pressed would open the view to check an exam.
     */
    public void checkExamTest() {
        out.println("Not available.");
    }

    /**
     * Unavailable option - When pressed would open the view to review an exam.
     */
    public void reviewExamTest() {
        out.println("Not available.");
    }
}
