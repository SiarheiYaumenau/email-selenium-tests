package org.selenium.test;

import org.selenium.model.email.Email;
import org.selenium.model.user.User;
import org.selenium.pages.DraftsPage;
import org.selenium.pages.InboxPage;
import org.selenium.pages.MessageEditorPage;
import org.selenium.pages.NewsLettersPage;
import org.selenium.pages.NonEditableEmailPage;
import org.selenium.pages.SentEmailAlertPage;
import org.selenium.pages.SentPage;
import org.selenium.model.email.EmailCreator;
import org.selenium.model.user.UserCreator;
import org.selenium.util.InboxPageUtils;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.assertEquals;

public class MailServiceTests extends CommonConditions {
    @Test(priority = 1, description = "Save the email as a draft")
    public void SaveMailTest() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        Email email = EmailCreator.withRecipientFromPropertyAndGeneratedSubjectAndBody();
        inboxPage = createAndSaveDraftOfSimpleEmail(inboxPage, email);
        MessageEditorPage messageEditorPage = inboxPage
                .waitLoadPage()
                .switchToDraftsPage()
                .waitLoadPage()
                .openFirstMailForEdit();
        validateRecipientSubjectAndBodyOfDraft(messageEditorPage, email);
    }

    @Test(priority = 2, description = "Send the email")
    public void SendMailTest() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        DraftsPage draftsPage = inboxPage.waitLoadPage().switchToDraftsPage();
        Email draftEmail = new Email();
        MessageEditorPage messageEditorPage = sendTheFirstEmailFromDrafts(draftsPage, draftEmail)
                .waitLoadPage()
                .openFirstMailForEdit();
        validateSentEmailIsDisappearedInDraftPage(messageEditorPage, draftEmail);
        validateEmailIsSent(messageEditorPage, draftEmail);
    }

    @Test(priority = 3, description = "Drag-n-drop email from Inbox to News letters")
    public void MoveEmailFromInboxToNewsLetters() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        Email receivedEmail = new Email();
        getSubjectOfTheFirstEmailFromInbox(inboxPage, receivedEmail);
        NewsLettersPage newsLettersPage = inboxPage.moveTheFirstEmailToNewsLetters()
                .switchToNewsLettersPage().waitLoadPage();
        new SoftAssert().assertTrue(newsLettersPage.ifEmailSubjectExistsInNewsLettersPage(receivedEmail.getSubject()));
    }

    @Test(priority = 4, description = "Move email from News letters to Inbox with email pop-up menu")
    public void MoveEmailFromNewsLettersToInboxWithPopupMenu() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        NewsLettersPage newsLettersPage = inboxPage.waitLoadPage().switchToNewsLettersPage().waitLoadPage();
        Email newsLetter = new Email();
        getSubjectOfTheFirstEmailFromNewsLettersPage(newsLettersPage, newsLetter);
        inboxPage = newsLettersPage
                .openEmailPopupMenu().waitLoadPage()
                .openSecondLevelMenuMoveTo().waitLoadPage()
                .moveEmailToInbox().waitLoadPage()
                .switchToInboxPage().waitLoadPage();
        new SoftAssert().assertTrue(inboxPage.ifEmailSubjectExistsInInboxPage(newsLetter.getSubject()));
    }

    private InboxPage createAndSaveDraftOfSimpleEmail(InboxPage inboxPage, Email email) {
        MessageEditorPage messageEditorPage = inboxPage.waitLoadPage()
                .createNewMessage();
        messageEditorPage.waitLoadPage().fillAddressee(email.getRecipient())
                .fillSubject(email.getSubject()).fillBody(email.getBody()).saveDraft();
        return messageEditorPage.closeMessageEditorPageAndSwitchToInboxPage();
    }

    private DraftsPage sendTheFirstEmailFromDrafts(DraftsPage draftsPage, Email email) {
        MessageEditorPage messageEditorPage = draftsPage.waitLoadPage().openFirstMailForEdit();
        email.setSubject(messageEditorPage.getSubjectOfDraft());
        email.setBody(messageEditorPage.getBodyTextOfDraft());
        SentEmailAlertPage sentEmailAlertPage = messageEditorPage.sendEmail();
        return sentEmailAlertPage.waitLoadPage().closeSentEmailAlertPage();
    }

    private void getSubjectOfTheFirstEmailFromInbox(InboxPage inboxPage, Email email) {
        NonEditableEmailPage nonEditableEmailPage = inboxPage.waitLoadPage().openFirstNonEditableEmail();
        email.setSubject(nonEditableEmailPage.getSubjectOfNonEditableEmail());
        nonEditableEmailPage.retutnToInboxPage();
    }

    private void getSubjectOfTheFirstEmailFromNewsLettersPage(NewsLettersPage newsLettersPage, Email email) {
        NonEditableEmailPage nonEditableEmailPage = newsLettersPage.waitLoadPage().openFirstNotEditableEmail();
        email.setSubject(nonEditableEmailPage.getSubjectOfNonEditableEmail());
        nonEditableEmailPage.retutnToInboxPage();
    }

    private void validateRecipientSubjectAndBodyOfDraft(MessageEditorPage messageEditorPage, Email email) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(messageEditorPage.waitLoadPage().getRecipientOfDraft(), email.getRecipient(),
                "The recipient is not displayed or wrong");
        softAssert.assertEquals(messageEditorPage.getSubjectOfDraft(), email.getSubject(),
                "The subject is not displayed or wrong");
        softAssert.assertTrue(messageEditorPage.getBodyTextOfDraft().contains(email.getBody()),
                "The body is not displayed or wrong");
        softAssert.assertAll();
    }

    private void validateSentEmailIsDisappearedInDraftPage(MessageEditorPage messageEditorPage, Email email) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotEquals(messageEditorPage.waitLoadPage().getSubjectOfDraft(), email.getSubject(),
                "The subject is equal");
        softAssert.assertFalse(messageEditorPage.getBodyTextOfDraft().contains(email.getBody()),
                "The body is equal");
        softAssert.assertAll();
    }

    private void validateEmailIsSent(MessageEditorPage messageEditorPage, Email email) {
        DraftsPage draftsPage = messageEditorPage.closeMessageEditorPageAndSwitchToDraftsPage();
        SentPage sentPage = draftsPage.waitLoadPage().switchToSentPage();
        NonEditableEmailPage nonEditableEmailPage = sentPage.waitLoadPage().openFirstNonEditableEmail();
        assertEquals(nonEditableEmailPage.waitLoadPage().getSubjectOfNonEditableEmail(), email.getSubject(),
                "The subject is not displayed or wrong");
    }

}
