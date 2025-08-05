package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.v136.page.model.Screenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import java.lang.reflect.Method;
import driver.DriverManager;
import io.reactivex.rxjava3.functions.Action;
import listeners.ScreenshotListener;
import utils.ExtentReportManager;

public class CommonToAllTest {
	protected static ExtentReports extent;
	protected ExtentTest test;
	protected WebDriver driver;
	
	@BeforeSuite
    public void startReport() {
        extent = ExtentReportManager.getReportInstance();
    }

    @AfterSuite
    public void endReport() {
        if (extent != null) {
            extent.flush();
        }
    }

    @Parameters("browser")
	@BeforeMethod
	public void setup(@Optional("chrome") String browser,Method method) {
	   // System.out.println("Browser from XML or default: " + browser); // For debugging
	    DriverManager.init(browser); // Use the parameter
	    driver = DriverManager.getDriver(); // Set the driver
	    test = extent.createTest(method.getName()); // Automatically uses the test method name
    }
    
	
	

	@AfterMethod
	public void teardown(ITestResult result) {
	    if (result.getStatus() == ITestResult.FAILURE) {
	        test.fail("❌ Test Failed: " + result.getThrowable().getMessage());
	        String sreenshotpath = ExtentReportManager.captureScreenShot(result.getName());
	  try {
		  test.addScreenCaptureFromPath(sreenshotpath);
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	    
	    } else if (result.getStatus() == ITestResult.SUCCESS) {
	        test.pass("✅ Test Passed Successfully.");
	    } else if (result.getStatus() == ITestResult.SKIP) {
	        test.skip("⚠️ Test Skipped: " + result.getThrowable());
	    }

	   // DriverManager.tearDown();
	}
}