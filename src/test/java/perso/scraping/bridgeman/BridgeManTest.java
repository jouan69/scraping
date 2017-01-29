package perso.scraping.bridgeman;

import org.junit.Before;
import org.junit.Test;

import perso.scraping.AbstractPage;

public class BridgeManTest {

	BridgeManHomePage homePage;
	BridgeManSearchResultsPage results;
	String searchFile = "gauguin.properties";
	
	private int startFrom = 1;
	
	@Before
	public void prepare(){
		homePage = new BridgeManHomePage(searchFile);
		results = new BridgeManSearchResultsPage(searchFile);		
	}

	@Test
	public void main() {
		homePage.goTo();
		homePage.login();
		homePage.typeSearch();
		int nbRslt = results.getResultNumber();
		int pageSize = results.getPageSize();
		//results.clickFirst();
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
			results.pageUp();
		}
		//results.changeItem();
	}
}
