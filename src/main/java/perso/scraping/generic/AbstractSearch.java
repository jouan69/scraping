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
        int offset = 100;
        search(offset);
    }

    private void search(int offset) {
        homePage.login();
        homePage.typeArtistName();
        try {
            resultPage.processResults(offset);
        } catch (RestartBrowserException e) {
            updateDriver(e.getDriver());
            offset = e.getIndex();
            search(offset);
        }
    }

    private void updateDriver(WebDriver driver){
        homePage.setDriver(driver);
        resultPage.setDriver(driver);
    }
}
