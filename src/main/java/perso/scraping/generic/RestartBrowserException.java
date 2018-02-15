package perso.scraping.generic;

import org.openqa.selenium.WebDriver;

public class RestartBrowserException extends Exception {

    private WebDriver driver;
    private int index;

    public RestartBrowserException(WebDriver driver, int i) {
        this.driver = driver;
        this.index = i;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public int getIndex() {
        return index;
    }
}
