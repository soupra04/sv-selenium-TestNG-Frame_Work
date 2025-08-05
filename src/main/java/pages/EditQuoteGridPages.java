package pages;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.CommonToAllPages;
import driver.DriverManager;

public class EditQuoteGridPages extends CommonToAllPages {

	public EditQuoteGridPages(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public List<String[]> changeMarginForSaasItems() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		System.out.println("Executing changeMarginForSaasItems");

		int itemTypeColIndex = getColumnIndexByPartialHeader("item type");
		int marginColIndex = getColumnIndexByPartialHeader("cc mrgn");
		int ccUnitCostFColIndex = getColumnIndexByPartialHeader("unit cost");
		int custUnitPriceColIndex = getColumnIndexByPartialHeader("unit price");
		int custToalPriceIndex = getColumnIndexByPartialHeader("ext price");
		int qtyColIndex = getColumnIndexByPartialHeader("Qty");

		List<String[]> resultList = new ArrayList<>();
		List<WebElement> rows = driver.findElements(By.xpath("//tr[@tabindex='-1']"));

		for (int i = 1; i <= rows.size(); i++) {
			if (!isSaaSItem(i, itemTypeColIndex))
				continue;

			updateEditableCell(i, marginColIndex, "40", "VAR_Margin_Pct__c");

			WebElement row = driver.findElement(By.xpath("//table[contains(@id,'qtGrid')]//tr[" + i + "]"));

			double ccUCostFinal = parseDouble(getCellDataAttribute(row, ccUnitCostFColIndex, "data-src"));
			double custUPrice = parseDouble(getCellDataAttribute(row, custUnitPriceColIndex, "data-src"));
			double ccMarginPercent = parseDouble(getCellDataAttribute(row, marginColIndex, "data-src"));
			double custTPrice = parseDouble(getCellDataAttribute(row, custToalPriceIndex, "data-src"));
			double qty = parseDouble(getCellDataAttribute(row, qtyColIndex, "data-src"));

			double expectedCustUnitPrice = Math.round((ccUCostFinal / (1 - ccMarginPercent / 100)) * 100.0) / 100.0;
			double expectedCustTotalPrice = custUPrice * qty;

			String expectedQtyStr = String.valueOf((long) qty);
			String actualQtyStr = getCellText(row, qtyColIndex);

			addResult(resultList, "Qty", Double.parseDouble(expectedQtyStr), Double.parseDouble(actualQtyStr));
			addResult(resultList, "Unit Price", expectedCustUnitPrice, custUPrice);
			addResult(resultList, "Total Price", expectedCustTotalPrice, custTPrice);
		}

		waitAndSaveQuoteChanges();
		return resultList;
	}

	public List<String[]> changeCustDiscount() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		System.out.println("Executing changeCustDiscForSaasItems");

		int itemTypeColIndex = getColumnIndexByPartialHeader("item type");
		int custUnitPriceColIndex = getColumnIndexByPartialHeader("cust unit");
		int custToalPriceIndex = getColumnIndexByPartialHeader("ext price");
		int qtyColIndex = getColumnIndexByPartialHeader("Qty");
		int custDiscColIndex = getColumnIndexByPartialHeader("cust dsc");
		int listPriceColIndex = getColumnIndexByPartialHeader("list price");
		int termColIndex = getColumnIndexByPartialHeader("term");

		List<String[]> resultList = new ArrayList<>();

		if (itemTypeColIndex == -1 || custDiscColIndex == -1) {
			System.out.println("Required columns not found");
			return resultList;
		}

		List<WebElement> rows = driver.findElements(By.xpath("//tr[@tabindex='-1']"));

