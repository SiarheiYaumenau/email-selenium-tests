package org.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;

public class NewsLettersPage extends MailPage {
    @FindBy(css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/newsletters/']:first-of-type")
    private WebElement topMailsRow;

    public NewsLettersPage(WebDriver driver) {
        super(driver);
    }

    public NewsLettersPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(inboxTab);
        return this;
    }

    public NonEditableEmailPage openFirstNotEditableEmail() {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new NonEditableEmailPage(driver);
    }

    public EmailPopupMenu openEmailPopupMenu() {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        Actions action = new Actions(driver);
        action.contextClick(topMailsRow).build().perform();
        return new EmailPopupMenu(driver);
    }
    public InboxPage switchToInboxPage() {
        new Waits(driver).waitForElementToBeClickable(inboxTab);
        inboxTab.click();
        return new InboxPage(driver);
    }
}
