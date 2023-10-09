package org.selenium.test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pages.*;
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
        MailsGrid mailsGrid = inboxPage.openPage().switchToDraftsGrid();
        MessageEditorPage messageEditorPage = mailsGrid.openPage().openFirstMailForEdit();
        validateRecipientSubjectAndBody(messageEditorPage);
    }

    @Test (description = "Send the email")
    public void SendMailTest() {
        InboxPage inboxPage = getInboxMailPage();
        MailsGrid mailsGrid = inboxPage.openPage().switchToDraftsGrid();
        MessageEditorPage messageEditorPage = mailsGrid.openPage().openFirstMailForEdit();
        recipient = messageEditorPage.openPage().getRecipientOfMail();
        subject = messageEditorPage.getSubjectOfMail();
        body = messageEditorPage.getBodyTextOfMail();
        SentEmailAlertPage sentEmailAlertPage = messageEditorPage.sendEmail();
        mailsGrid = sentEmailAlertPage.openPage().closeSentEmailAlertPage();
        messageEditorPage = mailsGrid.openPage().openFirstMailForEdit();
        messageEditorPage.openPage();
        validateSubjectAndBodyAreDifferent(messageEditorPage);
        inboxPage = messageEditorPage.closeMessageEditorPage();
        mailsGrid = inboxPage.openPage().switchToSentPage();
        messageEditorPage = mailsGrid.openPage().openFirstMailForEdit();
        messageEditorPage.openPage();
        validateRecipientSubjectAndBody(messageEditorPage);
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
        inboxPage = messageEditorPage.closeMessageEditorPage();
        return inboxPage;
    }


    private void validateRecipientSubjectAndBody(MessageEditorPage messageEditorPage) {
        Assert.assertEquals(messageEditorPage.getRecipientOfMail(), recipient,
                "The recipient is not displayed or wrong");
        Assert.assertEquals(messageEditorPage.getSubjectOfMail(), subject,
                "The subject is not displayed or wrong");
        Assert.assertTrue(messageEditorPage.getBodyTextOfMail().contains(body),
                "The body is not displayed or wrong");
    }

    private void validateSubjectAndBodyAreDifferent(MessageEditorPage messageEditorPage) {
        Assert.assertNotEquals(messageEditorPage.getSubjectOfMail(), subject,
                "The subject is equal");
        Assert.assertFalse(messageEditorPage.getBodyTextOfMail().contains(body),
                "The body is equal");
    }

    private String generateSubjectText() {
        String[] subjects = {
                "Subject: Meeting Tomorrow",
                "Subject: Important Update",
                "Subject: Reminder",
                "Subject: New Opportunity",
                "Subject: Project Status"
        };
        int randomIndex = (int) (Math.random() * subjects.length);
        return subjects[randomIndex];
    }

    private String generateBodyText() {
        String[] bodyTexts = {
                "Body: Hello, I hope this message finds you well.",
                "Body: Please find attached the requested documents.",
                "Body: I wanted to update you on our progress.",
                "Body: Thanks for your prompt response.",
                "Body: Don't forget our meeting next week."
        };
        int randomIndex = (int) (Math.random() * bodyTexts.length);
        return bodyTexts[randomIndex];
    }

}
