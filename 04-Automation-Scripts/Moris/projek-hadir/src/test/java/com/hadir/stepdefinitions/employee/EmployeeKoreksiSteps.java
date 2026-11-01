package com.hadir.stepdefinitions.employee;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.employee.EmployeeDashboardPage;
import com.hadir.pages.employee.EmployeeKoreksiPage;
import com.hadir.pages.employee.EmployeeLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class EmployeeKoreksiSteps {
    private WebDriver driver;
    private EmployeeLoginPage loginPage;
    private EmployeeDashboardPage dashboardPage;
    private EmployeeKoreksiPage koreksiPage;

    public EmployeeKoreksiSteps() {
        this.driver = DriverSingleton.getDriver();
        this.loginPage = new EmployeeLoginPage(driver);
        this.dashboardPage = new EmployeeDashboardPage(driver);
        this.koreksiPage = new EmployeeKoreksiPage(driver);
    }

    @Given("Pengguna berhasil login ke Hadir Employee dan berada di halaman Koreksi Absen")
    public void penggunaBerhasilLoginKeHadirEmployeeDanBeradaDiHalamanKoreksiAbsen() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuKoreksi();
        Assert.assertTrue(koreksiPage.isKoreksiPageDisplayed(), "Verifikasi Gagal: Halaman Koreksi Absen tidak tampil.");
    }

    @When("Pengguna mengklik tombol floating Ajukan Koreksi")
    public void penggunaMengklikTombolFloatingAjukanKoreksi() {
        koreksiPage.clickAjukanKoreksi();
    }

    @Then("Drawer form Ajukan Koreksi Absen berhasil ditampilkan dari bawah")
    public void drawerFormAjukanKoreksiAbsenBerhasilDitampilkanDariBawah() {
        Assert.assertTrue(koreksiPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form Ajukan Koreksi Absen tidak tampil.");
    }

    @And("Form menampilkan field {string}, {string}, tombol {string}, dan tombol {string}")
    public void formMenampilkanFieldTombolDanTombol(String f1, String f2, String b1, String b2) {
        Assert.assertTrue(koreksiPage.areFormElementsDisplayed(), "Verifikasi Gagal: Elemen input atau tombol form tidak lengkap.");
    }

    @Given("Pengguna membuka drawer form Ajukan Koreksi Absen")
    public void penggunaMembukaDrawerFormAjukanKoreksiAbsen() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuKoreksi();
        koreksiPage.clickAjukanKoreksi();
        Assert.assertTrue(koreksiPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form koreksi absen tidak terbuka.");
    }

    @When("Pengguna mengklik ikon picker pada field Jam masuk")
    public void penggunaMengklikIkonPickerPadaFieldJamMasuk() {
        koreksiPage.openJamMasukPicker();
    }

    @And("Pengguna memilih tanggal aktif pada kalender picker")
    public void penggunaMemilihTanggalAktifPadaKalenderPicker() {
        koreksiPage.selectDayInCalendar("2");
    }

    @Then("Tampilan jam picker ditampilkan dan nilai Jam masuk terisi otomatis pada form")
    public void tampilanJamPickerDitampilkanDanNilaiJamMasukTerisiOtomatisPadaForm() {
        Assert.assertTrue(koreksiPage.isClockViewDisplayed(), "Verifikasi Gagal: Tampilan jam (clock face) tidak muncul setelah memilih tanggal.");
        String val = koreksiPage.getJamMasukValue();
        Assert.assertFalse(val.isEmpty(), "Verifikasi Gagal: Nilai Jam masuk belum terisi pada form.");
    }

    @When("Pengguna mengklik area backdrop overlay di luar drawer Koreksi")
    public void penggunaMengklikAreaBackdropOverlayDiLuarDrawerKoreksi() {
        koreksiPage.closeDrawerViaBackdrop();
    }

    @Then("Drawer form Koreksi Absen berhasil ditutup dari layar")
    public void drawerFormKoreksiAbsenBerhasilDitutupDariLayar() {
        Assert.assertTrue(koreksiPage.isDrawerClosed(), "Verifikasi Gagal: Drawer form koreksi absen masih tampil di layar.");
    }

    @When("Pengguna memilih tanggal dan jam pada field Jam masuk")
    public void penggunaMemilihTanggalDanJamPadaFieldJamMasuk() {
        koreksiPage.openJamMasukPicker();
        koreksiPage.selectDayInCalendar("2");
    }

    @And("Pengguna memilih tanggal dan jam pada field Jam keluar")
    public void penggunaMemilihTanggalDanJamPadaFieldJamKeluar() {
        koreksiPage.openJamKeluarPicker();
        koreksiPage.selectDayInCalendar("2");
    }

    @And("Pengguna mengklik tombol Reset pada form Koreksi")
    public void penggunaMengklikTombolResetPadaFormKoreksi() {
        koreksiPage.clickReset();
    }

    @Then("Nilai kedua field Jam masuk dan Jam keluar harus kembali kosong")
    public void nilaiKeduaFieldJamMasukDanJamKeluarHarusKembaliKosong() {
        String valMasuk = koreksiPage.getJamMasukValue();
        String valKeluar = koreksiPage.getJamKeluarValue();
        System.out.println("[Potential Defect Check DEF-01] Jam Masuk: '" + valMasuk + "', Jam Keluar: '" + valKeluar + "'");
        // Sesuai aturan: "Verifikasi kondisi aktual kedua field. Jangan mengubah hasil aktual menjadi PASS jika behavior Reset memang belum sesuai harapan."
        // Assert memeriksa kondisi yang diharapkan (kosong). Jika terjadi defect (nilai tidak ter-reset), test akan fail dengan pesan defect.
        Assert.assertTrue(valMasuk.isEmpty(), "Potential Defect DEF-01 Terkonfirmasi: Tombol Reset gagal mengosongkan nilai Jam Masuk! Nilai aktual: '" + valMasuk + "'");
        Assert.assertTrue(valKeluar.isEmpty(), "Potential Defect DEF-01 Terkonfirmasi: Tombol Reset gagal mengosongkan nilai Jam Keluar! Nilai aktual: '" + valKeluar + "'");
    }

    @When("Pengguna mengklik ikon picker pada field Jam keluar")
    public void penggunaMengklikIkonPickerPadaFieldJamKeluar() {
        koreksiPage.openJamKeluarPicker();
    }

    @Then("Tampilan jam picker ditampilkan dan nilai Jam keluar terisi otomatis pada form")
    public void tampilanJamPickerDitampilkanDanNilaiJamKeluarTerisiOtomatisPadaForm() {
        Assert.assertTrue(koreksiPage.isClockViewDisplayed(), "Verifikasi Gagal: Tampilan jam (clock face) tidak muncul setelah memilih tanggal.");
        String val = koreksiPage.getJamKeluarValue();
        Assert.assertFalse(val.isEmpty(), "Verifikasi Gagal: Nilai Jam keluar belum terisi pada form.");
    }

    @Then("Field input {string} dan {string} berstatus readonly")
    public void fieldInputDanBerstatusReadonly(String f1, String f2) {
        Assert.assertTrue(koreksiPage.isJamMasukReadonly(), "Verifikasi Gagal: Field " + f1 + " tidak berstatus readonly.");
        Assert.assertTrue(koreksiPage.isJamKeluarReadonly(), "Verifikasi Gagal: Field " + f2 + " tidak berstatus readonly.");
    }

    @Then("Header halaman menampilkan {string} dan {string}")
    public void headerHalamanMenampilkanDan(String h1, String h2) {
        Assert.assertTrue(koreksiPage.isKoreksiPageDisplayed(), "Verifikasi Gagal: Header " + h1 + " atau " + h2 + " tidak ditampilkan.");
    }

    @And("Halaman menampilkan counter total koreksi yang valid")
    public void halamanMenampilkanCounterTotalKoreksiYangValid() {
        Assert.assertTrue(koreksiPage.isTotalCounterDisplayed(), "Verifikasi Gagal: Counter total koreksi tidak tampil di layar.");
    }

    @Then("Daftar riwayat menampilkan kartu koreksi dengan detail jam kerja dan status")
    public void daftarRiwayatMenampilkanKartuKoreksiDenganDetailJamKerjaDanStatus() {
        Assert.assertTrue(koreksiPage.areHistoryCardsDisplayedWithDetails(), "Verifikasi Gagal: Detail kartu riwayat koreksi tidak lengkap.");
    }

    @Then("Tombol {string} berstatus aktif dan bertipe submit tanpa diklik")
    public void tombolBerstatusAktifDanBertipeSubmitTanpaDiklik(String btnName) {
        Assert.assertTrue(koreksiPage.isSubmitButtonDisplayedAndEnabled(), "Verifikasi Gagal: Tombol " + btnName + " tidak tampil atau tidak berstatus aktif sebagai submit button.");
    }
}

