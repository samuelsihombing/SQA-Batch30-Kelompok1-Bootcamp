package com.hadir.stepdefinitions;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.LaporanKehadiranPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.List;

public class LaporanKehadiranSteps {

    private WebDriver driver;
    private LaporanKehadiranPage laporanKehadiranPage;

    @When("Pengguna mengakses halaman Laporan Kehadiran di URL {string}")
    public void penggunaMengaksesHalamanLaporanKehadiranDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
    }

    @When("Pengguna membuka halaman Laporan Kehadiran di URL {string}")
    public void penggunaMembukaHalamanLaporanKehadiranDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
        laporanKehadiranPage = new LaporanKehadiranPage(driver);
    }

    @Then("Halaman Laporan Kehadiran berhasil dimuat")
    public void halamanLaporanKehadiranBerhasilDimuat() {
        if (laporanKehadiranPage == null) {
            driver = DriverSingleton.getDriver();
            laporanKehadiranPage = new LaporanKehadiranPage(driver);
        }
        Assert.assertTrue(laporanKehadiranPage.isPageLoaded(),
                "Verifikasi Gagal: URL tidak mengarah ke /laporan/activity. URL Aktual: " + driver.getCurrentUrl());
        System.out.println("Halaman Laporan Kehadiran berhasil dimuat pada URL: " + driver.getCurrentUrl());
    }

    @And("Tabel laporan kehadiran dan 10 header kolom ditampilkan")
    public void tabelLaporanKehadiranDan10HeaderKolomDitampilkan() {
        Assert.assertTrue(laporanKehadiranPage.isTableDisplayed(),
                "Verifikasi Gagal: Tabel laporan kehadiran tidak ditampilkan.");

        int headerCount = laporanKehadiranPage.getTableHeadersCount();
        List<String> headers = laporanKehadiranPage.getAllTableHeadersText();
        System.out.println("Jumlah header kolom terdeteksi: " + headerCount);
        System.out.println("Header kolom terdeteksi: " + headers);

        Assert.assertEquals(headerCount, 10,
                "Verifikasi Gagal: Jumlah header kolom tidak berjumlah 10. Ditemukan: " + headerCount);

        // Catat jumlah baris jika ada (tidak mengharuskan tabel memiliki data)
        int rows = laporanKehadiranPage.getTableRowsCount();
        System.out.println("Jumlah baris data tabel saat ini: " + rows);
    }

    @And("Kontrol pagination laporan kehadiran ditampilkan")
    public void kontrolPaginationLaporanKehadiranDitampilkan() {
        Assert.assertTrue(laporanKehadiranPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Kontrol pagination tidak ditampilkan.");
        System.out.println("Kontrol pagination berhasil diverifikasi.");
    }

    @And("Seluruh komponen filter dan tombol aksi laporan kehadiran ditampilkan")
    public void seluruhKomponenFilterDanTombolAksiLaporanKehadiranDitampilkan() {
        boolean searchInput = laporanKehadiranPage.isSearchInputDisplayed();
        boolean startDate = laporanKehadiranPage.isStartDateInputDisplayed();
        boolean endDate = laporanKehadiranPage.isEndDateInputDisplayed();
        boolean searchBtn = laporanKehadiranPage.isSearchButtonDisplayed();
        boolean resetBtn = laporanKehadiranPage.isResetButtonDisplayed();
        boolean exportBtn = laporanKehadiranPage.isExportButtonDisplayed();

        System.out.println("Status Komponen Filter dan Tombol Aksi:");
        System.out.println("- Input Nama Karyawan (#search): " + searchInput);
        System.out.println("- Input Start Date: " + startDate);
        System.out.println("- Input End Date: " + endDate);
        System.out.println("- Tombol Search: " + searchBtn);
        System.out.println("- Tombol Reset: " + resetBtn);
        System.out.println("- Tombol Export: " + exportBtn);

        Assert.assertTrue(searchInput, "Verifikasi Gagal: Input Nama Karyawan (#search) tidak ditampilkan.");
        Assert.assertTrue(startDate, "Verifikasi Gagal: Input Start Date tidak ditampilkan.");
        Assert.assertTrue(endDate, "Verifikasi Gagal: Input End Date tidak ditampilkan.");
        Assert.assertTrue(searchBtn, "Verifikasi Gagal: Tombol Search tidak ditampilkan.");
        Assert.assertTrue(resetBtn, "Verifikasi Gagal: Tombol Reset tidak ditampilkan.");
        Assert.assertTrue(exportBtn, "Verifikasi Gagal: Tombol Export tidak ditampilkan.");
    }

    @When("Pengguna memasukkan nama {string} ke kolom pencarian dan klik Search")
    public void penggunaMemasukkanNamaKeKolomPencarianDanKlikSearch(String nama) {
        laporanKehadiranPage.enterSearchName(nama);
        String enteredVal = laporanKehadiranPage.getSearchInputValue();
        Assert.assertEquals(enteredVal, nama, "Verifikasi Gagal: Nilai input pencarian tidak sesuai yang diketik.");
        System.out.println("Memasukkan nama pencarian: " + nama);

        laporanKehadiranPage.clickSearchButton();
        System.out.println("Tombol Search diklik.");
    }

    @Then("Halaman Laporan Kehadiran tetap stabil")
    public void halamanLaporanKehadiranTetapStabil() {
        boolean isStable = laporanKehadiranPage.waitForTableOrPageStable();
        Assert.assertTrue(isStable, "Verifikasi Gagal: Halaman atau tabel tidak stabil setelah pencarian.");

        int rowCount = laporanKehadiranPage.getTableRowsCount();
        System.out.println("Kestabilan halaman terverifikasi. Jumlah baris tabel setelah search: " + rowCount);
    }

    @When("Pengguna mengklik tombol Reset pada filter Laporan Kehadiran")
    public void penggunaMengklikTombolResetPadaFilterLaporanKehadiran() {
        laporanKehadiranPage.clickResetButton();
        System.out.println("Tombol Reset diklik.");
    }

    @Then("Kolom input pencarian nama menjadi kosong kembali")
    public void kolomInputPencarianNamaMenjadiKosongKembali() {
        boolean isEmpty = laporanKehadiranPage.waitForSearchInputEmpty();
        String currentVal = laporanKehadiranPage.getSearchInputValue();
        System.out.println("Nilai input pencarian setelah reset: '" + currentVal + "'");
        Assert.assertTrue(isEmpty || currentVal.isEmpty(),
                "Verifikasi Gagal: Kolom input pencarian tidak kosong setelah Reset. Nilai Aktual: " + currentVal);
    }
}
