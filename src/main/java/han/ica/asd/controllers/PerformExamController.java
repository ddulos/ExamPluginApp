package han.ica.asd.controllers;

import han.ica.asd.domain.Exam;
import han.ica.asd.domain.AbstractQuestion;
import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionView;
import han.ica.asd.utility.PluginHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the PerformExam view.
 * <p>
 * Here the user can perform on an exam where the questions use dynamically loaded plugins for each type of question.
 * <p>
 * If the user finishes the exam, than the user can press on a button to convert it to JSON text.
 */
public class PerformExamController {

    private static final Logger logger = Logger.getLogger(PerformExamController.class.getName());

    @FXML
    private VBox rootUI;

    @FXML
    private VBox questionsPane;

    private PluginHandler pluginHandler = new PluginHandler();
    private Exam currentExam;

    /**
     * Setup the Pane for starting the exam. Namespace makes sure that the questionsPane and root ui can be used.
     * <p>
     * Loads the available plugins into HashMap which will be used while creating the exam and the questions.
     *
     * @param namespace A set of elements that contains in the main Pane of the view.
     */
    void setupPane(Map<String, Object> namespace) {
        try {
            if (!namespace.isEmpty()) {
                questionsPane = (VBox) namespace.get("questionsPane");
                rootUI = (VBox) namespace.get("rootUI");
                pluginHandler.loadPlugins();
                getExam();
            } else {
                logger.log(Level.SEVERE, "Error while retrieving namespace!");
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Something happened while setting up the pane.", ex);
        }
    }

    /**
     * Get current exam from json file.
     */
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
            currentExam.setQuestions(getQuestions(questionsJsonArray));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create questions that are present in the exam based on the loaded plugins.
     *
     * @param questionsJsonArray JSON array containing data of each question present in the exam.
     * @return Array of domain questions present in the exam.
     */
    private AbstractQuestion[] getQuestions(JSONArray questionsJsonArray) {
        List<AbstractQuestion> questionList = new ArrayList<>();

        for (Object object : questionsJsonArray) {
            JSONObject questionJson = (JSONObject) object;
            String questionType = (String) questionJson.get("questionType");

            AbstractQuestion question = null;
            IQuestionView questionView = null;
            try {
                IPlugin plugin = pluginHandler.getLoadedPlugins().get(questionType);
                question = plugin.createQuestion(questionJson);
                questionView = plugin.createQuestionView(question);

            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Something happened while creating questions.", ex);
            }

            if (questionsPane != null && questionView != null) {
                questionsPane.getChildren().add(questionView.getExamPane());
            } else {
                logger.log(Level.SEVERE, "Error while adding child to pane!");
            }

            questionList.add(question);
        }
        AbstractQuestion[] questions = new AbstractQuestion[questionList.size()];

        return questionList.toArray(questions);
    }

    /**
     * Return back to menu.
     */
    public void backToMenu() throws IOException {
        Stage window = (Stage) rootUI.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();

        window.setScene(new Scene(root, 600, 400));
    }

    /**
     * Convert givenanswer from each question in the exam to JSON string.
     */
    @SuppressWarnings("unchecked")
    public void convertToText() {
        JSONObject examJSON = new JSONObject();
        examJSON.put("name", currentExam.getName());
        examJSON.put("course", currentExam.getCourse());

        JSONArray questionsJSONArray = new JSONArray();
        for (AbstractQuestion question : currentExam.getQuestions()) {
            JSONObject questionJSON = new JSONObject();

            questionJSON.put("questionType", question.getQuestionType());
            questionJSON.put("questionPhrasing", question.getQuestionPhrasing());
            questionJSON.put("givenAnswer", question.getGivenAnswer());
            questionsJSONArray.add(questionJSON);
        }

        examJSON.put("questions", questionsJSONArray);

        logger.log(Level.INFO, "JSON printed from converted answer: \n" + examJSON.toJSONString());
    }
}




