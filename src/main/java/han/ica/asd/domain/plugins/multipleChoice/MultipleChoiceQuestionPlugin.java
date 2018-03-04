package han.ica.asd.domain.plugins.multipleChoice;

import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionType;
import han.ica.asd.domain.interfaces.IQuestionView;
import org.json.simple.JSONObject;

/**
 * TODO Javadoc
 */
public class MultipleChoiceQuestionPlugin implements IPlugin {

    private String pluginID = "MultipleChoice";

    private MultipleChoiceQuestion question;
    private MultipleChoiceQuestionView questionView;


    public MultipleChoiceQuestionPlugin(String questionPhrase, int points, JSONObject context, String questionType) {
        question = new MultipleChoiceQuestion(questionPhrase, points, context, questionType);
        questionView = new MultipleChoiceQuestionView(question);
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
