package perso.scraping.gakg.rmn;

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

public class RmnModule extends AbstractModule {

    public static final String RMN_PROPERTIES = "rmn.properties";

    protected void configure() {
    }

    @Provides
    @Singleton
    @Named("rmnDriver")
    WebDriver getWebDriver() {
        return DriverFactory.getWebDriver(Browser.CHROME);
    }

    @Provides
    @Named("rmnResultPage")
    ResultPage getRmnResultPage(@Named("rmnDriver") WebDriver driver,
                                  ArtistSearch artistSearch) {
        return new RmnSearchResultsPage(driver, artistSearch);
    }

    @Provides
    @Named("rmnHomePage")
    HomePage getRmnHomePage(@Named("rmnDriver") WebDriver driver,
                            PropertiesLoader propertyLoader,
                            ArtistSearch artistSearch) {
        Properties siteProperties = propertyLoader.loadSiteProp(RMN_PROPERTIES);
        return new RmnHomePage(driver, artistSearch, siteProperties);
    }
}
