package dbproject.DBClasses;

import dbproject.Answer;
import dbproject.Question;

import java.lang.reflect.Type;
import java.util.List;

public class DBQuestion extends Question {

    int numAnswers;
    int numCorrectAnswers;
    QuestionType type;
    List<Integer> answerIDs;


    public DBQuestion(int ID, String question, Difficulty difficulty, QuestionType type) {
        super(ID, question, difficulty);
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
}
