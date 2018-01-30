package perso.scraping.bridgeman;

import perso.scraping.AbstractHomePage;
import perso.scraping.AbstractSearch;

public class BridgeManSearch extends AbstractSearch {

    public static final String BRIDGEMAN_PROPERTIES = "bridgeman.properties";

    public void search(String artist, int fromYear, int toYear) {

        launchDriver();

        AbstractHomePage homePage = new BridgeManHomePage(driver, BRIDGEMAN_PROPERTIES);
        homePage.login();
        homePage.typeSearch(artist);

        BridgeManSearchResultsPage b = new BridgeManSearchResultsPage(driver, artist, fromYear, toYear);
        b.processResults();
    }

    public static void main(String[] args) {
        new BridgeManSearch().search("Joan Miro", 1893, 1983);
    }
}
