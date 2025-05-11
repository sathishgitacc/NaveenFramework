package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

import io.qameta.allure.Step;
import net.minidev.json.JSONObject;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static String highlight;

	/**
	 * This method is used to init the driver on the basis of given browser name
	 * 
	 * @param browserName
	 */
	@Step("init driver with properties: {0}")
	public WebDriver initDriver(Properties prop) {
		

		String browserName = prop.getProperty("browser");
		System.out.println("browser name : " + browserName);
		ChainTestListener.log("browser name: "+browserName);
		optionsManager = new OptionsManager(prop);

		highlight = prop.getProperty("highlight");

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));			
			break;
		case "edge":
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));			
			break;
		case "firefox":
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));			
			break;
		case "safari":
			tlDriver.set(new SafariDriver());			
			break;

		default:
			System.out.println("plz pass the valid browser name..." + browserName);
			throw new BrowserException("===INVALID BROWSER===");
		}

		getDriver().get(prop.getProperty("url"));// login page url
		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		return getDriver();
	}
	
	
	/**
	 * getDriver: get the local thready copy of the driver
	 */
	
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	

	/**
	 * this is used to init the config properties
	 * 
	 * @return
	 */

	// mvn clean install -Denv="stage"
	public Properties initProp() {

		String envName = System.getProperty("env");
		FileInputStream ip = null;
		prop = new Properties();

		try {
			if (envName == null) {
				System.out.println("env is null, hence running the tests on QA env by default...");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				System.out.println("Running tests on env: " + envName);
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/prod.config.properties");
					break;

				default:
					throw new FrameworkException("===INVALID ENV NAME==== : "+ envName);
				}
			}
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	
	/**
	 * takescreenshot
	 */

	public static File getScreenshotFile() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		return srcFile;
	}

	public static byte[] getScreenshotByte() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);// temp dir

	}

	public static String getScreenshotBase64() {
		return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);// temp dir

	}
	
	public void createEnvDet(Properties prop)
	{
		File resultsDir = new File("allure-results");
        if (!resultsDir.exists()) {
            resultsDir.mkdir();
        }

        // Write to environment.properties
        try (FileWriter writer = new FileWriter("allure-results/environment.properties")) {
            writer.write("Browser=" + prop.getProperty("browser") + "\n");
          
           
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
        
        public static void writeJson()
        {
            // Create a JSON object for the executor
            JSONObject executor = new JSONObject();
            executor.put("name", "Local Execution");
            executor.put("type", "Local");
            executor.put("url", "http://localhost");
            executor.put("buildOrder", "1");
            executor.put("buildName", "Local Test Run");
            executor.put("buildUrl", "http://localhost/run/1");

            // Write to the executor.json file
            try (FileWriter file = new FileWriter("allure-results/executor.json")) {
                file.write(executor.toString());  // Pretty print with indentation
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}


