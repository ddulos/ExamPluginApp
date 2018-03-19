package han.ica.asd.domain;

import org.json.simple.JSONObject;

/**
 * Domain model for question.
 * <p>
 * Used by plugins to create many types of questions.
 */
public abstract class Question {
    private String questionPhrasing;
    private int points;
    private JSONObject context;
    private String questionType;

    /**
     * Constructor for Question
     *
     * @param questionPhrasing The text that is the question itself.
     * @param points         The total points to earn on this question.
     * @param context        JSONObject that contains extra options or information for the questiontype.
     * @param questionType   What kind of question it is. This is represented by the plugin itself.
     */
    protected Question(String questionPhrasing, int points, JSONObject context, String questionType) {
        this.questionPhrasing = questionPhrasing;
        this.points = points;
        this.context = context;
        this.questionType = questionType;
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

    /**
     * Getter for property 'givenAnswer'.
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
