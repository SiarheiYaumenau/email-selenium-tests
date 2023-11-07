package org.selenium.model.email;

import org.selenium.model.email.generator.EmailContentDecorator;
import org.selenium.model.email.generator.SignatureEmailDecorator;
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
    public static Email withSignatureInBody(Email baseEmail, String signature) {
        EmailContentDecorator signatureDecorator = new SignatureEmailDecorator(baseEmail, signature);
        return Email.builder()
                .recipient(baseEmail.getRecipient())
                .subject(baseEmail.getSubject())
                .body(signatureDecorator.generateBodyText())
                .build();
    }
}