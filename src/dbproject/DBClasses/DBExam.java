package dbproject.DBClasses;

import dbproject.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public abstract class DBExam implements Examable {

    protected List<Integer> questionIDs;

    protected int eid;
    protected DBWrapper db;
    protected int maxNumQue;
    protected int currNumQue;
    protected String creationDate;
    private boolean showSolution;

    public DBExam(int maxNumQue, DBWrapper db) {
        this.maxNumQue = maxNumQue;
        this.currNumQue = 0;
        this.db = db;
        this.creationDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exam created on: ").append(creationDate).append("\n\n");

        List<Question> questions = db.getQuestionsFromExam(eid);
        for (Question q : questions) {
            sb.append(q.getText()).append("\n");
            sb.append("Difficulty: ").append(q.getDifficulty()).append("\n");
            List<Answer> answers = null;
            int i = 0;
            try {
                answers = db.getAnswersFromQuestion(q);
                for (Answer a : answers) {
                    a.setDisplaySolution(showSolution);
                    i++;
                    sb.append(i).append(") ").append(a).append("\n");
                }
            } catch (Exception e) {
                sb.append("No answers were added yet!\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String writeExam(boolean showSolution) throws FileNotFoundException {
        String filePath = (showSolution ? "solution_" : "exam_") + creationDate + ".txt";
        File file = new File(filePath);
        PrintWriter pw = new PrintWriter(file);
        setShowSolution(showSolution);
        pw.write(this.toString());
        pw.close();
        return filePath;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getEid() {
        return eid;
    }

    public void setShowSolution(boolean showSolution) {
        this.showSolution = showSolution;
    }

    public boolean getShowSolution() {
        return showSolution;
    }

    @Override
    public void createExam() {
        int examID = -1;
        examID = db.addExam(creationDate);
        setEid(examID);
    }
}
