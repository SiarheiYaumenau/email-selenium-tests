package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;

public class SentEmailPage extends MailPage {
    @FindBy (css = ".thread__letter")
    private WebElement treadLetter;
    @FindBy (className = "thread-subject")
    private WebElement subjectField;

    public SentEmailPage(WebDriver driver) {
        super(driver);
    }
    public SentEmailPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(treadLetter);
        return this;
    }

    public String getSubjectOfSentEmail() {
        new Waits(driver).waitForVisibilityOf(subjectField);
        return subjectField.getText();
    }

}
