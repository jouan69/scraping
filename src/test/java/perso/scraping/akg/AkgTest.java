package perso.scraping.akg;

import org.junit.Before;
import org.junit.Test;

import perso.scraping.AbstractPage;

public class AkgTest {

	AkgHomePage homePage;
	AkgSearchResultsPage results;
	//String searchFile = "gauguin.properties";
	//String searchFile = "millet.properties";
	String searchFile = "mondrian.properties";
	
	private int startFrom = 1;

	@Before
	public void prepare(){
		homePage = new AkgHomePage(searchFile);
		results = new AkgSearchResultsPage(searchFile);
	}
	
	@Test
	public void main() {
		homePage.goTo();
		homePage.login();
		homePage.typeSearch();
		int nbRslt = results.getResultNumber();
		int pageSize = results.getPageSize();
		for (int i = 1; i <= nbRslt; i++) {
			processResult(i, pageSize);
		}
	}

	private void processResult(int entryNb, int pageSize) {
		int indexInPage = AbstractPage.indexInPage(entryNb, pageSize);
		int pageNumber = AbstractPage.pageNumber(entryNb, pageSize);
		if (entryNb >= startFrom) {
			results.get(entryNb, indexInPage, pageNumber);
		}
		if (((entryNb % pageSize) == 0)) {
			results.log("entryNb",entryNb, "pageNumber",pageNumber,"indexInPage",indexInPage );
			results.pageUp();
		}
	}
}
