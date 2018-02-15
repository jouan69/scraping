package perso.scraping.gakg.rmn;

import org.openqa.selenium.WebDriver;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Properties;

public class RmnHomePage extends AbstractHomePage {

    private final String xPathInputSearch = "//input[@placeholder='Recherche']";

    public RmnHomePage(WebDriver driver, ArtistSearch artistSearch, Properties rmnProperties) {
        super(driver, artistSearch, rmnProperties);
    }

    public void login() {
        goTo();
    }

    public String getxPathInputSearch() {
        return xPathInputSearch;
    }
}
