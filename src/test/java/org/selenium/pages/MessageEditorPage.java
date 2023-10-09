package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class MessageEditorPage extends MailPage {
    @FindBy (css = "button[type='button'][data-test-id='save']")
    private WebElement saveButton;
    @FindBy (css = "button[type='button'][data-test-id='cancel']")
    private WebElement cancelButton;
    @FindBy (css = "button[type='button'][data-test-id='send']")
    private WebElement sendButton;
    @FindBy (className = "container--H9L5q")
    private WebElement emptyRecipientsField;
    @FindBy (className = "text--1tHKB")
    private WebElement firstRecipient;
    @FindBy (name = "Subject")
    private WebElement subjectField;
    @FindBy (xpath = "//div[@role='textbox']")
    private WebElement bodyField;
    @FindBy (css = "span.notify__message span.notify__message__text")
    private WebElement notificationSavedMessage;

    public MessageEditorPage(WebDriver driver) {
        super(driver);
    }
    public MessageEditorPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(saveButton));
        return this;
    }

    public MessageEditorPage fillAddressee(String recipients) {
        emptyRecipientsField.sendKeys(recipients);
        return this;
    }
    public MessageEditorPage fillSubject(String subject) {
        subjectField.sendKeys(subject);
        return this;
    }
    public MessageEditorPage fillBody(String body) {
        bodyField.sendKeys(body);
        return this;
    }
    public MessageEditorPage saveDraft() {
        saveButton.click();
        assert notificationSavedMessage != null : "Saved message alert does not exist";
        return this;
    }
    public InboxPage closeMessageEditorPage() {
        cancelButton.click();
        return new InboxPage(driver);
    }
    public SentEmailAlertPage sendEmail() {
        sendButton.click();
        return new SentEmailAlertPage(driver);
    }

    public String getRecipientOfMail() {
        return firstRecipient.getText();
    }

    public String getSubjectOfMail() {
        return subjectField.getAttribute("value");
    }

    public String getBodyTextOfMail() {
        return bodyField.getText();
    }
}
