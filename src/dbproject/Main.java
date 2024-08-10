package dbproject;

import dbproject.DBClasses.DBQuestion;
import dbproject.DBClasses.Wrapper;
import dbproject.DBClasses.DBWrapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Wrapper dbWrapper = new DBWrapper(Subject.Math);
        List<Question> questions = dbWrapper.getAllQuestionsFromSubject(Subject.Math);
        for (Question question : questions) {
            System.out.println(question);
        }
    }
}