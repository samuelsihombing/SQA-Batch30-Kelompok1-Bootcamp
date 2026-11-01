package com.hadir.pages.employee;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeIzinPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators Halaman List Izin
    private By pageTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'Halaman Izin')] | //*[contains(@class,'MuiTypography') and contains(.,'Halaman Izin')]");
    private By tabTerlambat = By.xpath("//button[@role='tab' and contains(.,'Terlambat')]");
    private By tabPulangCepat = By.xpath("//button[@role='tab' and contains(.,'Pulang Cepat')]");
    private By listTerlambatTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'List Izin Terlambat')] | //*[contains(@class,'MuiTypography') and contains(.,'List Izin Terlambat')]");
    private By listPulangCepatTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'List Pulang Cepat')] | //*[contains(@class,'MuiTypography') and contains(.,'List Pulang Cepat')]");
    private By ajukanButton = By.xpath("//button[contains(.,'Ajukan Izin Terlambat') or contains(.,'Ajukan Pulang Cepat') or contains(.,'Ajukan')]");

    // Locators Drawer Form Izin
    private By drawerContainer = By.xpath("//div[contains(@class,'MuiDrawer-paper')]");
    private By drawerTitle = By.xpath("//div[contains(@class,'MuiDrawer')]//p[contains(text(),'Ajukan')]");
    private By tanggalLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Tanggal')]");
    private By jamLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam')]");
    private By keteranganLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Keterangan')]");
    private By tanggalInput = By.xpath("//div[contains(@class,'MuiDrawer')]//input[contains(@placeholder,'mm/dd/yyyy')]");
    private By jamInput = By.xpath("//div[contains(@class,'MuiDrawer')]//input[contains(@placeholder,'14:00')]");
    private By keteranganTextarea = By.xpath("//div[contains(@class,'MuiDrawer')]//textarea[@id='notes']");
    private By resetButton = By.xpath("//div[contains(@class,'MuiDrawer')]//button[normalize-space()='Reset']");
    private By submitButton = By.xpath("//div[contains(@class,'MuiDrawer')]//button[normalize-space()='Ajukan']");
    private By backdrop = By.xpath("//div[contains(@class,'MuiBackdrop-root')]");

    public EmployeeIzinPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isIzinPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("/apps/absent/permit"));
            boolean hasHeader = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).isDisplayed();
            boolean hasTab = wait.until(ExpectedConditions.presenceOfElementLocated(tabTerlambat)).isDisplayed();
            return hasHeader && hasTab;
        } catch (Exception e) {
            try {
                return driver.getCurrentUrl().contains("/apps/absent/permit")
                    && wait.until(ExpectedConditions.presenceOfElementLocated(tabTerlambat)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public boolean isTabTerlambatActive() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(tabTerlambat));
        return "true".equals(el.getAttribute("aria-selected"));
    }

    public boolean isTabPulangCepatActive() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(tabPulangCepat));
        return "true".equals(el.getAttribute("aria-selected"));
    }

    public void clickTabTerlambat() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(tabTerlambat));
        js.executeScript("arguments[0].click();", el);
    }

    public void clickTabPulangCepat() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(tabPulangCepat));
        js.executeScript("arguments[0].click();", el);
    }

    public String getAjukanButtonText() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(ajukanButton));
        return el.getText().trim();
    }

    public boolean isListTerlambatDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(listTerlambatTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isListPulangCepatDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(listPulangCepatTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAjukanIzin() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(ajukanButton));
        js.executeScript("arguments[0].scrollIntoView(true);", el);
        js.executeScript("arguments[0].click();", el);
    }

    public boolean isDrawerDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(drawerTitle)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getDrawerTitleText() {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(drawerTitle));
        return el.getText().trim();
    }

    public boolean areDrawerFormElementsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(tanggalLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(jamLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(keteranganLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(tanggalInput)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(jamInput)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(keteranganTextarea)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(resetButton)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterKeterangan(String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(keteranganTextarea));
        js.executeScript("arguments[0].focus();", el);
        el.clear();
        el.sendKeys(text);
        if (el.getAttribute("value").isEmpty()) {
            js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", el, text);
        }
    }

    public String getKeteranganValue() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(keteranganTextarea));
        return el.getAttribute("value");
    }

    public void closeDrawerViaBackdrop() {
        List<WebElement> backdrops = driver.findElements(backdrop);
        if (!backdrops.isEmpty()) {
            js.executeScript("arguments[0].click();", backdrops.get(0));
        }
    }

    public boolean isDrawerClosed() {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(drawerContainer));
        } catch (Exception e) {
            return driver.findElements(drawerContainer).isEmpty() || !driver.findElement(drawerContainer).isDisplayed();
        }
    }

    public boolean isTotalCounterDisplayed() {
        try {
            By totalLocator = By.xpath("//*[self::p or self::h6 or self::span or self::div][contains(normalize-space(),'Total :')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(totalLocator)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSubmitButtonDisplayedAndEnabled() {
        try {
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(submitButton));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
            boolean displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).isDisplayed();
            boolean enabled = btn.isEnabled();
            String type = btn.getAttribute("type");
            return displayed && enabled && "submit".equalsIgnoreCase(type);
        } catch (Exception e) {
            return false;
        }
    }

    public void openDatePicker() {
        By pickerBtn = By.xpath("(//div[contains(@class,'MuiDrawer')]//button[@aria-label='Choose date'])[1]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(pickerBtn));
        js.executeScript("arguments[0].click();", btn);
    }

    public void selectDayInCalendar(String day) {
        By dayLocator = By.xpath("//button[contains(@class,'MuiPickersDay-root') and normalize-space()='" + day + "']");
        WebElement dayEl = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
        js.executeScript("arguments[0].click();", dayEl);
    }

    public String getTanggalValue() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(tanggalInput));
        return el.getAttribute("value");
    }

    public void openTimePicker() {
        By timeBtn = By.xpath("(//div[contains(@class,'MuiDrawer')]//button[@aria-label='Choose time'])[1]");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(timeBtn));
        js.executeScript("arguments[0].click();", btn);
    }

    public boolean isTimePickerPopupDisplayed() {
        try {
            By popper = By.xpath("//div[contains(@class,'MuiPickersPopper-root') or contains(@class,'MuiClock-root') or contains(@role,'dialog')]");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(popper)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}


