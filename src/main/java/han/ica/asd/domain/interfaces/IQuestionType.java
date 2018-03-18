package han.ica.asd.domain.interfaces;

import org.json.simple.JSONObject;

/**
 * Interface used for the question types used by plugins.
 */
public interface IQuestionType {
    /**
     * Getter for property 'givenAnswer'.
     *
     * @return Answer given by the user converted from the Pane.
     */
    JSONObject getGivenAnswer();

    /**
     * Check the given answer with the correct answer.
     *
     * @return Points given based on the given answer.
     */
    int checkGivenAnswer();
}
