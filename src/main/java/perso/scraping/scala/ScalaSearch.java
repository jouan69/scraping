package perso.scraping.scala;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.AbstractSearch;

public class ScalaSearch extends AbstractSearch {

    @Inject
    public ScalaSearch(@Named("scalaHomePage") final AbstractHomePage homePage,
                       @Named("scalaResultPage") final AbstractResultPage resultPage) {
        super(homePage, resultPage);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new ScalaModule());
        AbstractSearch abstractSearch = injector.getInstance(ScalaSearch.class);
        abstractSearch.search();
    }
}
