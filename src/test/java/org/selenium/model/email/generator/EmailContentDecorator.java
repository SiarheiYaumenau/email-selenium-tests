package org.selenium.model.email.generator;

public interface EmailContentDecorator {
    String generateSubjectText();
    String generateBodyText();
}
