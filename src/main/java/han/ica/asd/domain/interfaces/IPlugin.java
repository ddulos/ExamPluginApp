package han.ica.asd.domain.interfaces;

/**
 * TODO Javadoc
 */
public interface IPlugin {
    String getPluginID();
    IQuestionType getQuestion();
    IQuestionView getQuestionView();
}
