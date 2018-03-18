package han.ica.asd.domain.interfaces;

import han.ica.asd.domain.Question;
import org.json.simple.JSONObject;


/**
 * Interface to implement questiontype plugins.
 */
public interface IPlugin {
    /**
     * @return the ID that represents the questiontype used by the application to select the correct plugin.
     */
    String getPluginID();

    /**
     * Create a new question from given parameters. Which is used by the plugin to create a question from the defined questiontype.
     * @param questionPhrase The text that is the question itself.
     * @param points The total points to earn on this question.
     * @param context JSONObject that contains extra options or information for the questiontype.
     * @param questionType What kind of question it is. This is represented by the plugin itself.
     * @return The newly created question based of the defined questiontype.
     */
    IQuestionType createQuestion(String questionPhrase, int points, JSONObject context, String questionType);

    /**
     * Here the view for the question will be created, which the user can use to answer the question.
     * @param question The question used in the exam based of the defined questiontype.
     * @return The view of the question with input possibilities.
     */
    IQuestionView createQuestionView(Question question);
}
