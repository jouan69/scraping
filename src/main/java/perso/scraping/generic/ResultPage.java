package perso.scraping.generic;

public interface ResultPage extends WebPage {

    void processResults(int offset) throws RestartBrowserException;

    int getResultNumber();

    int getPageSize();

    void processResult(int entryNb, int pageSize);

    void get(int entryNb, int indexInPage, int pageNumber);

    void pageUp();
}
