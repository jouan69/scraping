package perso.scraping.scala;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.ResultPage;

public class ScalaSearch extends AbstractSearch {

    @Inject
    public ScalaSearch(@Named("scalaHomePage") final HomePage homePage,
                       @Named("scalaResultPage") final ResultPage resultPage) {
        super(homePage, resultPage);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ScalaModule());
        AbstractSearch abstractSearch = injector.getInstance(ScalaSearch.class);
        abstractSearch.search();
    }
}
