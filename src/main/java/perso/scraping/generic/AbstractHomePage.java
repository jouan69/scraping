package perso.scraping.generic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.generic.param.ArtistSearch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractHomePage extends AbstractPage implements HomePage {

    protected Properties siteProp;
    private final ArtistSearch artistSearch;

    public AbstractHomePage(WebDriver driver, ArtistSearch artistSearch, Properties siteProp) {
        super(driver);
        this.artistSearch = artistSearch;
        this.siteProp = siteProp;
    }
    
    public Properties getSiteProp() {
        return siteProp;
    }

    public void setSiteProp(Properties siteProp) {
        this.siteProp = siteProp;
    }

    public void goTo() {
        driver.get(siteProp.getProperty("url"));
    }

    public void typeSearch(String artist) {
        WebElement inputWidget = driver.findElement(By.xpath(getxPathInputSearch()));
        inputWidget.clear();
        inputWidget.sendKeys(artist + ENTER_KEY);
    }

    public abstract void login();

    public ArtistSearch getArtistSearch() {
        return artistSearch;
    }

    public void typeArtistName() {
        typeSearch(getArtistName());
    }

    public String getArtistName() {
        return artistSearch.getArtistName();
    }

    public abstract String getxPathInputSearch();

}