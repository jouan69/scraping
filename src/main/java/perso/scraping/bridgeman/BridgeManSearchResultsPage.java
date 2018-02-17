package perso.scraping.bridgeman;

import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Optional;

public class BridgeManSearchResultsPage extends AbstractResultPage {

    private final String xPathResultNumber = "//span[@class='results-title']";
    private final String xpathPageSize = "//select[@class='form-control']/option[@selected='selected']";
    private final String xpathPageUp = "//div[@class='pagination row-section-grey']/a[2]/i[contains(@class,'fa fa-chevron-right')]";

    private final String agency = "BridgeMan";

    public BridgeManSearchResultsPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public void get(int entryNb, int indexInPage, int pageNumber) {
        // open element
        try {
            String xpathExpr = "(//div[contains(@class,'search-results-wrapper')])[" + indexInPage + "]/div/span/span/a/img";
            WebElement thumb = driver.findElement(By.xpath(xpathExpr));
            thumb.click();
            //String xpathExpr = "//img[@class='asset-image-draggable']";
            smallPause();
            ((JavascriptExecutor) driver).executeScript("scroll(0,310)");
            // title
            xpathExpr = "(//dl/dd[@class='is-special'])[1]";
            WebElement titleData = findElement(By.xpath(xpathExpr));
            String dataText = titleData.getText();
            Optional<String> descrDate = getDateDescription(dataText);
            String title = formatTitle(dataText, descrDate);
            log(Level.FINE,"title", title);

            takeScreenshot(entryNb, title, artist, getAgency());

            driver.navigate().back();
        } catch (Exception e) {
            // thumb not found do nothing
        }
        ((JavascriptExecutor) driver).executeScript("scroll(0,0)");

        smallPause();

    }

    private Optional<String> getDateDescription(String dataText) {
        try{
            String xpathExpr = "//dt[contains(text(),'Description')]/following-sibling::dd[1]";
            WebElement element = driver.findElement(By.xpath(xpathExpr));
            String descr = element.getText();
            String year = extractYearFromString(descr);
            return UNKNOWN_YEAR.equals(year) ? Optional.empty() : Optional.of(year);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    private WebElement findElement(By xpath) {
        try {
            return driver.findElement(xpath);
        } catch (Exception e) {
            try {
                driver.wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    public void changeItem() {
        String xpathExpr = "//span[@class='navigation-result-next icon is-active arrow-right-large-pink']";
        WebElement element = driver.findElement(By.xpath(xpathExpr));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
        smallPause();
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
    public String getXpathPageUp() {
        return xpathPageUp;
    }

    public String getAgency() {
        return agency;
    }
}