package hadir.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By userMenu= By.xpath("//h5[normalize-space(.)='Admin Hadir']");

    // Dari HTML yang Anda kirim: dicari lewat teks, bukan class MUI
    private final By logoutButton = By.xpath("//button[normalize-space(.)='Logout']");

    // TEBAKAN: ganti jika HTML nama pengguna Anda berbeda
   

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void logout() {
    // Langkah 1: klik "Admin Hadir" agar popup terbuka
    wait.until(ExpectedConditions.elementToBeClickable(userMenu)).click();

    // Langkah 2: tunggu tombol Logout terlihat, lalu klik lewat JavaScript
    WebElement tombol = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tombol);
}

   public boolean isLoggedOut() {
    WebDriverWait waitLama = new WebDriverWait(driver, Duration.ofSeconds(20));
    try {
        waitLama.until(ExpectedConditions.urlContains("authentication/login"));
        return true;
    } catch (TimeoutException e) {
        System.out.println("URL setelah logout (tidak sesuai harapan): " + driver.getCurrentUrl());
        return false;
    }
}
}