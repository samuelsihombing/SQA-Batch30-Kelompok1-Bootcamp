package com.hadir.pages.employee;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class EmployeeLemburPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators Halaman List Lembur
    private By pageTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'Halaman Lembur')] | //*[contains(@class,'MuiTypography') and contains(.,'Halaman Lembur')]");
    private By listTitle = By.xpath("//*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(normalize-space(),'List Overtime')] | //*[contains(@class,'MuiTypography') and contains(.,'List Overtime')]");
    private By ajukanLemburButton = By.xpath("//button[contains(.,'Ajukan Lembur')]");

    // Locators Drawer Form Lembur
    private By drawerContainer = By.xpath("//div[contains(@class,'MuiDrawer-paper')]");
    private By drawerTitle = By.xpath("//div[contains(@class,'MuiDrawer')]//*[text()='Ajukan Lembur']");
    private By jamMasukLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam masuk')]");
    private By jamKeluarLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam keluar')]");
    private By catatanLabel = By.xpath("//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Catatan')]");
    private By jamMasukInput = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam masuk')]/following::input)[1]");
    private By jamKeluarInput = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam keluar')]/following::input)[1]");
    private By catatanTextarea = By.xpath("//div[contains(@class,'MuiDrawer')]//textarea[@id='notes']");
    private By resetButton = By.xpath("//div[contains(@class,'MuiDrawer')]//button[normalize-space()='Reset']");
    private By submitButton = By.xpath("//div[contains(@class,'MuiDrawer')]//button[normalize-space()='Ajukan']");
    private By backdrop = By.xpath("//div[contains(@class,'MuiBackdrop-root')]");
    private By jamMasukPickerBtn = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam masuk')]/following::button[@aria-label='Choose date'])[1]");
    private By jamKeluarPickerBtn = By.xpath("(//div[contains(@class,'MuiDrawer')]//label[contains(text(),'Jam keluar')]/following::button[@aria-label='Choose date'])[1]");
    private By clockRoot = By.xpath("//div[contains(@class,'MuiClock-root')]");

    public EmployeeLemburPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isLemburPageDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("/apps/absent/overtime-client"));
            boolean hasHeader = wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle)).isDisplayed();
            boolean hasList = wait.until(ExpectedConditions.presenceOfElementLocated(listTitle)).isDisplayed();
            return hasHeader && hasList;
        } catch (Exception e) {
            try {
                return driver.getCurrentUrl().contains("/apps/absent/overtime-client")
                    && wait.until(ExpectedConditions.presenceOfElementLocated(ajukanLemburButton)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public void clickAjukanLembur() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(ajukanLemburButton));
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

    public boolean areDrawerFormElementsDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(jamMasukLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(jamKeluarLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(catatanLabel)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(jamMasukInput)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(jamKeluarInput)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(catatanTextarea)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(resetButton)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOfElementLocated(submitButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterCatatan(String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(catatanTextarea));
        js.executeScript("arguments[0].focus();", el);
        el.clear();
        el.sendKeys(text);
        if (el.getAttribute("value").isEmpty()) {
            js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", el, text);
        }
    }

    public String getCatatanValue() {
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(catatanTextarea));
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

    public boolean areInputFieldsValidAndActive() {
        try {
            WebElement masuk = wait.until(ExpectedConditions.presenceOfElementLocated(jamMasukInput));
            WebElement keluar = wait.until(ExpectedConditions.presenceOfElementLocated(jamKeluarInput));
            boolean masukOk = "dd mm yyyy, hh:mm".equals(masuk.getAttribute("placeholder"))
                && masuk.isEnabled()
                && "tel".equals(masuk.getAttribute("type"));
            boolean keluarOk = "dd mm yyyy, hh:mm".equals(keluar.getAttribute("placeholder"))
                && keluar.isEnabled()
                && "tel".equals(keluar.getAttribute("type"));
            return masukOk && keluarOk;
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

    public boolean areHistoryCardsDisplayedWithDetails() {
        try {
            By cardLocator = By.xpath("//*[contains(normalize-space(),'Hadir SQA') and contains(normalize-space(),'Jam Masuk') and contains(normalize-space(),'Jam Keluar')]");
            WebElement card = wait.until(ExpectedConditions.visibilityOfElementLocated(cardLocator));
            String text = card.getText();
            boolean hasUser = text.contains("Hadir SQA");
            boolean hasMasuk = text.contains("Jam Masuk");
            boolean hasKeluar = text.contains("Jam Keluar");
            boolean hasStatus = text.contains("Status");
            return hasUser && hasMasuk && hasKeluar && hasStatus;
        } catch (Exception e) {
            try {
                By userLabel = By.xpath("//*[contains(text(),'Hadir SQA') or contains(.,'Hadir SQA')]");
                By masukLabel = By.xpath("//*[contains(text(),'Jam Masuk :') or contains(.,'Jam Masuk :')]");
                By keluarLabel = By.xpath("//*[contains(text(),'Jam Keluar :') or contains(.,'Jam Keluar :')]");
                return wait.until(ExpectedConditions.visibilityOfElementLocated(userLabel)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(masukLabel)).isDisplayed()
                    && wait.until(ExpectedConditions.visibilityOfElementLocated(keluarLabel)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }


    public void enterJamMasukManual(String dateTimeStr) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(jamMasukInput));
        js.executeScript("arguments[0].focus();", el);
        el.clear();
        el.sendKeys(dateTimeStr);
        if (el.getAttribute("value").isEmpty()) {
            js.executeScript("arguments[0].value = arguments[1]; arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", el, dateTimeStr);
        }
    }
}


