package dbproject;

public class ExamCreationException extends Exception {

	public ExamCreationException(String message) {
		super(message);
	}
	
	public ExamCreationException() {
		super("General dbproject.ExamCreationException: Failed to create an dbproject.Exam!");
	}
}
