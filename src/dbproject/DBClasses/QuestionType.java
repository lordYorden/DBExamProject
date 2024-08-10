package dbproject.DBClasses;

public enum QuestionType {
    OpenEnded(1, "Open Ended"),
    SingleSelection(2, "Single Selection"),
    MultipleChoice(3, "Multiple choice");
    private int id;
    private String type;

    QuestionType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public static QuestionType toQuestionType(int id) {
        return switch (id) {
            case 1 -> OpenEnded;
            case 2 -> SingleSelection;
            case 3 -> MultipleChoice;
            default -> null;
        };
    }

    public static QuestionType toQuestionType(String type) {
        return switch (type) {
            case "Open Ended" -> OpenEnded;
            case "Single Selection" -> SingleSelection;
            case "Multiple choice" -> MultipleChoice;
            default -> null;
        };
    }

    public int getID() {
        return id;
    }

    public String getType() {
        return type;
    }
}
