package han.ica.asd.domain;

import han.ica.asd.domain.interfaces.IQuestionType;
import org.json.simple.JSONObject;

public abstract class Question implements IQuestionType{
    private String questionPhrase;
    private int points;
    private JSONObject context;
    private String questionType;

    protected Question(String questionPhrase, int points, JSONObject context, String questionType) {
        this.questionPhrase = questionPhrase;
        this.points = points;
        this.context = context;
        this.questionType = questionType;
    }

    public String getQuestionPhrasing() {
        return questionPhrase;
    }

    public int getPoints() {
        return points;
    }

    public JSONObject getContext() {
        return context;
    }

    public String getQuestionType() {
        return questionType;
    }
}
