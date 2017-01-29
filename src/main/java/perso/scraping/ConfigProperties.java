package perso.scraping;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ConfigProperties {
	
	private WebDriver driver;
	private static ConfigProperties instance =null;
	public static String configPathSearch="src\\main\\resources\\search\\";
	public static String configPathSite="src\\main\\resources\\site\\";
	
	private ConfigProperties(){
		System.setProperty("webdriver.chrome.driver", "C:/Dev/chromedriver/chromedriver.exe"); 
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
		driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	
	public static ConfigProperties getInstance(){
		if(instance == null){
			instance = new ConfigProperties();
		}
		return instance;
	}
	
	public WebDriver getDriver(){
		return driver;
	}
	
}
