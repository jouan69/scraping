package perso.scraping;

import com.google.inject.Guice;
import com.google.inject.Injector;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.guice.BasicModule;
import perso.scraping.scala.ScalaSearch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScrapingScala {

    public static void main(String[] args) throws InterruptedException {
        final Injector injector = Guice.createInjector(new BasicModule());
        AbstractSearch scalaSearch = injector.getInstance(ScalaSearch.class);
        scalaSearch.search();
    }

}
