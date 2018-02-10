package perso.scraping.gakg.akg;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.openqa.selenium.WebDriver;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.driver.Browser;
import perso.scraping.generic.driver.DriverFactory;
import perso.scraping.generic.param.ArtistSearch;
import perso.scraping.generic.param.ResultPage;

public class AkgModule extends AbstractModule {

    public static final String AKG_PROPERTIES = "akg.properties";

    protected void configure() {}

    @Provides
    @Singleton
    @Named("akgDriver")
    WebDriver getWebDriver(){
        return DriverFactory.getWebDriver(Browser.CHROME);
    }


    @Provides
    @Named("akgResultPage")
    ResultPage getAkgResultPage(@Named("akgDriver") WebDriver driver,
                                ArtistSearch artistSearch){
        return new AkgSearchResultsPage(driver, artistSearch);
    }

    @Provides
    @Named("akgHomePage")
    HomePage getAkgHomePage(@Named("akgDriver") WebDriver driver,
                            ArtistSearch artistSearch){
        return new AkgHomePage(driver, artistSearch, AKG_PROPERTIES);
    }

}
