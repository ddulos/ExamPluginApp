package han.ica.asd.domain.plugins.multipleChoice;

import han.ica.asd.domain.interfaces.IPlugin;
import han.ica.asd.domain.interfaces.IQuestionType;
import han.ica.asd.domain.interfaces.IQuestionView;

/**
 * TODO Javadoc
 */
public class MultipleChoiceQuestionPlugin implements IPlugin {

    public String getID() {
        return null;
    }

    public IQuestionType createQuestion(String questionPhrase, int points) {
        MultipleChoiceQuestion multipleChoiceQuestion = new MultipleChoiceQuestion(questionPhrase, points);

        return multipleChoiceQuestion;
    }

    public IQuestionView createView() {
        MultipleChoiceQuestionView multipleChoiceQuestionView = new MultipleChoiceQuestionView();

        return multipleChoiceQuestionView;
    }
}
