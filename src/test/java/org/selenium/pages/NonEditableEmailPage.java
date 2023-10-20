package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class NonEditableEmailPage extends MailPage {
    private final Logger logger = LogManager.getLogger(NonEditableEmailPage.class);
    @FindBy (css = ".thread__letter")
    private WebElement treadLetter;
    @FindBy (className = "thread-subject")
    private WebElement subjectField;
    @FindBy (css = "svg.ico.ico_16-navigation\\:arrow_previous")
    private  WebElement returnButton;

    public NonEditableEmailPage(WebDriver driver) {
        super(driver);
    }
    public NonEditableEmailPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(treadLetter);
        logger.info("Non-Editable Email page loaded successfully.");
        return this;
    }

    public String getSubjectOfNonEditableEmail() {
        new Waits(driver).waitForVisibilityOf(subjectField);
        String subject = subjectField.getText();
        logger.info("Subject of the non-editable email: " + subject);
        return subject;
    }

    public InboxPage retutnToInboxPage() {
        new Waits(driver).waitForElementToBeClickable(returnButton);
        returnButton.click();
        logger.info("Returned to the Inbox page.");
        return new InboxPage(driver);
    }
}
