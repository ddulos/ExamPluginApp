package han.ica.asd.domain.plugins.freeInput;

import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionType;
import han.ica.asd.domain.interfaces.IQuestionView;

/**
 * TODO Javadoc
 */
public class FreeInputQuestionPlugin implements IPlugin {

    public String getID() {
        return null;
    }

    public IQuestionType createQuestion(String questionPhrase, int points) {
        FreeInputQuestion freeInputQuestion = new FreeInputQuestion(questionPhrase, points);

        return freeInputQuestion;
    }

    public IQuestionView createView() {
        FreeInputQuestionView freeInputQuestionView = new FreeInputQuestionView();

        return freeInputQuestionView;
    }
}
