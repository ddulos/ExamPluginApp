package han.ica.asd.controllers;

import han.ica.asd.domain.Exam;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.misc.IOUtils;

import java.io.*;

import static java.lang.System.out;

/**
 * TODO Javadoc
 */
public class PerformExamController {
    public VBox mainPane;
    private String fileName = "json/file1.json";

    public PerformExamController() {
        getExam();
    }

    private void getExam() {
        JSONParser jsonParser = new JSONParser();

        try {
            File jsonFile = new File(getClass().getResource("/json/example.json").toURI());

            FileReader fileReader = new FileReader(jsonFile);

            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            String name = (String) jsonObject.get("name");
            String course = (String) jsonObject.get("course");
            JSONArray questionList = (JSONArray) jsonObject.get("questions");

            Exam exam = new Exam(name, course);

            out.println("Name: " + name);
            out.println("Course: " + course);
            out.println("\nQuestions:");
            for (int i = 0; i < questionList.size(); i++) {
                JSONObject questionJson = (JSONObject) questionList.get(i);

                String questionPhrasing = (String) questionJson.get("questionPhrasing");
                int points = Integer.valueOf(String.valueOf(questionJson.get("points")));
                out.println(questionPhrasing + " - worth " + points + " points");

//                Question question = new Question();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) mainPane.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        window.setScene(new Scene(root, 600, 400));
    }
}
