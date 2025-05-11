package com.qa.opencart.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest {
	
	WebDriver driver;
	
	DriverFactory df;
	protected Properties prop;
	
	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setup(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
			//browserName is passed from .xml file
			if(browserName!=null) {
				prop.setProperty("browser", browserName);
			}

		driver = df.initDriver(prop);//login page
		loginPage = new LoginPage(driver);
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	

}