		for (int i = 1; i <= rows.size(); i++) {
			String itemTypeXpath = "//table[contains(@id,'qtGrid')]//tr[" + i + "]//td[" + itemTypeColIndex + "]";
			WebElement itemTypeCell = driver.findElement(By.xpath(itemTypeXpath));
			String itemTypeText = itemTypeCell.getText().trim();

			if (itemTypeText.equalsIgnoreCase("SaaS")) {
				updateEditableCell(i, custDiscColIndex, "20", "Cust_Discount_Pct__c");

				WebElement row = driver.findElement(By.xpath("//table[contains(@id,'qtGrid')]//tr[" + i + "]"));

				double custDiscPct = parseDouble(getCellDataAttribute(row, custDiscColIndex, "data-src"));
				double custUPrice = parseDouble(getCellDataAttribute(row, custUnitPriceColIndex, "data-src"));
				double custTPrice = parseDouble(getCellDataAttribute(row, custToalPriceIndex, "data-src"));
				double qty = parseDouble(getCellDataAttribute(row, qtyColIndex, "data-src"));
				double listPrice = parseDouble(getCellDataAttribute(row, listPriceColIndex, "data-src"));
				double term = parseDouble(getCellDataAttribute(row, termColIndex, "data-src"));

				double expectedCustUnitPrice = Math.round(listPrice * term * (1 - custDiscPct / 100) * 100.0) / 100.0;
				System.out.println("I want to check " + expectedCustUnitPrice);
				double expectedCustTotalPrice = Math.round(expectedCustUnitPrice * qty * 100.0) / 100.0;
				System.out.println("I want to check qty " + qty);
				System.out.println("I want to check expected Cust total price " + expectedCustTotalPrice);

				String expectedUnitPriceStr = String.valueOf(expectedCustUnitPrice);
				String actualUnitPriceStr = String.valueOf(custUPrice);

				String expectedTotalPriceStr = String.valueOf(expectedCustTotalPrice);
				String actualTotalPriceStr = String.valueOf(custTPrice);

				String expectedQtyStr = String.valueOf((long) qty);
				String actualQtyStr = row.findElement(By.xpath(".//td[" + qtyColIndex + "]//span")).getText().trim();

				System.out.printf("Row %d => Qty Expected: %s | Actual: %s%n", i, expectedQtyStr, actualQtyStr);
				System.out.printf("Row %d => Unit Price Expected: %s | Actual: %s%n", i, expectedUnitPriceStr,
						actualUnitPriceStr);
				System.out.printf("Row %d => Total Price Expected: %s | Actual: %s%n", i, expectedTotalPriceStr,
						actualTotalPriceStr);

				resultList.add(new String[] { "Qty", expectedQtyStr, actualQtyStr });
				resultList.add(new String[] { "Unit Price", expectedUnitPriceStr, actualUnitPriceStr });
				resultList.add(new String[] { "Total Price", expectedTotalPriceStr, actualTotalPriceStr });
			}
		}
		waitAndSaveQuoteChanges();
		return resultList;
	}

	public List<String[]> changeCustUnitPrice() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		System.out.println("Executing change cust unit price for Saas Items");

		int itemTypeColIndex = getColumnIndexByPartialHeader("item type");
		int custUnitPriceColIndex = getColumnIndexByPartialHeader("cust unit");
		int custToalPriceIndex = getColumnIndexByPartialHeader("ext price");
		int qtyColIndex = getColumnIndexByPartialHeader("Qty");
		int custDiscColIndex = getColumnIndexByPartialHeader("cust dsc");
		int listPriceColIndex = getColumnIndexByPartialHeader("list price");
		int termColIndex = getColumnIndexByPartialHeader("term");

		List<String[]> resultList = new ArrayList<>();

		if (itemTypeColIndex == -1 || custDiscColIndex == -1) {
			System.out.println("Required columns not found");
			return resultList;
		}

		List<WebElement> rows = driver.findElements(By.xpath("//tr[@tabindex='-1']"));

		for (int i = 1; i <= rows.size(); i++) {
			String itemTypeXpath = "//table[contains(@id,'qtGrid')]//tr[" + i + "]//td[" + itemTypeColIndex + "]";
			WebElement itemTypeCell = driver.findElement(By.xpath(itemTypeXpath));
			String itemTypeText = itemTypeCell.getText().trim();

			if (itemTypeText.equalsIgnoreCase("SaaS")) {
				updateEditableCell(i, custUnitPriceColIndex, "200", "Cust_Unit_Price__c");

				WebElement row = driver.findElement(By.xpath("//table[contains(@id,'qtGrid')]//tr[" + i + "]"));

				double custDiscPct = parseDouble(getCellDataAttribute(row, custDiscColIndex, "data-src"));
				double custUPrice = parseDouble(getCellDataAttribute(row, custUnitPriceColIndex, "data-src"));
				double custTPrice = parseDouble(getCellDataAttribute(row, custToalPriceIndex, "data-src"));
				double qty = parseDouble(getCellDataAttribute(row, qtyColIndex, "data-src"));
				double listPrice = parseDouble(getCellDataAttribute(row, listPriceColIndex, "data-src"));
				double term = parseDouble(getCellDataAttribute(row, termColIndex, "data-src"));

				double expectedCustTotalPrice = custUPrice * qty;
				double expectedCustDiscPct = ((listPrice * term - custUPrice) / (listPrice * term)) * 100;
				expectedCustDiscPct = Math.round(expectedCustDiscPct * 10000.0) / 10000.0;
				System.out.println("Expected Cust Discount %: " + expectedCustDiscPct);

				System.out.println("I want to check expected Cust total price " + expectedCustTotalPrice);

				String expectedCustTotalPriceStr = String.valueOf(expectedCustTotalPrice);
				String actualCustTotalPriceStr = String.valueOf(custTPrice);

				String expectedCustDiscPctStr = String.valueOf(expectedCustDiscPct);
				String actualCustDiscPctStr = String.valueOf(custDiscPct);

				System.out.printf("Row %d =>Cust Disc PCT Expected: %s | Actual: %s%n", i, expectedCustDiscPctStr,
						actualCustDiscPctStr);
				System.out.printf("Row %d =>Cust Total Price Expected: %s | Actual: %s%n", i, expectedCustTotalPriceStr,
						actualCustTotalPriceStr);

				resultList.add(new String[] { "Cust Disc PCT", expectedCustDiscPctStr, actualCustDiscPctStr });
				resultList.add(new String[] { "Cust Total Price", expectedCustTotalPriceStr, actualCustTotalPriceStr });
			}
		}
		waitAndSaveQuoteChanges();
		return resultList;
	}
	
	
	public List<String[]> changeCCDiscountPCT() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		System.out.println("Executing change CC Disc For SaasItems");

		int itemTypeColIndex = getColumnIndexByPartialHeader("item type");
		int custUnitPriceColIndex = getColumnIndexByPartialHeader("cust unit");
		int custToalPriceIndex = getColumnIndexByPartialHeader("ext price");
		int qtyColIndex = getColumnIndexByPartialHeader("Qty");
		int custDiscColIndex = getColumnIndexByPartialHeader("cust dsc");
		int listPriceColIndex = getColumnIndexByPartialHeader("list price");
		int termColIndex = getColumnIndexByPartialHeader("term");
		int ccDiscColIndex = getColumnIndexByPartialHeader("cc discount");
		int ccUnitCostColIndex = getColumnIndexByPartialHeader("unit cost");
		int ccUnitCostFColIndex = getColumnIndexByPartialHeader("unit cost final");
		int ccTotalCostFColIndex = getColumnIndexByPartialHeader("total cost final");

		List<String[]> resultList = new ArrayList<>();

		if (itemTypeColIndex == -1 || listPriceColIndex == -1) {
			System.out.println("Required columns not found");
			return resultList;
		}

		List<WebElement> rows = driver.findElements(By.xpath("//tr[@tabindex='-1']"));

		for (int i = 1; i <= rows.size(); i++) {
			String itemTypeXpath = "//table[contains(@id,'qtGrid')]//tr[" + i + "]//td[" + itemTypeColIndex + "]";
			WebElement itemTypeCell = driver.findElement(By.xpath(itemTypeXpath));
			String itemTypeText = itemTypeCell.getText().trim();

			if (itemTypeText.equalsIgnoreCase("SaaS")) {
				updateEditableCell(i, ccDiscColIndex, "30", "CC_Source_Discount__c");

				WebElement row = driver.findElement(By.xpath("//table[contains(@id,'qtGrid')]//tr[" + i + "]"));

				BigDecimal custDiscPct = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, custDiscColIndex, "data-src")));
				BigDecimal custUPrice = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, custUnitPriceColIndex, "data-src")));
				BigDecimal custTPrice = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, custToalPriceIndex, "data-src")));
				BigDecimal qty = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, qtyColIndex, "data-src")));
				BigDecimal listPrice = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, listPriceColIndex, "data-src")));
				BigDecimal term = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, termColIndex, "data-src")));
				BigDecimal ccDiscPct = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, ccDiscColIndex, "data-src")));
				BigDecimal ccUnitCostFinal = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, ccUnitCostFColIndex, "data-src")));
				BigDecimal ccUnitCost = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, ccUnitCostColIndex, "data-src")));
				BigDecimal ccTotalCostFinal = BigDecimal.valueOf(parseDouble(getCellDataAttribute(row, ccTotalCostFColIndex, "data-src")));

				BigDecimal discountMultiplier = BigDecimal.ONE.subtract(ccDiscPct.divide(BigDecimal.valueOf(100), 8, RoundingMode.HALF_UP));
				BigDecimal expectedCCUnitCost = listPrice.multiply(term).multiply(discountMultiplier).setScale(2, RoundingMode.HALF_UP);

				BigDecimal expectedCCTotalCostFinal = ccUnitCostFinal.multiply(qty).setScale(2, RoundingMode.HALF_UP);

				System.out.println("I want to check List Price " + listPrice);
				System.out.println("I want to check cc discount % " + ccDiscPct);
				System.out.println("I want to check Calculated/Expected CC Unit Cost " + expectedCCUnitCost);
				System.out.println("I want to check cc Unit cost final " + ccUnitCostFinal);
				System.out.println("I want to check expected CC total Cost Final " + expectedCCTotalCostFinal);

				System.out.printf("Row %d => CC Unit cost  Expected: %s | Actual: %s%n", i, expectedCCUnitCost, ccUnitCost);
				System.out.printf("Row %d => CC Total Cost Final Expected: %s | Actual: %s%n", i, expectedCCTotalCostFinal, ccTotalCostFinal);

				resultList.add(new String[] { "Cc Unit Cost", expectedCCUnitCost.toPlainString(), ccUnitCost.toPlainString() });
				resultList.add(new String[] { "CC Total Cost Final", expectedCCTotalCostFinal.toPlainString(), ccTotalCostFinal.toPlainString() });
			}
		}
		waitAndSaveQuoteChanges();
		return resultList;
	}
}