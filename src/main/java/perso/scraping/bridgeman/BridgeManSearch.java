package perso.scraping.bridgeman;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.ResultPage;

public class BridgeManSearch extends AbstractSearch {

    @Inject
    public BridgeManSearch(@Named("bridgeManHomePage") final HomePage homePage,
                           @Named("bridgeManResultPage") final ResultPage resultPage) {
        super(homePage, resultPage);
    }

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BridgeManModule());
        AbstractSearch abstractSearch = injector.getInstance(BridgeManSearch.class);
        abstractSearch.search();
    }

}
