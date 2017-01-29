package perso.scraping;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AbstractPage {

	protected static final String UNKNOWN_YEAR = "UnknownYear";
	protected static final String ENTER_KEY = "\r\n";
	protected final WebDriver driver;
	
	protected Properties searchProp;
	protected Properties siteProp;
	
	public AbstractPage(String searchFile) {
		driver = ConfigProperties.getInstance().getDriver();
		setSearchProp(searchFile);
	}

	protected void log(String name, String value) {
		System.out.println("DBG : " + name + " = " + value);
	}

	protected void log(String name, int value) {
		System.out.println("DBG : " + name + " = " + value);
	}

	public void log(String string, int entryNb, String string2, int pageNumber, String string3, int indexInPage) {
		StringBuilder sb = new StringBuilder();
		sb.append("DBG : ");
		sb.append(string);
		sb.append(" = ");
		sb.append(entryNb);
		sb.append(", ");
		sb.append(string2);
		sb.append(" = ");
		sb.append(pageNumber);
		sb.append(", ");
		sb.append(string3);
		sb.append(" = ");
		sb.append(indexInPage);
		System.out.println(sb.toString());
	}

	protected String extractTitle(String s) {
		Pattern p = Pattern.compile("Title:(.*)$");
		Matcher m = p.matcher(s);
		if (m.find()) {
			return formatTitle(m.group(1));
		}
		return null;
	}

	protected String extractYearFromString(String s) {
		int yearMinConf = Integer.valueOf(searchProp.getProperty("minYear"));
		int yearMaxConf = Integer.valueOf(searchProp.getProperty("maxYear"));
		int yearMin = yearMinConf == 0 ? Integer.MIN_VALUE : yearMinConf;
		int yearMax = yearMaxConf == 0 ? Integer.MAX_VALUE : yearMaxConf;
		return extractYearFromString(s, yearMin, yearMax);
	}

	static String extractYearFromString(String s, int yearMin, int yearMax) {
		Pattern p = Pattern.compile("(\\d{4})");
		Matcher m = p.matcher(s);
		boolean yearMaxFound = false;
		while (m.find()) {
			try {
				int yearFound = Integer.parseInt(m.group(1));
				if (yearMin < yearFound && yearFound < yearMax) {
					return m.group(1);
				} else {
					if (yearFound == yearMax) {
						yearMaxFound = true;
					}
				}
			} catch (Exception e) {
				// parsing error
			}
		}
		if (yearMaxFound) {
			return String.valueOf(yearMax);
		} else {
			return UNKNOWN_YEAR;
		}
	}

	protected int extractIntFromString(String s) {
		Pattern p = Pattern.compile("(\\d+)");
		Matcher m = p.matcher(s);
		if (m.find()) {
			// log("found",m.group(1));
			return Integer.parseInt(m.group(1));
		}
		return 0;
	}

	public static int pageNumber(int entryNb, int pageSize) {
		return ((entryNb - 1) / pageSize) + 1;
	}

	public static int indexInPage(int entryNb, int pageSize) {
		return entryNb - (pageNumber(entryNb, pageSize) - 1) * pageSize;
	}

	protected String formatTitle(String title) {
		String yearTxt = extractYearFromString(title);
		String shortTitle = title.replaceAll(yearTxt, "");
		shortTitle = shortTitle.replaceAll(",", "");
		shortTitle = shortTitle.replaceAll("\\.", "");
		shortTitle = shortTitle.replaceAll("\\?", "");
		shortTitle = shortTitle.replaceAll("\\\\", "");
		shortTitle = shortTitle.replaceAll("\\/", "");
		shortTitle = shortTitle.replaceAll("\\:", "");
		shortTitle = shortTitle.replaceAll("\\|", "");
		shortTitle = shortTitle.replaceAll(">", "");
		shortTitle = shortTitle.replaceAll("<", "");
		shortTitle = shortTitle.replaceAll("\"", "");
		shortTitle = shortTitle.replaceAll("\\*", "");
		shortTitle = shortTitle.replaceAll("\r\n", "");
		shortTitle = shortTitle.replaceAll("\n", "");
		shortTitle = shortTitle.trim();
		int len = Math.min(100, shortTitle.length());
		shortTitle = shortTitle.substring(0, len);
		return yearTxt + "_" + shortTitle;
	}

	public void takeScreenshot(String title) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Now you can do whatever you need to do with it, for example copy
		// somewhere
		log(" screenshot title", title);
		try {
			File newFile = getUniqueFilename(new File("c:\\tmp\\" + title + ".png"));
			FileUtils.copyFile(scrFile, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getUniqueFilename(File file) {
		String baseName = FilenameUtils.getBaseName(file.getName());
		String extension = FilenameUtils.getExtension(file.getName());
		int counter = 1;
		while (file.exists()) {
			file = new File(file.getParent(), baseName + "-" + (counter++) + "." + extension);
		}
		return file;
	}

	protected void verySmallPause() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void smallPause() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected void pause() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setSiteProp(String siteFile) {
		String path = ConfigProperties.configPathSite+siteFile;
		setSiteProp(loadProp(path));
	}

	public void setSearchProp(String searchFile) {
		String path = ConfigProperties.configPathSearch+searchFile;
		setSearchProp(loadProp(path));
	}
	
	public Properties loadProp(String path) {
		InputStream input = null;
		Properties prop = null;
		try {
			prop = new Properties();
			input = new FileInputStream(path);
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public Properties getSearchProp() {
		return searchProp;
	}

	public void setSearchProp(Properties searchProp) {
		this.searchProp = searchProp;
	}

	public Properties getSiteProp() {
		return siteProp;
	}

	public void setSiteProp(Properties siteProp) {
		this.siteProp = siteProp;
	}
}
