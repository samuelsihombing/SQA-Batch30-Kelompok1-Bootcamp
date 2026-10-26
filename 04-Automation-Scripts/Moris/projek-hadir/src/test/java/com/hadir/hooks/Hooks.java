package com.hadir.hooks;

import com.hadir.drivers.DriverSingleton;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        // Menginisialisasi browser Chrome sebelum setiap skenario Cucumber dijalankan
        DriverSingleton.getDriver();
    }

    @After
    public void tearDown() {
        // Menutup browser secara bersih setelah setiap skenario Cucumber selesai
        DriverSingleton.quitDriver();
    }
}
