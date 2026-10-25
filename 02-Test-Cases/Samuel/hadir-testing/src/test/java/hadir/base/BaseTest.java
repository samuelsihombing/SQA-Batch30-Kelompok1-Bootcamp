package hadir.base;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

public class BaseTest {

    protected WebDriver driver;
    protected static Properties config = new Properties();

    static {
        try (InputStream in = BaseTest.class.getClassLoader()
                .getResourceAsStream("config.properties")) {
            config.load(in);
        } catch (Exception e) {
            throw new RuntimeException("config.properties tidak bisa dibaca", e);
        }
    }

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    // Hook: test turunan boleh mengisi ini untuk menghapus data uji
    protected void bersihkan() {
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            if (result.getStatus() == ITestResult.FAILURE) {
                try {
                    File sumber = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    Path folder = Paths.get("target", "screenshots");
                    Files.createDirectories(folder);
                    Files.copy(sumber.toPath(),
                            folder.resolve(result.getName() + ".png"),
                            StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    System.out.println("Gagal mengambil screenshot: " + e.getMessage());
                }
            }
            try {
                bersihkan();
            } catch (Exception e) {
                System.out.println("Pembersihan data gagal: " + e.getMessage());
            }
            driver.quit();
        }
    }
}