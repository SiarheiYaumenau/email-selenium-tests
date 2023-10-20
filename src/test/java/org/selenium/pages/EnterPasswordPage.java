package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class EnterPasswordPage extends MailPage {
    private final Logger logger = LogManager.getLogger(EnterPasswordPage.class);
    @FindBy(name = "password")
    private WebElement passwordField;
    @FindBy(css = "button[data-test-id='submit-button']")
    private WebElement submitButton;
    public EnterPasswordPage(WebDriver driver) {
        super(driver);
    }

    public EnterPasswordPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(passwordField);
        new Waits(driver).waitForElementToBeClickable(submitButton);
        logger.info("Enter Password page loaded successfully.");
        return this;
    }

    public InboxPage inputPasswordAndConfirm(String password) {
        passwordField.sendKeys(password);
        submitButton.click();
        logger.info("Password entered and confirmed.");
        return new InboxPage(driver);
    }
}
