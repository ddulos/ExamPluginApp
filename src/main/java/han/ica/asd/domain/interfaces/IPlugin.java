package han.ica.asd.domain.interfaces;

import han.ica.asd.domain.Question;
import org.json.simple.JSONObject;

public interface IPlugin {
    String getPluginID();

    IQuestionType createQuestion(String questionPhrase, int points, JSONObject context, String questionType);

    IQuestionView createQuestionView(Question question);
}
