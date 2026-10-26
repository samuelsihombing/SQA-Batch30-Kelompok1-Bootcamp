package com.hadir.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.hadir.pages.LoginPage;
import com.hadir.utils.TestCredentials;

public class LoginTest extends BaseTest {

    @Test(description = "Memverifikasi halaman login terbuka dan elemen email, password, serta tombol Masuk tersedia")
    public void testHalamanLoginDanKetersediaanElemen() {
        LoginPage loginPage = new LoginPage(driver);

        // 1. Verifikasi URL aktual
        String currentUrl = driver.getCurrentUrl();
        System.out.println("URL Aktual: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/authentication/login"),
                "Verifikasi Gagal: URL aktif bukan halaman login Hadir HRD.");

        // 2. Verifikasi Title dokumen browser aktual
        String actualTitle = driver.getTitle();
        System.out.println("Title Aktual: " + actualTitle);
        Assert.assertNotNull(actualTitle, "Verifikasi Gagal: Title halaman tidak terbaca.");
        Assert.assertEquals(actualTitle, "HADIR", "Verifikasi Gagal: Title halaman tidak sesuai.");

        // 3. Verifikasi ketersediaan elemen interaktif (tanpa input atau submit)
        Assert.assertTrue(loginPage.isEmailInputDisplayed(),
                "Verifikasi Gagal: Kolom input email tidak ditampilkan di halaman.");
        Assert.assertTrue(loginPage.isPasswordInputDisplayed(),
                "Verifikasi Gagal: Kolom input password tidak ditampilkan di halaman.");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Verifikasi Gagal: Tombol Masuk tidak ditampilkan di halaman.");
    }

    @Test(description = "Melakukan autentikasi menggunakan kredensial dan memverifikasi pengalihan ke Dashboard")
    public void testLoginBerhasil() {
        LoginPage loginPage = new LoginPage(driver);

        String email = TestCredentials.EMAIL;
        String password = TestCredentials.PASSWORD;

        // Log informasi diagnostik aman tanpa mencetak password
        System.out.println("Mencoba login dengan Email: " + email);

        // Melakukan proses login
        loginPage.login(email, password);

        // Menunggu respons apakah aplikasi mengalihkan URL dari login ke Dashboard
        boolean isRedirected = loginPage.waitForRedirectFromLogin();
        String currentUrl = driver.getCurrentUrl();

        if (!isRedirected) {
            String alertMessage = loginPage.getAlertMessage();
            System.out.println("Diagnostik: Login tidak mengalihkan halaman.");
            System.out.println("URL saat ini: " + currentUrl);
            System.out.println("Pesan alert sistem: " + (alertMessage.isEmpty() ? "Tidak ada alert pesan" : alertMessage));

            Assert.fail("Verifikasi Gagal: Autentikasi tidak berhasil masuk ke Dashboard. " +
                    "Pesan sistem: [" + alertMessage + "]. Silakan periksa kredensial di TestCredentials.java.");
        }

        // Jika berhasil dialihkan ke Dashboard
        System.out.println("Autentikasi Berhasil! URL Tujuan: " + currentUrl);
        Assert.assertFalse(currentUrl.contains("/authentication/login"),
                "Verifikasi Gagal: Browser masih berada pada halaman login.");
    }
}

