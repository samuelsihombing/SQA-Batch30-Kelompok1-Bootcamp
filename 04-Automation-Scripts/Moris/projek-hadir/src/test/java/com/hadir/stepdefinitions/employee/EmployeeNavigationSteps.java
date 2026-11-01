package com.hadir.stepdefinitions.employee;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.employee.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class EmployeeNavigationSteps {
    private WebDriver driver;
    private EmployeeLoginPage loginPage;
    private EmployeeDashboardPage dashboardPage;
    private EmployeeKoreksiPage koreksiPage;
    private EmployeeIzinPage izinPage;
    private EmployeeLemburPage lemburPage;

    public EmployeeNavigationSteps() {
        this.driver = DriverSingleton.getDriver();
        this.loginPage = new EmployeeLoginPage(driver);
        this.dashboardPage = new EmployeeDashboardPage(driver);
        this.koreksiPage = new EmployeeKoreksiPage(driver);
        this.izinPage = new EmployeeIzinPage(driver);
        this.lemburPage = new EmployeeLemburPage(driver);
    }

    @Given("Pengguna berhasil login dan berada di Dashboard Employee")
    public void penggunaBerhasilLoginDanBeradaDiDashboardEmployee() {
        loginPage.loginWithConfig();
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Verifikasi Gagal: Dashboard tidak tampil setelah login.");
    }

    @When("Pengguna mengklik menu {string} pada grid menu")
    public void penggunaMengklikMenuPadaGridMenu(String menuName) {
        if (menuName.equalsIgnoreCase("Koreksi Absen")) {
            dashboardPage.clickMenuKoreksi();
        } else if (menuName.equalsIgnoreCase("Izin")) {
            dashboardPage.clickMenuIzin();
        } else if (menuName.equalsIgnoreCase("Lembur")) {
            dashboardPage.clickMenuLembur();
        } else {
            throw new IllegalArgumentException("Menu tidak dikenali: " + menuName);
        }
    }

    @Then("Pengguna diarahkan ke halaman Koreksi Absen dengan route {string}")
    public void penggunaDiarahkanKeHalamanKoreksiAbsenDenganRoute(String route) {
        Assert.assertTrue(driver.getCurrentUrl().contains(route), "Verifikasi Gagal: URL tidak mengandung route " + route);
    }

    @And("Halaman menampilkan judul {string} dan {string}")
    public void halamanMenampilkanJudulDan(String title1, String title2) {
        if (title1.contains("Koreksi")) {
            Assert.assertTrue(koreksiPage.isKoreksiPageDisplayed(), "Verifikasi Gagal: Judul halaman koreksi absen tidak tampil.");
        } else if (title1.contains("Lembur")) {
            Assert.assertTrue(lemburPage.isLemburPageDisplayed(), "Verifikasi Gagal: Judul halaman lembur tidak tampil.");
        }
    }

    @Then("Pengguna diarahkan ke halaman Izin dengan route {string}")
    public void penggunaDiarahkanKeHalamanIzinDenganRoute(String route) {
        Assert.assertTrue(driver.getCurrentUrl().contains(route), "Verifikasi Gagal: URL tidak mengandung route " + route);
    }

    @And("Halaman menampilkan judul {string} dan tab default {string} berstatus aktif")
    public void halamanMenampilkanJudulDanTabDefaultBerstatusAktif(String title, String tabName) {
        Assert.assertTrue(izinPage.isIzinPageDisplayed(), "Verifikasi Gagal: Judul halaman izin tidak tampil.");
        Assert.assertTrue(izinPage.isTabTerlambatActive(), "Verifikasi Gagal: Tab default " + tabName + " tidak aktif.");
    }

    @Then("Pengguna diarahkan ke halaman Lembur dengan route {string}")
    public void penggunaDiarahkanKeHalamanLemburDenganRoute(String route) {
        Assert.assertTrue(driver.getCurrentUrl().contains(route), "Verifikasi Gagal: URL tidak mengandung route " + route);
    }

    @Given("Pengguna berada di halaman Koreksi Absen Employee")
    public void penggunaBeradaDiHalamanKoreksiAbsenEmployee() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuKoreksi();
        Assert.assertTrue(koreksiPage.isKoreksiPageDisplayed(), "Verifikasi Gagal: Halaman koreksi absen tidak tampil.");
    }

    @When("Pengguna mengklik tombol Kembali pada header")
    public void penggunaMengklikTombolKembaliPadaHeader() {
        dashboardPage.clickBackButton();
    }

    @Then("Pengguna berhasil kembali ke halaman Dashboard Employee")
    public void penggunaBerhasilKembaliKeHalamanDashboardEmployee() {
        Assert.assertTrue(dashboardPage.isDashboardDisplayed(), "Verifikasi Gagal: Pengguna gagal kembali ke dashboard.");
    }

    @Then("Header dashboard menampilkan nama profil pengguna {string} dan {string}")
    public void headerDashboardMenampilkanNamaProfilPenggunaDan(String greeting, String subtitle) {
        Assert.assertTrue(dashboardPage.isGreetingAndSubtitleDisplayed(greeting, subtitle), "Verifikasi Gagal: Nama profil atau subtitle tidak sesuai di header dashboard.");
    }

    @And("Dashboard menampilkan widget periode kehadiran {string}")
    public void dashboardMenampilkanWidgetPeriodeKehadiran(String keyword) {
        Assert.assertTrue(dashboardPage.isPeriodWidgetDisplayed(keyword), "Verifikasi Gagal: Widget periode kehadiran tidak tampil di dashboard.");
    }

    @Then("Dashboard menampilkan ringkasan kartu status kehadiran")
    public void dashboardMenampilkanRingkasanKartuStatusKehadiran() {
        Assert.assertTrue(dashboardPage.areSummaryCardsDisplayed(), "Verifikasi Gagal: Kartu ringkasan status kehadiran tidak lengkap.");
    }

    @Then("Tombol aksi utama floating {string} tampil di layar dan berstatus aktif")
    public void tombolAksiUtamaFloatingTampilDiLayarDanBerstatusAktif(String buttonText) {
        Assert.assertTrue(dashboardPage.isAbsenMasukButtonDisplayedAndEnabled(), "Verifikasi Gagal: Tombol floating " + buttonText + " tidak tampil atau tidak aktif.");
    }

    @When("Pengguna mengklik tombol Kembali pada header modul")
    public void penggunaMengklikTombolKembaliPadaHeaderModul() {
        dashboardPage.clickBackButton();
    }
}
