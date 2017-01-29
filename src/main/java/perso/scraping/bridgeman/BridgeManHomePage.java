package perso.scraping.bridgeman;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractPage;

public class BridgeManHomePage extends AbstractPage{

    private final String URL;

    public BridgeManHomePage(String searchFile) {
    	super(searchFile);
    	setSiteProp("bridgeman.properties");
    	URL = siteProp.getProperty("url");
    }

    public String getUrl() {
        return URL;
    }
    
    public void typeSearch(){	
        WebElement inputWidget = driver.findElement(By.xpath("//input[@id='search_filter_text']"));
        inputWidget.clear();
        String input = searchProp.getProperty("search");
        inputWidget.sendKeys(input+ENTER_KEY);
    }

    public void submitSearch(){	
        WebElement submitButton = driver.findElement(By.xpath("//div[@id='btn_cerca']/a/img"));
        submitButton.click();
    }

    public void login(){	
    	WebElement loginInput = driver.findElement(By.xpath("(//form[@id='login']/div[@id='login-container']/div[@class='form-field-field ']/input[@id='login_login'])[1]"));
    	String login = siteProp.getProperty("login");
    	String password = siteProp.getProperty("password");
    	loginInput.sendKeys(login+"\t"+password);
    	WebElement submitButton = driver.findElement(By.xpath("(//input[@value='Log in'])[1]"));
    	submitButton.click();
    }
    
	public void goTo() {
	    driver.get(URL); 
	}

}