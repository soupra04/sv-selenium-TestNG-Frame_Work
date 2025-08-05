package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

	private static WebDriver driver;

	public static WebDriver getDriver() {
		return driver;
	}

	public static void init(String browser) {
		if (browser == null || browser.isBlank()) {
			throw new IllegalArgumentException("Browser name is null or empty.");
		}

		if (browser.equalsIgnoreCase("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			chromeOptions.addArguments("--disable-notifications");
			driver = new ChromeDriver(chromeOptions);

		} else if (browser.equalsIgnoreCase("firefox")) {
			FirefoxOptions firefoxOptions = new FirefoxOptions();
			firefoxOptions.addArguments("--start-maximized");
			driver = new FirefoxDriver(firefoxOptions);

		} else if (browser.equalsIgnoreCase("edge")) {
			EdgeOptions edgeOptions = new EdgeOptions();
			edgeOptions.addArguments("--start-maximized");
			driver = new EdgeDriver(edgeOptions);

		} else {
			throw new RuntimeException("Unsupported browser: " + browser);
		}
	}

	public static void tearDown() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}
}
