package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;
import java.util.List;
import java.util.stream.Collectors;

public class NewsLettersPage extends MailPage {
    private final Logger logger = LogManager.getLogger(NewsLettersPage.class);
    @FindBy(css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/newsletters/']:first-of-type")
    private WebElement topMailsRow;
    @FindAll({@FindBy(css = "span.ll-sj__normal")})
    private List<WebElement> subjects;
    public NewsLettersPage(WebDriver driver) {
        super(driver);
    }

    public NewsLettersPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(inboxTab);
        logger.info("Newsletters page loaded successfully.");
        return this;
    }

    public NonEditableEmailPage openFirstNotEditableEmail() {
        logger.info("Opening the first non-editable email.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new NonEditableEmailPage(driver);
    }

    public EmailPopupMenu openEmailPopupMenu() {
        logger.info("Opening the email popup menu.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        Actions action = new Actions(driver);
        action.contextClick(topMailsRow).build().perform();
        return new EmailPopupMenu(driver);
    }
    public InboxPage switchToInboxPage() {
        logger.info("Switching to Inbox page.");
        new Waits(driver).waitForElementToBeClickable(inboxTab);
        inboxTab.click();
        return new InboxPage(driver);
    }
    public boolean ifEmailSubjectExistsInNewsLettersPage(String emailSubject) {
        logger.info("Checking if email subject exists in Newsletters page.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        List<String> emailSubjects = subjects.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        boolean exists = emailSubjects.contains(emailSubject.toLowerCase());
        logger.info(exists ? "Email subject '" + emailSubject + "' exists in Newsletters page."
                : "Email subject '" + emailSubject + "' does not exist in Newsletters page.");
        return exists;
    }
}
