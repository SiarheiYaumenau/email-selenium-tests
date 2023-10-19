package org.selenium.test;

import org.selenium.pages.DraftsPage;
import org.selenium.pages.InboxPage;
import org.selenium.pages.MessageEditorPage;
import org.selenium.pages.NewsLettersPage;
import org.selenium.pages.NonEditableEmailPage;
import org.selenium.pages.SentEmailAlertPage;
import org.selenium.pages.SentPage;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class MailServiceTests extends CommonConditions {
    private final String recipient = "selenium.test@internet.ru";
    private String subject;
    private String body;

    @Test(priority = 1, description = "Save the email as a draft")
    public void SaveMailTest() {
        InboxPage inboxPage = getInboxMailPage();
        inboxPage = createAndSaveDraftOfSimpleEmail(inboxPage);
        MessageEditorPage messageEditorPage = inboxPage
                .waitLoadPage()
                .switchToDraftsPage()
                .waitLoadPage()
                .openFirstMailForEdit();
        validateRecipientSubjectAndBodyOfDraft(messageEditorPage);
    }

    @Test(priority = 2, description = "Send the email")
    public void SendMailTest() {
        InboxPage inboxPage = getInboxMailPage();
        DraftsPage draftsPage = inboxPage.waitLoadPage().switchToDraftsPage();
        MessageEditorPage messageEditorPage = sendTheFirstEmailFromDrafts(draftsPage)
                .waitLoadPage()
                .openFirstMailForEdit();
        validateSentEmailIsDisappearedInDraftPage(messageEditorPage);
        validateEmailIsSent(messageEditorPage);
    }

    @Test(priority = 3, description = "Drag-n-drop email from Inbox to News letters")
    public void MoveEmailFromInboxToNewsLetters() {
        InboxPage inboxPage = getInboxMailPage();
        getSubjectOfTheFirstEmailFromInbox(inboxPage);
        NewsLettersPage newsLettersPage = inboxPage.moveTheFirstEmailToNewsLetters()
                .switchToNewsLettersPage().waitLoadPage();
        assertTrue(newsLettersPage.ifEmailSubjectExistsInNewsLettersPage(subject));
    }

    @Test(priority = 4, description = "Move email from News letters to Inbox with email pop-up menu")
    public void MoveEmailFromNewsLettersToInboxWithPopupMenu() {
        InboxPage inboxPage = getInboxMailPage();
        NewsLettersPage newsLettersPage = inboxPage.waitLoadPage().switchToNewsLettersPage().waitLoadPage();
        getSubjectOfTheFirstEmailFromNewsLettersPage(newsLettersPage);
        inboxPage = newsLettersPage
                .openEmailPopupMenu().waitLoadPage()
                .openSecondLevelMenuMoveTo().waitLoadPage()
                .moveEmailToInbox().waitLoadPage()
                .switchToInboxPage().waitLoadPage();
        assertTrue(inboxPage.ifEmailSubjectExistsInInboxPage(subject));
    }

    private InboxPage createAndSaveDraftOfSimpleEmail(InboxPage inboxPage) {
        subject = generateSubjectText();
        body = generateBodyText();
        MessageEditorPage messageEditorPage = inboxPage.waitLoadPage()
                .createNewMessage();
        messageEditorPage.waitLoadPage().fillAddressee(recipient).fillSubject(subject).fillBody(body).saveDraft();
        return messageEditorPage.closeMessageEditorPageAndSwitchToInboxPage();
    }

    private DraftsPage sendTheFirstEmailFromDrafts(DraftsPage draftsPage) {
        MessageEditorPage messageEditorPage = draftsPage.waitLoadPage().openFirstMailForEdit();
        subject = messageEditorPage.getSubjectOfDraft();
        body = messageEditorPage.getBodyTextOfDraft();
        SentEmailAlertPage sentEmailAlertPage = messageEditorPage.sendEmail();
        return sentEmailAlertPage.waitLoadPage().closeSentEmailAlertPage();
    }

    private void getSubjectOfTheFirstEmailFromInbox(InboxPage inboxPage) {
        NonEditableEmailPage nonEditableEmailPage = inboxPage.waitLoadPage().openFirstNonEditableEmail();
        subject = nonEditableEmailPage.getSubjectOfNonEditableEmail();
        nonEditableEmailPage.retutnToInboxPage();
    }

    private void getSubjectOfTheFirstEmailFromNewsLettersPage(NewsLettersPage newsLettersPage) {
        NonEditableEmailPage nonEditableEmailPage = newsLettersPage.waitLoadPage().openFirstNotEditableEmail();
        subject = nonEditableEmailPage.getSubjectOfNonEditableEmail();
        nonEditableEmailPage.retutnToInboxPage();
    }

    private void validateRecipientSubjectAndBodyOfDraft(MessageEditorPage messageEditorPage) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(messageEditorPage.waitLoadPage().getRecipientOfDraft(), recipient,
                "The recipient is not displayed or wrong");
        softAssert.assertEquals(messageEditorPage.getSubjectOfDraft(), subject,
                "The subject is not displayed or wrong");
        softAssert.assertTrue(messageEditorPage.getBodyTextOfDraft().contains(body),
                "The body is not displayed or wrong");
        softAssert.assertAll();
    }

    private void validateSentEmailIsDisappearedInDraftPage(MessageEditorPage messageEditorPage) {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotEquals(messageEditorPage.waitLoadPage().getSubjectOfDraft(), subject,
                "The subject is equal");
        softAssert.assertFalse(messageEditorPage.getBodyTextOfDraft().contains(body),
                "The body is equal");
        softAssert.assertAll();
    }

    private void validateEmailIsSent(MessageEditorPage messageEditorPage) {
        DraftsPage draftsPage = messageEditorPage.closeMessageEditorPageAndSwitchToDraftsPage();
        SentPage sentPage = draftsPage.waitLoadPage().switchToSentPage();
        NonEditableEmailPage nonEditableEmailPage = sentPage.waitLoadPage().openFirstNonEditableEmail();
        assertEquals(nonEditableEmailPage.waitLoadPage().getSubjectOfNonEditableEmail(), subject,
                "The subject is not displayed or wrong");
    }

    private String generateSubjectText() {
        String[] subjects = {
                "Subject: Meeting Tomorrow " + (int) (Math.random() * 100),
                "Subject: Important Update " + (int) (Math.random() * 100),
                "Subject: Reminder " + (int) (Math.random() * 100),
                "Subject: New Opportunity " + (int) (Math.random() * 100),
                "Subject: Project Status " + (int) (Math.random() * 100)
        };
        int randomIndex = (int) (Math.random() * subjects.length);
        return subjects[randomIndex];
    }

    private String generateBodyText() {
        String[] bodyTexts = {
                "Body: Hello, I hope this message finds you well." + (int) (Math.random() * 100),
                "Body: Please find attached the requested documents." + (int) (Math.random() * 100),
                "Body: I wanted to update you on our progress." + (int) (Math.random() * 100),
                "Body: Thanks for your prompt response." + (int) (Math.random() * 100),
                "Body: Don't forget our meeting next week." + (int) (Math.random() * 100)
        };
        int randomIndex = (int) (Math.random() * bodyTexts.length);
        return bodyTexts[randomIndex];
    }

}
