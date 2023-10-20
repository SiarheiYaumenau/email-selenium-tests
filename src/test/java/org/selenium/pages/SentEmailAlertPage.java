package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class SentEmailAlertPage extends MailPage {
    private final Logger logger = LogManager.getLogger(SentEmailAlertPage.class);
    @FindBy (css = "a.layer__link[href^='/sent/']")
    private WebElement notificationSentMessage;
    @FindBy (css = "span.button2__wrapper.button2__wrapper_centered > span.button2__ico > svg.ico.ico_16-close.ico_size_s")
    private WebElement closeIcon;

    public SentEmailAlertPage(WebDriver driver) {
        super(driver);
    }
    public SentEmailAlertPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(notificationSentMessage);
        logger.info("Sent Email Alert page loaded successfully.");
        return this;
    }

    public DraftsPage closeSentEmailAlertPage() {
        new Waits(driver).waitForElementToBeClickable(closeIcon);
        closeIcon.click();
        logger.info("Sent Email Alert page closed.");
        return new DraftsPage(driver);
    }
}
