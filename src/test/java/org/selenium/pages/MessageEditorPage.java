package org.selenium.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class MessageEditorPage extends MailPage {
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
        return this;
    }

    public MessageEditorPage fillAddressee(String recipients) {
        new Waits(driver).waitForVisibilityOf(emptyRecipientsField);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", emptyRecipientsField, recipients);
        return this;
    }
    public MessageEditorPage fillSubject(String subject) {
        new Waits(driver).waitForVisibilityOf(subjectField);
        subjectField.sendKeys(subject);
        return this;
    }
    public MessageEditorPage fillBody(String body) {
        new Waits(driver).waitForVisibilityOf(bodyField);
        bodyField.sendKeys(body);
        return this;
    }
    public MessageEditorPage saveDraft() {
        new Waits(driver).waitForElementToBeClickable(saveButton);
        saveButton.click();
        return this;
    }
    public DraftsPage closeMessageEditorPageAndSwitchToDraftsPage() {
        new Waits(driver).waitForElementToBeClickable(cancelButton);
        cancelButton.click();
        return new DraftsPage(driver);
    }
    public InboxPage closeMessageEditorPageAndSwitchToInboxPage() {
        new Waits(driver).waitForElementToBeClickable(cancelButton);
        cancelButton.click();
        return new InboxPage(driver);
    }
    public SentEmailAlertPage sendEmail() {
        new Waits(driver).waitForElementToBeClickable(sendButton);
        sendButton.click();
        return new SentEmailAlertPage(driver);
    }

    public String getRecipientOfDraft() {
        new Waits(driver).waitForVisibilityOf(firstRecipient);
        return firstRecipient.getText();
    }

    public String getSubjectOfDraft() {
        new Waits(driver).waitForVisibilityOf(subjectField);
        return subjectField.getAttribute("value");
    }

    public String getBodyTextOfDraft() {
        new Waits(driver).waitForVisibilityOf(bodyField);
        return bodyField.getText();
    }
}
