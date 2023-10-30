package org.selenium.model.email;


public class EmailContentGenerator {
    private static final String[] SUBJECTS = {
            "Subject: Meeting Tomorrow ",
            "Subject: Important Update ",
            "Subject: Reminder ",
            "Subject: New Opportunity ",
            "Subject: Project Status "
    };

    private static final String[] BODY_TEXTS = {
            "Body: Hello, I hope this message finds you well.",
            "Body: Please send me the requested documents.",
            "Body: I wanted to update you on our progress.",
            "Body: Thanks for your prompt response.",
            "Body: Don't forget our meeting next week."
    };

    public static String generateSubjectText() {
        int randomIndex = (int) (Math.random() * SUBJECTS.length);
        return SUBJECTS[randomIndex] + (int) (Math.random() * 100);
    }

    public static String generateBodyText() {
        int randomIndex = (int) (Math.random() * BODY_TEXTS.length);
        return BODY_TEXTS[randomIndex] + (int) (Math.random() * 100);
    }
}
