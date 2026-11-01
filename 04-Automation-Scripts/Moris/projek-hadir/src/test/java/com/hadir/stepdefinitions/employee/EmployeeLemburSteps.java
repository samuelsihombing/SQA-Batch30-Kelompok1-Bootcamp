package com.hadir.stepdefinitions.employee;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.employee.EmployeeDashboardPage;
import com.hadir.pages.employee.EmployeeLemburPage;
import com.hadir.pages.employee.EmployeeLoginPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class EmployeeLemburSteps {
    private WebDriver driver;
    private EmployeeLoginPage loginPage;
    private EmployeeDashboardPage dashboardPage;
    private EmployeeLemburPage lemburPage;
    private String longText = "";

    public EmployeeLemburSteps() {
        this.driver = DriverSingleton.getDriver();
        this.loginPage = new EmployeeLoginPage(driver);
        this.dashboardPage = new EmployeeDashboardPage(driver);
        this.lemburPage = new EmployeeLemburPage(driver);
    }

    @Given("Pengguna berhasil login ke Hadir Employee dan berada di halaman Lembur")
    public void penggunaBerhasilLoginKeHadirEmployeeDanBeradaDiHalamanLembur() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuLembur();
        Assert.assertTrue(lemburPage.isLemburPageDisplayed(), "Verifikasi Gagal: Halaman Lembur tidak tampil.");
    }

    @When("Pengguna mengklik tombol floating Ajukan Lembur")
    public void penggunaMengklikTombolFloatingAjukanLembur() {
        lemburPage.clickAjukanLembur();
    }

    @Then("Drawer form Ajukan Lembur berhasil ditampilkan dari bawah")
    public void drawerFormAjukanLemburBerhasilDitampilkanDariBawah() {
        Assert.assertTrue(lemburPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form Ajukan Lembur tidak tampil.");
    }

    @And("Form menampilkan field Jam masuk, Jam keluar, Catatan, tombol Reset, dan tombol Ajukan")
    public void formMenampilkanFieldJamMasukJamKeluarCatatanTombolResetDanTombolAjukan() {
        Assert.assertTrue(lemburPage.areDrawerFormElementsDisplayed(), "Verifikasi Gagal: Elemen input form lembur tidak lengkap.");
    }

    @Given("Pengguna membuka drawer form Ajukan Lembur")
    public void penggunaMembukaDrawerFormAjukanLembur() {
        loginPage.loginWithConfig();
        dashboardPage.clickMenuLembur();
        lemburPage.clickAjukanLembur();
        Assert.assertTrue(lemburPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer form lembur tidak terbuka.");
    }

    @When("Pengguna memasukkan teks catatan lembur panjang 255 karakter alfanumerik")
    public void penggunaMemasukkanTeksCatatanLemburPanjang255KarakterAlfanumerik() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 255; i++) {
            sb.append((char) ('0' + (i % 10)));
        }
        longText = sb.toString();
        lemburPage.enterCatatan(longText);
    }

    @Then("Field textarea Catatan lembur menerima seluruh teks tanpa terjadi error layout UI")
    public void fieldTextareaCatatanLemburMenerimaSeluruhTeksTanpaTerjadiErrorLayoutUi() {
        String actualVal = lemburPage.getCatatanValue();
        Assert.assertEquals(actualVal, longText, "Verifikasi Gagal: Nilai textarea catatan lembur tidak sesuai dengan input boundary.");
        Assert.assertTrue(lemburPage.isDrawerDisplayed(), "Verifikasi Gagal: Drawer lembur error atau tertutup setelah input teks panjang.");
    }

    @When("Pengguna mengklik area backdrop overlay di luar drawer Lembur")
    public void penggunaMengklikAreaBackdropOverlayDiLuarDrawerLembur() {
        lemburPage.closeDrawerViaBackdrop();
    }

    @Then("Drawer form Lembur berhasil ditutup dari layar")
    public void drawerFormLemburBerhasilDitutupDariLayar() {
        Assert.assertTrue(lemburPage.isDrawerClosed(), "Verifikasi Gagal: Drawer form lembur masih tampil di layar.");
    }

    @When("Pengguna mengklik ikon picker pada field Jam masuk lembur")
    public void penggunaMengklikIkonPickerPadaFieldJamMasukLembur() {
        lemburPage.openJamMasukPicker();
    }

    @And("Pengguna memilih tanggal aktif pada kalender picker lembur")
    public void penggunaMemilihTanggalAktifPadaKalenderPickerLembur() {
        lemburPage.selectDayInCalendar("2");
    }

    @Then("Tampilan jam picker lembur ditampilkan dan nilai Jam masuk lembur terisi otomatis pada form")
    public void tampilanJamPickerLemburDitampilkanDanNilaiJamMasukLemburTerisiOtomatisPadaForm() {
        Assert.assertTrue(lemburPage.isClockViewDisplayed(), "Verifikasi Gagal: Tampilan jam (clock face) lembur tidak muncul.");
        String val = lemburPage.getJamMasukValue();
        Assert.assertFalse(val.isEmpty(), "Verifikasi Gagal: Nilai Jam masuk lembur belum terisi.");
    }

    @When("Pengguna mengklik ikon picker pada field Jam keluar lembur")
    public void penggunaMengklikIkonPickerPadaFieldJamKeluarLembur() {
        lemburPage.openJamKeluarPicker();
    }

    @Then("Tampilan jam picker lembur ditampilkan dan nilai Jam keluar lembur terisi otomatis pada form")
    public void tampilanJamPickerLemburDitampilkanDanNilaiJamKeluarLemburTerisiOtomatisPadaForm() {
        Assert.assertTrue(lemburPage.isClockViewDisplayed(), "Verifikasi Gagal: Tampilan jam (clock face) lembur tidak muncul.");
        String val = lemburPage.getJamKeluarValue();
        Assert.assertFalse(val.isEmpty(), "Verifikasi Gagal: Nilai Jam keluar lembur belum terisi.");
    }

    @Then("Header halaman lembur menampilkan {string} dan {string}")
    public void headerHalamanLemburMenampilkanDan(String h1, String h2) {
        Assert.assertTrue(lemburPage.isLemburPageDisplayed(), "Verifikasi Gagal: Header " + h1 + " atau " + h2 + " tidak ditampilkan.");
    }

    @And("Halaman lembur menampilkan counter total lembur yang valid")
    public void halamanLemburMenampilkanCounterTotalLemburYangValid() {
        Assert.assertTrue(lemburPage.isTotalCounterDisplayed(), "Verifikasi Gagal: Counter total lembur tidak ditampilkan.");
    }

    @Then("Daftar riwayat lembur menampilkan kartu dengan detail jam kerja dan status")
    public void daftarRiwayatLemburMenampilkanKartuDenganDetailJamKerjaDanStatus() {
        Assert.assertTrue(lemburPage.areHistoryCardsDisplayedWithDetails(), "Verifikasi Gagal: Detail kartu riwayat lembur tidak lengkap.");
    }

    @When("Pengguna memasukkan teks jam masuk lembur secara manual {string}")
    public void penggunaMemasukkanTeksJamMasukLemburSecaraManual(String inputVal) {
        lemburPage.enterJamMasukManual(inputVal);
    }

    @Then("Field input Jam masuk lembur berhasil menerima dan menyimpan nilai yang dimasukkan")
    public void fieldInputJamMasukLemburBerhasilMenerimaDanMenyimpanNilaiYangDimasukkan() {
        String val = lemburPage.getJamMasukValue();
        Assert.assertFalse(val.isEmpty(), "Verifikasi Gagal: Nilai Jam masuk lembur masih kosong setelah input manual.");
    }
}


