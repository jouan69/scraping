package perso.scraping.gakg.akg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Properties;

public class AkgHomePage extends AbstractHomePage {

    private final String xPathInputSearch = "//input[@class='ui-autocomplete-input']";

    public AkgHomePage(WebDriver driver, ArtistSearch artistSearch, Properties akgProperties) {
        super(driver, artistSearch, akgProperties);
    }

    public void login() {

        goTo();

        verySmallPause();

        WebElement login = driver.findElement(By.xpath("//input[contains(@id,'LoginField')]"));
        login.clear();
        login.sendKeys(siteProp.getProperty("login"));

        verySmallPause();

        WebElement passWord = driver.findElement(By.xpath("//input[contains(@id,'Password')]"));
        passWord.clear();
        passWord.sendKeys(siteProp.getProperty("password") + ENTER_KEY);

        verySmallPause();

        // search this on page after login
        WebElement link = driver.findElement(By.xpath("//div[contains(@class,'CT Slideshow ABS')]"));

        verySmallPause();
    }

    public String getxPathInputSearch() {
        return xPathInputSearch;
    }
}