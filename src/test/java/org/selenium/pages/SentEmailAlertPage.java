package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class SentEmailAlertPage extends MailPage {
    @FindBy (css = "a.layer__link[href^='/sent/']")
    private WebElement notificationSentMessage;
    @FindBy (css = "span.button2__wrapper.button2__wrapper_centered > span.button2__ico > svg.ico.ico_16-close.ico_size_s")
    private WebElement closeIcon;

    public SentEmailAlertPage(WebDriver driver) {
        super(driver);
    }
    public SentEmailAlertPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(notificationSentMessage));
        assert notificationSentMessage != null : "Sent message alert does not exist";
        return this;
    }

    public MailsGrid closeSentEmailAlertPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(closeIcon));
        closeIcon.click();
        return new MailsGrid(driver);
    }

}
