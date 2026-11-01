package com.hadir.pages.employee;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeKoreksiPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators Halaman List Koreksi
    private By pageTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'Halaman Koreksi')] | //*[contains(@class,'MuiTypography') and contains(.,'Halaman Koreksi')]");
    private By listTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'List Koreksi')] | //*[contains(@class,'MuiTypography') and contains(.,'List Koreksi')]");
    private By ajukanKoreksiButton = By.xpath("//button[contains(.,'Ajukan Koreksi')]");

    // Locators Drawer Form Koreksi
    private By drawerContainer = By.xpath("//div[contains(@class,'MuiDrawer-paper')]");
    private By drawerTitle = By.xpath("//div[contains(@class,'MuiDrawer')]//*[text()='Ajukan Koreksi Absen']");
    private By jamMasukLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam masuk')]");
    private By jamKeluarLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam keluar')]");
    private By jamMasukInput = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam masuk')]/following::input)[1]");
    private By jamKeluarInput = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam keluar')]/following::input)[1]");
    private By jamMasukPickerBtn = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam masuk')]/following::button[@aria-label='Choose date'])[1]");
    private By jamKeluarPickerBtn = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam keluar')]/following::button[@aria-label='Choose date'])[1]");
    private By resetButton = By.xpath("//div[contains(@class,'MuiDrawer')]//button[normalize-space()='Reset']");
    private By submitButton = By.xpath("//div[contains(@class,'MuiDrawer')]//button[normalize-space()='Ajukan']");
    private By backdrop = By.xpath("//div[contains(@class,'MuiBackdrop-root')]");
    private By clockRoot = By.xpath("//div[contains(@class,'MuiClock-root')]");

    public EmployeeKoreksiPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isKoreksiPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("/apps/absent/correction"));
            boolean hasHeader = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).isDisplayed();
            boolean hasList = wait.until(ExpectedConditions.presenceOfElementLocated(listTitle)).isDisplayed();
            return hasHeader && hasList;
        } catch (Exception e) {
            try {
                return driver.getCurrentUrl().contains("/apps/absent/correction")
                    && wait.until(ExpectedConditions.presenceOfElementLocated(ajukanKoreksiButton)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public void clickAjukanKoreksi() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(ajukanKoreksiButton));
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

    public boolean areFormElementsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(jamMasukLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(jamKeluarLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(resetButton)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void openJamMasukPicker() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(jamMasukPickerBtn));
        js.executeScript("arguments[0].click();", btn);
    }

    public void openJamKeluarPicker() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(jamKeluarPickerBtn));
        js.executeScript("arguments[0].click();", btn);
    }

    public void selectDayInCalendar(String day) {
        By dayLocator = By.xpath("//button[contains(@class,'MuiPickersDay-root') and normalize-space()='" + day + "']");
        WebElement dayEl = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
        js.executeScript("arguments[0].click();", dayEl);
    }

    public boolean isClockViewDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(clockRoot)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getJamMasukValue() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(jamMasukInput));
        return el.getAttribute("value");
    }

    public String getJamKeluarValue() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(jamKeluarInput));
        return el.getAttribute("value");
    }

    public void clickReset() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        js.executeScript("arguments[0].click();", el);
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

    public boolean isJamMasukReadonly() {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(jamMasukInput));
            String ro = el.getAttribute("readonly");
            String prop = el.getDomProperty("readOnly");
            return ro != null || "true".equalsIgnoreCase(prop);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isJamKeluarReadonly() {
        try {
            WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(jamKeluarInput));
            String ro = el.getAttribute("readonly");
            String prop = el.getDomProperty("readOnly");
            return ro != null || "true".equalsIgnoreCase(prop);
        } catch (Exception e) {
            return false;
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

    public boolean areHistoryCardsDisplayedWithDetails() {
        try {
            By cardLocator = By.xpath("//*[contains(normalize-space(),'Hadir SQA') and contains(normalize-space(),'Jam masuk') and contains(normalize-space(),'Jam keluar')]");
            WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(cardLocator));
            String text = card.getText();
            return text.contains("Jam masuk") && text.contains("Jam keluar") && text.contains("status");
        } catch (Exception e) {
            try {
                By labelMasuk = By.xpath("//*[contains(text(),'Jam masuk :')] | //*[contains(.,'Jam masuk :')]");
                By labelKeluar = By.xpath("//*[contains(text(),'Jam keluar :')] | //*[contains(.,'Jam keluar :')]");
                return wait.until(ExpectedConditions.visibilityOfElementLocated(labelMasuk)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(labelKeluar)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
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
}

