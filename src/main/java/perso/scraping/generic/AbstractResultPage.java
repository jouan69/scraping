 package perso.scraping.generic;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.*;
import perso.scraping.generic.param.ArtistSearch;
import perso.scraping.generic.param.ResultPage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractResultPage extends AbstractPage implements ResultPage {

    protected String artist;
    protected int fromYear;
    protected int toYear;

    public AbstractResultPage(WebDriver driver, ArtistSearch artistSearch) {
        super(driver);
        this.artist = artistSearch.getArtistName();
        this.fromYear = artistSearch.fromYear();
        this.toYear = artistSearch.toYear();
    }

    public void processResults() {
        int nbRslt = getResultNumber();
        int pageSize = getPageSize();
        for (int i = 1; i <= nbRslt; i++) {
            try {
                processResult(i, pageSize);
            } catch (Exception t) {
                LOGGER.log(Level.INFO, t.getMessage());
            }

        }
    }

    public void processResult(int entryNb, int pageSize) {
        int indexInPage = indexInPage(entryNb, pageSize);
        int pageNumber = pageNumber(entryNb, pageSize);
        int startFrom = 1;
        if (entryNb >= startFrom) {
            get(entryNb, indexInPage, pageNumber);
        }
        if (((entryNb % pageSize) == 0)) {
            log(Level.FINE,"entryNb", entryNb, "pageNumber", pageNumber, "indexInPage", indexInPage);
            pageUp();
        }
    }

    protected String extractTitle(String s) {
        Pattern p = Pattern.compile("Title:(.*)$");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return formatTitle(m.group(1), Optional.empty());
        }
        return null;
    }

    protected String extractYearFromString(String s) {
        int yearMin = this.fromYear == 0 ? Integer.MIN_VALUE : this.fromYear;
        int yearMax = this.toYear == 0 ? Integer.MAX_VALUE : this.toYear;
        return extractYearFromString(s, yearMin, yearMax);
    }

    static String extractYearFromString(String s, int yearMin, int yearMax) {
        Pattern p = Pattern.compile("(\\d{4})");
        Matcher m = p.matcher(s);
        boolean yearMaxFound = false;
        while (m.find()) {
            try {
                int yearFound = Integer.parseInt(m.group(1));
                if (yearMin < yearFound && yearFound < yearMax) {
                    return m.group(1);
                } else {
                    if (yearFound == yearMax) {
                        yearMaxFound = true;
                    }
                }
            } catch (Exception e) {
                // parsing error
            }
        }
        if (yearMaxFound) {
            return String.valueOf(yearMax);
        } else {
            return UNKNOWN_YEAR;
        }
    }

    protected int extractIntFromString(String s) {
        String cleaned = s.replaceAll("\\.", "")
                .replaceAll(",", "")
                .replaceAll(" ", "");
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(cleaned);
        if (m.find()) {
            // log("found",m.group(1));
            return Integer.parseInt(m.group(1));
        }
        return 0;
    }

    public int pageNumber(int entryNb, int pageSize) {
        return ((entryNb - 1) / pageSize) + 1;
    }

    public int indexInPage(int entryNb, int pageSize) {
        return entryNb - (pageNumber(entryNb, pageSize) - 1) * pageSize;
    }

    protected String formatTitle(String title, String yearTxt) {
        String shortTitle = title.replaceAll(yearTxt, "");
        shortTitle = shortTitle.replaceAll(",", "");
        shortTitle = shortTitle.replaceAll(";", "");
        shortTitle = shortTitle.replaceAll("\\.", "");
        shortTitle = shortTitle.replaceAll("\\?", "");
        shortTitle = shortTitle.replaceAll("\\\\", "");
        shortTitle = shortTitle.replaceAll("\\/", "");
        shortTitle = shortTitle.replaceAll("\\:", "");
        shortTitle = shortTitle.replaceAll("\\|", "");
        shortTitle = shortTitle.replaceAll(">", "");
        shortTitle = shortTitle.replaceAll("<", "");
        shortTitle = shortTitle.replaceAll("\"", "");
        shortTitle = shortTitle.replaceAll("\\*", "");
        shortTitle = shortTitle.replaceAll("\r\n", "");
        shortTitle = shortTitle.replaceAll("\n", "");
        shortTitle = shortTitle.trim();
        int len = Math.min(100, shortTitle.length());
        shortTitle = shortTitle.substring(0, len);
        return yearTxt + "_" + shortTitle;
    }

    protected String formatTitle(String title, Optional<String> date) {
        String yearTxt = date.isPresent() ? date.get() : extractYearFromString(title);
        return formatTitle(title, yearTxt);
    }

    public void takeScreenshot(String title, String artist, String agency) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy
        // somewhere
        log(Level.SEVERE," screenshot title", title);
        try {
            String filePath = "c:\\tmp\\" + agency + "\\" + artist + "\\" + title + ".png";
            File newFile = getUniqueFilename(new File(filePath));
            FileUtils.copyFile(scrFile, newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getUniqueFilename(File file) {
        String baseName = FilenameUtils.getBaseName(file.getName());
        String extension = FilenameUtils.getExtension(file.getName());
        int counter = 1;
        while (file.exists()) {
            file = new File(file.getParent(), baseName + "-" + (counter++) + "." + extension);
        }
        return file;
    }

    public int getResultNumber() {
        smallPause();
        WebElement nb = driver.findElement(By.xpath(getxPathResultNumber()));
        String raw = nb.getText();
        int resultNumber = extractIntFromString(raw);
        log(Level.SEVERE,"resultNumber", resultNumber);
        return resultNumber;
    }

    public int getPageSize() {
        verySmallPause();
        WebElement raw;
        try{
            raw = driver.findElement(By.xpath(getXpathPageSize()));
            int pageSize = extractIntFromString(raw.getText());
            log(Level.SEVERE,"pageSize", pageSize);
            return pageSize;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void pageUp() {
        ((JavascriptExecutor) driver).executeScript("scroll(0,0)");
        WebElement pageNum = driver.findElement(By.xpath(getXpathPageUp()));
        pageNum.click();
        smallPause();
    }

    protected abstract String getxPathResultNumber();

    protected abstract String getXpathPageSize();

    protected abstract String getXpathPageUp();

    protected abstract String getAgency();

}
