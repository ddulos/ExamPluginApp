package han.ica.asd.domain;

/**
 * Domain model for exams.
 */
public class Exam {
    private String name;
    private String course;
    private AbstractQuestion[] questions;

    /**
     * Constructor for exam.
     *
     * @param name   Name of the exam.
     * @param course Course of the exam.
     */
    public Exam(String name, String course) {
        this.name = name;
        this.course = course;
    }

    /**
     * Getter for property 'name'.
     *
     * @return Name of the exam.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for property 'name'.
     *
     * @param name Name of the exam.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for property 'course'.
     *
     * @return Course of the exam.
     */
    public String getCourse() {
        return course;
    }

    /**
     * Setter for property 'course'.
     *
     * @param course Course of the exam.
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Getter for property 'questions'.
     *
     * @return Array of questions in exam.
     */
    public AbstractQuestion[] getQuestions() {
        return questions;
    }

    /**
     * Setter for property 'questions'.
     *
     * @param questions Array of questions in exam.
     */
    public void setQuestions(AbstractQuestion[] questions) {
        this.questions = questions;
    }
}
