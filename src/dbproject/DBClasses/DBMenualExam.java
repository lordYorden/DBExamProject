package dbproject.DBClasses;

import dbproject.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBMenualExam extends DBExam {
    private final Scanner input;

    public DBMenualExam(int maxNumQue, Scanner input, DBWrapper dbWrapper) throws NumOfQuestionsException {
        super(maxNumQue, dbWrapper);
        this.input = input;
    }

    @Override
    public void createExam() {
        Subject subject = db.getSubject();
        if (subject == null) {
            System.out.println("Error! No subject selected.");
            return;
        }
        int Eid = -1;
        try {
            Eid = db.addExam(creationDate);
        } catch (RuntimeException e) {
            System.out.println("Error! " + e.getMessage());
        }
        for (int i = 0; i < this.maxNumQue; i++) {
            System.out.println("Do you want to add a new question to the exam? (yes/no)");
            String response = input.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                displayAllQuestions();
                DBQuestion question = selectQuestionFromDatabase();
                if (question != null) {
                    addQuestionToList(question,Eid);
                    boolean addAnswers = askIfAddAnswers();
                    if (addAnswers) {
                        addAnswersToQuestion(question);
                        System.out.println("Do you want to delete any answers from the exam? (yes/no)");
                        String choose = input.nextLine();
                        if(choose.equalsIgnoreCase("yes")) {
                            try {
                                deleteAnswerFromAQuestion(question, input);
                            } catch (NumOfAnswersException e) {
                                System.out.println("Error! " + e.getMessage());
                            }
                        }
                    }
                    try {
                        db.addQuestion(question);
                        System.out.println("The question was successfully added!");
                    } catch (RuntimeException e) {
                        System.out.println("Error! " + e.getMessage());
                    }
                }
            } else {
                break; // Exit the loop if the user does not want to add more questions
            }
        }
    }

    private void displayAllQuestions() {
        List<Question> questions = db.getAllQuestionsFromSubject(db.getSubject());
        System.out.println("Available questions:");
        for (Question q : questions) {
            System.out.println(q);
        }
    }

    private DBQuestion selectQuestionFromDatabase() {
        System.out.println("Select a question ID from the list:");
        int questionId = input.nextInt();
        input.nextLine();

        DBQuestion question = (DBQuestion)db.getQuestionBylD(questionId);
        if (question == null) {
            System.out.println("Error! Question with ID " + questionId + " not found.");
        }
        return question;
    }

    private void addQuestionToList(DBQuestion question,int Eid) {
        db.addQuestionToExam(question.getId(),Eid);
    }

    private boolean askIfAddAnswers() {
        System.out.println("Do you want to add answers to the selected question? (yes/no)");
        String response = input.nextLine();
        return response.equalsIgnoreCase("yes");
    }

    private void addAnswersToQuestion(DBQuestion question) {
        List<Answer> answers = db.getAllAnswers();
        System.out.println("Available answers:");
        for (Answer a : answers) {
            System.out.println(a);
        }

        while (true) {
            System.out.println("Enter the ID of the answer you want to add (or -1 to stop):");
            int answerId = input.nextInt();
            input.nextLine();
            if (answerId == -1) {
                break;
            }
            String answerText = db.getAnswerBylD(answerId);
            if (answerText != null) {
                System.out.println("Is this the correct answer? (yes/no)");
                boolean isCorrect = input.nextLine().equalsIgnoreCase("yes");
                db.addAnswerToQuestion(question.getId(), answerId, isCorrect);
            } else {
                System.out.println("Error! Answer with ID " + answerId + " not found.");
            }
        }
    }


    public void deleteAnswerFromAQuestion(DBQuestion question)  {
        int numOfAns = question.getNumAnswers();
        if (numOfAns <= 3) {
            System.out.println("Cannot delete answers. The question must have at least 3 answers.");
            return;
        }

        while (true) {
            System.out.println(question.toString());
            System.out.println("Select an answer ID to remove (or -1 to stop): ");
            int selection = input.nextInt();
            input.nextLine();
            if (selection == -1) {
                break;
            }
            boolean answerExist = db.deleteAnswerBylD(selection - 1);
            if (!answerExist) {
                System.out.println("Error! Answer with ID " + selection + " doesn't exist!");
            }
            if (question.getNumAnswers() <= 3) {
                System.out.println("Cannot delete more answers. The question must have at least 3 answers.");
                break;
            }
        }
    }


}
