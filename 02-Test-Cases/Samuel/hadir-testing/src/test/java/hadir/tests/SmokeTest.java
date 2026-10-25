package hadir.tests;

import hadir.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SmokeTest extends BaseTest {

    @Test
    public void halamanLoginAdminTerbuka() {
        driver.get(config.getProperty("admin.url"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[type='password']")));

        Assert.assertTrue(driver.getCurrentUrl().contains("login"),
                "URL seharusnya mengandung kata 'login'");
        System.out.println("Judul halaman: " + driver.getTitle());
    }
}