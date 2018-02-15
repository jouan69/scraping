package perso.scraping.gakg.akg;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.ResultPage;

public class AkgSearch extends AbstractSearch {

    @Inject
    public AkgSearch(@Named("akgHomePage") final HomePage homePage,
                     @Named("akgResultPage") final ResultPage resultPage) {
        super(homePage , resultPage);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AkgModule());
        AbstractSearch abstractSearch = injector.getInstance(AkgSearch.class);
        abstractSearch.search();
    }
}
