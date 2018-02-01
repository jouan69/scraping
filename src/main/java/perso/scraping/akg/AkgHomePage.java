package perso.scraping.akg;

import com.google.inject.Inject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.param.ArtistSearch;

public class AkgHomePage extends AbstractHomePage {

    public AkgHomePage(WebDriver driver, ArtistSearch artistSearch, String akgProperties) {
        super(driver, artistSearch, akgProperties);
    }

    public void typeSearch(String artist) {

        WebElement inputWidget = driver.findElement(By.xpath("//input[@class='ui-autocomplete-input']"));
        inputWidget.sendKeys(artist + ENTER_KEY);
        pause();

    }

    public void login() {

        goTo();

        verySmallPause();

        WebElement login = driver.findElement(By.xpath("//input[contains(@id,'LoginField')]"));
        login.sendKeys(siteProp.getProperty("login"));

        verySmallPause();

        WebElement passWord = driver.findElement(By.xpath("//input[contains(@id,'Password')]"));
        passWord.sendKeys(siteProp.getProperty("password"));

        verySmallPause();

        WebElement submitButton = driver.findElement(By.xpath("//div[contains(@id,'LoginBtn')]/a"));
        submitButton.click();

        pause();

    }

}