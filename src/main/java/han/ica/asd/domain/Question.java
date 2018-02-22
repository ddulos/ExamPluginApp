package han.ica.asd.domain;

/**
 * TODO Javadoc
 */
public abstract class Question {
    private String questionPhrase;
    private int points;

    protected Question(String questionPhrase, int points) {
        this.questionPhrase = questionPhrase;
        this.points = points;
    }

    public String getQuestionPhrase() {
        return questionPhrase;
    }

    public int getPoints() {
        return points;
    }
}
