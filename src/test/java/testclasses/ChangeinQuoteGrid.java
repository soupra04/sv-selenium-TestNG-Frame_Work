package testclasses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.CommonToAllTest;
import driver.DriverManager;
import pages.EditQuoteGridPages;
import pages.LoginPage;
import pages.QuotePage;
import utils.PropertiesReader;

public class ChangeinQuoteGrid extends CommonToAllTest {

	//@Test(priority = 1, retryAnalyzer = listeners.RetryAnalyzer.class)
    public void testChangeMargin() throws InterruptedException {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));

        QuotePage oppPage = new QuotePage(DriverManager.getDriver());
        oppPage.openQuote();

        EditQuoteGridPages ep = new EditQuoteGridPages(DriverManager.getDriver());
        List<String[]> results = ep.changeMarginForSaasItems();

        for (String[] result : results) {
            String field = result[0];
            String expected = result[1];
            String actual = result[2];

            Assert.assertEquals(actual, expected, "❌ Mismatch in " + field);
        }
    }
    
	//@Test(priority = 2, retryAnalyzer = listeners.RetryAnalyzer.class)
    public void testChangecustDiscountPct() throws InterruptedException {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));

        QuotePage oppPage = new QuotePage(DriverManager.getDriver());
        oppPage.openQuote();

        EditQuoteGridPages ep = new EditQuoteGridPages(DriverManager.getDriver());
        List<String[]> results = ep.changeCustDiscount();

        for (String[] result : results) {
            String field = result[0];
            String expected = result[1];
            String actual = result[2];

            Assert.assertEquals(actual, expected, "❌ Mismatch in " + field);
        }
    }
	
	@Test(priority = 3) //retryAnalyzer = listeners.RetryAnalyzer.class)
    public void testChangeCustUnitPrice() throws InterruptedException {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));

        QuotePage oppPage = new QuotePage(DriverManager.getDriver());
        oppPage.openQuote();

        EditQuoteGridPages ep = new EditQuoteGridPages(DriverManager.getDriver());
        List<String[]> results = ep.changeCustUnitPrice();

        for (String[] result : results) {
            String field = result[0];
            String expected = result[1];
            String actual = result[2];

            Assert.assertEquals(actual, expected, "❌ Mismatch in " + field);
        }
    }
	
   // @Test(priority = 1)
    public void testChangeCCDiscPCT() throws InterruptedException {
        LoginPage loginPage = new LoginPage(DriverManager.getDriver());
        loginPage.loginWithValidCred(PropertiesReader.readkey("cc_user"), PropertiesReader.readkey("cc_pwd"));

        QuotePage oppPage = new QuotePage(DriverManager.getDriver());
        oppPage.openQuote();

        EditQuoteGridPages ep = new EditQuoteGridPages(DriverManager.getDriver());
        List<String[]> results = ep.changeCCDiscountPCT();

        for (String[] result : results) {
            String field = result[0];
            BigDecimal expected = new BigDecimal(result[1]).setScale(2, RoundingMode.HALF_UP);
            BigDecimal actual = new BigDecimal(result[2]).setScale(2, RoundingMode.HALF_UP);

            Assert.assertEquals(actual, expected, "❌ Mismatch in " + field);
        }
    }

	
    
}
