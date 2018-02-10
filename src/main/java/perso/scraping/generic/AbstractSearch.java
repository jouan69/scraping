package perso.scraping.generic;

import com.google.inject.Inject;
import perso.scraping.generic.param.ResultPage;

public abstract class AbstractSearch implements ImageWebSite {

    private final HomePage homePage;
    private final ResultPage resultPage;

    @Inject
    public AbstractSearch(final HomePage homePage, final ResultPage resultPage) {
        this.homePage = homePage;
        this.resultPage = resultPage;
    }

    public void search() {
        homePage.login();
        homePage.typeArtistName();
        resultPage.processResults();
    }
}
