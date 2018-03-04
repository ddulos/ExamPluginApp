package han.ica.asd.domain.plugins.multipleChoice;

import han.ica.asd.domain.Question;
import han.ica.asd.domain.interfaces.IQuestionType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * TODO Javadoc
 */
public class MultipleChoiceQuestion extends Question implements IQuestionType {

    private Map<String, String> answerMap;

    public MultipleChoiceQuestion(String questionPhrase, int points, JSONObject context, String questionType) {
        super(questionPhrase, points, context, questionType);
        answerMap = new HashMap<>();
    }

    @SuppressWarnings("unchecked")
    public JSONObject getGivenAnswer() {
        JSONObject givenAnswerJSON = new JSONObject();

        JSONArray answers = new JSONArray();

        Iterator<Map.Entry<String, String>> iterator = answerMap.entrySet().iterator();

        while (iterator.hasNext()) {
            HashMap.Entry<String, String> answer = iterator.next();
            JSONObject answerJSON = new JSONObject();
            answerJSON.put(answer.getKey(), answer.getValue());
            answers.add(answerJSON);
        }
        givenAnswerJSON.put("answers: ", answers);
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
