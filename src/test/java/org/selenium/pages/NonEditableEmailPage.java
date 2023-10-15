package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;

public class NonEditableEmailPage extends MailPage {
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
        return this;
    }

    public String getSubjectOfNonEditableEmail() {
        new Waits(driver).waitForVisibilityOf(subjectField);
        return subjectField.getText();
    }

    public InboxPage retutnToInboxPage() {
        new Waits(driver).waitForElementToBeClickable(returnButton);
        returnButton.click();
        return new InboxPage(driver);
    }
}
