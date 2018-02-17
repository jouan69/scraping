package perso.scraping.gakg.rmn;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.gakg.AbstractAkgResultPage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.logging.Level;

public class RmnSearchResultsPage extends AbstractAkgResultPage {

    private final String xPathImagesNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[1]";
    private final String xPathAlbumNumber = "(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[2]";
    private final String xpathPageSize = "//input[contains(@id,'ItemPerPageDdl')][@type='text']";
    private final String xpathPageUp = "//a[@original-title='Suivante']";
    private final String agency = "RMN";
    public RmnSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public int getResultNumber() {
        smallPause();
        int albumNumber = getAlbumNumber();
        WebElement nb = driver.findElement(By.xpath(getxPathResultNumber()));
        String raw = nb.getText();
        int resultNumber = albumNumber + extractIntFromString(raw);
        log(Level.SEVERE, "total resultNumber", resultNumber);
        return resultNumber;
    }

    private int getAlbumNumber() {
        try {
            WebElement nb = driver.findElement(By.xpath(getxPathAlbumNumber()));
            String raw = nb.getText();
            int resultNumber = extractIntFromString(raw);
            log(Level.SEVERE, "AlbumNumber", resultNumber);
            return resultNumber;
        } catch (Exception e) {
            return 0;
        }
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
