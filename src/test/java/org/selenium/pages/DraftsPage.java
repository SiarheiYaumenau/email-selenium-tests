package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class DraftsPage extends MailPage {
    private final Logger logger = LogManager.getLogger(DraftsPage.class);
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
        logger.info("Drafts page loaded successfully.");
        return this;
    }

    public SentPage switchToSentPage() {
        logger.info("Switching to Sent page.");
        new Waits(driver).waitForElementToBeClickable(sentTab);
        sentTab.click();
        return new SentPage(driver);
    }
    public MessageEditorPage openFirstMailForEdit() {
        logger.info("Opening the first email for edit.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new MessageEditorPage(driver);
    }

}
