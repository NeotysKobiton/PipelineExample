package com.neotys.kobiton.sampleproject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.List;

import com.neotys.selenium.proxies.NLRemoteWebDriver;
import com.neotys.selenium.proxies.NLWebDriverFactory;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import io.appium.java_client.AppiumDriver;

public class KobitonAppium {
    AppiumDriver<MobileElement> wd;
    NLRemoteWebDriver driver;
    String openSearch = "//*[@id=\'open-search\']";
    String searchField = "//*[@id=\'search-input-mobile\']";
    String searchSubmit = "//*[@id=\'search-button-mobile\']";

    String applicationURL = "";

    @BeforeMethod
    @Before
    public void beforeMethod() throws Exception {
        //Replace <<cloud name>> with your perfecto cloud name (e.g. demo) or pass it as maven properties: -DcloudName=<<cloud name>>
        String cloudName = "<<cloud name>>";
        //Replace <<security token>> with your perfecto security token or pass it as maven properties: -DsecurityToken=<<SECURITY TOKEN>>  More info: https://developers.perfectomobile.com/display/PD/Generate+security+tokens
        String projectPath = "";
        applicationURL = "<<applicationURL>>";
        applicationURL = Utils.fetchApplicationURL(applicationURL);
        //A sample perfecto connect appium script to connect with a perfecto android device and perform addition validation in calculator app.
        String browserName = "mobileOS";
        DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
        capabilities.setCapability("sessionName", "NeoLoad Demo test session");
        capabilities.setCapability("sessionDescription", "");
        capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("captureScreenshots", true);
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("groupId", 212); // Group: Default Group
        capabilities.setCapability("deviceGroup", "KOBITON");
        // The given group is used for finding devices and the created session will be visible for all members within the group.
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "*");
        capabilities.setCapability("deviceName", "Galaxy*");
        boolean session = false;
        for (int i = 0; i < 5; i++) {
            try {
                session = createSession(capabilities, cloudName, projectPath);
                if (session)
                    break;
            } catch (SessionNotCreatedException e) {
                e.printStackTrace();
            }
        }
        if (!session) throw new RuntimeException("Driver not created with capabilities: " + capabilities.toString());
    }

    public boolean createSession(Capabilities capabilities, String cloudname, String projectname) throws Exception, SessionNotCreatedException {
        boolean result = false;
        wd = new AndroidDriver<MobileElement>(new URL("https://" + Utils.fetchCloudName(cloudname) + "@api.kobiton.com/wd/hub"), capabilities);
        driver = (NLRemoteWebDriver) NLWebDriverFactory.newNLWebDriver(wd, "KonaKart Android", projectname);

        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        System.out.println("Session created, session id :" + driver.getSessionId());
        result = true;
        return result;
    }

    @Test
    @org.junit.Test
    public void appiumTest() throws Exception {

        //----enable vitals metrics---
        /*driver.startTransaction("Accessibility Assertion");

		Map<String, Object> params = new HashMap<>();
		params.put("sources", "Device");
		params.put("interval", 4);
		driver.executeScript("mobile:monitor:start", params);*/

        driver.startTransaction("Navigate to Konakart.com");
        driver.get("http://" + applicationURL);
        driver.stopTransaction();

        driver.startTransaction("Search for Computer");
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
        WebElement element = driver.findElement(By.xpath((openSearch)));
        element.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
        element = driver.findElement(By.xpath((searchField)));
        element.clear();
        element.sendKeys("book");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
        element = driver.findElement(By.xpath((searchSubmit)));
        element.click();
        driver.stopTransaction();


        driver.startTransaction("Search for phone");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
        element = driver.findElement(By.xpath((openSearch)));
        element.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
        element = driver.findElement(By.xpath((searchField)));
        element.clear();
        element.sendKeys("phone");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
        element = driver.findElement(By.xpath((searchSubmit)));
        element.click();
        driver.stopTransaction();


        driver.startTransaction("Search for game");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
        element = driver.findElement(By.xpath((openSearch)));
        element.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
        element = driver.findElement(By.xpath((searchField)));
        element.clear();
        element.sendKeys("game");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
        element = driver.findElement(By.xpath((searchSubmit)));
        element.click();
        driver.stopTransaction();

/*		//----disable vitals
		Map<String, Object> parames = new HashMap<>();
		driver.executeScript("mobile:monitor:stop", parames);*/

        driver.close();
        driver.quit();
    }


}

