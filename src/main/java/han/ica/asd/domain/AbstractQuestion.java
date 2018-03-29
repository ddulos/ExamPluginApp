package han.ica.asd.domain;

import han.ica.asd.domain.interfaces.IQuestion;
import org.json.simple.JSONObject;

/**
 * Abstract domain model for question.
 * <p>
 * Used by plugins to create different types of questions.
 */
public abstract class AbstractQuestion implements IQuestion {
    private String questionPhrasing;
    private int points;
    private JSONObject context;
    private String questionType;

    /**
     * Constructor for AbstractQuestion
     *
     * @param questionJson The JSONObject that contains a question of an exam.
     */
    protected AbstractQuestion(JSONObject questionJson) {
        this.questionPhrasing = (String) questionJson.get("questionPhrasing");
        this.points = Integer.parseInt(String.valueOf(questionJson.get("points")));
        this.context = (JSONObject) questionJson.get("questionContext");
        this.questionType = (String) questionJson.get("questionType");
    }

    /**
     * Getter for property 'questionPhrasing'.
     *
     * @return The text that is the question itself.
     */
    public String getQuestionPhrasing() {
        return questionPhrasing;
    }

    /**
     * Getter for property 'points'.
     *
     * @return The total points to earn on this question.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Getter for property 'context'.
     *
     * @return JSONObject that contains extra options or information for the questiontype.
     */
    public JSONObject getContext() {
        return context;
    }

    /**
     * Getter for property 'questionType'.
     *
     * @return What kind of question it is. This is represented by the plugin itself.ç
     */
    public String getQuestionType() {
        return questionType;
    }
}
