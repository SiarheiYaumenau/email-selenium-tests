package org.selenium.model.email;

import org.selenium.model.email.generator.EmailContentDecorator;
import org.selenium.model.email.generator.EmailContentGenerator;
import org.selenium.model.email.generator.SignatureEmailDecorator;
import org.selenium.model.email.generator.UrgentEmailDecorator;
import org.selenium.util.TestDataReader;

import static org.selenium.model.email.generator.EmailContentGenerator.*;

public class EmailCreator {
    private static final String TESTDATA_EMAIL_RECIPIENT = "testdata.email.recipient";

    public static Email withRecipientFromPropertyAndGeneratedSubjectAndBody() {
        return Email.builder()
                .recipient(TestDataReader.getTestData(TESTDATA_EMAIL_RECIPIENT))
                .subject(generateSubjectText())
                .body(generateBodyText())
                .build();
    }

    public static Email withUrgentSubjectAndBody() {
        EmailContentGenerator baseGenerator = new EmailContentGenerator();
        EmailContentDecorator urgentDecorator = new UrgentEmailDecorator(baseGenerator);

        return Email.builder()
                .recipient(TestDataReader.getTestData(TESTDATA_EMAIL_RECIPIENT))
                .subject(urgentDecorator.generateSubjectText())
                .body(urgentDecorator.generateBodyText())
                .build();
    }

    public static Email withSignatureInBody(String signature) {
        EmailContentGenerator baseGenerator = new EmailContentGenerator();
        EmailContentDecorator signatureDecorator = new SignatureEmailDecorator(baseGenerator, signature);

        return Email.builder()
                .recipient(TestDataReader.getTestData(TESTDATA_EMAIL_RECIPIENT))
                .subject(generateSubjectText())
                .body(signatureDecorator.generateBodyText())
                .build();
    }
}