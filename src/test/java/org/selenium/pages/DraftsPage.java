package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DraftsPage extends MailPage {
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
    @FindBy (css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection[href^='/drafts/']:first-of-type")
    private WebElement topMailsRow;

    public DraftsPage(WebDriver driver) {
        super(driver);
    }

    public DraftsPage openPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(inboxTab));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(topMailsRow));
        return this;
    }

    public SentPage switchToSentPage() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(sentTab));
        sentTab.click();
        return new SentPage(driver);
    }
    public MessageEditorPage openFirstMailForEdit() {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(topMailsRow));
        topMailsRow.click();
        return new MessageEditorPage(driver);
    }

}
