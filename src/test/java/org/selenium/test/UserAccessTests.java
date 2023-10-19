package org.selenium.test;

import org.selenium.pages.*;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class UserAccessTests extends CommonConditions {
    @Test(priority = 1, description = "Login to Mail")
    public void MailLoginTest() {
        InboxPage inboxPage = getInboxMailPage();
        String accountName = inboxPage
                .waitLoadPage()
                .getAccountName();
        String expectedAccountName = USER_NAME + "@mail.ru";
        assertEquals(accountName, expectedAccountName,
                "Inbox page is not displayed or the account name is wrong");
    }
}
