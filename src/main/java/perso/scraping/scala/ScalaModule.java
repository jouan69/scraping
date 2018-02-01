package perso.scraping.scala;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.openqa.selenium.WebDriver;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.driver.DriverFactory;
import perso.scraping.generic.param.ArtistSearch;

public class ScalaModule extends AbstractModule {

    public static final String SCALA_PROPERTIES = "scala.properties";

    protected void configure() {
    }

    @Provides
    @Singleton
    @Named("scalaDriver")
    WebDriver getWebDriver() {
        return DriverFactory.getWebDriver();
    }

    @Provides
    @Named("scalaResultPage")
    AbstractResultPage getScalaResultPage(@Named("scalaDriver") WebDriver driver,
                                          ArtistSearch artistSearch) {
        return new ScalaSearchResultsPage(driver, artistSearch);
    }

    @Provides
    @Named("scalaHomePage")
    AbstractHomePage getScalaHomePage(@Named("scalaDriver") WebDriver driver,
                                    ArtistSearch artistSearch) {
        return new ScalaHomePage(driver, artistSearch, SCALA_PROPERTIES);
    }
}
