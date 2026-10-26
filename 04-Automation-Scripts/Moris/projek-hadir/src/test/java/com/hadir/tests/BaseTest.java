package com.hadir.tests;

import com.hadir.drivers.DriverSingleton;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    // Modifier protected agar variabel driver dapat diakses langsung oleh class test turunan
    protected WebDriver driver;

    // Menyiapkan browser dan membuka halaman login sebelum setiap test dijalankan
    @BeforeMethod
    public void setUp() {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com/authentication/login");
    }

    // Menutup browser secara bersih setelah setiap test selesai dijalankan
    @AfterMethod
    public void tearDown() {
        DriverSingleton.quitDriver();
    }
}
