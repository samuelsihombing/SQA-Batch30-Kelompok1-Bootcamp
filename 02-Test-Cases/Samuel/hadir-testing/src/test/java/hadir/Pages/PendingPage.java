package hadir.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PendingPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Dari HTML yang Anda kirim
    private final By startDate = By.cssSelector("input[placeholder='Start Date']");
    private final By filterButton = By.xpath("//button[.//*[contains(@class,'feather-filter')]]");
    private final By unitInput = By.id("job_departement");
    private final By terapkanButton = By.xpath("//button[normalize-space(.)='Terapkan']");

    // Terbukti jalan (test sebelumnya lulus)
    private final By endDate = By.cssSelector("input[placeholder='End Date']");
    private final By searchButton = By.xpath("//button[normalize-space(.)='Search']");
    private final By resetButton = By.xpath("//button[normalize-space(.)='Reset']");
    private final By batalButton = By.xpath("//button[normalize-space(.)='Batal']");
    private final By filterTitle = By.xpath("//*[normalize-space(text())='Filter By']");

    public PendingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ---------- Validator ----------
    public boolean isValidatorDisplayed(int nomor) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[normalize-space(text())='Validator " + nomor + "']")));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- Tanggal ----------
    private void isiTanggal(By locator, String nilai) {
        WebElement kolom = wait.until(ExpectedConditions.elementToBeClickable(locator));
        kolom.click();
        kolom.sendKeys(Keys.chord(Keys.CONTROL, "a"), nilai);
        kolom.sendKeys(Keys.TAB);
    }

    public void setStartDate(String nilai) {
        isiTanggal(startDate, nilai);
    }

    public void setEndDate(String nilai) {
        isiTanggal(endDate, nilai);
    }

    public String getStartDate() {
        return driver.findElement(startDate).getAttribute("value");
    }

    public String getEndDate() {
        return driver.findElement(endDate).getAttribute("value");
    }

    // ---------- Search dan Reset ----------
    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickReset() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton)).click();
    }

    // ---------- Dialog Filter ----------
    public void openFilter() {
        wait.until(ExpectedConditions.elementToBeClickable(filterButton)).click();
    }

    public boolean isFilterDialogDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterTitle));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickBatal() {
        wait.until(ExpectedConditions.elementToBeClickable(batalButton)).click();
    }

    public boolean isFilterDialogClosed() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(filterTitle));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ---------- Filter Unit (BARU untuk TC-DASH-09) ----------
    public void pilihUnit(String namaUnit) {
        WebElement kolom = wait.until(ExpectedConditions.elementToBeClickable(unitInput));
        kolom.click();
        kolom.sendKeys(namaUnit);

        // TEBAKAN: opsi Autocomplete MUI berbentuk <li role="option">
        By opsi = By.xpath("//li[@role='option'][normalize-space(.)='" + namaUnit + "']");
        wait.until(ExpectedConditions.elementToBeClickable(opsi)).click();
    }

    public String getUnitValue() {
        return driver.findElement(unitInput).getAttribute("value");
    }

    public void clickTerapkan() {
        wait.until(ExpectedConditions.elementToBeClickable(terapkanButton)).click();
    }
}