package org.selenium.pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class MailsGrid extends MailPage {
    @FindBy(css = "span.button2__explanation.button2__explanation_ellipsis")
    private WebElement selectAllButton;
    @FindBy(css = "a.llc.llc_normal.llc_first.llc_new.llc_new-selection.js-letter-list-item.js-tooltip-direction_letter-bottom")
    private WebElement topMailsRow;

    public MailsGrid(WebDriver driver) {
        super(driver);
    }

    public MailsGrid openPage() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.visibilityOf(selectAllButton));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
        return this;
    }
    public MessageEditorPage openFirstMailForEdit() {
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(2));
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(topMailsRow));
        topMailsRow.click();
        return new MessageEditorPage(driver);
    }

}
