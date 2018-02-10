package perso.scraping;

import com.google.inject.Guice;
import com.google.inject.Injector;
import perso.scraping.generic.AbstractSearch;
import perso.scraping.generic.guice.BasicModule;
import perso.scraping.gakg.rmn.RmnSearch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScrapingRmn {

    public static void main(String[] args) throws InterruptedException {

        final Injector injector = Guice.createInjector(new BasicModule());

        ExecutorService executorService = new ThreadPoolExecutor(4, 6,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        executorService.submit(() -> {
            System.out.println("debut Search Rmn");
            AbstractSearch rmnSearch = injector.getInstance(RmnSearch.class);
            rmnSearch.search();
            System.out.println("fin tache");
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("Fin thread principal");

    }

}
