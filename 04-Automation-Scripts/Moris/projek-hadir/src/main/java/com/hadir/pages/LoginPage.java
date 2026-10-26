package com.hadir.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locator elemen berdasarkan HTML aktual yang diverifikasi
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");
    private By loginButton = By.cssSelector("button[type='submit']");

    // Constructor: Menerima WebDriver dari luar (Dependency Injection)
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Method untuk mengisi email dengan Explicit Wait
    public void enterEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        element.clear();
        element.sendKeys(email);
    }

    // Method untuk mengisi password dengan Explicit Wait
    public void enterPassword(String password) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
        element.clear();
        element.sendKeys(password);
    }

    // Method untuk klik tombol Masuk dengan Explicit Wait
    public void clickLoginButton() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        element.click();
    }

    // Method alur lengkap untuk melakukan login
    public void login(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    // Method pengecekan visibilitas elemen menggunakan Explicit Wait
    public boolean isEmailInputDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput)).isDisplayed();
    }

    public boolean isPasswordInputDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).isDisplayed();
    }

    // Locator kotak alert notifikasi/error
    private By alertBox = By.cssSelector("div[role='alert']");

    // Menunggu respon pengalihan URL (keluar dari halaman login)
    public boolean waitForRedirectFromLogin() {
        try {
            return wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("/authentication/login")));
        } catch (Exception e) {
            return false;
        }
    }

    // Memeriksa apakah alert error/pesan muncul
    public boolean isAlertDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Mengambil teks dari alert box secara aman
    public String getAlertMessage() {
        try {
            WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(alertBox));
            return alert.getText();
        } catch (Exception e) {
            return "";
        }
    }
}

