package han.ica.asd.domain.plugins.multipleChoice;

import han.ica.asd.domain.interfaces.IQuestionView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.logging.Logger;

public class MultipleChoiceQuestionView implements IQuestionView {
    private static final Logger logger = Logger.getLogger(MultipleChoiceQuestionView.class.getName());

    private static int idNum = 1;

    private Map<String, String> givenAnswer;
    private MultipleChoiceQuestion question;

    MultipleChoiceQuestionView(MultipleChoiceQuestion question) {
        this.question = question;
        givenAnswer = question.getAnswerMap();
    }

    private void setupRadioButtons(VBox box, JSONArray options) {
        final ToggleGroup toggleGroup = getToggleGroup();

        for (Object option : options) {
            JSONObject optionJSON = (JSONObject) option;
            String optionName = (String) optionJSON.get("name");

            RadioButton radioButton = new RadioButton(optionName);
            radioButton.setId("mChoice-" + idNum++);
            radioButton.setToggleGroup(toggleGroup);
            box.getChildren().add(radioButton);

//            System.out.println("Radiobutton created: " + radioButton.getId() + "-" + radioButton.getText());
        }
    }

    private ToggleGroup getToggleGroup() {
        final ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton radioButton = (RadioButton) newValue.getToggleGroup().getSelectedToggle();
//                System.out.println("Value " + givenAnswer.get(radioButton.getId()) + " of " + radioButton.getId() + " has changed to " + radioButton.getText());
                givenAnswer.put(radioButton.getId(), radioButton.getText());
            }
        });
        return toggleGroup;
    }

    private void setupCheckboxes(VBox box, JSONArray options) {
        EventHandler<ActionEvent> eventHandler = getCheckBoxEventHandler();

        for (Object option : options) {
            JSONObject optionJSON = (JSONObject) option;
            String optionName = (String) optionJSON.get("name");

            CheckBox checkBox = new CheckBox(optionName);
            checkBox.setId("mChoice-" + idNum++);
            checkBox.setOnAction(eventHandler);
            box.getChildren().add(checkBox);

//            System.out.println("CheckBox created: " + checkBox.getId() + "-" + checkBox.getText());
        }
    }

    private EventHandler<ActionEvent> getCheckBoxEventHandler() {
        return event -> {
            CheckBox checkBox = (CheckBox) event.getSource();
//            System.out.println("\nID: " + checkBox.getId() + " Text: " + checkBox.getText());

            // Inserts ID to remove from Map.
            String idTag = "";
            for (Map.Entry<String, String> entry : givenAnswer.entrySet()) {
                System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());

                if (entry.getValue().equals(checkBox.getText()) && !checkBox.isSelected()) {
                    idTag = checkBox.getId();
//                    System.out.println("\nValue removed: " + entry);
                }
            }

            if (!idTag.equals("")) {
                givenAnswer.remove(idTag);
                idTag = "";
            }

            if (checkBox.isSelected()) {
                givenAnswer.put(checkBox.getId(), checkBox.getText());
//                System.out.println("\nValue added: " + checkBox.getText());
            }
        };
    }

    public Pane getView() {
        JSONArray options = (JSONArray) question.getContext().get("options");

        VBox box = new VBox();
        Label lblQuestionPhrasing = new Label(question.getQuestionPhrasing() + " - " + question.getPoints() + " punten.");
        box.getChildren().add(lblQuestionPhrasing);

        boolean singleSelection = Boolean.parseBoolean(String.valueOf(question.getContext().get("singleSelection")));

        if (singleSelection) setupRadioButtons(box, options);
        else setupCheckboxes(box, options);

        return box;
    }

    public Pane getFeedbackView() {
        return null;
    }

    public Pane getCorrectAnswerView() {
        return null;
    }
}
