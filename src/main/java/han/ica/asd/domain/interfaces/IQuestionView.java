package han.ica.asd.domain.interfaces;

import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;

/**
 * TODO Javadoc
 */
public interface IQuestionView {
    Pane getView(String questionPhrasing, int points, JSONObject context);
    Pane getFeedbackView();
    Pane getCorrectAnswerView();
}
