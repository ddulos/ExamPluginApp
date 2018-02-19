package han.ica.asd.domain.interfaces;

/**
 * TODO Javadoc
 */
public interface IPlugin {
    String getID();
    IQuestionType createQuestion();
    IQuestionView createView();
}
