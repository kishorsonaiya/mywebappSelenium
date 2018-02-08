package com.infosys.fsstar.selenium.ie;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyWebAppUserLoginTest {
	private WebDriver driver = null;

	@BeforeMethod
	public void beforeTest() {
		System.setProperty("webdriver.ie.driver", "C:\\IEDriverServer.exe");

		DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
		// this line of code is to resolve protected mode issue
		// capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
		// true);
		capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		// without this setting, page will get open and then get closed
		capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "");

		// capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
		// "http://localhost:8090/mywebapp/userlogin.jsp");

		// this line of code is to resolve protected mode issue
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);

		// **VERY important** adding this line make launching the webapp site
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);

		// Adding INITIAL_BROWSER_URL, help to load the IE with required url.
		// Otherwise it will open browser with default url and then redirect to
		// application url
		capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL,
				"http://PUNITP383056D:8090/mywebapp/userlogin.jsp");

		// using this feature, sendKeys function was with good speed. Other wise
		// it takes 3 sends to type one character.
		capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);

		driver = new InternetExplorerDriver(capabilities);
		driver.manage().window().maximize();

	}

	@Test
	public void testUserLoginPageLoad() {
		String userlogin_title = driver.findElement(By.id("userlogin_title")).getText();
		Assert.assertTrue(null != userlogin_title && userlogin_title.contains("Login Here"));
		WebElement email = driver.findElement(By.id("email"));
		WebElement password = driver.findElement(By.id("password"));

		Assert.assertTrue(null != email);
		Assert.assertTrue(null != password);
	}

	@Test
	public void testUserLoginSuccess() {
		String userlogin_title = driver.findElement(By.id("userlogin_title")).getText();
		if (null != userlogin_title && userlogin_title.contains("Login Here")) {
			WebElement email = driver.findElement(By.id("email"));
			WebElement password = driver.findElement(By.id("password"));
			email.sendKeys("jiteshkumar.patel@infosys.com");
			password.sendKeys("1234");
			driver.findElement(By.id("loginsubmit")).click();
		}

		WebElement loginServlet_success = driver.findElement(By.id("loginServlet_success"));

		if (null != loginServlet_success) {
			String loginServlet_success_txt = loginServlet_success.getText();
			if (loginServlet_success_txt.contains("Welcome,")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		} else {
			Assert.assertTrue(false);
		}

	}

	@Test
	public void testUserLoginFailure() {
		String userlogin_title = driver.findElement(By.id("userlogin_title")).getText();
		if (null != userlogin_title && userlogin_title.contains("Login Here")) {
			WebElement email = driver.findElement(By.id("email"));
			WebElement password = driver.findElement(By.id("password"));
			email.sendKeys("A");
			password.sendKeys("B");
			driver.findElement(By.id("loginsubmit")).click();
		}

		WebElement loginServlet_failure = driver.findElement(By.id("loginServlet_failure"));

		if (null != loginServlet_failure) {
			String loginServlet_success_txt = loginServlet_failure.getText();
			if (loginServlet_success_txt.contains("Email/Password is not correct.")) {
				Assert.assertTrue(true);
			} else {
				Assert.assertTrue(false);
			}
		} else {
			Assert.assertTrue(false);
		}
		
		
	}

	@AfterMethod
	public void afterTest() {
		driver.quit();
	}

}
