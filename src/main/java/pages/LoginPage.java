package pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.CommonToAllPages;
import utils.PropertiesReader;

public class LoginPage extends CommonToAllPages {

	public LoginPage(WebDriver driver) {
		super(driver);

	}

	private By username = By.id("username");
	private By password = By.id("password");
	private By submitBtn = By.id("Login");

	public String loginWithValidCred(String user, String pwd) {

		driver.get(PropertiesReader.readkey("CC_url"));
		enterInput(username, user);
		enterInput(password, pwd);
		clickElement(submitBtn);

		WebElement eQText = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[contains(@title,'Enterprise Quoting')]")));
		System.out.println("text is retrieved" + " : " + eQText.getText());
		return eQText.getText();

	
	}

}
