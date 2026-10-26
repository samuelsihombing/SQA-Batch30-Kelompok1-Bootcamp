package com.hadir.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LaporanIzinTerlambatPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Selector filter dan tombol aksi terverifikasi dari DOM dan bundle aktual
    private By searchInput = By.id("search");
    private By startDateInput = By.cssSelector("input[placeholder='Start Date']");
    private By endDateInput = By.cssSelector("input[placeholder='End Date']");
    private By searchButton = By.cssSelector("button[type='submit']");
    private By resetButton = By.xpath("//button[normalize-space()='Reset']");

    // Selector tabel data, header, dan pagination terverifikasi dari DOM aktual
    private By table = By.cssSelector("table[aria-label='sticky table']");
    private By tableHeaders = By.cssSelector("table[aria-label='sticky table'] thead th");
    private By tableRows = By.cssSelector("table[aria-label='sticky table'] tbody tr");
    private By pagination = By.cssSelector(".MuiTablePagination-root");

    public LaporanIzinTerlambatPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isPageLoaded() {
        try {
            return wait.until(ExpectedConditions.urlContains("/laporan/izin-terlambat"));
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStartDateInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(startDateInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEndDateInputDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(endDateInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSearchButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(searchButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isResetButtonDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(resetButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTableDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(table)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getTableHeadersCount() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(table));
            return driver.findElements(tableHeaders).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public List<String> getAllTableHeadersText() {
        List<String> headers = new ArrayList<>();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(table));
            List<WebElement> elements = driver.findElements(tableHeaders);
            for (WebElement el : elements) {
                headers.add(el.getText().trim());
            }
        } catch (Exception e) {
            // Mengembalikan list kosong jika gagal
        }
        return headers;
    }

    public List<String> getTableHeaders() {
        return getAllTableHeadersText();
    }

    public boolean isPaginationDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(pagination)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public int getTableRowsCount() {
        try {
            return driver.findElements(tableRows).size();
        } catch (Exception e) {
            return 0;
        }
    }

    public void enterSearchName(String name) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
        element.clear();
        element.sendKeys(name);
    }

    public void clickSearchButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        button.click();
    }

    public void clickResetButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        button.click();
    }

    public String getSearchInputValue() {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
            String val = element.getAttribute("value");
            return val != null ? val : "";
        } catch (Exception e) {
            return "";
        }
    }

    public boolean waitForTableOrPageStable() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(table)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForSearchInputEmpty() {
        try {
            return wait.until(ExpectedConditions.attributeToBe(searchInput, "value", ""));
        } catch (Exception e) {
            return false;
        }
    }
}
