package perso.scraping.scala;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractPage;

public class ScalaHomePage extends AbstractPage{

    private final String URL;

    public ScalaHomePage(String searchFile) {
    	super(searchFile);
    	setSiteProp("scala.properties");
    	URL = siteProp.getProperty("url");
    }
    
    public String getUrl() {
        return URL;
    }
    
    public void typeSearch(){	
        WebElement inputWidget = driver.findElement(By.xpath("//input[@id='ricerca_s']"));
        String input = searchProp.getProperty("search");
        inputWidget.clear();
        inputWidget.sendKeys(input);
    }

    public void submitSearch(){	
        WebElement submitButton = driver.findElement(By.xpath("//div[@id='btn_cerca']/a/img"));
        submitButton.click();
    }

	public void goTo() {
	    driver.get(URL); 
	}

}