package hadir.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardMenuPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DashboardMenuPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By judulKartu(String judul) {
        return By.xpath("//h3[normalize-space(.)='" + judul + "']");
    }

    private By angkaKartu(String judul) {
        return By.xpath("//h3[normalize-space(.)='" + judul + "']"
                + "/ancestor::div[contains(@class,'MuiCard-root')]//p");
    }

    public void open(String url) {
        driver.get(url);
    }

    public boolean isCardDisplayed(String judul) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(judulKartu(judul)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getCardValue(String judul) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(angkaKartu(judul)))
                .getText().trim();
    }
}