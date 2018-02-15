package perso.scraping;

import com.google.inject.Guice;
import com.google.inject.Injector;
import perso.scraping.gakg.rmn.RmnSearch;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.guice.BasicModule;

public class ScrapingRmn {

    public static void main(String[] args) throws InterruptedException {
        final Injector injector = Guice.createInjector(new BasicModule());
        AbstractSearch rmnSearch = injector.getInstance(RmnSearch.class);
        rmnSearch.search();
    }

}
