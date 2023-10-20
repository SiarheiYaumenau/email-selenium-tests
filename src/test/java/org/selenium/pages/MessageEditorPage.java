package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class MessageEditorPage extends MailPage {
    private final Logger logger = LogManager.getLogger(MessageEditorPage.class);
    @FindBy (css = "button[type='button'][data-test-id='save']")
    private WebElement saveButton;
    @FindBy (css = "button[type='button'][data-test-id='cancel']")
    private WebElement cancelButton;
    @FindBy (css = "button[type='button'][data-test-id='send']")
    private WebElement sendButton;
    @FindBy (className = "container--H9L5q")
    private WebElement emptyRecipientsField;
    @FindBy (css = "span.text--1tHKB")
    private WebElement firstRecipient;
    @FindBy (name = "Subject")
    private WebElement subjectField;
    @FindBy (xpath = "//div[@role='textbox']")
    private WebElement bodyField;

    public MessageEditorPage(WebDriver driver) {
        super(driver);
    }
    public MessageEditorPage waitLoadPage() {
        new Waits(driver).waitForElementToBeClickable(saveButton);
        logger.info("Message Editor page loaded successfully.");
        return this;
    }

    public MessageEditorPage fillAddressee(String recipients) {
        logger.info("Filling addressee: " + recipients);
        new Waits(driver).waitForVisibilityOf(emptyRecipientsField);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", emptyRecipientsField, recipients);
        return this;
    }
    public MessageEditorPage fillSubject(String subject) {
        logger.info("Filling subject: " + subject);
        new Waits(driver).waitForVisibilityOf(subjectField);
        subjectField.sendKeys(subject);
        return this;
    }
    public MessageEditorPage fillBody(String body) {
        logger.info("Filling email body.");
        new Waits(driver).waitForVisibilityOf(bodyField);
        bodyField.sendKeys(body);
        return this;
    }
    public MessageEditorPage saveDraft() {
        logger.info("Saving draft.");
        new Waits(driver).waitForElementToBeClickable(saveButton);
        saveButton.click();
        return this;
    }
    public DraftsPage closeMessageEditorPageAndSwitchToDraftsPage() {
        logger.info("Closing Message Editor and switching to Drafts page.");
        new Waits(driver).waitForElementToBeClickable(cancelButton);
        cancelButton.click();
        return new DraftsPage(driver);
    }
    public InboxPage closeMessageEditorPageAndSwitchToInboxPage() {
        logger.info("Closing Message Editor and switching to Inbox page.");
        new Waits(driver).waitForElementToBeClickable(cancelButton);
        cancelButton.click();
        return new InboxPage(driver);
    }
    public SentEmailAlertPage sendEmail() {
        logger.info("Sending email.");
        new Waits(driver).waitForElementToBeClickable(sendButton);
        sendButton.click();
        return new SentEmailAlertPage(driver);
    }

    public String getRecipientOfDraft() {
        logger.info("Getting recipient of the draft.");
        new Waits(driver).waitForVisibilityOf(firstRecipient);
        return firstRecipient.getText();
    }

    public String getSubjectOfDraft() {
        logger.info("Getting subject of the draft.");
        new Waits(driver).waitForVisibilityOf(subjectField);
        return subjectField.getAttribute("value");
    }

    public String getBodyTextOfDraft() {
        logger.info("Getting body text of the draft.");
        new Waits(driver).waitForVisibilityOf(bodyField);
        return bodyField.getText();
    }
}
