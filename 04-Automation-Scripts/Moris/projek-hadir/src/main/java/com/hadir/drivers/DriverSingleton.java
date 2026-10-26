package com.hadir.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverSingleton {
    // Variabel static untuk menyimpan satu-satunya instance WebDriver
    private static WebDriver driver;

    // Constructor dibuat 'private' agar class ini tidak bisa dibuat objek baru dengan 'new DriverSingleton()'
    private DriverSingleton() {
    }

    // Method untuk membuka atau mengambil browser Chrome
    public static WebDriver getDriver() {
        if (driver == null) {
            // Opsi tambahan untuk Chrome
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized"); // Otomatis membuka browser dalam ukuran layar penuh

            // Selenium Manager otomatis mengurus ChromeDriver yang cocok di baris ini:
            driver = new ChromeDriver(options);

        }
        return driver;
    }

    // Method untuk menutup browser secara bersih setelah pengujian selesai
    public static void quitDriver() {
        if (driver != null) {
            driver.quit(); // Menutup semua jendela browser dan mematikan proses chromedriver.exe di background
            driver = null; // Mengosongkan variabel agar siap jika sesi pengujian berikutnya butuh browser baru
        }
    }
}
