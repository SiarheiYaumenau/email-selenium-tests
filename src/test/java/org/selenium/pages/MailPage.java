package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
public abstract class MailPage {
    protected WebDriver driver;
    protected abstract MailPage openPage();
    protected final int WAIT_TIMEOUT_SECONDS = 10;
    protected MailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}

