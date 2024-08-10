package dbproject.DBClasses;

import dbproject.Exam;
import dbproject.Examable;
import dbproject.Repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public abstract class DBExam implements Examable {

    protected List<Integer> questionIDs;
    protected DBWrapper db;
    protected int maxNumQue;
    protected int currNumQue;
    private String creationDate;

    public DBExam(int maxNumQue, DBWrapper db) {
        this.maxNumQue = maxNumQue;
        this.currNumQue = 0;
        this.db = db;
    }

    public String writeExam(boolean displaySolution) throws FileNotFoundException {
        String filePath = (displaySolution ? "solution_" : "exam_") + creationDate + ".txt";
        File file = new File(filePath);
        PrintWriter pw = new PrintWriter(file);
        //this.displaySolution = displaySolution;
        pw.write(this.toString());
        pw.close();
        return filePath;
    }
}
