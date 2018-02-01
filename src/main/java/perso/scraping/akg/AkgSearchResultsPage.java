package perso.scraping.akg;

import org.openqa.selenium.*;
import perso.scraping.generic.AbstractPage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.param.ArtistSearch;

public class AkgSearchResultsPage extends AbstractResultPage {

	public AkgSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
		super(driver, artistSearch);
	}

	protected void processResult(int entryNb, int pageSize) {
		int indexInPage = indexInPage(entryNb, pageSize);
		int pageNumber = pageNumber(entryNb, pageSize);
		int startFrom = 1;
		if (entryNb >= startFrom) {
			get(entryNb, indexInPage, pageNumber);
		}
		if (((entryNb % pageSize) == 0)) {
			log("entryNb",entryNb, "pageNumber",pageNumber,"indexInPage",indexInPage );
			pageUp();
		}
	}

	protected int getResultNumber(){
    	WebElement nb = driver.findElement(By.xpath("(//div[contains(@id,'KeywordsPnl')]/div[contains(@class,'CT Button ABS')]/a)[1]"));
    	String raw = nb.getText();
    	int resultNumber = extractIntFromString(raw);
    	log("resultNumber",resultNumber);
    	return resultNumber;
    }

	protected int getPageSize() {
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
		verySmallPause();
		thumb.click();
		
		// scroll up on details
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
		
		takeScreenshot(title, artist, "AKG");
		
		close();
	
		// scroll up on details
		smallPause();
	}
	
	private void close() {
		String xpathExpr = "//a[@class='PopupCloseC']";
		WebElement close = findElement(By.xpath(xpathExpr));
		close.click();
	}

	private String getTitle(){
		String xpathExpr = "//section[contains(@id,'MainPnl')]/div[contains(@id,'TitlePnl')]/div/span[contains(@id,'Title_Lbl')]";
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