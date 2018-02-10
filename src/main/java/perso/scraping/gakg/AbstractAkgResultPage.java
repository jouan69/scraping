package perso.scraping.gakg;

import org.openqa.selenium.*;
import perso.scraping.generic.AbstractPage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.param.ArtistSearch;

import java.awt.*;
import java.util.Optional;
import java.util.logging.Level;

public abstract class AbstractAkgResultPage extends AbstractResultPage {

    public AbstractAkgResultPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public void get(int entryNb, int indexInPage, int pageNumber) {
        // open element
        log(Level.FINE,"entryNb", entryNb, "pageNumber", pageNumber, "indexInPage", indexInPage);

        String xpathExpr = "(//img[contains(@id,'I_img')])[" + indexInPage + "]/parent::a";
        WebElement thumb = driver.findElement(By.xpath(xpathExpr));
        if(thumb.getAttribute("href").contains("Package")){
            log(Level.FINER,"Is an album, skip", entryNb);
            return;
        }

        verySmallPause();
        thumb.click();

        // scroll up on details
        smallPause();
        // date
        Optional<String> date = getDateField();
        // title
        String title;
        try {
            title = getTitle(date);
        } catch (Exception e1) {
            try {
                title = getCaptionShort(date);
            } catch (Exception e2) {
                title = getCaptionLong(date);
            }
        }
        // data
        if (title.startsWith(AbstractPage.UNKNOWN_YEAR)) {
            title = setYear(title);
        }

        takeScreenshot(title, artist, getAgency());

        close();

        moveFocus();

        smallPause();
    }

    public int getPageSize() {
        verySmallPause();
        String raw;
        try{
            raw = driver.findElement(By.xpath(getXpathPageSize())).getAttribute("value");
            int pageSize = extractIntFromString(raw);
            log(Level.SEVERE,"pageSize", pageSize);
            return pageSize;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    protected void close() {
        String xpathExpr = "//a[@class='PopupCloseC']";
        WebElement close = findElement(By.xpath(xpathExpr));
        close.click();
    }

    protected WebElement findElement(By xpath) {
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

    protected String getCaptionLong(final Optional<String> date) {
        String xpathExpr = "//section[contains(@id,'MainPnl')]";
        xpathExpr += "/div[contains(@id,'CaptionLongPnl')]";
        xpathExpr += "/div[contains(@id,'CaptionLong')]";
        xpathExpr += "/span[contains(@id,'CaptionLong_Lbl')]";
        WebElement titleData = driver.findElement(By.xpath(xpathExpr));
        String dataText = titleData.getText();
        String title = formatTitle(dataText, date);
        log(Level.FINER,"caption long title", title);
        return title;
    }

    protected String getTitle(final Optional<String> date) {
        String xpathExpr = "//span[contains(@id,'Title_Lbl')]/h1";
        WebElement titleData = driver.findElement(By.xpath(xpathExpr));
        String dataText = titleData.getText();
        log(Level.FINE,"gettitle pre-format", dataText);
        String title = formatTitle(dataText, date);
        log(Level.FINE,"gettitle detected", title);
        String restrictions = AbstractPage.UNKNOWN_YEAR + "_RESTRICTIONS";
        if (restrictions.equals(title)) {
            throw new NoSuchElementException(title);
        }
        log(Level.FINE,"gettitle final", title);
        return title;
    }

    protected String getCaptionShort(final Optional<String> date) {
        String xpathExpr = "//section[contains(@id,'MainPnl')]";
        xpathExpr += "/div[contains(@id,'TitlePnl')]";
        xpathExpr += "/div[contains(@id,'CaptionShort')]";
        xpathExpr += "/span[contains(@id,'CaptionShort_Lbl')]";
        WebElement titleData = driver.findElement(By.xpath(xpathExpr));
        String dataText = titleData.getText();
        log(Level.FINER,"caption short before formatting", dataText);
        String title = formatTitle(dataText, date);
        log(Level.FINER,"caption short title", title);
        return title;
    }

    protected void moveFocus() {
        // Move cursor
        WebElement currentElement = driver.switchTo().activeElement();
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.visibility='hidden'", currentElement);
        Robot robot = null;
        try {
            robot = new Robot();
            verySmallPause();
            int x = currentElement.getLocation().getX();
            int y = currentElement.getLocation().getY();
            robot.mouseMove(0, y+200);
            verySmallPause();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Remove f*cking tooltip that hides other elements
        String xpathExpr = "//div[contains(@id,'TooltipPnl')]/div[@class='ABS AvoidBreak VF']/parent::div";
        currentElement = driver.findElement(By.xpath(xpathExpr));
        ((JavascriptExecutor)driver).executeScript("arguments[0].style.display='none'", currentElement);
    }

    protected String setDateToTitle(String year, String title){
        String toReturn = title.replace(AbstractPage.UNKNOWN_YEAR, year);
        log(Level.FINE,"setDateToTitle",toReturn);
        return toReturn;
    }

    protected String setYear(String title) {
        log(Level.FINE,"Before Tile setYear", title);
        String year = AbstractPage.UNKNOWN_YEAR;
        String xpathExpr = "//span[contains(@id,'IssueDateYear_Lbl')]";
        try {
            WebElement yearElement = driver.findElement(By.xpath(xpathExpr));
            year = extractYearFromString(yearElement.getText());
            log(Level.FINEST,"case1", year);
            return setDateToTitle(year, title);
        } catch (Exception e1) {
            try {
                xpathExpr = "//span[contains(@id,'DocYear_Lbl')]";
                WebElement yearElement = driver.findElement(By.xpath(xpathExpr));
                year = extractYearFromString(yearElement.getText());
                log(Level.FINEST,"case2", year);
                return setDateToTitle(year, title);
            } catch (Exception e2) {
                return setDateToTitle(AbstractPage.UNKNOWN_YEAR, title);
            }
        }
    }

    protected Optional<String> getDateField() {
        try {
            String date = driver.findElement(By.xpath("//div[contains(@id,'DatedP')]/div[3]/span")).getText();
            log(Level.FINER,"date", date);
            return Optional.of(date);
        } catch (Exception e) {
            log(Level.FINER,"nodate", null);
            return Optional.empty();
        }
    }
}
