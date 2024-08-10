package dbproject.DBClasses;

import dbproject.Answer;
import dbproject.Question;
import dbproject.Subject;
import dbproject.Question.Difficulty;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWrapper implements Wrapper{
    private Subject subject;
    //PostgreSQL connection
    Connection conn = null;
    //Class.forName("org.postgresql.Driver");
    final String dbUrl = "jdbc:postgresql:finalproject";

    public DBWrapper(Subject subject) {
        this.subject = subject;
        try {
            this.conn = DriverManager.getConnection(dbUrl, "postgres", "1234");
        }catch(SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    @Override
    public boolean addAnswer(String answer) {
        return false;
    }

    @Override
    public Question getQuestionBylD(int ID) {
        DBQuestion question = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select qid,text,difficulty,typeID from ((select * from question where sid = ?) as question natural join difficulty) natural join type");
            stmt.setInt(1, subject.getID());
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                int id = res.getInt("qid");
                String text = res.getString("text");
                String difficulty = res.getString("difficulty");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                question = new DBQuestion(id, text, Difficulty.valueOf(difficulty), type);
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error! Answer with an id of " + ID + " was not found!");
        }
        return question;
    }

    @Override
    public String getAnswerBylD(int ID) {
        String answer = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("select text from answer where aid = ?");
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                answer = res.getString("text");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error! Answer with an id of " + ID + " was not found!");
        }
        return answer;
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

    @Override
    public List<Question> getAllQuestionsFromSubject(Subject subject) {
        List<Question> questions = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select qid,text,difficulty,typeID from ((select * from question where sid = ?) as question natural join difficulty) natural join type");
            stmt.setInt(1, subject.getID());
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int id = res.getInt("qid");
                String text = res.getString("text");
                String difficulty = res.getString("difficulty");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                questions.add(new DBQuestion(id, text, Difficulty.valueOf(difficulty), type));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Error! No Question of subject " + subject.getSubject() + " was not found!");
        }
        return questions;
    }

    @Override
    public List<Answer> getAllAnswers() {
        return null;
    }


    @Override
    public boolean addAnswerToQuestion(int QID, int AID, boolean isCorrect) {
        return false;
    }

    @Override
    public boolean deleteAnswerFromQuestion(int QID, int AID) {
        return false;
    }

    @Override
    public List<Answer> getAnswersFromQuestion(int QID) {
        return null;
    }

    @Override
    public boolean addSubjectToTeacher(int ID, Subject subject) {
        return false;
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        return false;
    }

    @Override
    public boolean deleteTeacherByID(int ID) {
        return false;
    }

    @Override
    public void close() {
        try {
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
            System.err.println("SQLState: " + ex.getSQLState());
            System.err.println("VendorError: " + ex.getErrorCode());
        }
    }

}
