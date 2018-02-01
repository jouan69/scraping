package perso.scraping.bridgeman;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractHomePage;
import perso.scraping.generic.AbstractResultPage;
import perso.scraping.generic.AbstractSearch;

public class BridgeManSearch extends AbstractSearch {

    @Inject
    public BridgeManSearch(@Named("bridgeManHomePage") final AbstractHomePage homePage,
                           @Named("bridgeManResultPage") final AbstractResultPage resultPage) {
        super(homePage, resultPage);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BridgeManModule());
        AbstractSearch abstractSearch = injector.getInstance(BridgeManSearch.class);
        abstractSearch.search();
    }

}
