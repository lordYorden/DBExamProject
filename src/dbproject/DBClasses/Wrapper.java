package dbproject.DBClasses;

import dbproject.Answer;
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
    public List<Answer> getAllAnswers();
    public boolean addAnswerToQuestion(int QID, int AID, boolean isCorrect);
    public boolean deleteAnswerFromQuestion(int QID, int AID);
    public List<Answer> getAnswersFromQuestion(int QID);
    public boolean addSubjectToTeacher(int ID, Subject subject);
    public boolean addTeacher(Teacher teacher);
    public boolean deleteTeacherByID(int ID);
    public void close();

}
