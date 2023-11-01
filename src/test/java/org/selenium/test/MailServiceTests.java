package org.selenium.test;

import org.selenium.actions.DraftPageActions;
import org.selenium.actions.InboxPageActions;
import org.selenium.actions.NewsLettersPageActions;
import org.selenium.model.email.Email;
import org.selenium.model.user.User;
import org.selenium.pages.*;
import org.selenium.model.email.EmailCreator;
import org.selenium.model.user.UserCreator;
import org.selenium.util.InboxPageUtils;
import org.selenium.validators.MessageEditorPageValidators;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class MailServiceTests extends CommonConditions {
    @Test(priority = 1, description = "Save the email as a draft")
    public void SaveMailTest() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        Email email = EmailCreator.withSignatureInBody("The best regards, John");
        new InboxPageActions(inboxPage).createAndSaveDraftOfSimpleEmail(email);
        MessageEditorPage messageEditorPage = inboxPage
                .waitLoadPage()
                .switchToDraftsPage()
                .waitLoadPage()
                .openFirstMailForEdit();
        new MessageEditorPageValidators(messageEditorPage)
                .validateRecipientSubjectAndBodyOfDraft(email);
    }

    @Test(priority = 2, description = "Send the email")
    public void SendMailTest() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        DraftsPage draftsPage = inboxPage.waitLoadPage().switchToDraftsPage();
        Email draftEmail = new Email();
        MessageEditorPage messageEditorPage = new DraftPageActions(draftsPage)
                .sendTheFirstEmailFromDrafts(draftEmail)
                .waitLoadPage()
                .openFirstMailForEdit();
        new MessageEditorPageValidators(messageEditorPage)
                .validateSentEmailIsDisappearedInDraftPage(draftEmail);
        new MessageEditorPageValidators(messageEditorPage)
                .validateEmailIsSent(draftEmail);
    }

    @Test(priority = 3, description = "Drag-n-drop email from Inbox to News letters")
    public void MoveEmailFromInboxToNewsLetters() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        Email receivedEmail = new Email();
        new InboxPageActions(inboxPage).
                getSubjectOfTheFirstEmailFromInbox(receivedEmail);
        NewsLettersPage newsLettersPage = inboxPage
                .moveTheFirstEmailToNewsLetters()
                .switchToNewsLettersPage().waitLoadPage();
        new SoftAssert().assertTrue(newsLettersPage.ifEmailSubjectExistsInNewsLettersPage(receivedEmail.getSubject()));
    }

    @Test(priority = 4, description = "Move email from News letters to Inbox with email pop-up menu")
    public void MoveEmailFromNewsLettersToInboxWithPopupMenu() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        NewsLettersPage newsLettersPage = inboxPage.waitLoadPage().switchToNewsLettersPage().waitLoadPage();
        Email newsLetter = new Email();
        new NewsLettersPageActions(newsLettersPage)
                .getSubjectOfTheFirstEmailFromNewsLettersPage(newsLetter);
        inboxPage = newsLettersPage
                .openEmailPopupMenu().waitLoadPage()
                .openSecondLevelMenuMoveTo().waitLoadPage()
                .moveEmailToInbox().waitLoadPage()
                .switchToInboxPage().waitLoadPage();
        new SoftAssert().assertTrue(inboxPage.ifEmailSubjectExistsInInboxPage(newsLetter.getSubject()));
    }
}
