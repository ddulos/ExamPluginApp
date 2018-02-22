package han.ica.asd.domain.plugins.freeInput;

import han.ica.asd.domain.interfaces.IQuestionView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.json.simple.JSONObject;

/**
 * TODO Javadoc
 */
public class FreeInputQuestionView implements IQuestionView {
    private static int idNum = 0;

    public Pane getView(String questionPhrasing, int points, JSONObject context) {
        System.out.println("questionPhrasing: " + questionPhrasing);

        int fieldAmount = Integer.parseInt(String.valueOf(context.get("fieldAmount")));

        VBox box = new VBox();
        box.setSpacing(4);
        Label lblQuestionPhrasing = new Label(questionPhrasing + " - " + points + " points.");
        box.getChildren().add(lblQuestionPhrasing);

        for (int i = 0; i < fieldAmount; i++) {
            TextField textField = new TextField();
            textField.setId("freeInput" + idNum++);
            System.out.println("textfield id: " + textField.getId());
            box.getChildren().add(textField);
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
