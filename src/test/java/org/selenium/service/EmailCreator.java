package org.selenium.service;

import org.selenium.model.Email;

public class EmailCreator {
    private static final String RECIPIENT = "selenium.test@internet.ru";
    private static String subject;
    private static String body;

    public static Email withRecipientFromPropertyAndGeneratedSubjectAndBody() {
        subject = generateSubjectText();
        body = generateBodyText();
        return new Email(RECIPIENT, subject, body);
    }

    private static String generateSubjectText() {
        String[] subjects = {
                "Subject: Meeting Tomorrow " + (int) (Math.random() * 100),
                "Subject: Important Update " + (int) (Math.random() * 100),
                "Subject: Reminder " + (int) (Math.random() * 100),
                "Subject: New Opportunity " + (int) (Math.random() * 100),
                "Subject: Project Status " + (int) (Math.random() * 100)
        };
        int randomIndex = (int) (Math.random() * subjects.length);
        return subjects[randomIndex];
    }

    private static String generateBodyText() {
        String[] bodyTexts = {
                "Body: Hello, I hope this message finds you well." + (int) (Math.random() * 100),
                "Body: Please find attached the requested documents." + (int) (Math.random() * 100),
                "Body: I wanted to update you on our progress." + (int) (Math.random() * 100),
                "Body: Thanks for your prompt response." + (int) (Math.random() * 100),
                "Body: Don't forget our meeting next week." + (int) (Math.random() * 100)
        };
        int randomIndex = (int) (Math.random() * bodyTexts.length);
        return bodyTexts[randomIndex];
    }
}
