package com.hadir.stepdefinitions;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.LaporanSemuaPage;
import com.hadir.pages.LoginPage;
import com.hadir.utils.TestCredentials;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class LaporanSemuaSteps {

    private WebDriver driver;
    private LoginPage loginPage;
    private LaporanSemuaPage laporanSemuaPage;

    @Given("Pengguna membuka browser")
    public void penggunaMembukaBrowser() {
        driver = DriverSingleton.getDriver();
    }

    @When("Pengguna mengakses halaman Laporan Semua di URL {string}")
    public void penggunaMengaksesHalamanLaporanSemuaDiURL(String path) {
        driver.get("https://magang.dikahadir.com" + path);
    }

    @Then("Pengguna dialihkan ke halaman login {string}")
    public void penggunaDialihkanKeHalamanLogin(String expectedPath) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedPath),
                "Verifikasi Gagal: URL aktif bukan halaman login. URL Aktual: " + currentUrl);
    }

    @And("Judul halaman adalah {string}")
    public void judulHalamanAdalah(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle,
                "Verifikasi Gagal: Judul halaman tidak sesuai.");
    }

    @And("Form input email dan password login ditampilkan")
    public void formInputEmailDanPasswordLoginDitampilkan() {
        loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.isEmailInputDisplayed(),
                "Verifikasi Gagal: Kolom email tidak ditampilkan.");
        Assert.assertTrue(loginPage.isPasswordInputDisplayed(),
                "Verifikasi Gagal: Kolom password tidak ditampilkan.");
    }

    @And("Pengguna login menggunakan kredensial akun testing yang valid")
    public void penggunaLoginMenggunakanKredensialAkunTestingYangValid() {
        driver.get("https://magang.dikahadir.com/authentication/login");
        loginPage = new LoginPage(driver);
        
        System.out.println("Melakukan login dengan Email: " + TestCredentials.EMAIL);
        loginPage.login(TestCredentials.EMAIL, TestCredentials.PASSWORD);

        // Verifikasi indikator login sukses (berhasil ter-redirect dari halaman login)
        boolean isRedirected = loginPage.waitForRedirectFromLogin();
        String currentUrl = driver.getCurrentUrl();

        if (!isRedirected) {
            String alertMessage = loginPage.getAlertMessage();
            System.out.println("Pesan alert sistem: " + (alertMessage.isEmpty() ? "Tidak ada alert pesan" : alertMessage));
            Assert.fail("Verifikasi Gagal: Tidak berhasil login ke dashboard. Pesan: " + alertMessage);
        }

        System.out.println("Login Sukses terkonfirmasi. URL saat ini: " + currentUrl);
        Assert.assertFalse(currentUrl.contains("/authentication/login"),
                "Verifikasi Gagal: Masih berada pada halaman login setelah autentikasi.");
    }

    @When("Pengguna membuka halaman Laporan Semua di URL {string}")
    public void penggunaMembukaHalamanLaporanSemuaDiURL(String path) {
        driver.get("https://magang.dikahadir.com" + path);
        laporanSemuaPage = new LaporanSemuaPage(driver);
    }

    @Then("Halaman Laporan Semua berhasil dimuat")
    public void halamanLaporanSemuaBerhasilDimuat() {
        Assert.assertTrue(laporanSemuaPage.isPageLoaded(),
                "Verifikasi Gagal: Halaman Laporan Semua tidak termuat. URL Aktual: " + driver.getCurrentUrl());
        System.out.println("Halaman Laporan Semua berhasil dimuat pada URL: " + driver.getCurrentUrl());
    }

    @And("Tabel laporan dan kontrol pagination ditampilkan")
    public void tabelLaporanDanKontrolPaginationDitampilkan() {
        Assert.assertTrue(laporanSemuaPage.isTableDisplayed(),
                "Verifikasi Gagal: Tabel laporan tidak ditampilkan di halaman.");
        
        List<String> headers = laporanSemuaPage.getTableHeaders();
        System.out.println("Header kolom tabel terdeteksi: " + headers);
        Assert.assertFalse(headers.isEmpty(),
                "Verifikasi Gagal: Header kolom tabel tidak ditemukan.");

        Assert.assertTrue(laporanSemuaPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Kontrol pagination tidak ditampilkan.");

        int rows = laporanSemuaPage.getTableRowsCount();
        System.out.println("Jumlah baris data tabel saat ini: " + rows);
    }

    @And("Komponen filter tanggal mulai dan tanggal akhir ditampilkan")
    public void komponenFilterTanggalMulaiDanTanggalAkhirDitampilkan() {
        boolean startDate = laporanSemuaPage.isStartDateInputDisplayed();
        boolean endDate = laporanSemuaPage.isEndDateInputDisplayed();
        
        System.out.println("Status Filter Tanggal:");
        System.out.println("- Input Start Date tampil: " + startDate);
        System.out.println("- Input End Date tampil: " + endDate);

        Assert.assertTrue(startDate, "Verifikasi Gagal: Input Start Date tidak ditampilkan.");
        Assert.assertTrue(endDate, "Verifikasi Gagal: Input End Date tidak ditampilkan.");

        // Diagnostik komponen pencarian teks dan tombol aksi jika tersedia
        boolean searchInput = laporanSemuaPage.isSearchInputDisplayed();
        boolean searchBtn = laporanSemuaPage.isSearchButtonDisplayed();
        boolean resetBtn = laporanSemuaPage.isResetButtonDisplayed();
        System.out.println("Status Filter Tambahan:");
        System.out.println("- Input Cari/Nama Karyawan tampil: " + searchInput);
        System.out.println("- Tombol Search tampil: " + searchBtn);
        System.out.println("- Tombol Reset tampil: " + resetBtn);
    }
}
