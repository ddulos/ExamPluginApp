package han.ica.asd.domain.interfaces;

import javafx.scene.layout.Pane;
import org.json.simple.JSONObject;

public interface IQuestionView {
    Pane getView();
    Pane getFeedbackView();
    Pane getCorrectAnswerView();
}
