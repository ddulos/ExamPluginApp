package han.ica.asd.domain.plugins.freeInput;

import han.ica.asd.domain.interfaces.IQuestionView;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.*;
import java.util.logging.Logger;

/**
 * TODO Javadoc
 */
public class FreeInputQuestionView implements IQuestionView {
    private static final Logger logger = Logger.getLogger(FreeInputQuestionView.class.getName());

    private static int idNum = 1;

    private Map<String, String> givenAnswers;
    private FreeInputQuestion question;

    public FreeInputQuestionView(FreeInputQuestion question) {
        this.question = question;
        givenAnswers = question.getAnswerMap();
    }

    public Pane getView() {

        int fieldAmount = Integer.parseInt(String.valueOf(question.getContext().get("fieldAmount")));

        VBox box = new VBox();
        box.setSpacing(4);
        Label lblQuestionPhrasing = new Label(question.getQuestionPhrasing() + " - " + question.getPoints() + " points.");
        box.getChildren().add(lblQuestionPhrasing);

        for (int i = 0; i < fieldAmount; i++) {
            final TextField textField = new TextField();
            textField.setId("freeInput-" + idNum++);

            givenAnswers.put(textField.getId(), "");
            textField.textProperty().addListener(getStringChangeListener());
            box.getChildren().add(textField);
//            System.out.println("Textfield created: " + textField.getId());
        }

        return box;
    }

    private ChangeListener<String> getStringChangeListener() {
        return (observable, oldValue, newValue) -> {

            String id = observable.toString().substring(observable.toString().indexOf("id=") + 3, observable.toString().indexOf(","));
//            System.out.println("TextField id " + id);
            givenAnswers.put(id, newValue);
//            System.out.println("TextMap " + givenAnswers);
        };
    }

    public Pane getFeedbackView() {
        return null;
    }

    public Pane getCorrectAnswerView() {
        return null;
    }
}
