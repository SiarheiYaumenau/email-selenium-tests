package org.selenium.test;

import org.openqa.selenium.WebDriver;
import org.selenium.util.WebDriverSeleniumGrid;
import org.selenium.driver.DriverSingleton;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class CommonConditions {
    protected WebDriver driver;

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

}
