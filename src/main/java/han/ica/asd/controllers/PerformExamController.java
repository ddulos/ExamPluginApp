package han.ica.asd.controllers;

import han.ica.asd.domain.Exam;
import han.ica.asd.domain.Question;
import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionView;
import han.ica.asd.domain.plugins.freeInput.FreeInputQuestionPlugin;
import han.ica.asd.domain.plugins.multipleChoice.MultipleChoiceQuestionPlugin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
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

    private Exam currentExam;
    private HashMap<String, Class> pluginsMap;

    void setupPane(Map<String, Object> namespace) throws IOException {
        if (!namespace.isEmpty()) {
            questionsPane = (VBox) namespace.get("questionsPane");
            rootUI = (VBox) namespace.get("rootUI");
            getExam();

            for (Node node : getAllNodes(questionsPane)) {
//                System.out.println("Node ID: " + node.getId());
            }
        } else {
            logger.log(Level.SEVERE, "Error while retrieving namespace!");
        }
    }

    private static List<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendants(root, nodes);
        return nodes;
    }

    private static void addAllDescendants(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node.getId() != null)
                nodes.add(node);
            if (node instanceof Parent)
                addAllDescendants((Parent) node, nodes);
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

            currentExam = new Exam(name, course);
            currentExam.setQuestions(getQuestions(questionsJsonArray, currentExam));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Question[] getQuestions(JSONArray questionsJsonArray, Exam exam) {
        List<Question> questionList = new ArrayList<>();
        pluginsMap = new HashMap<>();

        for (Object object : questionsJsonArray) {
            JSONObject questionJson = (JSONObject) object;

            String questionPhrasing = (String) questionJson.get("questionPhrasing");
            int points = Integer.parseInt(String.valueOf(questionJson.get("points")));

            String questionType = (String) questionJson.get("questionType");
            JSONObject context = (JSONObject) questionJson.get("questionContext");

            Question question = null;
            IQuestionView questionView = null;

            pluginsMap.put(questionType, IPlugin.class);


            if (questionType.equals("freeInput")) {
                FreeInputQuestionPlugin freeInputQuestionPlugin = new FreeInputQuestionPlugin(questionPhrasing, points, context, questionType);
                question = (Question) freeInputQuestionPlugin.getQuestion();
                questionView = freeInputQuestionPlugin.getQuestionView();

            } else if (questionType.equals("multipleChoice")) {
                MultipleChoiceQuestionPlugin multipleChoiceQuestionPlugin = new MultipleChoiceQuestionPlugin(questionPhrasing, points, context, questionType);
                question = (Question) multipleChoiceQuestionPlugin.getQuestion();
                questionView = multipleChoiceQuestionPlugin.getQuestionView();

            } else {
                logger.log(Level.WARNING, "No known type found!");
            }

            if (questionsPane != null && questionView != null) {
                questionsPane.getChildren().add(questionView.getView());
            } else {
                logger.log(Level.SEVERE, "Error while adding child to pane!");
            }

            questionList.add(question);
        }
        Question[] questions = new Question[questionList.size()];

        return questionList.toArray(questions);
    }

    public void backToMenu(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) rootUI.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        window.setScene(new Scene(root, 600, 400));
    }

    @SuppressWarnings("unchecked")
    public void convertToText(ActionEvent actionEvent) {
        int questionNum = 0;

        JSONObject examJSON = new JSONObject();
        examJSON.put("name", currentExam.getName());
        examJSON.put("course", currentExam.getCourse());

        JSONArray questionsJSONArray = new JSONArray();
        for (Question question : currentExam.getQuestions()) {
//            System.out.println("Question " + ++questionNum + " - points: " + question.getPoints() + "\n :phrasing: " + question.getQuestionPhrasing() + " questionType: " + question.getQuestionType());
            JSONObject questionJSON = new JSONObject();

            questionJSON.put("questionType", question.getQuestionType());
            questionJSON.put("questionPhrasing", question.getQuestionPhrasing());
            questionJSON.put("givenAnswer", question.getGivenAnswer());
            questionsJSONArray.add(questionJSON);
        }

        examJSON.put("questions", questionsJSONArray);


        System.out.println(examJSON.toJSONString());
//
//        for (HashMap.Entry<String, Class> entry : pluginsMap.entrySet()) {
//            System.out.println("Key: " + entry.getKey() + " - Value: " + entry.getValue());
//        }
    }
}
