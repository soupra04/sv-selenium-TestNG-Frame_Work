package testclasses;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.CreateCommercialOrder;
import pages.LoginPage;
import utils.PropertiesReader;


public class CreateCO extends CommonToAllTest{

	@Test (priority = 1)
	
	public void testCOWithoutItem() {
		test.info("redirect to login Page");
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));
		test.info("Right credentials have been provided");
		CreateCommercialOrder co = new CreateCommercialOrder(DriverManager.getDriver());
		String textMsg = co.createCOWithoutItem();
		test.info("clicked on Create CO button without Selecting Item");
		Assert.assertEquals(textMsg, "Please select at least one item from the list");
		test.info("Error message is receieved since the Create CO button is clicked without selecting Item");
		
		
	}
	@Test (priority = 2)
	public void testCOWithoutChangingQuoteStatus() {
		test.info("redirect to login Page");
		LoginPage loginPage = new LoginPage(DriverManager.getDriver());
		loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));
		test.info("Right credentials have been provided");
		CreateCommercialOrder co = new CreateCommercialOrder(DriverManager.getDriver());
		String textMsg = co.createCOwithoutChangingQuoteStatus();;
		test.info("Create CO button is clicked without changing status of the Quote");
		Assert.assertEquals(textMsg, "Please change the quote status to “Won” to create a commercial order");
		test.info("Error message is receieved since the Create CO button is clicked without changing the Quote status");
		
		
	}
	
	//@Test (priority = 3)
		public void testCoCreationAfterChangingStatus() {
			LoginPage loginPage = new LoginPage(DriverManager.getDriver());
			loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));
			
			CreateCommercialOrder co = new CreateCommercialOrder(DriverManager.getDriver());
			co.createQuoteAfterStatusChange();
			
			
			
			
		}
		
	
		
		
	
}
