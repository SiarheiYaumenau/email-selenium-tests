package org.selenium.auxiliary;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Waits {
    private final WebDriver driver;
    private final int WAIT_TIMEOUT_30_SECONDS = 30;
    private final int WAIT_TIMEOUT_3_SECONDS = 3;

    public Waits(WebDriver driver) {
        this.driver = driver;
    }

    public void waitForVisibilityOf(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_30_SECONDS))
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void waitForElementToBeClickable(WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_30_SECONDS))
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    public WebElement waitForPresenceOfElementLocated(String id) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT_3_SECONDS));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

}