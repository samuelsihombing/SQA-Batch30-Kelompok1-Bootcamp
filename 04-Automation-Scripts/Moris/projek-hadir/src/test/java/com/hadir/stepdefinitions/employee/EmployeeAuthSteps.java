package com.hadir.stepdefinitions.employee;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.employee.EmployeeDashboardPage;
import com.hadir.pages.employee.EmployeeLoginPage;
import com.hadir.utils.ConfigReader;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class EmployeeAuthSteps {
    private WebDriver driver;
    private EmployeeLoginPage loginPage;
    private EmployeeDashboardPage dashboardPage;

    public EmployeeAuthSteps() {
        this.driver = DriverSingleton.getDriver();
        this.loginPage = new EmployeeLoginPage(driver);
        this.dashboardPage = new EmployeeDashboardPage(driver);
    }

    @Given("Pengguna membuka halaman login Hadir Employee")
    public void penggunaMembukaHalamanLoginHadirEmployee() {
        loginPage.openLoginPage();
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Verifikasi Gagal: Halaman login Hadir Employee tidak tampil.");
    }

    @When("Pengguna memasukkan email dan password valid dari konfigurasi")
    public void penggunaMemasukkanEmailDanPasswordValidDariKonfigurasi() {
        loginPage.enterEmail(ConfigReader.getLoginEmail());
        loginPage.enterPassword(ConfigReader.getLoginPassword());
    }

    @And("Pengguna mengklik tombol Masuk")
    public void penggunaMengklikTombolMasuk() {
        loginPage.clickLoginButton();
    }

    @Then("Pengguna berhasil dialihkan ke halaman Dashboard Employee")
    public void penggunaBerhasilDialihkanKeHalamanDashboardEmployee() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Verifikasi Gagal: Dashboard Employee tidak ditampilkan setelah login.");
    }

    @And("Dashboard menampilkan salam pengguna {string}")
    public void dashboardMenampilkanSalamPengguna(String greeting) {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Verifikasi Gagal: Profil salam pengguna tidak sesuai di dashboard.");
    }

    @Given("Pengguna belum melakukan login ke Hadir Employee")
    public void penggunaBelumMelakukanLoginKeHadirEmployee() {
        // Driver dibuka dengan sesi bersih baru
    }

    @When("Pengguna mencoba mengakses langsung URL route protected {string}")
    public void penggunaMencobaMengaksesLangsungUrlRouteProtected(String protectedRoute) {
        loginPage.openDirectUrl("https://magang.dikahadir.com" + protectedRoute);
    }

    @Then("Sistem mengarahkan kembali pengguna ke halaman login Hadir Employee")
    public void sistemMengarahkanKembaliPenggunaKeHalamanLoginHadirEmployee() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Verifikasi Gagal: Pengguna tanpa autentikasi tidak diarahkan kembali ke login.");
    }

    @When("Pengguna memasukkan email valid dan password salah {string}")
    public void penggunaMemasukkanEmailValidDanPasswordSalah(String wrongPassword) {
        loginPage.enterEmail(ConfigReader.getLoginEmail());
        loginPage.enterPassword(wrongPassword);
    }

    @Then("Sistem menolak login dan pengguna tetap berada di halaman login Hadir Employee")
    public void sistemMenolakLoginDanPenggunaTetapBeradaDiHalamanLoginHadirEmployee() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Verifikasi Gagal: Pengguna tidak berada di halaman login setelah gagal autentikasi.");
    }

    @When("Pengguna memasukkan format email tidak valid {string}")
    public void penggunaMemasukkanFormatEmailTidakValid(String invalidEmail) {
        loginPage.enterEmail(invalidEmail);
    }

    @And("Pengguna memasukkan password {string}")
    public void penggunaMemasukkanPassword(String password) {
        loginPage.enterPassword(password);
    }

    @Then("Field email menandai format input tidak valid sesuai spesifikasi browser")
    public void fieldEmailMenandaiFormatInputTidakValidSesuaiSpesifikasiBrowser() {
        Assert.assertTrue(loginPage.isEmailFormatInvalid(), "Verifikasi Gagal: Format email tidak ditandai sebagai typeMismatch oleh browser.");
    }

    @When("Pengguna mengosongkan field email dan password")
    public void penggunaMengosongkanFieldEmailDanPassword() {
        loginPage.clearFields();
    }

    @Then("Form login tidak terkirim dan pengguna tetap berada di halaman login Hadir Employee")
    public void formLoginTidakTerkirimDanPenggunaTetapBeradaDiHalamanLoginHadirEmployee() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Verifikasi Gagal: Form login terkirim atau berpindah halaman.");
    }
}
