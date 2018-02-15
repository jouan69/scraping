package perso.scraping.scala;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.openqa.selenium.WebDriver;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.PropertiesLoader;
import perso.scraping.generic.driver.Browser;
import perso.scraping.generic.driver.DriverFactory;
import perso.scraping.generic.param.ArtistSearch;
import perso.scraping.generic.ResultPage;

import java.util.Properties;

public class ScalaModule extends AbstractModule {

    public static final String SCALA_PROPERTIES = "scala.properties";

    protected void configure() {
    }

    @Provides
    @Singleton
    @Named("scalaDriver")
    WebDriver getWebDriver() {
        return DriverFactory.getWebDriver(Browser.CHROME);
    }

    @Provides
    @Named("scalaResultPage")
    ResultPage getScalaResultPage(@Named("scalaDriver") WebDriver driver,
                                  ArtistSearch artistSearch) {
        return new ScalaSearchResultsPage(driver, artistSearch);
    }

    @Provides
    @Named("scalaHomePage")
    HomePage getScalaHomePage(@Named("scalaDriver") WebDriver driver,
                              PropertiesLoader propertyLoader,
                              ArtistSearch artistSearch) {
        Properties siteProperties = propertyLoader.loadSiteProp(SCALA_PROPERTIES);
        return new ScalaHomePage(driver, artistSearch, siteProperties);
    }
}
