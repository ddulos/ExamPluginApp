package han.ica.asd.controllers;

import han.ica.asd.domain.Exam;
import han.ica.asd.domain.Question;
import han.ica.asd.domain.interfaces.IQuestionView;
import han.ica.asd.domain.plugins.freeInput.FreeInputQuestionPlugin;
import han.ica.asd.domain.plugins.freeInput.FreeInputQuestionView;
import han.ica.asd.domain.plugins.multipleChoice.MultipleChoiceQuestionPlugin;
import han.ica.asd.domain.plugins.multipleChoice.MultipleChoiceQuestionView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PerformExamController {

    private static final Logger logger = Logger.getLogger(PerformExamController.class.getName());

    @FXML
    private VBox rootUI;
    @FXML
    private VBox questionsPane;

    void setupPane(Map<String, Object> namespace) throws IOException {
        if (!namespace.isEmpty()) {
            questionsPane = (VBox) namespace.get("questionsPane");
            rootUI = (VBox) namespace.get("rootUI");
            getExam();
        } else {
            logger.log(Level.SEVERE, "Error while retrieving namespace!");
        }
    }

    private void getExam() throws IOException {
        JSONParser jsonParser = new JSONParser();

        try {
            // Retrieving File from resources.
            File jsonFile = new File(getClass().getResource("/json/example.json").toURI());
            FileReader fileReader = new FileReader(jsonFile);

            // Parse json String into JSONObject.
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            String name = (String) jsonObject.get("name");
            String course = (String) jsonObject.get("course");
            JSONArray questionsJsonArray = (JSONArray) jsonObject.get("questions");

            Exam exam = new Exam(name, course);

            List<Question> questionList = new ArrayList<Question>();
            for (Object object : questionsJsonArray) {
                JSONObject questionJson = (JSONObject) object;

                String questionPhrasing = (String) questionJson.get("questionPhrasing");
                int points = Integer.parseInt(String.valueOf(questionJson.get("points")));

                String type = (String) questionJson.get("questionType");
                JSONObject context = (JSONObject) questionJson.get("questionContext");


                logger.log(Level.FINE, "Context: " + context);

                Question question = null;
                IQuestionView questionView = null;

                if (type.equals("freeInput")) {
                    FreeInputQuestionPlugin freeInputQuestionPlugin = new FreeInputQuestionPlugin();
                    question = (Question) freeInputQuestionPlugin.createQuestion(questionPhrasing, points);
                    questionView = freeInputQuestionPlugin.createView();

                } else if (type.equals("multipleChoice")) {
                    MultipleChoiceQuestionPlugin freeInputQuestionPlugin = new MultipleChoiceQuestionPlugin();
                    question = (Question) freeInputQuestionPlugin.createQuestion(questionPhrasing, points);
                    questionView = freeInputQuestionPlugin.createView();

                } else {
                    logger.log(Level.WARNING, "No known type found!");
                }

                if (questionsPane != null && questionView != null) {
                    questionsPane.getChildren().add(questionView.getView(questionPhrasing, points, context));
                } else {
                    logger.log(Level.SEVERE, "Error while adding child to pane!");
                }

                questionList.add(question);
            }
            Question[] questions = new Question[questionList.size()];

            exam.setQuestions(questionList.toArray(questions));

            for (Question question : questions) {
                logger.log(Level.FINE, question.getQuestionPhrase() + " - worth " + question.getPoints() + " points");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) rootUI.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        window.setScene(new Scene(root, 600, 400));
    }
}
