package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;
import org.selenium.model.User;
import org.selenium.service.UserCreator;

public class StartPage extends MailPage {
    private static final String STARTAPP_URL = "https://e.mail.ru/";
    @FindBy(name = "username")
    private WebElement userNameField;
    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;
    public StartPage(WebDriver driver) {
        super(driver);
    }

    public StartPage waitLoadPage() {
        driver.get(STARTAPP_URL);
        new Waits(driver).waitForVisibilityOf(userNameField);
        return this;
    }

    public EnterPasswordPage LoginAndConfirm(String userName) {
        new Waits(driver).waitForVisibilityOf(userNameField);
        new Waits(driver).waitForElementToBeClickable(submitButton);
        userNameField.sendKeys(userName);
        submitButton.click();
        return new EnterPasswordPage(driver);
    }

}
