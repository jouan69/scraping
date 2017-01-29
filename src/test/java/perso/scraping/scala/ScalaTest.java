package perso.scraping.scala;

import org.junit.Before;
import org.junit.Test;

import perso.scraping.AbstractPage;

public class ScalaTest {
	
	ScalaHomePage home;
	ScalaSearchResultsPage searchResults;
	String searchFile = "gauguin.properties";
	
	//private int startFrom = 324;
	private int startFrom = 1;
	
	@Before
	public void prepare(){
		home = new ScalaHomePage(searchFile);
		searchResults = new ScalaSearchResultsPage(searchFile);	
	}
	
    @Test
    public void main() {
        // Aller sur la page d'accueil
    	home.goTo();
    	home.typeSearch();
    	home.submitSearch();
    	int nbRslt = searchResults.getResultNumber();
    	int pageSize = searchResults.getPageSize();
    	for(int i=1;i<=nbRslt;i++){
    		processResult(i,pageSize);
    	}
    	//searchResults.openFirstItem();
    }

	private void processResult(int entryNb, int pageSize) {
		int indexInPage = AbstractPage.indexInPage(entryNb, pageSize);
		int pageNumber = AbstractPage.pageNumber(entryNb, pageSize);
		if(entryNb>=startFrom){
			searchResults.get(entryNb, indexInPage, pageNumber);			
		}
		//
		pageNumber++;	
		if(((entryNb%pageSize)==0)){
			boolean shouldScroll = ((pageNumber-1)%5)==0;
			if(shouldScroll){
				searchResults.scrollRight();
			}else{
				searchResults.changePage(pageNumber);	
			}
		}
	}
}