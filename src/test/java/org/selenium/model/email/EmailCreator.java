package org.selenium.model.email;

import org.selenium.util.TestDataReader;

import static org.selenium.model.email.EmailContentGenerator.*;

public class EmailCreator {
    private static final String TESTDATA_EMAIL_RECIPIENT = "testdata.email.recipient";

    public static Email withRecipientFromPropertyAndGeneratedSubjectAndBody() {
        return Email.builder()
                .recipient(TestDataReader.getTestData(TESTDATA_EMAIL_RECIPIENT))
                .subject(generateSubjectText())
                .body(generateBodyText())
                .build();
    }
}