package com.hadir.hooks;

import com.hadir.drivers.DriverSingleton;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chromium.ChromiumDriver;

import java.util.HashMap;
import java.util.Map;

public class Hooks {

    @Before(order = 1)
    public void setUp() {
        // Menginisialisasi browser Chrome sebelum setiap skenario Cucumber dijalankan
        DriverSingleton.getDriver();
    }

    @Before(value = "@employee", order = 2)
    public void setUpEmployeeViewport() {
        WebDriver driver = DriverSingleton.getDriver();
        if (driver instanceof ChromiumDriver) {
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("width", 375);
            metrics.put("height", 812);
            metrics.put("deviceScaleFactor", 1);
            metrics.put("mobile", true);
            ((ChromiumDriver) driver).executeCdpCommand("Emulation.setDeviceMetricsOverride", metrics);
        } else {
            driver.manage().window().setSize(new Dimension(375, 812));
        }
    }

    @After(order = 1)
    public void tearDown() {
        // Menutup browser secara bersih setelah setiap skenario Cucumber selesai
        DriverSingleton.quitDriver();
    }
}
