package han.ica.asd.domain.plugins.multipleChoice;

import han.ica.asd.domain.interfaces.IQuestionView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * TODO Javadoc
 */
public class MultipleChoiceQuestionView implements IQuestionView {
    private static int idNum = 0;

    public Pane getView(String questionPhrasing, int points, JSONObject context) {
        VBox box = new VBox();
        Label lblQuestionPhrasing = new Label(questionPhrasing + " - " + points + " punten.");
        box.getChildren().add(lblQuestionPhrasing);

        boolean singleSelection = Boolean.parseBoolean(String.valueOf(context.get("singleSelection")));
        System.out.println("singleSelection: " + singleSelection);

        JSONArray options = (JSONArray) context.get("options");

        final ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

            }
        });

        for (Object option : options) {
            JSONObject optionJSON = (JSONObject) option;
            String optionName = (String) optionJSON.get("name");

            if (singleSelection) {
                RadioButton radioButton = new RadioButton(optionName);
                radioButton.setId("mChoice-" + idNum++);
                radioButton.setToggleGroup(toggleGroup);

                System.out.println("Radiobutton ID: " + radioButton.getId());

                box.getChildren().add(radioButton);
            } else {
                CheckBox checkBox = new CheckBox(optionName);
                checkBox.setId("mChoice-" + idNum++);
            }
        }

        return box;
    }

    public Pane getFeedbackView() {
        return null;
    }

    public Pane getCorrectAnswerView() {
        return null;
    }
}
