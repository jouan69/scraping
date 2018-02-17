package perso.scraping.scala;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.param.ArtistSearch;

import java.io.IOException;
import java.util.logging.Level;

public class ScalaSearchResultsPage extends AbstractResultPage {

    protected final String xPathResultNumber = "(//div[@class='ric-tools adj'])[1]";
    protected final String xpathPageSize = "//select[@id='selNRisPag']/option[@selected]";
    private final String agency = "Scala";

    private int currentPageNumber = 1;

    public ScalaSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public void get(int entryNb, int indexInPage, int pageNumber) {
        currentPageNumber = pageNumber;
        String xpathExpr = "(//div[@class='item-data'])[" + indexInPage + "]";
        WebElement data = driver.findElement(By.xpath(xpathExpr));
        String dataText = data.getText();

        String title = extractTitle(dataText);
        log(Level.FINE, "title", title);

        String index = String.valueOf(indexInPage);
        WebElement img = driver.findElement(By.xpath("(//div[@class='res-item'])[" + index + "]/div/img"));
        String winHandleSearchResults = driver.getWindowHandle();
        img.click();
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }
        pause();
        takeScreenshot(entryNb, title, artist, getAgency());
        driver.close();
        driver.switchTo().window(winHandleSearchResults);
    }

    public void pageUp() {
        nextPage(currentPageNumber);
    }

    @Override
    protected void searchPage(int offset, int pageSize) {
        int targetPageNumber = pageNumber(offset + 1, pageSize);
        for (int page = 1; page < targetPageNumber; page++) {
            nextPage(page);
        }
    }

    private void nextPage(int page) {
        int nextPage = page + 1;
        boolean shouldScroll = (((nextPage - 1) % 5) == 0);
        if (shouldScroll) {
            scrollRight();
        } else {
            changePage(nextPage);
        }
    }

    public void changePage(int pageNumber) {
        String xpathExpr = "(//div[@class='ric-tools-dx']/a[text()='" + pageNumber + "'])[1]";
        WebElement pageNum = driver.findElement(By.xpath(xpathExpr));
        pageNum.click();
    }


    public void scrollRight() {
        String xpathExpr = "//img[@src='img/arr_next.png']/parent::a";
        WebElement pageNum = driver.findElement(By.xpath(xpathExpr));
        pageNum.click();
    }

    @Override
    public String getxPathResultNumber() {
        return xPathResultNumber;
    }

    @Override
    public String getXpathPageSize() {
        return xpathPageSize;
    }

    @Override
    protected String getXpathPageUp() {
        return null;
    }

    public String getAgency() {
        return agency;
    }
}