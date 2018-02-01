package perso.scraping.bridgeman;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.openqa.selenium.WebDriver;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.ImageWebSite;
import perso.scraping.generic.driver.DriverFactory;
import perso.scraping.generic.param.ArtistSearch;

public class BridgeManModule extends AbstractModule {

    public static final String BRIDGEMAN_PROPERTIES = "bridgeman.properties";

    protected void configure() {}

    @Provides
    @Singleton
    @Named("bridgeManDriver")
    WebDriver getWebDriver(){
        return DriverFactory.getWebDriver();
    }

    @Provides
    ImageWebSite getBridgeManSearch(@Named("bridgeManHomePage") AbstractHomePage homePage,
                                    @Named("bridgeManResultPage") AbstractResultPage resultPage){
        return new BridgeManSearch(homePage, resultPage);
    }

    @Provides
    @Named("bridgeManResultPage")
    AbstractResultPage getBridgeManResultPage(@Named("bridgeManDriver") WebDriver driver,
                                              ArtistSearch artistSearch){
        return new BridgeManSearchResultsPage(driver, artistSearch);
    }

    @Provides
    @Named("bridgeManHomePage")
    AbstractHomePage getBridgeManHomePage(@Named("bridgeManDriver") WebDriver driver,
                                    ArtistSearch artistSearch){
        return new BridgeManHomePage(driver, artistSearch, BRIDGEMAN_PROPERTIES);
    }
}
