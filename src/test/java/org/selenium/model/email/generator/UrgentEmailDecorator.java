package org.selenium.model.email.generator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UrgentEmailDecorator implements EmailContentDecorator {
    private final EmailContentGenerator generator;

    @Override
    public String generateSubjectText() {
        return "Urgent " + EmailContentGenerator.generateSubjectText();
    }

    @Override
    public String generateBodyText() {
        return "Urgent " + EmailContentGenerator.generateBodyText();
    }
}
