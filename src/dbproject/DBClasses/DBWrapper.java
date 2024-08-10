package dbproject.DBClasses;

import dbproject.Question;
import dbproject.Subject;

public class DBWrapper implements Wrapper{
    private Subject subject;
    //postgersql connection

    public DBWrapper(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean addAnswer(String answer) {
        return false;
    }

    @Override
    public Question getQuestionBylD(int ID) {
        return null;
    }

    @Override
    public String getAnswerBylD(int ID) {
        return null;
    }

    @Override
    public int getNumQuestions() {
        return 0;
    }

    @Override
    public int getNumAnswers() {
        return 0;
    }

    @Override
    public Subject getSubject() {
        return subject;
    }

    @Override
    public boolean addQuestion(Question question) {
        return false;
    }

    @Override
    public boolean deleteQuestionBylD(int ID) {
        return false;
    }

    @Override
    public boolean deleteAnswerBylD(int ID) {
        return false;
    }

}
