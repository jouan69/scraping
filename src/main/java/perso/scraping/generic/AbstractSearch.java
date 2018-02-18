package perso.scraping.generic;

import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;

public abstract class AbstractSearch implements ImageWebSite {

    private final HomePage homePage;
    private final ResultPage resultPage;

    public static final int RESTART_LIMIT = 100;

    @Inject
    public AbstractSearch(final HomePage homePage, final ResultPage resultPage) {
        this.homePage = homePage;
        this.resultPage = resultPage;
    }

    public void search() {
        int offset = 1999;
        search(offset+1);
    }

    private void search(int fromPage) {
        homePage.login();
        homePage.typeArtistName();
        try {
            resultPage.processResults(fromPage);
        } catch (RestartBrowserException e) {
            updateDriver(e.getDriver());
            fromPage = e.getIndex();
            search(fromPage);
        }
    }

    private void updateDriver(WebDriver driver){
        homePage.setDriver(driver);
        resultPage.setDriver(driver);
    }
}
