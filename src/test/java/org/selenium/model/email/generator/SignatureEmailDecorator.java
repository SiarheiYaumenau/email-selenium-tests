package org.selenium.model.email.generator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignatureEmailDecorator implements EmailContentDecorator {
    private final EmailContentGenerator generator;
    private final String signature;

    @Override
    public String generateSubjectText() {
        return EmailContentGenerator.generateSubjectText();
    }

    @Override
    public String generateBodyText() {
        return EmailContentGenerator.generateBodyText() + "\n\n" + signature;
    }
}
