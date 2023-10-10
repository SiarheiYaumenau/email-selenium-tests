package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
public abstract class MailPage {
    protected WebDriver driver;
    protected abstract MailPage waitLoadPage();
    protected MailPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}

