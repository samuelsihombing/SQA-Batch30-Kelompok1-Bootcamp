package com.hadir.pages.webadmin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SidebarNavigationPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Selector Menu Laporan di Sidebar
    private By menuLaporan = By.xpath("//div[contains(@class, 'sidebar')]//div[contains(@class, 'sidebar__wrapper')]//p[normalize-space()='Laporan'] | //div[contains(@class, 'sidebar')]//p[normalize-space()='Laporan']");

    public SidebarNavigationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Membuka menu Laporan (accordion) jika belum terbuka
    public void clickMenuLaporan() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(menuLaporan));
        element.click();
    }

    // Memastikan menu Laporan dalam keadaan terbuka
    public void ensureMenuLaporanExpanded() {
        if (!isSubmenuDisplayed("Semua")) {
            clickMenuLaporan();
            waitForSubmenuDisplayed("Semua");
        }
    }

    // Memeriksa apakah submenu laporan tertentu ditampilkan
    public boolean isSubmenuDisplayed(String submenuName) {
        try {
            By locator = getSubmenuLocator(submenuName);
            List<WebElement> elements = driver.findElements(locator);
            return elements.size() > 0 && elements.get(0).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Menunggu hingga submenu laporan tertentu ditampilkan (menghandle transisi collapse)
    public boolean waitForSubmenuDisplayed(String submenuName) {
        try {
            By locator = getSubmenuLocator(submenuName);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Mengklik submenu laporan berdasarkan nama
    public void clickSubmenu(String submenuName) {
        ensureMenuLaporanExpanded();
        By locator = getSubmenuLocator(submenuName);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    // Memeriksa apakah submenu berstatus aktif (memiliki class active css-1kei35f atau parent MuiToolbar-root)
    public boolean isSubmenuActive(String submenuName) {
        try {
            By locator = getSubmenuLocator(submenuName);
            List<WebElement> elements = driver.findElements(locator);
            if (elements.isEmpty()) {
                return false;
            }
            WebElement pElement = elements.get(0);
            WebElement parentElement = pElement.findElement(By.xpath(".."));

            String pClass = pElement.getAttribute("class");
            String parentClass = parentElement.getAttribute("class");

            return (parentClass != null && parentClass.contains("MuiToolbar-root")) ||
                   (pClass != null && pClass.contains("css-1kei35f"));
        } catch (Exception e) {
            return false;
        }
    }

    // Menunggu submenu berstatus aktif
    public boolean waitForSubmenuActive(String submenuName) {
        try {
            return wait.until(driver -> isSubmenuActive(submenuName));
        } catch (Exception e) {
            return false;
        }
    }

    // Menunggu submenu berstatus tidak aktif (setelah berpindah ke submenu lain)
    public boolean waitForSubmenuInactive(String submenuName) {
        try {
            return wait.until(driver -> !isSubmenuActive(submenuName));
        } catch (Exception e) {
            return false;
        }
    }

    // Menunggu URL memuat path tertentu
    public boolean waitForUrlContains(String expectedPath) {
        try {
            return wait.until(ExpectedConditions.urlContains(expectedPath));
        } catch (Exception e) {
            return false;
        }
    }

    // Navigasi browser Back
    public void navigateBack() {
        driver.navigate().back();
    }

    // Navigasi browser Forward
    public void navigateForward() {
        driver.navigate().forward();
    }

    // Refresh browser
    public void refreshPage() {
        driver.navigate().refresh();
    }

    // Helper untuk membuat locator submenu (berada di dalam sidebar Material-UI)
    private By getSubmenuLocator(String submenuName) {
        return By.xpath("//div[contains(@class, 'sidebar')]//p[normalize-space()='" + submenuName + "'] | //p[normalize-space()='" + submenuName + "']");
    }
}
