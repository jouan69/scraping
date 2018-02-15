package perso.scraping.gakg.akg;

import org.openqa.selenium.WebDriver;
import perso.scraping.gakg.AbstractAkgResultPage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Optional;

public class AkgSearchResultsPage extends AbstractAkgResultPage {

    private final String xPathResultNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[1]";
    private final String xPathAlbumNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[2]";
    private final String xpathPageSize = "//input[contains(@id,'ItemPerPageDdl')]";
    private final String xpathPageUp = "//img[contains(@id,'NextBtn_img')]";

    private final String agency = "AKG";

    public AkgSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public Optional<String> getDateField() {
        return Optional.empty();
    }

    @Override
    public String getxPathResultNumber() {
        return xPathResultNumber;
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