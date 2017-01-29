package perso.scraping.akg;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractPage;

public class AkgSearchResultsPage extends AbstractPage{

    public AkgSearchResultsPage(String searchFile) {
    	super(searchFile);
    }

    public int getResultNumber(){
    	WebElement nb = driver.findElement(By.xpath("//div[contains(@class,'CT Button ABS 77t0y5n0 g5043fa2')]"));
    	String raw = nb.getText();
    	int resultNumber = extractIntFromString(raw);
    	log("resultNumber",resultNumber);
    	return resultNumber;
    }
	
    public int getPageSize() {
    	String raw = driver.findElement(By.xpath("//input[contains(@id,'ItemPerPageDdl')]")).getAttribute("value");
		int pageSize = extractIntFromString(raw);
    	log("pageSize",pageSize);
    	return pageSize;
	}
    
	public void get(int entryNb, int indexInPage, int pageNumber) {
		// open element
		log("entryNb",entryNb,"pageNumber",pageNumber,"indexInPage",indexInPage);
	
		String xpathExpr = "(//img[contains(@id,'I_img')])["+indexInPage+"]";
		WebElement thumb = driver.findElement(By.xpath(xpathExpr));
		// scroll down to thumb
		//((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", thumb);
		verySmallPause();
		thumb.click();
		
		// scroll up on details
		//((JavascriptExecutor)driver).executeScript("scroll(0,0)");
		smallPause();
		// title
		String title = "";
		try{
			title = getTitle();
		}catch(Exception e1){
			try{
				title = getCaptionShort();
			}catch(Exception e2){
				title = getCaptionLong();
			}
		}
		// data
		if(title.startsWith(AbstractPage.UNKNOWN_YEAR)){
			title=setYear(title);
		}
		
		takeScreenshot(title);
		
		close();
	
		// scroll up on details
		//((JavascriptExecutor)driver).executeScript("scroll(0,0)");
		
		smallPause();
	}
	
	private void close() {
		//a[@class='PopupCloseC']
		String xpathExpr = "//a[@class='PopupCloseC']";
		WebElement close = findElement(By.xpath(xpathExpr));
		close.click();
	}

	private String getTitle(){
		//String xpathExpr = "//span[contains(@id,'Title_Lbl')]";
		String xpathExpr = "//section[contains(@id,'MainPnl')]/div[contains(@id,'TitlePnl')]/div/span[contains(@id,'Title_Lbl')]";
		//(//span[contains(@id,'Title_Lbl')])[2]
		WebElement titleData = driver.findElement(By.xpath(xpathExpr));
		String dataText = titleData.getText();
		log("gettitle pre-format",dataText);
		String title = formatTitle(dataText);
		log("gettitle detected",title);
		String restrictions = AbstractPage.UNKNOWN_YEAR+"_RESTRICTIONS";
		if(restrictions.equals(title)){
			throw new NoSuchElementException(title);
		}
		log("gettitle final",title);
		return title;
	}

	private String getCaptionShort(){
		String xpathExpr = "//section[contains(@id,'MainPnl')]";
		xpathExpr += "/div[contains(@id,'TitlePnl')]"; 
		xpathExpr += "/div[contains(@id,'CaptionShort')]"; 
		xpathExpr += "/span[contains(@id,'CaptionShort_Lbl')]"; 
		WebElement titleData = driver.findElement(By.xpath(xpathExpr));
		String dataText = titleData.getText();
		log("caption short before formatting",dataText);
		String title = formatTitle(dataText);
		log("caption short title",title);
		return title;
	}

	private String getCaptionLong(){
		String xpathExpr = "//section[contains(@id,'MainPnl')]";
		xpathExpr += "/div[contains(@id,'CaptionLongPnl')]"; 
		xpathExpr += "/div[contains(@id,'CaptionLong')]"; 
		xpathExpr += "/span[contains(@id,'CaptionLong_Lbl')]";
		WebElement titleData = driver.findElement(By.xpath(xpathExpr));
		String dataText = titleData.getText();
		String title = formatTitle(dataText);
		log("caption long title",title);
		return title;
	}
	
	private String setYear(String title){
		log("Before Tile setYear",title);
		String year = AbstractPage.UNKNOWN_YEAR;
		String xpathExpr = "//span[contains(@id,'IssueDateYear_Lbl')]";
		try{
			WebElement yearElement = driver.findElement(By.xpath(xpathExpr));
			year = extractYearFromString(yearElement.getText());
			log("case1",year);
			return setDateToTitle(year, title);
		}catch(Exception e1){
			try{
				xpathExpr = "//span[contains(@id,'DocYear_Lbl')]";
				WebElement yearElement = driver.findElement(By.xpath(xpathExpr));
				year = extractYearFromString(yearElement.getText());
				log("case2",year);
				return setDateToTitle(year, title);
			}catch(Exception e2){
				return setDateToTitle(AbstractPage.UNKNOWN_YEAR, title);
			}
		}
	}

	private String setDateToTitle(String year, String title){
		String toReturn = title.replace(AbstractPage.UNKNOWN_YEAR, year);
		log("setDateToTitle",toReturn);
		return toReturn;
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
		((JavascriptExecutor)driver).executeScript("scroll(0,0)");
		String xpathExpr = "//img[contains(@id,'NextBtn_img')]";
		WebElement pageNum = driver.findElement(By.xpath(xpathExpr));
		pageNum.click();
		smallPause();
	}

	
}