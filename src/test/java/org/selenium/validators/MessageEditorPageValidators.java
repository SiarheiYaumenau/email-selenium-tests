package org.selenium.validators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.selenium.model.email.Email;
import org.selenium.pages.DraftsPage;
import org.selenium.pages.MessageEditorPage;
import org.selenium.pages.NonEditableEmailPage;
import org.selenium.pages.SentPage;
import org.testng.asserts.SoftAssert;

import static org.testng.Assert.assertEquals;

@Data
@Builder
@AllArgsConstructor
public class MessageEditorPageValidators {
    private final MessageEditorPage messageEditorPage;

    public void validateRecipientSubjectAndBodyOfDraft(Email email) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(messageEditorPage.waitLoadPage().getRecipientOfDraft(), email.getRecipient(),
                "The recipient is not displayed or wrong");
        softAssert.assertEquals(messageEditorPage.getSubjectOfDraft(), email.getSubject(),
                "The subject is not displayed or wrong");
        softAssert.assertTrue(messageEditorPage.getBodyTextOfDraft().contains(email.getBody()),
                "The body is not displayed or wrong");
        softAssert.assertAll();
    }

    public void validateSentEmailIsDisappearedInDraftPage(Email email) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotEquals(messageEditorPage.waitLoadPage().getSubjectOfDraft(), email.getSubject(),
                "The subject is equal");
        softAssert.assertFalse(messageEditorPage.getBodyTextOfDraft().contains(email.getBody()),
                "The body is equal");
        softAssert.assertAll();
    }

    public void validateEmailIsSent(Email email) {
        DraftsPage draftsPage = messageEditorPage.closeMessageEditorPageAndSwitchToDraftsPage();
        SentPage sentPage = draftsPage.waitLoadPage().switchToSentPage();
        NonEditableEmailPage nonEditableEmailPage = sentPage.waitLoadPage().openFirstNonEditableEmail();
        assertEquals(nonEditableEmailPage.waitLoadPage().getSubjectOfNonEditableEmail(), email.getSubject(),
                "The subject is not displayed or wrong");
    }
}
