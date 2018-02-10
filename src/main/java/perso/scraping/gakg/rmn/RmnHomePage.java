package perso.scraping.gakg.rmn;

import org.openqa.selenium.WebDriver;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.param.ArtistSearch;

public class RmnHomePage extends AbstractHomePage {

    private final String xPathInputSearch = "//input[@placeholder='Recherche']";

    public RmnHomePage(WebDriver driver, ArtistSearch artistSearch, String akgProperties) {
        super(driver, artistSearch, akgProperties);
    }

    public void login() {
        goTo();
    }

    public String getxPathInputSearch() {
        return xPathInputSearch;
    }
}
