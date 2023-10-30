package org.selenium.actions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.selenium.model.email.Email;
import org.selenium.pages.DraftsPage;
import org.selenium.pages.MessageEditorPage;
import org.selenium.pages.SentEmailAlertPage;

@Data
@Builder
@AllArgsConstructor

public class DraftPageActions {
    private final DraftsPage draftsPage;

    public DraftsPage sendTheFirstEmailFromDrafts(Email email) {
        MessageEditorPage messageEditorPage = draftsPage
                .waitLoadPage()
                .openFirstMailForEdit();
        email
                .setSubject(messageEditorPage
                        .getSubjectOfDraft());
        email
                .setBody(messageEditorPage
                        .getBodyTextOfDraft());
        SentEmailAlertPage sentEmailAlertPage = messageEditorPage.sendEmail();
        return sentEmailAlertPage
                .waitLoadPage()
                .closeSentEmailAlertPage();
    }
}
