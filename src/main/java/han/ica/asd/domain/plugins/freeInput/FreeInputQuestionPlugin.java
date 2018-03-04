package han.ica.asd.domain.plugins.freeInput;

import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionType;
import han.ica.asd.domain.interfaces.IQuestionView;
import org.json.simple.JSONObject;

/**
 * TODO Javadoc
 */
public class FreeInputQuestionPlugin implements IPlugin {

    private String pluginID = "MultipleChoice";

    private FreeInputQuestion question;
    private FreeInputQuestionView questionView;


    public FreeInputQuestionPlugin(String questionPhrase, int points, JSONObject context, String questionType) {
        question = new FreeInputQuestion(questionPhrase, points, context, questionType);
        questionView = new FreeInputQuestionView(question);
    }

    public String getPluginID() {
        return pluginID;
    }

    public IQuestionType getQuestion() {
        return question;
    }

    public IQuestionView getQuestionView() {
        return questionView;
    }
}
