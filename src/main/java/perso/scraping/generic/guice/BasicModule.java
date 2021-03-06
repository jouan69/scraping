package perso.scraping.generic.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import perso.scraping.gakg.akg.AkgModule;
import perso.scraping.bridgeman.BridgeManModule;
import perso.scraping.generic.param.ArtistSearch;
import perso.scraping.gakg.rmn.RmnModule;
import perso.scraping.scala.ScalaModule;

public class BasicModule extends AbstractModule {

    protected void configure() {
        install(new SearchModule());
        install(new AkgModule());
        install(new BridgeManModule());
        install(new ScalaModule());
        install(new RmnModule());
    }
}
