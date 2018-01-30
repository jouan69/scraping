package perso.scraping;

import org.openqa.selenium.WebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractPage {

	protected static final String UNKNOWN_YEAR = "UnknownYear";
	protected final WebDriver driver;

	protected Logger LOGGER = Logger.getLogger(AbstractPage.class.getName());

	public AbstractPage(final WebDriver driver) {
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

	public void log(String string, int entryNb, String string2, int pageNumber, String string3, int indexInPage) {
		Object[] params = new Object[]{ string, entryNb, string2, pageNumber, string3, indexInPage };
		LOGGER.log(Level.INFO, "{0}={1}, {2}={3}, {4}={5}", params);
	}

	protected void log(String name, Object value) {
		LOGGER.log(Level.INFO, "{0}={1}", new Object[]{name, value});
	}

}
