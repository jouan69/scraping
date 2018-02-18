package perso.scraping.gakg;

import org.openqa.selenium.*;
import perso.scraping.generic.AbstractPage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.RestartBrowserException;
import perso.scraping.generic.param.ArtistSearch;

import java.awt.*;
import java.util.Optional;
import java.util.logging.Level;

public abstract class AbstractAkgResultPage extends AbstractResultPage {

    public AbstractAkgResultPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver, artistSearch);
    }

    public void get(int entryNb, int indexInPage, int pageNumber) throws RestartBrowserException {
        // open element
        log(Level.FINE, "entryNb", entryNb, "pageNumber", pageNumber, "indexInPage", indexInPage);

        String xpathExpr = "(//img[contains(@id,'I_img') and not(ancestor::div[contains(@id,'Tooltip')])])[" + indexInPage + "]/parent::a";
        WebElement thumb = driver.findElement(By.xpath(xpathExpr));
        if (thumb.getAttribute("href").contains("Package")) {
            log(Level.SEVERE, "Is an album, skip", entryNb);
            ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", thumb);
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

        takeScreenshot(entryNb, title, artist, getAgency());

        close();

        moveFocus(entryNb, thumb);

        smallPause();
    }

    public int getPageSize() {
        verySmallPause();
        String raw;
        try {
            raw = driver.findElement(By.xpath(getXpathPageSize())).getAttribute("value");
            int pageSize = extractIntFromString(raw);
            log(Level.INFO, "pageSize", pageSize);
            return pageSize;
        } catch (Exception e) {
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
        Optional<String> myDate = getDateCaptionLong(date, dataText);
        String title = formatTitle(dataText, myDate);
        log(Level.FINER, "caption long title", title);
        return title;
    }

    private Optional<String> getDateCaptionLong(final Optional<String> date, final String dataText) {
        Optional<String> myDate = date;
        if (!date.isPresent()) {
            myDate = Optional.of(extractYearFromString(dataText));
        }
        return myDate;
    }

    protected String getTitle(final Optional<String> date) {
        String xpathExpr = "//span[contains(@id,'Title_Lbl')]/h1";
        WebElement titleData = driver.findElement(By.xpath(xpathExpr));
        String dataText = titleData.getText();
        log(Level.FINE, "gettitle pre-format", dataText);
        String title = formatTitle(dataText, date);
        log(Level.FINE, "gettitle detected", title);
        String restrictions = AbstractPage.UNKNOWN_YEAR + "_RESTRICTIONS";
        if (restrictions.equals(title)) {
            throw new NoSuchElementException(title);
        }
        log(Level.FINE, "gettitle final", title);
        return title;
    }

    protected String getCaptionShort(final Optional<String> date) {
        String xpathExpr = "//section[contains(@id,'MainPnl')]";
        xpathExpr += "/div[contains(@id,'TitlePnl')]";
        xpathExpr += "/div[contains(@id,'CaptionShort')]";
        xpathExpr += "/span[contains(@id,'CaptionShort_Lbl')]";
        WebElement titleData = driver.findElement(By.xpath(xpathExpr));
        String dataText = titleData.getText();
        log(Level.FINER, "caption short before formatting", dataText);
        String title = formatTitle(dataText, date);
        log(Level.FINER, "caption short title", title);
        return title;
    }

    protected void moveFocus(int entryNb, WebElement currentElement) throws RestartBrowserException {
        // Move cursor
        //WebElement currentElement = driver.switchTo().activeElement();
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.visibility='hidden'", currentElement);
        Robot robot = null;
        try {
            robot = new Robot();
            verySmallPause();
            int y = currentElement.getLocation().getY();
            robot.mouseMove(0, y + 200);
            verySmallPause();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // if not album
        if (!currentElement.getAttribute("href").contains("Package")) {
            // Remove f*cking tooltip that hides other elements
            try{
                // DBG
                String xpathExpr = "//div[contains(@id,'TooltipPnl')]/div[@class='ABS AvoidBreak VF']/parent::div";
                currentElement = driver.findElement(By.xpath(xpathExpr));
                ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none'", currentElement);
                //throw new IllegalArgumentException("DBG");
            }catch (Exception e){
                if(e instanceof RestartBrowserException){
                    /// do not
                }else{
                    log(Level.SEVERE, "Error closing Tooltip", entryNb);
                    restartBrowser(entryNb);
                }
            }
        }
    }

    protected String setDateToTitle(String year, String title) {
        String toReturn = title.replace(AbstractPage.UNKNOWN_YEAR, year);
        log(Level.FINE, "setDateToTitle", toReturn);
        return toReturn;
    }

    protected String setYear(String title) {
        log(Level.FINE, "Before Tile setYear", title);
        String year = AbstractPage.UNKNOWN_YEAR;
        String xpathExpr = "//span[contains(@id,'IssueDateYear_Lbl')]";
        try {
            WebElement yearElement = driver.findElement(By.xpath(xpathExpr));
            year = extractYearFromString(yearElement.getText());
            log(Level.FINEST, "case1", year);
            return setDateToTitle(year, title);
        } catch (Exception e1) {
            try {
                xpathExpr = "//span[contains(@id,'DocYear_Lbl')]";
                WebElement yearElement = driver.findElement(By.xpath(xpathExpr));
                year = extractYearFromString(yearElement.getText());
                log(Level.FINEST, "case2", year);
                return setDateToTitle(year, title);
            } catch (Exception e2) {
                return setDateToTitle(AbstractPage.UNKNOWN_YEAR, title);
            }
        }
    }

    protected Optional<String> getDateField() {
        try {
            String date = driver.findElement(By.xpath("//div[contains(@id,'DatedP')]/div[3]/span")).getText();
            String s = extractYearFromString(date);
            log(Level.FINER, "date", s);
            return Optional.of(s);
        } catch (Exception e) {
            try {
                String date = driver.findElement(By.xpath("//span[contains(@id,'CaptionLong_Lbl')]")).getText();
                String extracted = extractYearFromString(date);
                return Optional.of(extracted);
            } catch (Exception e2) {
                return Optional.empty();
            }
        }
    }

    public abstract int getResultNumber();

    @Override
    protected void searchPage(int entryNb, int pageSize) {
        int pageNumber = pageNumber(entryNb, pageSize);
        WebElement pageNum = driver.findElement(By.xpath("//input[contains(@id,'TxtPJ')]"));
        pageNum.clear();
        pageNum.sendKeys(String.valueOf(pageNumber) + ENTER_KEY);
        verySmallPause();
    }

}
