package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;

public class InboxPage extends MailPage {
    @FindBy (className = "ph-avatar-img")
    private WebElement accountIcon;
    @FindBy (css = "span.compose-button__txt")
    private WebElement composeButton;
    @FindBy (css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy (css = "a.nav__item[href^='/drafts/']")
    private WebElement draftTab;
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/inbox/']:first-of-type")
    private WebElement topMailsRow;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public InboxPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(inboxTab);
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        return this;
    }

    public String getAccountName() {
        new Waits(driver).waitForElementToBeClickable(accountIcon);
        return accountIcon.getAttribute("alt");
    }

    public MessageEditorPage createNewMessage() {
        new Waits(driver).waitForElementToBeClickable(composeButton);
        composeButton.click();
        return new MessageEditorPage(driver);
    }
    public DraftsPage switchToDraftsPage() {
        new Waits(driver).waitForElementToBeClickable(draftTab);
        draftTab.click();
        return new DraftsPage(driver);
    }

}
