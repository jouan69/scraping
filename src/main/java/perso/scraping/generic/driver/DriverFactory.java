package perso.scraping.generic.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static WebDriver getWebDriver(Browser browser) {
        switch (browser) {
            case CHROME:
                return getChrome();
            case FIREFOX_HEADLESS:
                return getFireFoxHeadless();
            default:
                throw new IllegalArgumentException("unknown browser");
        }
    }

    private static WebDriver getChrome() {
        System.setProperty("webdriver.chrome.driver", "C:/Dev/chromedriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    private static WebDriver getFireFoxHeadless() {
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        firefoxBinary.addCommandLineOptions("--headless");
        System.setProperty("webdriver.gecko.driver", "C:/Dev/geckodriver/geckodriver.exe");
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.setBinary(firefoxBinary);
        return new FirefoxDriver(firefoxOptions);
    }
}