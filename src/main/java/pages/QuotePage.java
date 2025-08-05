package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.CommonToAllPages;
import utils.PropertiesReader;

public class QuotePage extends CommonToAllPages {

	public QuotePage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public String openQuote() {

		WebElement quoteLink = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='VCT Quotes']")));
		js.executeScript("arguments[0].click();", quoteLink);
		System.out.println("QuoteTab is clicked" + " :" + quoteLink.getText());
		String QuoteNumber = PropertiesReader.readkey("quoteNumnber");
		WebElement quoteElement = getQuoteElement(QuoteNumber);
		js.executeScript("arguments[0].click();", quoteElement);
		WebElement editQuoteGrid = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[normalize-space()='Edit Quote']")));
		js.executeScript("arguments[0].click();", editQuoteGrid);
		System.out.println("EditQuoteGrid button is clicked" + " :" + editQuoteGrid.getText());
		WebElement iframeElement = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//iframe[contains(@title, 'Edit Quote')]")));
		driver.switchTo().frame(iframeElement);
		System.out.println("Edit quote grid is opened");
		return "Edit quote grid is opened";
	
	
	}

}
