package perso.scraping.gakg.rmn;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.HomePage;
import perso.scraping.generic.ResultPage;

public class RmnSearch extends AbstractSearch {
    @Inject
    public RmnSearch(@Named("rmnHomePage") final HomePage homePage,
                       @Named("rmnResultPage") final ResultPage resultPage) {
        super(homePage, resultPage);
    }
}
