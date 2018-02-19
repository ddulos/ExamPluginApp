package han.ica.asd.domain.interfaces;

/**
 * TODO Javadoc
 */
public interface IQuestionType {
    String toText();
    void setGivenAnswer(String answer);
    String getGivenAnswer();
    int checkGivenAnswer();
}
