package org.selenium.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.selenium.model.email.Email;
import org.selenium.pages.NewsLettersPage;
import org.selenium.pages.NonEditableEmailPage;

@Data
@Builder
@AllArgsConstructor

public class NewsLettersPageActions {
    private final NewsLettersPage newsLettersPage;

    public void getSubjectOfTheFirstEmailFromNewsLettersPage(Email email) {
        NonEditableEmailPage nonEditableEmailPage = newsLettersPage
                .waitLoadPage()
                .openFirstNotEditableEmail();
        email
                .setSubject(nonEditableEmailPage
                        .getSubjectOfNonEditableEmail());
        nonEditableEmailPage
                .retutnToInboxPage();
    }
}
