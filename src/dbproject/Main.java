package dbproject;

import dbproject.DBClasses.Wrapper;
import dbproject.DBClasses.DBWrapper;

public class Main {
    public static void main(String[] args) {
        Wrapper dbWrapper = new DBWrapper(Subject.Math);
        System.out.println(dbWrapper.getAnswerBylD(1));
    }
}