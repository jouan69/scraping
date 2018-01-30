package perso.scraping.akg;

import perso.scraping.AbstractHomePage;
import perso.scraping.AbstractSearch;

public class AkgSearch extends AbstractSearch {

    public static final String AKG_PROPERTIES = "akg.properties";

    public void search(String artist, int fromYear, int toYear) {

        launchDriver();

        AbstractHomePage homePage = new AkgHomePage(driver, AKG_PROPERTIES);
        homePage.login();
        homePage.typeSearch(artist);

        AkgSearchResultsPage akgSearchResultsPage = new AkgSearchResultsPage(driver, artist, fromYear, toYear);
        akgSearchResultsPage.processResults();
    }

    public static void main(String[] args) {
        new AkgSearch().search("guerre",0,2017);
    }
}
