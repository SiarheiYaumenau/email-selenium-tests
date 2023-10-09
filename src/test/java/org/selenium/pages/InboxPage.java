package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class InboxPage extends MailPage {
    @FindBy (className = "ph-avatar-img")
    private WebElement accountIcon;
    @FindBy (className = "compose-button__txt")
    private WebElement composeButton;
    @FindBy (css = "a.nav__item[href^='/inbox/']")
    private WebElement inboxTab;
    @FindBy (css = "a.nav__item[href^='/sent/']")
    private WebElement sentTab;
    @FindBy (css = "a.nav__item[href^='/drafts/']")
    private WebElement draftTab;
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/inbox/']:first-of-type")
    private WebElement topMailsRow;

    public InboxPage(WebDriver driver) {
        super(driver);
    }

    public InboxPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(inboxTab));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(topMailsRow));
        return this;
    }

    public String getAccountName() {
        return accountIcon.getAttribute("alt");
    }

    public MessageEditorPage createNewMessage() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        composeButton.click();
        return new MessageEditorPage(driver);
    }
    public MailsGrid switchToDraftsGrid() {
        draftTab.click();
        return new MailsGrid(driver);
    }
    public MailsGrid switchToSentPage() {
        sentTab.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));
        return new MailsGrid(driver);
    }
}
