package perso.scraping.generic;

import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPage implements WebPage {

    protected static final String UNKNOWN_YEAR = "UnknownYear";
    protected WebDriver driver;
    protected static final String ENTER_KEY = "\r\n";

    protected Logger LOGGER = Logger.getLogger(AbstractPage.class.getName());

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    protected void verySmallPause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void smallPause() {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected void pause() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void log(Level level, Object... obj) {
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<obj.length;i++) {
            sb.append("{");
            sb.append(i);
            sb.append("} ");
        }
        LOGGER.log(level, sb.toString(), obj);
    }

    protected void log(String name, Object value) {
        LOGGER.log(Level.INFO, "{0}={1}", new Object[]{name, value});
    }

    public void setDriver(WebDriver driver){
        this.driver = driver;
    }
}
