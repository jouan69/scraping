package perso.scraping.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import perso.scraping.AbstractHomePage;
import perso.scraping.akg.AkgHomePage;
import perso.scraping.bridgeman.BridgeManHomePage;
import perso.scraping.scala.ScalaHomePage;

import java.util.concurrent.TimeUnit;

public class BasicModule extends AbstractModule {

    public static final String AKG_PROPERTIES = "akg.properties";
    public static final String BRIDGEMAN_PROPERTIES = "bridgeman.properties";
    public static final String SCALA_PROPERTIES = "scala.properties";

    protected void configure() {

    }

    @Provides
    @Named("akgHomePage")
    AbstractHomePage getAkgHomePage(WebDriver driver){
        return new AkgHomePage(driver, AKG_PROPERTIES);
    }

    @Provides
    @Named("bridgeManHomePage")
    AbstractHomePage getBridgeManHomePage(WebDriver driver){
        return new BridgeManHomePage(driver, BRIDGEMAN_PROPERTIES);
    }

    @Provides
    @Named("scalaHomePage")
    AbstractHomePage getScalaHomePage(WebDriver driver){
        return new ScalaHomePage(driver, SCALA_PROPERTIES);
    }

    @Provides
    WebDriver getWebDriver(){
        System.setProperty("webdriver.chrome.driver", "C:/Dev/chromedriver/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

}
