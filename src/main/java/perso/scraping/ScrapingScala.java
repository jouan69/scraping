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

        ExecutorService executorService = new ThreadPoolExecutor(4, 6,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("debut Search Scala");
                AbstractSearch rmnSearch = injector.getInstance(ScalaSearch.class);
                rmnSearch.search();
                System.out.println("fin tache");
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("Fin thread principal");

    }

}
