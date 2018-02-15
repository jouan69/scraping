package perso.scraping.gakg.rmn;

import org.openqa.selenium.WebDriver;
import perso.scraping.gakg.AbstractAkgResultPage;
import perso.scraping.generic.param.ArtistSearch;

public class RmnSearchResultsPage extends AbstractAkgResultPage {

    private final String xPathImagesNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[1]";
    private final String xPathAlbumNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[2]";
    private final String xpathPageSize = "//input[contains(@id,'ItemPerPageDdl')][@type='text']";
    private final String xpathPageUp = "//a[@original-title='Suivante']";
    private final String agency = "RMN";
    public RmnSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    @Override
    public String getxPathResultNumber() {
        return xPathImagesNumber;
    }

    @Override
    public String getXpathPageSize() {
        return xpathPageSize;
    }

    public String getAgency() {
        return agency;
    }

    public String getXpathPageUp() {
        return xpathPageUp;
    }

    public String getxPathAlbumNumber() {
        return xPathAlbumNumber;
    }
}
