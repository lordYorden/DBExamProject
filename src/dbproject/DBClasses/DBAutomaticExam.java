package dbproject.DBClasses;

import dbproject.*;
import java.util.List;
import java.util.Random;

public class DBAutomaticExam extends DBExam {

    private final static Random rnd = new Random();

    public DBAutomaticExam(int maxNumQue, DBWrapper db) {
        super(maxNumQue, db);
    }

    @Override
    public void createExam() {
        Subject subject = db.getSubject();
        if (subject == null) {
            System.out.println("Error! No subject selected.");
            return;
        }
        int maxRange = db.getNumQuestions();
        int examID = -1;
        try {
            examID = db.addExam(creationDate);
        } catch (RuntimeException e) {
            System.out.println("Error! " + e.getMessage());
            return;
        }
        int addedQuestions = 0;
        while (addedQuestions < this.maxNumQue) {
            int genQue = rnd.nextInt(maxRange);
            DBQuestion questionSelected = (DBQuestion) db.getQuestionBylD(genQue);

            if (questionSelected == null || questionIDs.contains(questionSelected.getId())) {
                System.out.println("Question already exists in the exam!");
                continue;
            }
            questionIDs.add(questionSelected.getId());
            addQuestionToList(questionSelected, examID);
            try {
                db.addQuestion(questionSelected);
                System.out.println("The question was successfully added!");
                addedQuestions++;
            } catch (RuntimeException e) {
                System.out.println("Error! " + e.getMessage());
            }
        }
    }

    private void addQuestionToList(DBQuestion question, int examID) {
        db.addQuestionToExam(question.getId(), examID);
    }
}
