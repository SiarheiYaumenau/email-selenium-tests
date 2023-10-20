package org.selenium.pages;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;
import java.util.List;
import java.util.stream.Collectors;


public class InboxPage extends MailPage {
    private final Logger logger = LogManager.getLogger(InboxPage.class);
    @FindBy(className = "ph-avatar-img")
    private WebElement accountIcon;
    @FindBy(css = "span.compose-button__txt")
    private WebElement composeButton;
    @FindBy(css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy(css = "a.nav__item[href^='/newsletters/']")
    private WebElement newsLettersTab;
    @FindBy(css = "a.nav__item[href^='/drafts/']")
    private WebElement draftTab;
    @FindBy(css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/inbox/']:first-of-type")
    private WebElement topMailsRow;
    @FindBy(css = ".mt-h-c_new-selection[href^='/newsletters/']")
    private WebElement newsLettersInMailGrid;
    @FindAll({@FindBy(css = "span.ll-sj__normal")})
    private List<WebElement> subjects;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public InboxPage waitLoadPage() {
        new Waits(driver).waitForVisibilityOf(inboxTab);
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        try {
            WebElement acceptAllButton = new Waits(driver).waitForPresenceOfElementLocated("cmpbntyestxt");
            if (acceptAllButton.isDisplayed()) {
                logger.info("Accept All button is displayed. Clicking on it.");
                acceptAllButton.click();
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            return this;
        }
        logger.info("Inbox page loaded successfully.");
        return this;
    }

    public String getAccountName() {
        new Waits(driver).waitForElementToBeClickable(accountIcon);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String accountName = (String) js.executeScript("return arguments[0].getAttribute('alt');", accountIcon);
        logger.info("Account name: " + accountName);
        return accountName;
    }

    public MessageEditorPage createNewMessage() {
        logger.info("Creating a new message.");
        new Waits(driver).waitForElementToBeClickable(composeButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", composeButton);
        return new MessageEditorPage(driver);
    }

    public DraftsPage switchToDraftsPage() {
        logger.info("Switching to Drafts page.");
        new Waits(driver).waitForElementToBeClickable(draftTab);
        draftTab.click();
        return new DraftsPage(driver);
    }

    public NewsLettersPage switchToNewsLettersPage() {
        logger.info("Switching to Newsletters page.");
        new Waits(driver).waitForElementToBeClickable(newsLettersTab);
        newsLettersTab.click();
        return new NewsLettersPage(driver);
    }

    public NonEditableEmailPage openFirstNonEditableEmail() {
        logger.info("Opening the first non-editable email.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new NonEditableEmailPage(driver);
    }

    public InboxPage moveTheFirstEmailToNewsLetters() {
        logger.info("Moving the first email to Newsletters.");
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        Actions actions = new Actions(driver);
        logger.warn("For Chrome and Edge browsers sometimes error occurred while drag and drop the first email from Inbox page to Newsletters tab");
        try {
        actions.dragAndDrop(topMailsRow, newsLettersTab).release().build().perform();
        } catch (Exception e) {
            logger.error("Error occurred while drag and drop the first email from Inbox page to Newsletters tab: " + e.getMessage());
        }
        new Waits(driver).waitForVisibilityOf(newsLettersInMailGrid);
        actions.moveToElement(newsLettersTab).build().perform();
        return this;
    }
    public boolean ifEmailSubjectExistsInInboxPage(String emailSubject) {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        List<String> emailSubjects = subjects.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        boolean exists = emailSubjects.contains(emailSubject.toLowerCase());
        if (exists) {
            logger.info("Email subject '" + emailSubject + "' exists in Inbox.");
        } else {
            logger.info("Email subject '" + emailSubject + "' does not exist in Inbox.");
        }
        return exists;
    }
}
