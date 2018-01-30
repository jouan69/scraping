package perso.scraping.scala;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import perso.scraping.AbstractHomePage;
import perso.scraping.AbstractPage;

public class ScalaHomePage extends AbstractHomePage {

    public ScalaHomePage(final WebDriver driver, final String propertyFile) {
        super(driver);
        setSiteProp(propertyFile);
    }

    public void typeSearch(String artist) {
        WebElement inputWidget = driver.findElement(By.xpath("//input[@id='ricerca_s']"));
        inputWidget.clear();
        inputWidget.sendKeys(artist + ENTER_KEY);
    }

    public void login() {
        goTo();
    }

    public void submitSearch() {
        WebElement submitButton = driver.findElement(By.xpath("//div[@id='btn_cerca']/a/img"));
        submitButton.click();
    }

}