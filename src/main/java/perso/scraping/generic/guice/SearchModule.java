package perso.scraping.generic.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import perso.scraping.generic.PropertiesLoader;
import perso.scraping.generic.param.ArtistSearch;

import java.util.Properties;

public class SearchModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(PropertiesLoader.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    ArtistSearch getArtistSearch(PropertiesLoader propertiesLoader){

        Properties props = propertiesLoader.loadProp("search.properties");

        return new ArtistSearch() {
            @Override
            public String getArtistName() {
                return props.getProperty("artistName");
            }
            @Override
            public int fromYear() {
                return Integer.valueOf(props.getProperty("fromYear"));
            }
            @Override
            public int toYear() {
                return Integer.valueOf(props.getProperty("toYear"));
            }
        };
    }

}
