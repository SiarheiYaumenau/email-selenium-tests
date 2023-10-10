package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SentEmailPage extends MailPage {
    @FindBy (css = ".thread__letter")
    private WebElement treadLetter;
    @FindBy (className = "thread-subject")
    private WebElement subjectField;

    public SentEmailPage(WebDriver driver) {
        super(driver);
    }
    public SentEmailPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(treadLetter));
        return this;
    }

    public String getSubjectOfSentEmail() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(subjectField));
        return subjectField.getText();
    }

}
