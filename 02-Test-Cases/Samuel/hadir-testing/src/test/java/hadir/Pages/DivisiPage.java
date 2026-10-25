package hadir.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DivisiPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Terbukti jalan di Step 4a
    private final By menuManagement = By.xpath("//*[normalize-space(text())='Management']");
    private final By menuDivisi = By.xpath("//*[normalize-space(text())='Divisi']");
    private final By tambahkanButton = By.xpath("//button[normalize-space(.)='Tambahkan']");
    private final By dialog = By.xpath("//div[@role='dialog']");
    private final By namaInput = By.xpath("//div[@role='dialog']//input");
    private final By tambahButton = By.xpath("//div[@role='dialog']//button[normalize-space(.)='Tambah']");
    private final By searchInput = By.id("search");
    private final By searchButton = By.xpath("//button[normalize-space(.)='Search']");

    // Dari HTML yang Anda kirim (menu titik tiga). Delete/Save/Yes menerima beberapa ejaan
    private final By editMenu = By.xpath("//li[@role='menuitem'][normalize-space(.)='Edit']");
    private final By hapusMenu = By.xpath("//li[@role='menuitem']"
            + "[normalize-space(.)='Delete' or normalize-space(.)='Hapus' or normalize-space(.)='Menghapus']");
    private final By simpanButton = By.xpath("//div[@role='dialog']//button"
            + "[normalize-space(.)='Simpan' or normalize-space(.)='Save']");
    private final By yaButton = By.xpath("//div[@role='dialog']//button"
            + "[normalize-space(.)='Ya' or normalize-space(.)='Yes']");

    public DivisiPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- Navigasi ----------
    public void bukaMenuDivisi() {
        wait.until(ExpectedConditions.elementToBeClickable(menuManagement)).click();
        wait.until(ExpectedConditions.elementToBeClickable(menuDivisi)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(tambahkanButton));
    }

    public void bukaLangsung(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(tambahkanButton));
    }

    public boolean isTambahkanDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(tambahkanButton));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- Tambah ----------
    public void tambahDivisi(String nama) {
        wait.until(ExpectedConditions.elementToBeClickable(tambahkanButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialog));

        WebElement kolom = wait.until(ExpectedConditions.elementToBeClickable(namaInput));
        kolom.click();
        kolom.sendKeys(nama);

        wait.until(ExpectedConditions.elementToBeClickable(tambahButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialog));
    }

    // ---------- Cari ----------
    public void cariDivisi(String nama) {
        WebElement kolom = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        kolom.click();
        kolom.sendKeys(Keys.chord(Keys.CONTROL, "a"), nama);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    private By barisNama(String nama) {
        return By.xpath("//*[normalize-space(text())='" + nama + "']");
    }

    public boolean isDivisiDisplayed(String nama) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(barisNama(nama)));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isDivisiAbsent(String nama) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(barisNama(nama)));
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- Titik tiga pada BARIS tertentu ----------
    private By titikTigaBaris(String nama) {
        String ikon = "contains(@class,'feather-more-vertical')";
        return By.xpath("//*[normalize-space(text())='" + nama + "']"
                + "/ancestor::*[.//*[" + ikon + "]][1]"
                + "//button[.//*[" + ikon + "]]");
    }

    private void bukaMenuBaris(String nama) {
        wait.until(ExpectedConditions.elementToBeClickable(titikTigaBaris(nama))).click();
    }

    // ---------- Edit ----------
    public void editDivisi(String namaLama, String namaBaru) {
        bukaMenuBaris(namaLama);
        wait.until(ExpectedConditions.elementToBeClickable(editMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialog));

        WebElement kolom = wait.until(ExpectedConditions.elementToBeClickable(namaInput));
        kolom.click();
        kolom.sendKeys(Keys.chord(Keys.CONTROL, "a"), namaBaru);

        wait.until(ExpectedConditions.elementToBeClickable(simpanButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialog));
    }

    // ---------- Hapus (dengan pengaman) ----------
    public void hapusDivisi(String nama) {
        bukaMenuBaris(nama);
        wait.until(ExpectedConditions.elementToBeClickable(hapusMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dialog));

        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(dialog, nama));
        } catch (TimeoutException e) {
            throw new IllegalStateException("Dialog hapus tidak menyebut '" + nama
                    + "'. Penghapusan dibatalkan demi keamanan data.");
        }

        wait.until(ExpectedConditions.elementToBeClickable(yaButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(dialog));
    }
}