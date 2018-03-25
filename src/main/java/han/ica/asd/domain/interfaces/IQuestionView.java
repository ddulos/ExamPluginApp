package han.ica.asd.domain.interfaces;

import javafx.scene.layout.Pane;

/**
 * Interface used to get the view used by the question of a plugin.
 */
public interface IQuestionView {
    /**
     * @return The Pane that contains the question itself with input possibilities to answer the question.
     */
    Pane getExamPane();

    /**
     * @return The Pane that contains the question itself with the possibility to check an exam and give feedback about an given answer on the question.
     */
    Pane getCheckExamPane();

    /**
     * @return The Pane that contains the question itself with the possibility to review the given answer.
     */
    Pane getReviewPane();
}
