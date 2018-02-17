package perso.scraping.generic;

import org.openqa.selenium.WebDriver;
import perso.scraping.logs.MyLogger;

import java.io.IOException;
import java.util.logging.Level;

public abstract class AbstractPage implements WebPage {

    protected static final String UNKNOWN_YEAR = "UnknownYear";
    protected WebDriver driver;
    protected static final String ENTER_KEY = "\r\n";

    protected MyLogger useLogger = new MyLogger();

    static {
        try {
            MyLogger.setup();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Problems with creating the log files");
        }
    }

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
        useLogger.log(level, sb.toString(), obj);
    }

    protected void log(String name, Object value) {
        useLogger.log(Level.INFO, "{0}={1}", new Object[]{name, value});
    }

    public void setDriver(WebDriver driver){
        this.driver = driver;
    }
}
