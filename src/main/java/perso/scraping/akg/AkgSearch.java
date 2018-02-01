package perso.scraping.akg;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.AbstractSearch;

public class AkgSearch extends AbstractSearch {

    @Inject
    public AkgSearch(@Named("akgHomePage") final AbstractHomePage homePage,
                     @Named("akgResultPage") final AbstractResultPage resultPage) {
        super(homePage , resultPage);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AkgModule());
        AbstractSearch abstractSearch = injector.getInstance(AkgSearch.class);
        abstractSearch.search();
    }
}
