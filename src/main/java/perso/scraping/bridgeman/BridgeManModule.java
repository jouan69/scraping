package perso.scraping.bridgeman;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.openqa.selenium.WebDriver;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.ImageWebSite;
import perso.scraping.generic.driver.Browser;
import perso.scraping.generic.driver.DriverFactory;
import perso.scraping.generic.param.ArtistSearch;
import perso.scraping.generic.param.ResultPage;

public class BridgeManModule extends AbstractModule {

    public static final String BRIDGEMAN_PROPERTIES = "bridgeman.properties";

    protected void configure() {}

    @Provides
    @Singleton
    @Named("bridgeManDriver")
    WebDriver getWebDriver(){
        return DriverFactory.getWebDriver(Browser.CHROME);
    }

    @Provides
    ImageWebSite getBridgeManSearch(@Named("bridgeManHomePage") HomePage homePage,
                                    @Named("bridgeManResultPage") ResultPage resultPage){
        return new BridgeManSearch(homePage, resultPage);
    }

    @Provides
    @Named("bridgeManResultPage")
    ResultPage getBridgeManResultPage(@Named("bridgeManDriver") WebDriver driver,
                                      ArtistSearch artistSearch){
        return new BridgeManSearchResultsPage(driver, artistSearch);
    }

    @Provides
    @Named("bridgeManHomePage")
    HomePage getBridgeManHomePage(@Named("bridgeManDriver") WebDriver driver,
                                  ArtistSearch artistSearch){
        return new BridgeManHomePage(driver, artistSearch, BRIDGEMAN_PROPERTIES);
    }
}
