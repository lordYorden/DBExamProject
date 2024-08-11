package dbproject.DBClasses;

import dbproject.Answer;

public class DBAnswer extends Answer {
    private QuestionType type;
    private int ID;

    public DBAnswer(int id, String answer, boolean isCorrect, QuestionType type) {
        super(answer, isCorrect);
        this.type = type;
        this.ID = id;
    }

    public DBAnswer(int id, String answer, QuestionType type) {
        this(id, answer, false, type);
        this.setDisplaySolution(false);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append("Type: ");
        sb.append(type.getType());
        sb.append("\n");
        return sb.toString();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public QuestionType getType() {
        return type;
    }
}
