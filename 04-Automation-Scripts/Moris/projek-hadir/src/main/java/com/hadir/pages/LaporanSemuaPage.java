package com.hadir.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LaporanSemuaPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators Komponen Filter Tanggal (berdasarkan DOM date picker Hadir)
    private By startDateInput = By.cssSelector("input[placeholder='Start Date']");
    private By endDateInput = By.cssSelector("input[placeholder='End Date']");

    // Locators Komponen Pencarian & Tombol Aksi
    private By searchInput = By.cssSelector("input[placeholder*='Cari' i], input[placeholder*='Search' i], input[placeholder*='Nama' i]");
    private By searchButton = By.xpath("//button[contains(translate(text(), 'SEARCH', 'search'), 'search') or contains(translate(text(), 'CARI', 'cari'), 'cari')]");
    private By resetButton = By.xpath("//button[contains(translate(text(), 'RESET', 'reset'), 'reset') or contains(translate(text(), 'BATAL', 'batal'), 'batal')]");

    // Locators Tabel Data & Header (berdasarkan Material-UI Table Hadir)
    private By table = By.cssSelector("table[aria-label='sticky table'], table.MuiTable-root");
    private By tableHeaders = By.cssSelector("thead th, th.MuiTableCell-head");
    private By tableRows = By.cssSelector("tbody tr.MuiTableRow-root");

    // Locator Kontrol Pagination
    private By pagination = By.cssSelector(".MuiTablePagination-root");

    public LaporanSemuaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Memverifikasi halaman /laporan/all berhasil dimuat
    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.urlContains("/laporan/all"));
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa keberadaan Input Start Date
    public boolean isStartDateInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(startDateInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa keberadaan Input End Date
    public boolean isEndDateInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(endDateInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa keberadaan Input Pencarian / Nama Karyawan
    public boolean isSearchInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa keberadaan Tombol Search
    public boolean isSearchButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa keberadaan Tombol Reset
    public boolean isResetButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(resetButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa keberadaan Tabel
    public boolean isTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(table)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Mengambil daftar teks nama kolom header tabel
    public List<String> getTableHeaders() {
        List<String> headers = new ArrayList<>();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(table));
            List<WebElement> elements = driver.findElements(tableHeaders);
            for (WebElement el : elements) {
                String text = el.getText().trim();
                if (!text.isEmpty()) {
                    headers.add(text);
                }
            }
        } catch (Exception e) {
            // Mengembalikan list kosong jika gagal
        }
        return headers;
    }

    // Memeriksa keberadaan Kontrol Pagination
    public boolean isPaginationDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pagination)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Menghitung jumlah baris data tabel tanpa mengasumsikan harus ada data
    public int getTableRowsCount() {
        try {
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }
}
