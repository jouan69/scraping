package perso.scraping.gakg.akg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.gakg.AbstractAkgResultPage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Optional;
import java.util.logging.Level;

public class AkgSearchResultsPage extends AbstractAkgResultPage {

    private final String xPathResultNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[1]";
    private final String xpathPageSize = "//input[contains(@id,'ItemPerPageDdl')]";
    private final String xpathPageUp = "//img[contains(@id,'NextBtn_img')]";

    private final String agency = "AKG";

    public AkgSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public int getResultNumber() {
        smallPause();
        WebElement nb = driver.findElement(By.xpath(getxPathResultNumber()));
        String raw = nb.getText();
        int resultNumber = extractIntFromString(raw);
        log(Level.INFO, "total resultNumber", resultNumber);
        return resultNumber;
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

}