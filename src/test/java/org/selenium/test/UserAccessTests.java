package org.selenium.test;

import org.selenium.model.User;
import org.selenium.pages.*;
import org.selenium.service.UserCreator;
import org.selenium.util.InboxPageUtils;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class UserAccessTests extends CommonConditions {
    @Test(priority = 1, description = "Login to Mail")
    public void MailLoginTest() {
        User user = UserCreator.withCredentialsFromProperty();
        InboxPage inboxPage = InboxPageUtils.loginAndNavigateToInbox(driver, user);
        String accountName = inboxPage
                .waitLoadPage()
                .getAccountName();
        String expectedAccountName = user.getUsername() + "@mail.ru";
        assertEquals(accountName, expectedAccountName,
                "Inbox page is not displayed or the account name is wrong");
    }
}
