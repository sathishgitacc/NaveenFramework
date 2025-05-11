package com.qa.opencart.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.exceptions.BrowserException;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static String highlight;

	/**
	 * This method is used to init the driver on the basis of given browser name
	 * 
	 * @param browserName
	 */
	public WebDriver initDriver(Properties prop) {
		
		String browserName = prop.getProperty("browser");
		System.out.println("browser name : " + browserName);
		optionsManager = new OptionsManager(prop);
		
		highlight = prop.getProperty("highlight");

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			driver = new ChromeDriver(optionsManager.getChromeOptions());
			break;
		case "edge":
			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			break;
		case "firefox":
			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			break;
		case "safari":
			driver = new SafariDriver();
			break;

		default:
			System.out.println("plz pass the valid browser name..." + browserName);
			throw new BrowserException("===INVALID BROWSER===");
		}

		driver.get(prop.getProperty("url"));//login page url
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		return driver;
	}
	
	/**
	 * this is used to init the config properties
	 * @return
	 */
	public Properties initProp() {
		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	

}
