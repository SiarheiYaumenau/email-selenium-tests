package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class SentPage extends MailPage {
    private final Logger logger = LogManager.getLogger(SentPage.class);
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
        logger.info("Sent page loaded successfully.");
        return this;
    }

    public NonEditableEmailPage openFirstNonEditableEmail() {
        logger.info("Opening the first non-editable email.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new NonEditableEmailPage(driver);
    }
}
