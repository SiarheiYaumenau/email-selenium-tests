package org.selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;


public class Login {
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        driver.get("https://e.mail.ru/");
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        driver.findElement(By.name("username")).sendKeys("swebdriver");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        driver.findElement(By.name("password")).sendKeys("Support-1234");
        driver.findElement(By.cssSelector("button[data-test-id='submit-button']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.nav__item[data-folder-link-id='0']")));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebElement element = driver.findElement(By.className("ph-avatar-img"));
        String expectedText = "swebdriver@mail.ru";
        String actualText = element.getAttribute("alt");
        Assert.assertEquals(expectedText, actualText, "Inbox page is not displayed or the account name is wrong");
        driver.quit();
    }

}
