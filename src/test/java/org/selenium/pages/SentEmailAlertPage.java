package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class SentEmailAlertPage extends MailPage {
    @FindBy (css = "a.layer__link[href^='/sent/']")
    private WebElement notificationSentMessage;
    @FindBy (css = "span.button2__wrapper.button2__wrapper_centered > span.button2__ico > svg.ico.ico_16-close.ico_size_s")
    private WebElement closeIcon;

    public SentEmailAlertPage(WebDriver driver) {
        super(driver);
    }
    public SentEmailAlertPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(notificationSentMessage);
        return this;
    }

    public DraftsPage closeSentEmailAlertPage() {
        new Waits(driver).waitForElementToBeClickable(closeIcon);
        closeIcon.click();
        return new DraftsPage(driver);
    }

}
