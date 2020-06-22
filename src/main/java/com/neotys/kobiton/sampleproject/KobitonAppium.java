package com.neotys.kobiton.sampleproject;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.List;

import com.neotys.selenium.proxies.NLRemoteWebDriver;
import com.neotys.selenium.proxies.NLWebDriverFactory;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import io.appium.java_client.AppiumDriver;

public class KobitonAppium {
	RemoteWebDriver wd;
	NLRemoteWebDriver driver;
	String openSearch = "//*[@id=\'open-search\']//*[@id=\'open-search\']|//*[@class=\'page-content\']//*[@class=\'search-field\']";
	String searchField = "//*[@id=\'search-input-mobile\']";
	String searchSubmit = "//*[@id=\'search-button-mobile\']";

	String applicationURL="";

	@BeforeMethod @Before
	public void beforeMethod() throws Exception {
		//Replace <<cloud name>> with your perfecto cloud name (e.g. demo) or pass it as maven properties: -DcloudName=<<cloud name>>
		String cloudName = "<<cloud name>>";
		//Replace <<security token>> with your perfecto security token or pass it as maven properties: -DsecurityToken=<<SECURITY TOKEN>>  More info: https://developers.perfectomobile.com/display/PD/Generate+security+tokens
		String projectPath="";
		applicationURL="<<applicationURL>>";
		applicationURL=Utils.fetchApplicationURL(applicationURL);
		//A sample perfecto connect appium script to connect with a perfecto android device and perform addition validation in calculator app.
		String browserName = "mobileOS";
		DesiredCapabilities capabilities = new DesiredCapabilities(browserName, "", Platform.ANY);
		capabilities.setCapability("sessionName", "NeoLoad Demo test session");
		capabilities.setCapability("sessionDescription", "");
		capabilities.setCapability("deviceOrientation", "portrait");
		capabilities.setCapability("captureScreenshots", true);
		capabilities.setCapability("browserName", "chrome");
// The given group is used for finding devices and the created session will be visible for all members within the group.
		capabilities.setCapability("groupId", 212); // Group: Default Group
		capabilities.setCapability("deviceGroup", "KOBITON");
		capabilities.setCapability("deviceName", "Galaxy*");
		capabilities.setCapability("platformName", "Android");
		try{
			wd = (RemoteWebDriver)(new AppiumDriver<>(new URL("https://" + Utils.fetchCloudName(cloudName)  + "@api.kobiton.com/wd/hub"), capabilities));
			driver = (NLRemoteWebDriver) NLWebDriverFactory.newNLWebDriver(wd, "KonaKart Android", projectPath);

			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		}catch(SessionNotCreatedException e){
			throw new RuntimeException("Driver not created with capabilities: " + capabilities.toString());
		}


	}

	@Test @org.junit.Test
	public void appiumTest() throws Exception {

		//----enable vitals metrics---
		driver.startTransaction("Accessibility Assertion");


		Map<String, Object> params = new HashMap<>();
		params.put("sources", "Device");
		params.put("interval", 4);
		driver.executeScript("mobile:monitor:start", params);

		driver.startTransaction("Navigate to Konakart.com");
		driver.get("http://"+applicationURL);
		driver.stopTransaction();

		driver.startTransaction("Search for Comptuer");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element.click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element.clear();
		element.sendKeys("Comptuer");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element.click();
		driver.stopTransaction();


		driver.startTransaction("Search for phone");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element.click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element.clear();
		element.sendKeys("phone");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element.click();
		driver.stopTransaction();

		driver.startTransaction("Search for game");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(openSearch)));
		element.click();
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchField)));
		element.clear();
		element.sendKeys("game");
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(searchSubmit)));
		element.click();
		driver.stopTransaction();


		//----disable vitals
		Map<String, Object> parames = new HashMap<>();
		driver.executeScript("mobile:monitor:stop", parames);
	}




}

