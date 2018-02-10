package perso.scraping.generic.param;

public interface ResultPage {

    void processResults();

    int getResultNumber();

    int getPageSize();

    void processResult(int entryNb, int pageSize);

    void get(int entryNb, int indexInPage, int pageNumber);

    void pageUp();
}
