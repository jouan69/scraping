package perso.scraping.bridgeman;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractPage;

public class BridgeManSearchResultsPage extends AbstractPage{

    public BridgeManSearchResultsPage(String searchFile) {
    	super(searchFile);
    }

    public int getResultNumber(){
    	WebElement nb = driver.findElement(By.xpath("//span[@class='results-title']"));
    	String raw = nb.getText();
    	int resultNumber = extractIntFromString(raw);
    	log("resultNumber",resultNumber);
    	return resultNumber;
    }
	
    public int getPageSize() {
    	WebElement raw = driver.findElement(By.xpath("//select[@class='form-control']/option[@selected='selected']"));
		int pageSize = extractIntFromString(raw.getText());
    	log("pageSize",pageSize);
    	return pageSize;
	}
	
    public void clickFirst(){	
        WebElement img = driver.findElement(By.xpath("(//div[contains(@class,'search-results-wrapper')])[1]/div/span/span/a/img"));
        img.click();
        ((JavascriptExecutor)driver).executeScript("scroll(0,330)");
    }
    
	public void get(int entryNb, int indexInPage, int pageNumber) {
		// open element
		log("entryNb",entryNb,"pageNumber",pageNumber,"indexInPage",indexInPage);
		try{
			String xpathExpr = "(//div[contains(@class,'search-results-wrapper')])["+indexInPage+"]/div/span/span/a/img";
			WebElement thumb = driver.findElement(By.xpath(xpathExpr));
			thumb.click();
			//String xpathExpr = "//img[@class='asset-image-draggable']";
			smallPause();
			((JavascriptExecutor)driver).executeScript("scroll(0,310)");
			// title
			xpathExpr = "(//dl/dd[@class='is-special'])[1]";
			WebElement titleData = findElement(By.xpath(xpathExpr));
			String dataText = titleData.getText();
			String title = formatTitle(dataText);
			log("title",title);
			
			takeScreenshot(title);
			
			driver.navigate().back();
		}catch(Exception e){
			// thumb not found do nothing
		}
		((JavascriptExecutor)driver).executeScript("scroll(0,0)");
		
		smallPause();
		
	}

	private WebElement findElement(By xpath) {
		try{
			return driver.findElement(xpath);
		}catch(Exception e){
			try {
				driver.wait();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	public void pageUp() {
		String xpathExpr = "//div[@class='pagination row-section-grey']/a[2]/i";
		WebElement pageNum = driver.findElement(By.xpath(xpathExpr));
		pageNum.click();
	}

	public void changeItem() {
		String xpathExpr = "//span[@class='navigation-result-next icon is-active arrow-right-large-pink']";
		WebElement element = driver.findElement(By.xpath(xpathExpr));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		element.click();
		smallPause();;
	}
}