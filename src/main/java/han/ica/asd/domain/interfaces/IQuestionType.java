package han.ica.asd.domain.interfaces;

import org.json.simple.JSONObject;

/**
 * TODO Javadoc
 */
public interface IQuestionType {
    JSONObject getGivenAnswer();
    int checkGivenAnswer();
}
