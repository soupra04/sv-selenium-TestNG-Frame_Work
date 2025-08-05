package testclasses;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.EditQuoteGridPages;
import pages.LoginPage;
import pages.QuotePage;
import utils.PropertiesReader;

public class CreateQuoteTest extends CommonToAllTest{

	@Test
	public  void testQuoteCreation() {
		// TODO Auto-generated method stub
		
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));
		QuotePage oppPage = new QuotePage(DriverManager.getDriver());
		String text = oppPage.openQuote();
		Assert.assertTrue(text.contains("Edit quote"));
	}

}
