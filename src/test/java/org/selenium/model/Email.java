package org.selenium.model;

import java.util.Objects;

public class Email {
    private String recipient;
    private String subject;
    private String body;

    public Email() {
    }

    public Email(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Email{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        if (!Objects.equals(recipient, email.recipient)) return false;
        if (!Objects.equals(subject, email.subject)) return false;
        return Objects.equals(body, email.body);
    }

    @Override
    public int hashCode() {
        int result = recipient != null ? recipient.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }
}
