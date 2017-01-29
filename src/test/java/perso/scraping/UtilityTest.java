package perso.scraping;

import org.junit.Ignore;
import org.junit.Test;

import perso.scraping.AbstractPage;

import static org.fest.assertions.Assertions.assertThat;

public class UtilityTest {

	@Test
	public void test1(){
		assertThat(AbstractPage.pageNumber(1, 12)).isEqualTo(1);
		assertThat(AbstractPage.pageNumber(13, 12)).isEqualTo(2);
		assertThat(AbstractPage.pageNumber(12, 12)).isEqualTo(1);
	}

	@Test
	public void test2(){
		assertThat(AbstractPage.indexInPage(1, 12)).isEqualTo(1);
		assertThat(AbstractPage.indexInPage(13, 12)).isEqualTo(1);
		assertThat(AbstractPage.indexInPage(12, 12)).isEqualTo(12);
	}


	@Test
	@Ignore
	public void test3(){
		String in = "Code: 0120906\n";
		in += "Artist: Gauguin, Paul (1848-1903)\n";
		in += "Title: The Moon and the Earth (Hina Te fatou), 1893";
		//
		String out = "1893_The Moon and the Earth (Hina Te fatou)";
		//
		AbstractPage a = new AbstractPage("");
		assertThat(a.extractTitle(in)).isEqualTo(out);
	}
	
	@Test
	public void test4(){
		String in = "Gauguin, Paul (1848-1903). Still Life with Mangoes; Nature Morte aux Mangoes. c.1896";
		assertThat(AbstractPage.extractYearFromString(in, 1848, 1903)).isEqualTo("1896");
		in = "Still Life with Mangoes; Nature Morte aux Mangoes. c.1903";
		assertThat(AbstractPage.extractYearFromString(in, 1848, 1903)).isEqualTo("1903");
		in = "Still Life c.1848";
		assertThat(AbstractPage.extractYearFromString(in, 1848, 1903)).isEqualTo(AbstractPage.UNKNOWN_YEAR);
	}
	
}
