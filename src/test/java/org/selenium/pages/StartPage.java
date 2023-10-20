package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class StartPage extends MailPage {
    private static final String STARTAPP_URL = "https://e.mail.ru/";
    private final Logger logger = LogManager.getLogger(StartPage.class);
    @FindBy(name = "username")
    private WebElement userNameField;
    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;
    public StartPage(WebDriver driver) {
        super(driver);
    }

    public StartPage waitLoadPage() {
        logger.info("Loading the start page: " + STARTAPP_URL);
        driver.get(STARTAPP_URL);
        new Waits(driver).waitForVisibilityOf(userNameField);
        logger.info("Start page loaded successfully.");
        return this;
    }

    public EnterPasswordPage LoginAndConfirm(String userName) {
        new Waits(driver).waitForVisibilityOf(userNameField);
        new Waits(driver).waitForElementToBeClickable(submitButton);
        userNameField.sendKeys(userName);
        logger.info("Entered username: " + userName);
        submitButton.click();
        logger.info("Clicked the submit button.");
        return new EnterPasswordPage(driver);
    }

}
