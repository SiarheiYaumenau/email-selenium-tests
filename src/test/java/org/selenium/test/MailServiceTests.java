package org.selenium.test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pages.DraftsPage;
import org.selenium.pages.EnterPasswordPage;
import org.selenium.pages.InboxPage;
import org.selenium.pages.MessageEditorPage;
import org.selenium.pages.SentEmailAlertPage;
import org.selenium.pages.SentEmailPage;
import org.selenium.pages.SentPage;
import org.selenium.pages.StartPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import static org.testng.Assert.assertEquals;

public class MailServiceTests {
    private static final String USER_NAME = "swebdriver";
    private static final String PASSWORD = "Support-1234";
    private String recipient;
    private String subject;
    private String body;
    private WebDriver driver;
    @BeforeMethod (alwaysRun = true)
    public void browserSetup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        recipient = "selenium.test@internet.ru";
    }

    @Test (description = "Login to Mail")
    public void MailLoginTest() {
        InboxPage inboxPage = getInboxMailPage();
        String accountName = inboxPage
                .waitLoadPage()
                .getAccountName();
        String expectedAccountName = USER_NAME + "@mail.ru";
        assertEquals(accountName, expectedAccountName,
                "Inbox page is not displayed or the account name is wrong");
    }

    @Test (description = "Save the email as a draft")
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

    @Test (description = "Send the email")
    public void SendMailTest() {
        InboxPage inboxPage = getInboxMailPage();
        DraftsPage draftsPage = inboxPage.waitLoadPage().switchToDraftsPage();
        MessageEditorPage messageEditorPage = sendTheFirstEmailFromDrafts(draftsPage)
                .waitLoadPage()
                .openFirstMailForEdit();
        validateSentEmailIsDisappearedInDraftPage(messageEditorPage);
        validateEmailIsSent(messageEditorPage);
    }

    @AfterMethod (alwaysRun = true)
    public void browserTearDown() {
        driver.quit();
        driver = null;
    }

    private InboxPage getInboxMailPage() {
        EnterPasswordPage enterPasswordPage = new StartPage(driver)
                .waitLoadPage().LoginAndConfirm(USER_NAME);
        enterPasswordPage.waitLoadPage();
        return enterPasswordPage.inputPasswordAndConfirm(PASSWORD);
    }

    private InboxPage createAndSaveDraftOfSimpleEmail(InboxPage inboxPage) {
        subject = generateSubjectText();
        body = generateBodyText();
        MessageEditorPage messageEditorPage = inboxPage.waitLoadPage()
                .createNewMessage();
        messageEditorPage.waitLoadPage().fillAddressee(recipient).fillSubject(subject).fillBody(body).saveDraft();
        inboxPage = messageEditorPage.closeMessageEditorPageAndSwitchToInboxPage();
        return inboxPage;
    }

    private DraftsPage sendTheFirstEmailFromDrafts(DraftsPage draftsPage) {
        MessageEditorPage messageEditorPage = draftsPage.waitLoadPage().openFirstMailForEdit();
        subject = messageEditorPage.getSubjectOfDraft();
        body = messageEditorPage.getBodyTextOfDraft();
        SentEmailAlertPage sentEmailAlertPage = messageEditorPage.sendEmail();
        draftsPage = sentEmailAlertPage.waitLoadPage().closeSentEmailAlertPage();
        return draftsPage;
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
        SentEmailPage sentEmailPage = sentPage.waitLoadPage().openFirstMailForEdit();
        assertEquals(sentEmailPage.waitLoadPage().getSubjectOfSentEmail(), subject,
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
                "Body: Don't forget our meeting next week."  + (int) (Math.random() * 100)
        };
        int randomIndex = (int) (Math.random() * bodyTexts.length);
        return bodyTexts[randomIndex];
    }

}
