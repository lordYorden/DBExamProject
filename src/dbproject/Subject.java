package dbproject;

public enum Subject {
    Math(1, "Math"),
    Science(2, "Science"),
    History(3, "History"),
    Geography(4, "Geography");

    int ID;
    String subject;

    Subject(int ID, String subject) {
        this.ID = ID;
        this.subject = subject;
    }

    public int getID() {
        return ID;
    }

    public String getSubject() {
        return subject;
    }

    public static Subject toSubject(int ID) {
        return switch (ID) {
            case 1 -> Math;
            case 2 -> Science;
            case 3 -> History;
            case 4 -> Geography;
            default -> null;
        };
    }

    public static Subject toSubject(String subject) {
        return switch (subject) {
            case "Math" -> Math;
            case "Science" -> Science;
            case "History" -> History;
            case "Geography" -> Geography;
            default -> null;
        };
    }
}
