package han.ica.asd.domain.interfaces;

import javafx.scene.layout.Pane;

/**
 * Interface used to get the view used by the question of a plugin.
 */
public interface IQuestionView {
    /**
     * @return The Pane that contains the question itself with input possibilities to answer the question.
     */
    Pane getView();

    /**
     * @return The Pane that contains the question itself with possibilities to give feedback about an given answer on the question.
     */
    Pane getFeedbackView();

    /**
     * @return The Pane that contains the question itself with the correct answer shown and the possibility to review the given answer.
     */
    Pane getCorrectAnswerView();
}
