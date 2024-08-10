package dbproject.DBClasses;

import dbproject.Exam;

import java.util.ArrayList;
import java.util.List;

public class Teacher {
   private  String firstName;
   private String lastName;
   private List<String> subjects;
   private List<Exam> exams;


    public Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = new ArrayList<>();
        this.exams = new ArrayList<>();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void addSubject(String subject) {
        subjects.add(subject);
    }

    /*public boolean createExam(String subject, String examName) {
        if(subject.contains(subject)) {
            exams.add(examName);
            return true;
        } else {
            return false;
        }
    }*/
}
