package perso.scraping.bridgeman;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.AbstractHomePage;

public class BridgeManHomePage extends AbstractHomePage {

    public BridgeManHomePage(final WebDriver driver, final String propertyFile) {
    	super(driver);
    	setSiteProp(propertyFile);
    }

    public void typeSearch(String artist){
        WebElement inputWidget = driver.findElement(By.xpath("//input[@id='search_filter_text']"));
        inputWidget.clear();
        inputWidget.sendKeys(artist+ENTER_KEY);
    }

    public void submitSearch(){	
        WebElement submitButton = driver.findElement(By.xpath("//div[@id='btn_cerca']/a/img"));
        submitButton.click();
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

}