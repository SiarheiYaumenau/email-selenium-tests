package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;

public class SentPage extends MailPage {
    @FindBy (css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/sent/']:first-of-type")
    private WebElement topMailsRow;

    public SentPage(WebDriver driver) {
        super(driver);
    }

    public SentPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(inboxTab);
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        return this;
    }

    public SentEmailPage openFirstMailForEdit() {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new SentEmailPage(driver);
    }

}
