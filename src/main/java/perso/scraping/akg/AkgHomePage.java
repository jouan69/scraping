package perso.scraping.akg;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractPage;

public class AkgHomePage extends AbstractPage{

    private final String URL;

    public AkgHomePage(String searchFile) {
       	super(searchFile);
    	setSiteProp("akg.properties");
		URL = siteProp.getProperty("url");
    }

    public String getUrl() {
        return URL;
    }
    
    public void typeSearch(){	
        WebElement inputWidget = driver.findElement(By.xpath("//input[@class='ui-autocomplete-input']"));
        String input = searchProp.getProperty("search");
        inputWidget.sendKeys(input+ENTER_KEY);
    }

    public void submitSearch(){	
        WebElement submitButton = driver.findElement(By.xpath("//div[@id='btn_cerca']/a/img"));
        submitButton.click();
    }

    public void login(){
    	
    	verySmallPause();
    	
    	WebElement login = driver.findElement(By.xpath("//input[contains(@id,'LoginField')]"));
    	login.sendKeys(siteProp.getProperty("login"));
    	
    	verySmallPause();
    	
    	WebElement passWord = driver.findElement(By.xpath("//input[contains(@id,'Password')]"));
    	passWord.sendKeys(siteProp.getProperty("password"));
    	
    	verySmallPause();
    	
    	WebElement submitButton = driver.findElement(By.xpath("//div[contains(@id,'LoginBtn')]/a"));
    	submitButton.click();
    	
    	smallPause();
    	
    }
    
	public void goTo() {
	    driver.get(URL); 
	}

}