package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.CommonToAllPages;
import utils.PropertiesReader;

public class CreateCommercialOrder extends CommonToAllPages {

	public CreateCommercialOrder(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public String createCOWithoutItem() {

		WebElement quoteLink = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='VCT Quotes']")));
		js.executeScript("arguments[0].click();", quoteLink);
		System.out.println("QuoteTab is clicked" + " :" + quoteLink.getText());
		String QuoteNumber = "QT-000101814";
		WebElement quoteElement = getQuoteElement(QuoteNumber);
		js.executeScript("arguments[0].click();", quoteElement);
		WebElement createCoButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Create Commercial Order')]")));
		js.executeScript("arguments[0].click();", createCoButton);
		WebElement iframeElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//iframe[contains(@title, 'Commercial Order : Split')]")));
		driver.switchTo().frame(iframeElement);
		System.out.println("switched to iframe");
		WebElement CreateCOBtnInside = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Create Commercial Order']")));
		js.executeScript("arguments[0].click();", CreateCOBtnInside);
		System.out.println("Inside CO button is clicked");

		WebElement button = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'OK')]")));
		WebElement errorText = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@id='swal2-title']")));

		js.executeScript("arguments[0].click();", button);
		System.out.println("ok button is clicked");
		System.out.println(errorText.getText());

		return errorText.getText();

	}

	public String createCOwithoutChangingQuoteStatus() {

		WebElement quoteLink = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='VCT Quotes']")));
		js.executeScript("arguments[0].click();", quoteLink);
		System.out.println("QuoteTab is clicked" + " :" + quoteLink.getText());
		String QuoteNumber = "QT-000101814";
		WebElement quoteElement = getQuoteElement(QuoteNumber);
		js.executeScript("arguments[0].click();", quoteElement);
		WebElement createCoButton = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//button[contains(text(),'Create Commercial Order')]")));
		js.executeScript("arguments[0].click();", createCoButton);
		WebElement iframeElement = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//iframe[contains(@title, 'Commercial Order : Split')]")));
		driver.switchTo().frame(iframeElement);
		System.out.println("switched to iframe");
		WebElement allCheckBox = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cb_coGrid']")));
		try {
		    js.executeScript("arguments[0].scrollIntoView(true);", allCheckBox);
		    js.executeScript("arguments[0].click();", allCheckBox);
		} catch (Exception e) {
		    System.out.println("JS click failed, trying normal click");
		    allCheckBox.click();
		}
		//clickElement(allCheckBox);
		WebElement CreateCOBtnInside = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Create Commercial Order']")));
		js.executeScript("arguments[0].click();", CreateCOBtnInside);
		System.out.println("Inside CO button is clicked");
		WebElement errorText = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h2[@id='swal2-title']")));
		System.out.println("message is retrieved" + errorText.getText());
		return errorText.getText();

	}

	public void createQuoteAfterStatusChange() {
		WebElement quoteLink = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='VCT Quotes']")));
		js.executeScript("arguments[0].click();", quoteLink);
		System.out.println("QuoteTab is clicked" + " :" + quoteLink.getText());
		String QuoteNumber = "QT-000101814";
		WebElement quoteElement = getQuoteElement(QuoteNumber);
		js.executeScript("arguments[0].click();", quoteElement);
		
		WebElement editQuoteStatus = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Edit Quote Status']")));
		js.executeScript("arguments[0].scrollIntoView(true);", editQuoteStatus);
		js.executeScript("arguments[0].click();", editQuoteStatus);
		System.out.println("Quote status pencil button is clicked");
		
		
		WebElement statuDropBox = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Quote Status']")));
		js.executeScript("arguments[0].click();", statuDropBox);
		System.out.println("status dropdown is opened");
		
		
		WebElement statusWon =wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
				"//lightning-base-combobox-item//span[@class='slds-media__body']/span[normalize-space()='Won']")));

		js.executeScript("arguments[0].click();", statusWon);
		System.out.println("WOn is selected");
		
	}

}
