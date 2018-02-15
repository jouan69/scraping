package perso.scraping.bridgeman;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Properties;

public class BridgeManHomePage extends AbstractHomePage {

    private final String xPathInputSearch = "//input[@id='search_filter_text']";

    public BridgeManHomePage(WebDriver driver, ArtistSearch artistSearch, Properties bridgemanProperties) {
        super(driver, artistSearch, bridgemanProperties);
    }

    public void login(){
        goTo();
        WebElement loginInput = driver.findElement(By.xpath("(//form[@id='login']/div[@id='login-container']/div[@class='form-field-field ']/input[@id='login_login'])[1]"));
    	String login = siteProp.getProperty("login");
    	String password = siteProp.getProperty("password");
    	loginInput.sendKeys(login+"\t"+password);
    	WebElement submitButton = driver.findElement(By.xpath("(//input[@value='Log in'])[1]"));
    	submitButton.click();
    }

    public String getxPathInputSearch() {
        return xPathInputSearch;
    }
}