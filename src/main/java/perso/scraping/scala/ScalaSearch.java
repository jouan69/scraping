package perso.scraping.scala;

import perso.scraping.AbstractHomePage;
import perso.scraping.AbstractSearch;

public class ScalaSearch extends AbstractSearch {

    public static final String SCALA_PROPERTIES = "scala.properties";

    public void search(String artist, int fromYear, int toYear) {

        launchDriver();

        AbstractHomePage home = new ScalaHomePage(driver, SCALA_PROPERTIES);
        home.login();
        home.typeSearch(artist);

        ScalaSearchResultsPage s = new ScalaSearchResultsPage(driver, artist, fromYear, toYear);
        s.processResults();
    }

    public static void main(String[] args) {
        new ScalaSearch().search("Joan Miro",1893,1983);
    }
}
