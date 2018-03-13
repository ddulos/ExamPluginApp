package han.ica.asd.domain.interfaces;

import org.json.simple.JSONObject;

public interface IQuestionType {
    JSONObject getGivenAnswer();
    int checkGivenAnswer();
}
