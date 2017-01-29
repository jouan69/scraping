package perso.scraping.scala;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractPage;
import perso.scraping.ConfigProperties;

public class ScalaSearchResultsPage extends AbstractPage {
	    
	private String winHandleSearchResults;
	
	public ScalaSearchResultsPage(String searchFile) {
    	super(searchFile);
    }
	
    public void openFirstItem() {    	
    	WebElement firstImg = driver.findElement(By.xpath("(//div[@class='res-item'])[1]/div/img"));
    	firstImg.click();
    }

    public int getResultNumber(){
    	WebElement nb = driver.findElement(By.xpath("(//div[@class='ric-tools adj'])[1]"));
    	String raw = nb.getText();
    	int resultNumber = extractIntFromString(raw);
    	//log("resultNumber",resultNumber);
    	return resultNumber;
    }

	public int getPageSize() {
		WebElement nb = driver.findElement(By.xpath("//select[@id='selNRisPag']/option[@selected]"));
    	String raw = nb.getText();
    	int pageSize = extractIntFromString(raw);
    	//log("PageSize",pageSize);
    	return pageSize;
	}

	public void get(int entryNb, int indexInPage, int pageNumber) {
		//
		String xpathExpr = "(//div[@class='item-data'])["+indexInPage+"]";
		WebElement data = driver.findElement(By.xpath(xpathExpr));
		String dataText = data.getText();
		//log("dataText",dataText);
		String title = extractTitle(dataText);
		log("title",title);
		//
		String index = String.valueOf(indexInPage);
		WebElement img = driver.findElement(By.xpath("(//div[@class='res-item'])["+index+"]/div/img"));
		winHandleSearchResults = driver.getWindowHandle();
		img.click();
		for(String winHandle : driver.getWindowHandles()){
		    driver.switchTo().window(winHandle);
		}
		// Perform the actions on new window
	    try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    takeScreenshot(title);
	    //
		driver.close();
		driver.switchTo().window(winHandleSearchResults);
		// Change page of 
	}

	public void changePage(int pageNumber) {
		String xpathExpr = "(//div[@class='ric-tools-dx']/a[text()='"+pageNumber+"'])[1]";
		WebElement pageNum = driver.findElement(By.xpath(xpathExpr));
		pageNum.click();
	}

	
	public void scrollRight() {
		String xpathExpr = "(//div[@class='ric-tools-dx']/a/img[@src='img/arr_next.png'])[1]";
		WebElement pageNum = driver.findElement(By.xpath(xpathExpr));
		pageNum.click();
	}
	
	protected String extractTitle(String s) {
		Pattern p = Pattern.compile("Title:(.*)$");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return formatTitle(m.group(1));
		}
		return null;
	}

}