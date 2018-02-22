package han.ica.asd.domain.plugins.freeInput;

import han.ica.asd.domain.Question;
import han.ica.asd.domain.interfaces.IQuestionType;

/**
 * TODO Javadoc
 */
public class FreeInputQuestion extends Question implements IQuestionType {

    public FreeInputQuestion(String questionPhrase, int points) {
        super(questionPhrase, points);
    }

    public String toText() {
        return null;
    }

    public void setGivenAnswer(String answer) {

    }

    public String getGivenAnswer() {
        return null;
    }

    public int checkGivenAnswer() {
        return 0;
    }
}
