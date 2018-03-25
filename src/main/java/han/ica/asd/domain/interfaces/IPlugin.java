package han.ica.asd.domain.interfaces;

import han.ica.asd.domain.AbstractQuestion;
import org.json.simple.JSONObject;

/**
 * Interface to implement questiontype plugins.
 */
public interface IPlugin {
    /**
     * Getter for property 'pluginID'.
     *
     * @return The ID that represents the questiontype used by the application to select the correct plugin.
     */
    String getPluginID();

    /**
     * Create a new question from given parameters. Which is used by the plugin to create a question from the defined questiontype.
     *
     * @param questionJSON The JSONObject that contains a question of an exam.
     * @return The newly created question based of the defined questiontype.
     */
    AbstractQuestion createQuestion(JSONObject questionJSON);

    /**
     * Here the view for the question will be created, which the user can use to answer the question.
     *
     * @param question The question used in the exam based of the defined questiontype.
     * @return The view of the question with input possibilities.
     */
    IQuestionView createQuestionView(AbstractQuestion question);
}
