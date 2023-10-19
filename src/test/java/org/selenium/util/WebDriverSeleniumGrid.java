package org.selenium.util;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

public class WebDriverSeleniumGrid {
    private WebDriver driver;

    public void setRandomWebDriverSeleniumGrid() {
        DesiredCapabilities caps = new DesiredCapabilities();
        String[] browserOptions = {"chrome", "firefox", "MicrosoftEdge"};
        String selectedBrowser = browserOptions[new Random().nextInt(browserOptions.length)];
        caps.setPlatform(Platform.WIN10);
        caps.setBrowserName(selectedBrowser);
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setWebDriverSeleniumGrid(String browser) {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setPlatform(Platform.WIN10);
        caps.setBrowserName(browser);
        try {
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), caps);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }


    public WebDriver getDriver() {
        return driver;
    }
}
