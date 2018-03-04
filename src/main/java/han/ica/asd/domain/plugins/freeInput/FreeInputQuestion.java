package han.ica.asd.domain.plugins.freeInput;

import han.ica.asd.domain.Question;
import han.ica.asd.domain.interfaces.IQuestionType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * TODO Javadoc
 */
public class FreeInputQuestion extends Question implements IQuestionType {

    private Map<String, String> answerMap;

    public FreeInputQuestion(String questionPhrase, int points, JSONObject context, String questionType) {
        super(questionPhrase, points, context, questionType);
        answerMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getGivenAnswer() {
        JSONObject givenAnswerJSON = new JSONObject();

        JSONArray answersJSONArray = new JSONArray();

        Iterator iterator = answerMap.entrySet().iterator();

        while (iterator.hasNext()) {
            HashMap.Entry<String, String> answer = (HashMap.Entry<String, String>) iterator.next();
            JSONObject answerJSON = new JSONObject();
            answerJSON.put(answer.getKey(), answer.getValue());
            answersJSONArray.add(answerJSON);
        }
        givenAnswerJSON.put("answers: ", answersJSONArray);

        return givenAnswerJSON;
    }

    public int checkGivenAnswer() {
        return 0;
    }

    public Map<String, String> getAnswerMap() {
        return answerMap;
    }

    public void setAnswerMap(Map<String, String> answerMap) {
        this.answerMap = answerMap;
    }
}
