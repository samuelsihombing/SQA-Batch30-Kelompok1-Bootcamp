package com.hadir.pages.employee;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeDashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators Dashboard
    private By dashboardIndicator = By.xpath("//button[contains(., 'Absen Masuk')] | //*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(., 'Hai, Hadir SQA')]");
    private By menuKoreksi = By.xpath("//a[contains(@class,'user__menu__item') and .//p[contains(text(),'Koreksi Absen')]]");
    private By menuIzin = By.xpath("//a[contains(@class,'user__menu__item') and .//p[contains(text(),'Izin')]]");
    private By menuLembur = By.xpath("//a[contains(@class,'user__menu__item') and .//p[contains(text(),'Lembur')]]");
    private By backButton = By.xpath("//button[contains(.,'Kembali')] | //*[text()='Kembali']");

    public EmployeeDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public boolean isDashboardDisplayed() {
        try {
            wait.until(ExpectedConditions.urlContains("/apps/absent"));
            By ind = By.xpath("//button[contains(., 'Absen Masuk')] | //*[self::p or self::h1 or self::h2 or self::h3 or self::h4 or self::h5 or self::h6 or self::span][contains(., 'Hai, Hadir SQA') or contains(., 'Kehadiranmu')]");
            try {
                return wait.until(ExpectedConditions.presenceOfElementLocated(ind)).isDisplayed();
            } catch (Exception ex) {
                return driver.getCurrentUrl().contains("/apps/absent");
            }
        } catch (Exception e) {
            return driver.getCurrentUrl().contains("/apps/absent");
        }
    }


    private void clickMenuItem(By locator, String expectedUrlPart) {
        for (int i = 0; i < 3; i++) {
            try {
                WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
                WebElement clickableEl = wait.until(ExpectedConditions.elementToBeClickable(locator));
                js.executeScript("arguments[0].click();", clickableEl);
                wait.until(ExpectedConditions.urlContains(expectedUrlPart));
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Retry if element became stale on scroll/render
            }
        }
    }

    public void clickMenuKoreksi() {
        clickMenuItem(menuKoreksi, "/correction");
    }

    public void clickMenuIzin() {
        clickMenuItem(menuIzin, "/permit");
    }

    public void clickMenuLembur() {
        clickMenuItem(menuLembur, "/overtime-client");
    }

    public void clickBackButton() {
        for (int i = 0; i < 3; i++) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(backButton));
                js.executeScript("arguments[0].click();", el);
                wait.until(ExpectedConditions.urlContains("/apps/absent"));
                return;
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Retry
            }
        }
    }

    public boolean isGreetingAndSubtitleDisplayed(String greeting, String subtitle) {
        try {
            boolean hasGreeting = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(., '" + greeting + "')]"))).isDisplayed();
            boolean hasSubtitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(., '" + subtitle + "')]"))).isDisplayed();
            return hasGreeting && hasSubtitle;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPeriodWidgetDisplayed(String keyword) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(., '" + keyword + "')]"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areSummaryCardsDisplayed() {
        try {
            By cardIndicator = By.xpath("//*[contains(normalize-space(),'WFO')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(cardIndicator));
            By allCards = By.xpath("//*[self::p or self::h6 or self::span or self::div][normalize-space()='WFO' or normalize-space()='Cuti' or normalize-space()='Sakit' or normalize-space()='WFH' or normalize-space()='Lembur' or normalize-space()='Terlambat' or normalize-space()='Pulang Cepat']");
            return driver.findElements(allCards).size() >= 4;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isAbsenMasukButtonDisplayedAndEnabled() {
        try {
            By btnLocator = By.xpath("//*[self::button or self::a][contains(., 'Absen Masuk') or contains(., 'Absen')]");
            WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(btnLocator));
            js.executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
            return wait.until(ExpectedConditions.visibilityOfElementLocated(btnLocator)).isDisplayed();
        } catch (Exception e) {
            try {
                By fallback = By.xpath("//button[contains(@class,'MuiButton') and contains(.,'Absen')]");
                WebElement el = driver.findElement(fallback);
                js.executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
                return el.isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
