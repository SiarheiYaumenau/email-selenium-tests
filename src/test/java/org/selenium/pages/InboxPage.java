package org.selenium.pages;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.selenium.auxiliary.Waits;
import java.util.List;
import java.util.stream.Collectors;


public class InboxPage extends MailPage {
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
                acceptAllButton.click();
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            return this;
        }
        return this;
    }

    public String getAccountName() {
        new Waits(driver).waitForElementToBeClickable(accountIcon);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return (String) js.executeScript("return arguments[0].getAttribute('alt');", accountIcon);
    }

    public MessageEditorPage createNewMessage() {
        new Waits(driver).waitForElementToBeClickable(composeButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", composeButton);
        return new MessageEditorPage(driver);
    }

    public DraftsPage switchToDraftsPage() {
        new Waits(driver).waitForElementToBeClickable(draftTab);
        draftTab.click();
        return new DraftsPage(driver);
    }

    public NewsLettersPage switchToNewsLettersPage() {
        new Waits(driver).waitForElementToBeClickable(newsLettersTab);
        newsLettersTab.click();
        return new NewsLettersPage(driver);
    }

    public NonEditableEmailPage openFirstNonEditableEmail() {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        topMailsRow.click();
        return new NonEditableEmailPage(driver);
    }

    public InboxPage moveTheFirstEmailToNewsLetters() {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        Actions actions = new Actions(driver);
        actions.dragAndDrop(topMailsRow, newsLettersTab).release().build().perform();
        new Waits(driver).waitForVisibilityOf(newsLettersInMailGrid);
        actions.moveToElement(newsLettersTab);
        return this;
    }
    public boolean ifEmailSubjectExistsInInboxPage(String emailSubject) {
        new Waits(driver).waitForElementToBeClickable(topMailsRow);
        List<String> emailSubjects = subjects.stream()
                .map(WebElement::getText)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        return emailSubjects.contains(emailSubject.toLowerCase());
    }

}
