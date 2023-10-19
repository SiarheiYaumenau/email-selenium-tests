package org.selenium.test;

import org.openqa.selenium.WebDriver;
import org.selenium.auxiliary.WebDriverSeleniumGrid;
import org.selenium.driver.DriverSingleton;
import org.selenium.pages.EnterPasswordPage;
import org.selenium.pages.InboxPage;
import org.selenium.pages.StartPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class CommonConditions {
    private WebDriver driver;
    protected static final String USER_NAME = "swebdriver";
    private static final String PASSWORD = "Support-1234";

    @BeforeMethod(alwaysRun = true)
    public void browserSetup() {
        driver = DriverSingleton.getDriver();
    }

    private void setRandomWebDriverWithSeleniumGrid() {
        WebDriverSeleniumGrid gridSetup = new WebDriverSeleniumGrid();
        gridSetup.setRandomWebDriverSeleniumGrid();
        driver = gridSetup.getDriver();
    }

    private void setWebDriverWithSeleniumGrid(String browser) {
        WebDriverSeleniumGrid gridSetup = new WebDriverSeleniumGrid();
        gridSetup.setWebDriverSeleniumGrid(browser);
        driver = gridSetup.getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown() {
        DriverSingleton.closeDriver();
    }

    public InboxPage getInboxMailPage() {
        EnterPasswordPage enterPasswordPage = new StartPage(driver)
                .waitLoadPage().LoginAndConfirm(USER_NAME);
        enterPasswordPage.waitLoadPage();
        return enterPasswordPage.inputPasswordAndConfirm(PASSWORD);
    }
}
