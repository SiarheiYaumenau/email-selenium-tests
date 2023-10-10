package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;

public class DraftsPage extends MailPage {
    @FindBy (css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy (css = "a.nav__item[href^='/sent/']")
    private WebElement sentTab;
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/drafts/']:first-of-type")
    private WebElement topMailsRow;

    public DraftsPage(WebDriver driver) {
        super(driver);
    }

    public DraftsPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(inboxTab);
        new Waits(driver).waitForVisibilityOf(topMailsRow);
        return this;
    }

    public SentPage switchToSentPage() {
        new Waits(driver).waitForElementToBeClickable(sentTab);
        sentTab.click();
        return new SentPage(driver);
    }
    public MessageEditorPage openFirstMailForEdit() {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new MessageEditorPage(driver);
    }

}
