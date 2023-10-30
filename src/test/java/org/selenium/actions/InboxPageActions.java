package org.selenium.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.selenium.model.email.Email;
import org.selenium.pages.InboxPage;
import org.selenium.pages.NonEditableEmailPage;

@Data
@Builder
@AllArgsConstructor

public class InboxPageActions {
    private final InboxPage inboxPage;

    public void createAndSaveDraftOfSimpleEmail(Email email) {
        inboxPage
                .waitLoadPage()
                .createNewMessage()
                .waitLoadPage()
                .fillAddressee(email.getRecipient())
                .fillSubject(email.getSubject())
                .fillBody(email.getBody())
                .saveDraft()
                .closeMessageEditorPageAndSwitchToInboxPage();
    }

    public void getSubjectOfTheFirstEmailFromInbox(Email email) {
        NonEditableEmailPage nonEditableEmailPage = inboxPage
                .waitLoadPage()
                .openFirstNonEditableEmail();
        email
                .setSubject(nonEditableEmailPage
                        .getSubjectOfNonEditableEmail());
        nonEditableEmailPage
                .retutnToInboxPage();
    }
}
