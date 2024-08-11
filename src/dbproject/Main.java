package dbproject;

import dbproject.DBClasses.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Teacher findTeacher(String firstname, String lastname) {
        List<Teacher> teachers = db.getAllTeachers();
        for (Teacher teacher : teachers) {
            if (teacher.getFirstName().equals(firstname) && teacher.getLastName().equals(lastname)) {
                return teacher;
            }
        }
        throw new IllegalArgumentException("No teacher found with that name!");
    }

    public static void printAllQuestionsFromSubject(Subject subject) {
        for (Question question : db.getAllQuestionsFromSubject(subject)) {
            System.out.println(question);
            for (Answer answer : db.getAnswersFromQuestion(question.getId())) {
                System.out.println(answer);
            }
        }
    }

    static final DBWrapper db = new DBWrapper();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Teacher teacher = null;

        System.out.println("Enter your full name:");
        String firstname = input.next();
        String lastname = input.next();

        try {
            teacher = findTeacher(firstname, lastname);
            System.out.println("Welcome back " + firstname + " " + lastname);
        } catch (Exception e) {
            System.out.println("Welcome " + firstname + " " + lastname);
            teacher = new Teacher(firstname, lastname);
            int tid = db.addTeacher(teacher);
            teacher.setID(tid);
        }
        Subject subject = null;

        do {
            try {
                if (teacher.getSubjects() != null && !teacher.getSubjects().isEmpty()) {
                    subject = Subject.getSubjectFromUser(teacher.getSubjects().toArray(new Subject[0]), input);
                } else {
                    System.out.println("You are not currently teaching any subjects.");
                    subject = Subject.getSubjectFromUser(Subject.values(), input);
                }
                db.addSubjectToTeacher(teacher.getID(), subject);
            } catch (RuntimeException e) {
                System.err.println("Error! " + e.getMessage());
                subject = null;
            }
        } while (subject == null);

        final int EXIT = -1;
        int selction = 0;

        do {
            printMenu();
            selction = input.nextInt();
            input.nextLine();

            try {
                switch (selction) {
                    case 1: {
                        printAllQuestionsFromSubject(subject);
                        break;
                    }
                    case 2: {
                        System.out.println("Enter the answer:");
                        String answer = input.nextLine();
                        System.out.println("Enter the type of the question:");
                        QuestionType type = QuestionType.getQuestionTypeFromUser(input);
                        db.addAnswer(answer, type);
                        break;
                    }
                    case 3: {
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        System.out.println("Enter the answer ID:");
                        int aid = input.nextInt();
                        System.out.println("Is the answer correct? (true/false)");
                        boolean isCorrect = input.nextBoolean();
                        db.addAnswerToQuestion(qid, aid, isCorrect);
                        break;
                    }
                    case 4: {
                        System.out.println("Enter the question:");
                        String question = input.nextLine();
                        System.out.println("Enter the type of the question:");
                        QuestionType type = QuestionType.getQuestionTypeFromUser(input);

                        break;
                    }
                    case 5: {
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        System.out.println("Enter the answer ID:");
                        int aid = input.nextInt();
                        db.deleteAnswerFromQuestion(qid, aid);
                        break;
                    }
                    case 6: {
                        System.out.println("Enter the question ID:");
                        int qid = input.nextInt();
                        db.deleteQuestionBylD(qid);
                        break;
                    }
                    case 7: {
                        //generateExam(input);
                        break;
                    }
                    case EXIT: {
                        System.out.println("Goodbye!");
                        break;
                    }
                    default:
                        System.out.println("Error! option dose not exist, Please try again!");
                }
            } catch (RuntimeException e) {
                System.err.println("Error! " + e.getMessage());
            }
        } while (selction != EXIT);
        db.close();
    }

    public static void printMenu() {
        System.out.println("\nWelcome to my Test Maker!");
        System.out.println("Please select an option: (-1 to exit)");
        System.out.println("1. Display all of the question from the repo (and their answers)");
        System.out.println("2. Add a new answers to the repo");
        System.out.println("3. Append an answer to an existing question");
        System.out.println("4. Add a new question");
//		System.out.println("4. Update an exsisting question");
//		System.out.println("5. Update an exsisting answer to a question");
        System.out.println("5. Delete an answers to a question");
        System.out.println("6. Delete a question");
        System.out.println("7. Generate a new test");
    }
}