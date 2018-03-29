package han.ica.asd.domain.interfaces;

import org.json.simple.JSONObject;

/**
 * Interface used for calling functions for a certain question.
 */
public interface IQuestion {

    /**
     * Converts the given answer from the input into an JSONObject.
     *
     * @return Answer given by the user converted from the Pane.
     */
    public abstract JSONObject getGivenAnswer();

    /**
     * Check the given answer with the correct answer.
     *
     * @return Points given based on the given answer.
     */
    public abstract int checkGivenAnswer();
}
