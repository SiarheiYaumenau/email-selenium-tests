package org.selenium.model.email.generator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.selenium.model.email.Email;

@Data
@Builder
@AllArgsConstructor
public class SignatureEmailDecorator implements EmailContentDecorator {
    private final Email decorated;
    private final String signature;

    @Override
    public String generateSubjectText() {
        String originalSubject = decorated.getSubject();
        return originalSubject + "\n\n" + "Signature: " + signature;
    }

    @Override
    public String generateBodyText() {
        String originalBody = decorated.getBody();
        return originalBody + "\n\n" + "Signature: " + signature;
    }
}
