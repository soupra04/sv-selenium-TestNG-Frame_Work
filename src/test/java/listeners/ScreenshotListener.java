package listeners;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import driver.DriverManager;

public class ScreenshotListener  extends TestListenerAdapter {
	
	
	@Override
	  public void onTestFailure(ITestResult result) {
		
		TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
		File src = ts.getScreenshotAs(OutputType.FILE);
		File trg = new File("D:\\Java workspace\\selenium\\Screenshots\\"+ result.getName()+".png");
		try {
			FileUtils.copyFile(src, trg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
