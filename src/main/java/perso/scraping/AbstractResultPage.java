package perso.scraping;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.spi.LoggerFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractResultPage extends AbstractPage {

    protected final String artist;
    protected final int fromYear;
    protected final int toYear;

    public AbstractResultPage(final WebDriver webDriver, final String artist, final int fromYear, final int toYear) {
        super(webDriver);
        this.artist = artist;
        this.fromYear = fromYear;
        this.toYear = toYear;
    }

    public void processResults() {
        int nbRslt = getResultNumber();
        int pageSize = getPageSize();
        for (int i = 1; i <= nbRslt; i++) {
            try {
                processResult(i, pageSize);
            } catch (Throwable t) {
                LOGGER.log(Level.INFO, t.getMessage());
            }

        }
    }

    abstract protected int getResultNumber();

    abstract protected int getPageSize();

    abstract protected void processResult(int resultIndex, int pageSize);

    protected String extractTitle(String s) {
        Pattern p = Pattern.compile("Title:(.*)$");
        Matcher m = p.matcher(s);
        if (m.find()) {
            return formatTitle(m.group(1));
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
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(s);
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

    protected String formatTitle(String title) {
        String yearTxt = extractYearFromString(title);
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

    public void takeScreenshot(String title, String artist, String agency) {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Now you can do whatever you need to do with it, for example copy
        // somewhere
        log(" screenshot title", title);
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
}
