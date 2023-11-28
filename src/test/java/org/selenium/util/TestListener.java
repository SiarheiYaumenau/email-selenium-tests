package org.selenium.util;

import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.listeners.LogLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.selenium.driver.DriverSingleton;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestException;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TestListener implements ITestListener {
    private final Logger log = LogManager.getLogger(TestListener.class);

    public void onTestStart(ITestResult iTestResult) {

    }

    public void onTestSuccess(ITestResult iTestResult) {

    }

    public void onTestFailure(ITestResult iTestResult) {
        saveScreenshot();
    }

    public void onTestSkipped(ITestResult iTestResult) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestContext) {

    }

    public void onFinish(ITestContext iTestContext) {

    }

    private void saveScreenshot(){
        File screenCapture = ((TakesScreenshot) DriverSingleton
                .getDriver())
                .getScreenshotAs(OutputType.FILE);
        String fileName = ".//target/screenshots/" + getCurrentTimeAsString() + ".png";
        try {
            FileUtils.copyFile(screenCapture, new File(fileName));
            log.info("Screenshot of failed test is saved in .//target/screenshots/ and named " +
                    new File(fileName).getName());
        } catch (IOException e) {
            throw new TestException("An unexpected error occurred during saving screenshot: " + e.getMessage());
        }
        ReportPortal.emitLog("screenshot", LogLevel.INFO.name(), new Date(), new File(fileName));
        ReportPortalMessage message;
        try {
            message = new ReportPortalMessage(new File(fileName), "screenshot2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info(message);
    }

    private String getCurrentTimeAsString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "uuuu-MM-dd_HH-mm-ss" );
        return ZonedDateTime.now().format(formatter);
    }
}
