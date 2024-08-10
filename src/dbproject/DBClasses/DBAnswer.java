package dbproject.DBClasses;

import dbproject.Answer;

public class DBAnswer extends Answer {
    private QuestionType type;

    public DBAnswer(String answer, boolean isCorrect, QuestionType type) {
        super(answer, isCorrect);
        this.type = type;
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

    public QuestionType getType() {
        return type;
    }
}
