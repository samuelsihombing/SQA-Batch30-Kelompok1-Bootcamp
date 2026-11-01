package com.hadir.pages.employee;

import com.hadir.utils.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class EmployeeLoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // Locators
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    public EmployeeLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    public void openLoginPage() {
        driver.get(ConfigReader.getLoginUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
    }

    public void openDirectUrl(String url) {
        driver.get(url);
    }

    public void enterEmail(String email) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        el.clear();
        el.sendKeys(email);
    }

    public void enterPassword(String password) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        el.clear();
        el.sendKeys(password);
    }

    public void clickLoginButton() {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        js.executeScript("arguments[0].click();", el);
    }

    public void loginWithConfig() {
        openLoginPage();
        enterEmail(ConfigReader.getLoginEmail());
        enterPassword(ConfigReader.getLoginPassword());
        clickLoginButton();
        // Pastikan proses autentikasi selesai dan redirect ke /apps/absent
        wait.until(ExpectedConditions.urlContains("/apps/absent"));
    }

    public boolean isLoginPageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed()
                && driver.getCurrentUrl().contains("/absen/login");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isEmailFormatInvalid() {
        try {
            WebElement el = driver.findElement(emailInput);
            return (Boolean) js.executeScript("return arguments[0].validity.typeMismatch;", el);
        } catch (Exception e) {
            return false;
        }
    }

    public void clearFields() {
        WebElement em = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        em.clear();
        WebElement pw = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        pw.clear();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
