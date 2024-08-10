package dbproject.DBClasses;

import dbproject.Question;
import dbproject.Subject;

import java.util.List;

public interface Wrapper {
    public boolean addAnswer(String answer);
    public Question getQuestionBylD(int ID);
    public String getAnswerBylD(int ID);
    public int getNumQuestions();
    public int getNumAnswers();
    public Subject getSubject();
    public boolean addQuestion(Question question);
    public boolean deleteQuestionBylD(int ID);
    public boolean deleteAnswerBylD(int ID);
    public List<Question> getAllQuestionsFromSubject(Subject subject);
}
