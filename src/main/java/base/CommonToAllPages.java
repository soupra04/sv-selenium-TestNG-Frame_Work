package base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import driver.DriverManager;

public class CommonToAllPages {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected JavascriptExecutor js;

	public CommonToAllPages(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		this.js = (JavascriptExecutor) driver;
	}

	public void clickElement(By by) {
		DriverManager.getDriver().findElement(by).click();
	}

	public void enterInput(By by, String value) {
		DriverManager.getDriver().findElement(by).sendKeys(value);
	}

	public void clickElement(WebElement ele) {
		ele.click();
	}

	public WebElement getQuoteElement(String quoteNumber) {
		By quoteLocator = By.xpath("//a[@title='" + quoteNumber + "']");
		return wait.until(ExpectedConditions.visibilityOfElementLocated(quoteLocator));
	}

	// ✅ Helper: Update a cell value by row, column index, input name (partial)
	protected void updateEditableCell(int rowIndex, int colIndex, String valueToSet, String partialInputName) {
		String cellXpath = "//table[contains(@id,'qtGrid')]//tr[" + rowIndex + "]//td[" + colIndex + "]";
		WebElement cell = driver.findElement(By.xpath(cellXpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", cell);

		Actions action = new Actions(driver);
		action.doubleClick(cell).perform();

		// Wait for input to appear inside that cell
		WebElement input = wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(cell,
				By.xpath(".//input[contains(@name,'" + partialInputName + "')]")));
		input.clear();
		input.sendKeys(valueToSet, Keys.ENTER);
	}

	// ✅ Helper method to fetch column index by partial header text
	protected int getColumnIndexByPartialHeader(String partialHeaderText) {
		List<WebElement> headers = driver.findElements(
				By.xpath("//table[@class='ui-jqgrid-htable']/thead/tr[@class='ui-jqgrid-labels']/th/div"));
		for (int i = 0; i < headers.size(); i++) {
			WebElement header = headers.get(i);
			js.executeScript("arguments[0].scrollIntoView({inline: 'center'});", header);
			String headerText = header.getText().trim().toLowerCase();
			if (headerText.contains(partialHeaderText.toLowerCase())) {
				return i + 1;
			}
		}
		return -1;
	}

	protected void waitAndSaveQuoteChanges() {
		// ✅ Wait for overlay to disappear
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("qtActBar-overlay")));

		// ✅ Wait for Save button to be clickable
		WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("btnsaveQuoteItems")));

		// ✅ Debug logs
		System.out.println("Save button displayed: " + saveBtn.isDisplayed());
		System.out.println("Save button enabled: " + saveBtn.isEnabled());

		// ✅ Scroll to and click Save via JS
		js.executeScript("arguments[0].scrollIntoView(true);", saveBtn);
		js.executeScript("arguments[0].click();", saveBtn);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public double parseDouble(String value) {
	    try {
	        if (value == null || value.trim().isEmpty()) return 0.0;
	        return Double.parseDouble(value.replace(",", "").trim());
	    } catch (NumberFormatException e) {
	        return 0.0;
	    }
	}

	public double getCellDataAsDouble(WebElement row, int colIndex) {
	    String value = getCellDataAttribute(row, colIndex, "data-src");
	    return parseDouble(value);
	}


	protected String getCellDataAttribute(WebElement row, int colIndex, String attribute) {
		return row.findElement(By.xpath(".//td[" + colIndex + "]//span")).getAttribute(attribute);
	}

	protected String getCellText(WebElement row, int colIndex) {
		return row.findElement(By.xpath(".//td[" + colIndex + "]//span")).getText().trim();
	}



	protected void addResult(List<String[]> resultList, String field, double expected, double actual) {
		System.out.printf("%s => Expected: %.2f | Actual: %.2f%n", field, expected, actual);
		resultList.add(new String[] { field, String.valueOf(expected), String.valueOf(actual) });
	}
	
	protected boolean isSaaSItem(int rowIndex, int itemTypeColIndex) {
	    String itemTypeXpath = "//table[contains(@id,'qtGrid')]//tr[" + rowIndex + "]//td[" + itemTypeColIndex + "]";
	    WebElement itemTypeCell = driver.findElement(By.xpath(itemTypeXpath));
	    return itemTypeCell.getText().trim().equalsIgnoreCase("SaaS");
	}


}
