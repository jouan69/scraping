package perso.scraping.generic;

import com.google.inject.Inject;

public abstract class AbstractSearch implements ImageWebSite {

    private final AbstractHomePage homePage;
    private final AbstractResultPage resultPage;

    @Inject
    public AbstractSearch(final AbstractHomePage homePage, final AbstractResultPage resultPage) {
        this.homePage = homePage;
        this.resultPage = resultPage;
    }

    public void search() {
        homePage.login();
        homePage.typeArtistName();
        resultPage.processResults();
    }
}
