package org.selenium.util;

import org.openqa.selenium.WebDriver;
import org.selenium.model.User;
import org.selenium.pages.EnterPasswordPage;
import org.selenium.pages.InboxPage;
import org.selenium.pages.StartPage;

public class InboxPageUtils {
    public static InboxPage loginAndNavigateToInbox(WebDriver driver, User user) {
        EnterPasswordPage enterPasswordPage = new StartPage(driver)
                .waitLoadPage()
                .LoginAndConfirm(user.getUsername());
        enterPasswordPage.waitLoadPage();
        return enterPasswordPage.inputPasswordAndConfirm(user.getPassword());
    }
}

