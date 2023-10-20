package org.selenium.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.selenium.util.Waits;

public class EmailPopupMenu extends MailPage {
    private final Logger logger = LogManager.getLogger(EmailPopupMenu.class);
    @FindBy (css = "svg.ico.ico_16-delete.ico_size_s")
    private WebElement deleteMenuItem;
    @FindBy (css = "svg.ico.ico_16-folder-move.ico_size_s")
    private WebElement moveToMenuItem;
    @FindBy (css = "div.list-item.list-item_hover-support.list-item_child-level_0[title=\"Inbox\"]")
    private WebElement inboxMenuItem;

    public EmailPopupMenu(WebDriver driver) {
        super(driver);
    }

    public EmailPopupMenu waitLoadPage() {
        new Waits(driver).waitForElementToBeClickable(deleteMenuItem);
        return this;
    }

    public EmailPopupMenu openSecondLevelMenuMoveTo() {
        new Waits(driver).waitForElementToBeClickable(moveToMenuItem);
        moveToMenuItem.click();
        logger.info("Second level menu 'Move To' is opened.");
        return this;
    }

    public NewsLettersPage moveEmailToInbox() {
        new Waits(driver).waitForElementToBeClickable(inboxMenuItem);
        inboxMenuItem.click();
        logger.info("Email is moved from the News letters page to Inbox page.");
        return new NewsLettersPage(driver);
    }





}
