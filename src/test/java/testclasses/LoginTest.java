package testclasses;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.LoginPage;
import utils.ExtentReportManager;
import utils.PropertiesReader;

public class LoginTest extends CommonToAllTest {

	@Test
	public void testLogin() {
		test.info("navigate to URL");

		LoginPage login = new LoginPage(DriverManager.getDriver());
		String textAfterLogin = login.loginWithValidCred(PropertiesReader.readkey("cc_user"),
				PropertiesReader.readkey("cc_pwd"));
		;
		test.info("Right Credentials have been provided");

		Assert.assertTrue(textAfterLogin.contains("Enterprise Quoting"));
		test.info("Successfully landed after login");

	}

}
