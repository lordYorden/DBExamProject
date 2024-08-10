package dbproject.DBClasses;

import dbproject.Question;
import dbproject.Subject;

import java.sql.*;

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
        return null;
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
    
    public void closeConnection() {
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
