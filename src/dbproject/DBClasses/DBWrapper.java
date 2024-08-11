package dbproject.DBClasses;

import dbproject.Answer;
import dbproject.Exam;
import dbproject.Question;
import dbproject.Subject;
import dbproject.Question.Difficulty;

import java.lang.reflect.Type;
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
    public int addAnswer(String answer, QuestionType type) {
        int aid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into answer (text, typeid) values (?, ?) returning aid");
            stmt.setString(1, answer);
            stmt.setInt(2, type.getID());
            ResultSet res = stmt.executeQuery();
            if(res.next()) {
                aid = res.getInt("aid");
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer!");
        }
        return aid;
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
            throw new RuntimeException("Answer with an id of " + ID + " was not found!");
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
            throw new RuntimeException("Answer with an id of " + ID + " was not found!");
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

    public Subject getSubject() {
        return subject;
    }

    @Override
    public int addQuestion(Question question) {
        DBQuestion toAdd = (DBQuestion) question;
        int qid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into question (text, sid, typeID) values (?, ?, ?) returning qid");
            stmt.setString(1, toAdd.getText());
            stmt.setInt(2, subject.getID());
            stmt.setInt(4, toAdd.getType().getID());
            ResultSet res = stmt.executeQuery();

            if(res.next()) {
                qid = res.getInt("qid");
            }

            stmt = conn.prepareStatement("insert into difficulty (qid, difficulty) values (?, ?)");
            stmt.setInt(1, getNumQuestions());
            stmt.setString(2, toAdd.getDifficulty().name());
            res = stmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add question!");
        }
        return qid;
    }

    @Override
    public boolean deleteQuestionBylD(int ID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from question where qid = ?");
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to remove question!");
        }
        return true;
    }

    @Override
    public boolean deleteAnswerBylD(int ID) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from answer where aid = ?");
            stmt.setInt(1, ID);
            ResultSet res = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer!");
        }
        return true;
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
            throw new RuntimeException("No Question of subject " + subject.getSubject() + " were found!");
        }
        return questions;
    }

    @Override
    public List<Answer> getAllAnswers() {
        List<Answer> answers = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select aid,text,typeID from answer");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("aid");
                String text = res.getString("text");
                QuestionType type = QuestionType.toQuestionType(res.getInt("typeID"));
                answers.add(new DBAnswer(id, text, type));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Answers were found!");
        }
        return answers;
    }

    @Override
    public boolean addAnswerToQuestion(int qid, int aid, boolean isCorrect) { //todo
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into question_answer (qid, aid, iscorrect) values (?, ? ,?)");
            stmt.setInt(1, qid);
            stmt.setInt(2, aid);
            stmt.setBoolean(3, isCorrect);
            ResultSet res = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer to question!");
        }
        return true;
    }

    @Override
    public boolean deleteAnswerFromQuestion(int qid, int aid) {
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from question_answer where qid = ? and aid = ?");
            stmt.setInt(1, qid);
            stmt.setInt(2, aid);
            ResultSet res = stmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to remove answer from question!");
        }
        return true;
    }

    @Override
    public List<Answer> getAnswersFromQuestion(int qid) {
        return null;
    }

    @Override
    public void addSubjectToTeacher(int tid, Subject subject) {
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into teacher_subject (tid, sid) values (?, ?)");
            stmt.setInt(1, tid);
            stmt.setInt(2, subject.getID());
            ResultSet res = stmt.executeQuery();

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to subject to teacher!");
        }
    }

    @Override
    public int addTeacher(Teacher teacher) {
        int tid = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement("insert into teacher (firstname, lastname) values (?, ?) returning tid");
            stmt.setString(1, teacher.getFirstName());
            stmt.setString(2, teacher.getLastName());
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                tid = res.getInt("tid");
            }

            for(Subject subject : teacher.getSubjects()) {
                addSubjectToTeacher(tid, subject);
            }

        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to add answer to question!");
        }
        return tid;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("select tid,firstname,lastname from teacher");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                int id = res.getInt("tid");
                String firstName = res.getString("firstname");
                String lastName = res.getString("lastname");
                teachers.add(new Teacher(firstName, lastName));
            }
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("No Teachers were found!");
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherByID(int ID) {
        return null;
    }

    @Override
    public List<Subject> getSubjectsFromTeacher(int tid) {
        return null;
    }

    @Override
    public boolean deleteTeacherByID(int ID) {
        return false;
    }

    @Override
    public int addExam(String creationDate) {
        return -1;
    }//TODO

    @Override
    public boolean addQuestionToExam(int qid, int eid) {
        return false;
    }//TODO

    @Override
    public Exam getExamByID(int eid) {
        return null;
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
