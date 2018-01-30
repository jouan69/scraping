package perso.scraping;

import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractHomePage extends AbstractPage {

    protected static final String ENTER_KEY = "\r\n";

    protected Properties siteProp;

    private static final String RESOURCE_FOLDER = "site/";

    public AbstractHomePage(WebDriver driver) {
        super(driver);
    }

    public void setSiteProp(String siteFile) {

        String path = RESOURCE_FOLDER + siteFile;
        setSiteProp(loadProp(path));
    }

    public Properties loadProp(String path) {
        InputStream input = null;
        Properties prop = null;
        try {
            prop = new Properties();
            //Get file from resources folder
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(path).getFile());

            input = new FileInputStream(file);
            // load a properties file
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

    public Properties getSiteProp() {
        return siteProp;
    }

    public void setSiteProp(Properties siteProp) {
        this.siteProp = siteProp;
    }

    public void goTo() {
        driver.get(siteProp.getProperty("url"));
    }

    public abstract void typeSearch(String artist);

    public abstract void login();

}