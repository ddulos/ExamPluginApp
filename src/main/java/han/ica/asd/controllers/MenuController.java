package han.ica.asd.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

import static java.lang.System.out;

public class MenuController {

    public VBox mainPane;

    public void performExamTest(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mainPane.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/performexam.fxml"));
        Parent root = loader.load();

        window.setScene(new Scene(root, 600, 400));
    }

    public void checkExamTest(ActionEvent actionEvent) throws IOException {
       out.println("Not available.");
    }

    public void reviewExamTest(ActionEvent actionEvent) throws IOException {
        out.println("Not available.");
    }
}
