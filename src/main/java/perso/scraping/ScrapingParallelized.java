package perso.scraping;

import com.google.inject.Guice;
import com.google.inject.Injector;
import perso.scraping.akg.AkgSearch;
import perso.scraping.bridgeman.BridgeManSearch;
import perso.scraping.guice.BasicModule;
import perso.scraping.scala.ScalaSearch;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScrapingParallelized {

    private static String recherche = "Marc Chagall";
    private static int fromYear = 1900;
    private static int toYear = 1985;

    public static void main(String[] args) throws InterruptedException {

        Injector injector = Guice.createInjector(new BasicModule());

        ExecutorService executorService = new ThreadPoolExecutor(4, 6, 60,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("debut Search Akg");
                new AkgSearch().search(recherche, fromYear, toYear);
                System.out.println("fin tache");
            }
        });

        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("debut Search BridgeManSearch");
                new BridgeManSearch().search(recherche, fromYear, toYear);
                System.out.println("fin tache");
            }
        });

        executorService.submit(new Runnable() {
            public void run() {
                System.out.println("debut Search Scala");
                new ScalaSearch().search(recherche, fromYear, toYear);
                System.out.println("fin tache");
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("Fin thread principal");

    }

}
