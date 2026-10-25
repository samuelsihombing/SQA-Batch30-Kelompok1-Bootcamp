package hadir.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // LOCATOR (tebakan, perbaiki di sini jika berbeda dengan HTML asli)
    private final By emailField = By.cssSelector("input[type='email'], input[name='email']");
    private final By passwordField = By.cssSelector("input[type='password']");
    private final By loginButton = By.xpath("//button[contains(.,'Masuk')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
    }

    public void login(String email, String password) {
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();
    }

    public boolean isTextDisplayed(String text) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'" + text + "')]")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isLoginSuccess() {
    try {
        wait.until(ExpectedConditions.urlContains("dashboards/pending"));
        return true;
    } catch (TimeoutException e) {
        return false;
    }
}
}