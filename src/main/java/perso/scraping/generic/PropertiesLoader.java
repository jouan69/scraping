package perso.scraping.generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static final String RESOURCE_FOLDER = "site/";

    public Properties loadSiteProp(String path) {
        return loadProp(RESOURCE_FOLDER + path);
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
}
