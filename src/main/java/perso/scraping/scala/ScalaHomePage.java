package perso.scraping.scala;

import org.openqa.selenium.WebDriver;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Properties;

public class ScalaHomePage extends AbstractHomePage {

    private final String xPathInputSearch = "//input[@id='ricerca_s']";

    public ScalaHomePage(WebDriver driver, ArtistSearch artistSearch, Properties scalaProperties) {
        super(driver, artistSearch, scalaProperties);
    }

    public void login() {
        goTo();
    }

    public String getxPathInputSearch() {
        return xPathInputSearch;
    }
}