package com.hadir.stepdefinitions.employee;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.employee.EmployeeDashboardPage;
import com.hadir.pages.employee.EmployeeIzinPage;
import com.hadir.pages.employee.EmployeeLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class EmployeeIzinSteps {
    private WebDriver driver;
    private EmployeeLoginPage loginPage;
    private EmployeeDashboardPage dashboardPage;
    private EmployeeIzinPage izinPage;
    private String longText = "";

    public EmployeeIzinSteps() {
        this.driver = DriverSingleton.getDriver();
        this.loginPage = new EmployeeLoginPage(driver);
        this.dashboardPage = new EmployeeDashboardPage(driver);
        this.izinPage = new EmployeeIzinPage(driver);
    }

    @Given("Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin")
    public void penggunaBerhasilLoginKeHadirEmployeeDanBeradaDiHalamanIzin() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuIzin();
        Assert.assertTrue(izinPage.isIzinPageDisplayed(), "Verifikasi Gagal: Halaman Izin tidak tampil.");
    }

    @When("Pengguna memverifikasi tab default Terlambat aktif dengan tombol {string}")
    public void penggunaMemverifikasiTabDefaultTerlambatAktifDenganTombol(String btnExpected) {
        Assert.assertTrue(izinPage.isTabTerlambatActive(), "Verifikasi Gagal: Tab Terlambat tidak aktif secara default.");
        Assert.assertEquals(izinPage.getAjukanButtonText(), btnExpected, "Verifikasi Gagal: Teks tombol floating izin tidak sesuai.");
    }

    @And("Pengguna mengklik tab {string}")
    public void penggunaMengklikTab(String tabName) {
        if (tabName.equalsIgnoreCase("Pulang Cepat")) {
            izinPage.clickTabPulangCepat();
        } else if (tabName.equalsIgnoreCase("Terlambat")) {
            izinPage.clickTabTerlambat();
        }
    }

    @Then("Tab {string} menjadi berstatus aktif")
    public void tabMenjadiBerstatusAktif(String tabName) {
        if (tabName.equalsIgnoreCase("Pulang Cepat")) {
            Assert.assertTrue(izinPage.isTabPulangCepatActive(), "Verifikasi Gagal: Tab Pulang Cepat tidak aktif.");
        } else {
            Assert.assertTrue(izinPage.isTabTerlambatActive(), "Verifikasi Gagal: Tab Terlambat tidak aktif.");
        }
    }

    @And("Halaman menampilkan judul riwayat {string} dan tombol {string}")
    public void halamanMenampilkanJudulRiwayatDanTombol(String listExpected, String btnExpected) {
        if (listExpected.contains("Pulang Cepat")) {
            Assert.assertTrue(izinPage.isListPulangCepatDisplayed(), "Verifikasi Gagal: List Pulang Cepat tidak tampil.");
        } else {
            Assert.assertTrue(izinPage.isListTerlambatDisplayed(), "Verifikasi Gagal: List Terlambat tidak tampil.");
        }
        Assert.assertEquals(izinPage.getAjukanButtonText(), btnExpected, "Verifikasi Gagal: Teks tombol floating tidak sesuai.");
    }

    @When("Pengguna berada pada tab Terlambat dan mengklik tombol {string}")
    public void penggunaBeradaPadaTabTerlambatDanMengklikTombol(String btnName) {
        izinPage.clickAjukanIzin();
    }

    @Then("Drawer form pengajuan Izin Terlambat berhasil ditampilkan dari bawah")
    public void drawerFormPengajuanIzinTerlambatBerhasilDitampilkanDariBawah() {
        Assert.assertTrue(izinPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form izin terlambat tidak tampil.");
        Assert.assertTrue(izinPage.getDrawerTitleText().contains("Ajukan"), "Verifikasi Gagal: Judul drawer form izin tidak sesuai.");
    }

    @And("Form menampilkan field Tanggal, Jam, Keterangan, tombol Reset, dan tombol Ajukan")
    public void formMenampilkanFieldTanggalJamKeteranganTombolResetDanTombolAjukan() {
        Assert.assertTrue(izinPage.areDrawerFormElementsDisplayed(), "Verifikasi Gagal: Elemen input form izin tidak lengkap.");
    }

    @When("Pengguna beralih ke tab Pulang Cepat dan mengklik tombol {string}")
    public void penggunaBeralihKeTabPulangCepatDanMengklikTombol(String btnName) {
        izinPage.clickTabPulangCepat();
        izinPage.clickAjukanIzin();
    }

    @Then("Drawer form pengajuan Izin Pulang Cepat berhasil ditampilkan dari bawah")
    public void drawerFormPengajuanIzinPulangCepatBerhasilDitampilkanDariBawah() {
        Assert.assertTrue(izinPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form izin pulang cepat tidak tampil.");
        Assert.assertTrue(izinPage.getDrawerTitleText().contains("Ajukan"), "Verifikasi Gagal: Judul drawer form izin tidak sesuai.");
    }

    @Given("Pengguna membuka drawer form pengajuan izin Terlambat")
    public void penggunaMembukaDrawerFormPengajuanIzinTerlambat() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuIzin();
        izinPage.clickAjukanIzin();
        Assert.assertTrue(izinPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form izin terlambat tidak terbuka.");
    }

    @When("Pengguna memasukkan teks keterangan panjang 255 karakter alfanumerik")
    public void penggunaMemasukkanTeksKeteranganPanjang255KarakterAlfanumerik() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 255; i++) {
            sb.append((char) ('a' + (i % 26)));
        }
        longText = sb.toString();
        izinPage.enterKeterangan(longText);
    }

    @Then("Field textarea Keterangan menerima seluruh teks tanpa terjadi error layout UI")
    public void fieldTextareaKeteranganMenerimaSeluruhTeksTanpaTerjadiErrorLayoutUi() {
        String actualVal = izinPage.getKeteranganValue();
        Assert.assertEquals(actualVal, longText, "Verifikasi Gagal: Nilai textarea keterangan tidak sesuai dengan string input boundary.");
        Assert.assertTrue(izinPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer izin error atau tertutup setelah input panjang.");
    }

    @When("Pengguna berada pada tab default Terlambat")
    public void penggunaBeradaPadaTabDefaultTerlambat() {
        Assert.assertTrue(izinPage.isTabTerlambatActive(), "Verifikasi Gagal: Tab Terlambat tidak aktif secara default.");
    }

    @Then("Tombol floating pengajuan bertuliskan {string}")
    public void tombolFloatingPengajuanBertuliskan(String expectedText) {
        Assert.assertEquals(izinPage.getAjukanButtonText(), expectedText, "Verifikasi Gagal: Teks tombol floating tidak sesuai.");
    }

    @Then("Tombol floating pengajuan berganti teks menjadi {string}")
    public void tombolFloatingPengajuanBergantiTeksMenjadi(String expectedText) {
        Assert.assertEquals(izinPage.getAjukanButtonText(), expectedText, "Verifikasi Gagal: Teks tombol floating tidak berganti menjadi " + expectedText);
    }

    @Given("Pengguna membuka drawer form pengajuan izin Pulang Cepat")
    public void penggunaMembukaDrawerFormPengajuanIzinPulangCepat() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuIzin();
        izinPage.clickTabPulangCepat();
        izinPage.clickAjukanIzin();
        Assert.assertTrue(izinPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form izin pulang cepat tidak terbuka.");
    }

    @When("Pengguna mengklik area backdrop overlay di luar drawer Izin")
    public void penggunaMengklikAreaBackdropOverlayDiLuarDrawerIzin() {
        izinPage.closeDrawerViaBackdrop();
    }

    @Then("Drawer form Izin berhasil ditutup dari layar")
    public void drawerFormIzinBerhasilDitutupDariLayar() {
        Assert.assertTrue(izinPage.isDrawerClosed(), "Verifikasi Gagal: Drawer form izin masih tampil di layar.");
    }

    @Then("Header halaman Izin menampilkan {string} dan {string}")
    public void headerHalamanIzinMenampilkanDan(String h1, String h2) {
        Assert.assertTrue(izinPage.isIzinPageDisplayed(), "Verifikasi Gagal: Header " + h1 + " tidak ditampilkan.");
        Assert.assertTrue(izinPage.isListTerlambatDisplayed(), "Verifikasi Gagal: Subheader " + h2 + " tidak ditampilkan.");
    }

    @And("Halaman Izin menampilkan counter total yang valid")
    public void halamanIzinMenampilkanCounterTotalYangValid() {
        Assert.assertTrue(izinPage.isTotalCounterDisplayed(), "Verifikasi Gagal: Counter total izin tidak tampil.");
    }

    @Then("Halaman menampilkan judul riwayat {string}")
    public void halamanMenampilkanJudulRiwayat(String expectedTitle) {
        if (expectedTitle.contains("Pulang Cepat")) {
            Assert.assertTrue(izinPage.isListPulangCepatDisplayed(), "Verifikasi Gagal: " + expectedTitle + " tidak tampil.");
        } else {
            Assert.assertTrue(izinPage.isListTerlambatDisplayed(), "Verifikasi Gagal: " + expectedTitle + " tidak tampil.");
        }
    }

    @When("Pengguna mengklik ikon picker tanggal pada form Izin Terlambat")
    public void penggunaMengklikIkonPickerTanggalPadaFormIzinTerlambat() {
        izinPage.openDatePicker();
    }

    @And("Pengguna memilih tanggal aktif pada kalender izin")
    public void penggunaMemilihTanggalAktifPadaKalenderIzin() {
        izinPage.selectDayInCalendar("2");
    }

    @Then("Nilai field Tanggal terisi otomatis dengan tanggal yang dipilih")
    public void nilaiFieldTanggalTerisiOtomatisDenganTanggalYangDipilih() {
        String val = izinPage.getTanggalValue();
        Assert.assertFalse(val.isEmpty(), "Verifikasi Gagal: Nilai field Tanggal izin masih kosong.");
    }

    @When("Pengguna mengklik ikon time picker pada field Jam form Izin")
    public void penggunaMengklikIkonTimePickerPadaFieldJamFormIzin() {
        izinPage.openTimePicker();
    }

    @Then("Popup pemilih waktu berhasil ditampilkan di layar")
    public void popupPemilihWaktuBerhasilDitampilkanDiLayar() {
        Assert.assertTrue(izinPage.isTimePickerPopupDisplayed(), "Verifikasi Gagal: Popup pemilih waktu (time picker) tidak tampil di layar.");
    }
}



