package org.selenium.test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pages.DraftsPage;
import org.selenium.pages.EnterPasswordPage;
import org.selenium.pages.InboxPage;
import org.selenium.pages.MessageEditorPage;
import org.selenium.pages.SentEmailAlertPage;
import org.selenium.pages.SentEmailPage;
import org.selenium.pages.SentPage;
import org.selenium.pages.StartPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MailServiceTests {
    private final String USER_NAME = "swebdriver";
    private final String PASSWORD = "Support-1234";
    private String recipient = "selenium.test@internet.ru";
    private String subject = generateSubjectText();
    private String body = generateBodyText();
    private WebDriver driver;
    @BeforeMethod (alwaysRun = true)
    public void browserSetup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test (description = "Login to Mail")
    public void MailLoginTest() {
        InboxPage inboxPage = getInboxMailPage();
        String accountName = inboxPage.openPage().getAccountName();
        String expectedAccountName = USER_NAME + "@mail.ru";
        Assert.assertEquals(accountName, expectedAccountName,
                "Inbox page is not displayed or the account name is wrong");
    }

    @Test (description = "Save the email as a draft")
    public void SaveMailTest() {
        InboxPage inboxPage = getInboxMailPage();
        inboxPage = createAndSaveDraftOfSimpleEmail(inboxPage);
        DraftsPage draftsPage = inboxPage.openPage().switchToDraftsPage();
        MessageEditorPage messageEditorPage = draftsPage.openPage().openFirstMailForEdit();
        validateRecipientSubjectAndBodyOfDraft(messageEditorPage);
    }

    @Test (description = "Send the email")
    public void SendMailTest() {
        InboxPage inboxPage = getInboxMailPage();
        DraftsPage draftsPage = inboxPage.openPage().switchToDraftsPage();
        draftsPage = sendTheFirstEmailFromDrafts(draftsPage);
        MessageEditorPage messageEditorPage = draftsPage.openPage().openFirstMailForEdit();
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
                .openPage().LoginAndConfirm(USER_NAME);
        enterPasswordPage.openPage();
        return enterPasswordPage.inputPasswordAndConfirm(PASSWORD);
    }

    private InboxPage createAndSaveDraftOfSimpleEmail(InboxPage inboxPage) {
        MessageEditorPage messageEditorPage = inboxPage.openPage()
                .createNewMessage();
        messageEditorPage.openPage().fillAddressee(recipient).fillSubject(subject).fillBody(body).saveDraft();
        inboxPage = messageEditorPage.closeMessageEditorPageAndSwitchToInboxPage();
        return inboxPage;
    }

    private DraftsPage sendTheFirstEmailFromDrafts(DraftsPage draftsPage) {
        MessageEditorPage messageEditorPage = draftsPage.openPage().openFirstMailForEdit();
        subject = messageEditorPage.getSubjectOfDraft();
        body = messageEditorPage.getBodyTextOfDraft();
        SentEmailAlertPage sentEmailAlertPage = messageEditorPage.sendEmail();
        draftsPage = sentEmailAlertPage.openPage().closeSentEmailAlertPage();
        return draftsPage;
    }

    private void validateRecipientSubjectAndBodyOfDraft(MessageEditorPage messageEditorPage) {
        Assert.assertEquals(messageEditorPage.openPage().getRecipientOfDraft(), recipient,
                "The recipient is not displayed or wrong");
        Assert.assertEquals(messageEditorPage.getSubjectOfDraft(), subject,
                "The subject is not displayed or wrong");
        Assert.assertTrue(messageEditorPage.getBodyTextOfDraft().contains(body),
                "The body is not displayed or wrong");
    }

    private void validateSentEmailIsDisappearedInDraftPage(MessageEditorPage messageEditorPage) {
        messageEditorPage.openPage();
        Assert.assertNotEquals(messageEditorPage.getSubjectOfDraft(), subject,
                "The subject is equal");
        Assert.assertFalse(messageEditorPage.getBodyTextOfDraft().contains(body),
                "The body is equal");
    }

    private void validateEmailIsSent(MessageEditorPage messageEditorPage) {
        DraftsPage draftsPage = messageEditorPage.closeMessageEditorPageAndSwitchToDraftsPage();
        SentPage sentPage = draftsPage.openPage().switchToSentPage();
        SentEmailPage sentEmailPage = sentPage.openPage().openFirstMailForEdit();
        Assert.assertEquals(sentEmailPage.openPage().getSubjectOfSentEmail(), subject,
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
