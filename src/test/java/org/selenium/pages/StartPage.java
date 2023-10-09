package org.selenium.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class StartPage extends MailPage {
    private static final String STARTAPP_URL = "https://e.mail.ru/";
    @FindBy(name = "username")
    private WebElement userNameField;
    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;
    public StartPage(WebDriver driver) {
        super(driver);
    }

    public StartPage openPage() {
        driver.get(STARTAPP_URL);
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(userNameField));
        return this;
    }

    public EnterPasswordPage LoginAndConfirm(String userName) {
        userNameField.sendKeys(userName);
        submitButton.click();
        return new EnterPasswordPage(driver);
    }
}
