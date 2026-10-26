package com.hadir.stepdefinitions;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.LaporanIzinPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class LaporanIzinSteps {

    private WebDriver driver;
    private LaporanIzinPage laporanIzinPage;

    @When("Pengguna mengakses halaman Laporan Izin di URL {string}")
    public void penggunaMengaksesHalamanLaporanIzinDiURL(String path) {
        driver = DriverSingleton.getDriver();
        // Menjamin kondisi unauthenticated bersih dari sesi sebelumnya
        driver.manage().deleteAllCookies();
        driver.get("https://magang.dikahadir.com" + path);
    }

    @When("Pengguna membuka halaman Laporan Izin di URL {string}")
    public void penggunaMembukaHalamanLaporanIzinDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
        laporanIzinPage = new LaporanIzinPage(driver);
    }

    @Then("Halaman Laporan Izin berhasil dimuat")
    public void halamanLaporanIzinBerhasilDimuat() {
        if (laporanIzinPage == null) {
            driver = DriverSingleton.getDriver();
            laporanIzinPage = new LaporanIzinPage(driver);
        }
        Assert.assertTrue(laporanIzinPage.isPageLoaded(),
                "Verifikasi Gagal: URL tidak mengarah ke /laporan/izin. URL Aktual: " + driver.getCurrentUrl());
        System.out.println("Halaman Laporan Izin berhasil dimuat pada URL: " + driver.getCurrentUrl());
    }

    @And("Tabel laporan izin dan 7 header kolom ditampilkan")
    public void tabelLaporanIzinDan7HeaderKolomDitampilkan() {
        Assert.assertTrue(laporanIzinPage.isTableDisplayed(),
                "Verifikasi Gagal: Tabel laporan izin tidak ditampilkan.");

        int headerCount = laporanIzinPage.getTableHeadersCount();
        List<String> headers = laporanIzinPage.getAllTableHeadersText();
        System.out.println("Jumlah header kolom terdeteksi: " + headerCount);
        System.out.println("Header kolom terdeteksi: " + headers);

        Assert.assertEquals(headerCount, 7,
                "Verifikasi Gagal: Jumlah header kolom tidak berjumlah 7. Ditemukan: " + headerCount);

        List<String> expectedHeaders = Arrays.asList(
                "", "NAMA USER", "UNIT", "TANGGAL IZIN", "NOTES", "STATUS", ""
        );
        for (int i = 0; i < expectedHeaders.size(); i++) {
            if (i < headers.size()) {
                Assert.assertEquals(headers.get(i), expectedHeaders.get(i),
                        "Verifikasi Gagal pada header indeks ke-" + i);
            }
        }

        // Catat jumlah baris yang tampil (tidak mewajibkan jumlah baris tertentu)
        int rows = laporanIzinPage.getTableRowsCount();
        System.out.println("Jumlah baris data tabel saat ini: " + rows);
    }

    @And("Kontrol pagination laporan izin ditampilkan")
    public void kontrolPaginationLaporanIzinDitampilkan() {
        Assert.assertTrue(laporanIzinPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Kontrol pagination tidak ditampilkan.");
        System.out.println("Kontrol pagination berhasil diverifikasi.");
    }

    @And("Seluruh komponen filter dan tombol aksi laporan izin ditampilkan")
    public void seluruhKomponenFilterDanTombolAksiLaporanIzinDitampilkan() {
        boolean searchInput = laporanIzinPage.isSearchInputDisplayed();
        boolean startDate = laporanIzinPage.isStartDateInputDisplayed();
        boolean endDate = laporanIzinPage.isEndDateInputDisplayed();
        boolean searchBtn = laporanIzinPage.isSearchButtonDisplayed();
        boolean resetBtn = laporanIzinPage.isResetButtonDisplayed();

        System.out.println("Status Komponen Filter dan Tombol Aksi Laporan Izin:");
        System.out.println("- Input Pencarian (#search): " + searchInput);
        System.out.println("- Input Start Date: " + startDate);
        System.out.println("- Input End Date: " + endDate);
        System.out.println("- Tombol Search: " + searchBtn);
        System.out.println("- Tombol Reset: " + resetBtn);

        Assert.assertTrue(searchInput, "Verifikasi Gagal: Input Pencarian (#search) tidak ditampilkan.");
        Assert.assertTrue(startDate, "Verifikasi Gagal: Input Start Date tidak ditampilkan.");
        Assert.assertTrue(endDate, "Verifikasi Gagal: Input End Date tidak ditampilkan.");
        Assert.assertTrue(searchBtn, "Verifikasi Gagal: Tombol Search tidak ditampilkan.");
        Assert.assertTrue(resetBtn, "Verifikasi Gagal: Tombol Reset tidak ditampilkan.");
    }

    @When("Pengguna memasukkan nama {string} ke kolom pencarian izin dan klik Search")
    public void penggunaMemasukkanNamaKeKolomPencarianIzinDanKlikSearch(String nama) {
        laporanIzinPage.enterSearchName(nama);
        String enteredVal = laporanIzinPage.getSearchInputValue();
        Assert.assertEquals(enteredVal, nama, "Verifikasi Gagal: Nilai input pencarian tidak sesuai yang diketik.");
        System.out.println("Memasukkan nama pencarian: " + nama);

        laporanIzinPage.clickSearchButton();
        System.out.println("Tombol Search diklik.");
    }

    @Then("Halaman Laporan Izin tetap stabil")
    public void halamanLaporanIzinTetapStabil() {
        boolean isStable = laporanIzinPage.waitForTableOrPageStable();
        Assert.assertTrue(isStable, "Verifikasi Gagal: Halaman atau tabel tidak stabil setelah pencarian.");

        // Verifikasi URL tetap berada di halaman target
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/laporan/izin"),
                "Verifikasi Gagal: URL berpindah setelah pencarian. URL Aktual: " + currentUrl);

        // Verifikasi struktur header tabel tetap 7
        int headerCount = laporanIzinPage.getTableHeadersCount();
        Assert.assertEquals(headerCount, 7,
                "Verifikasi Gagal: Struktur header tabel berubah setelah pencarian. Ditemukan: " + headerCount);

        int rowCount = laporanIzinPage.getTableRowsCount();
        System.out.println("Kestabilan halaman terverifikasi. Jumlah baris tabel setelah search: " + rowCount);
    }

    @When("Pengguna mengklik tombol Reset pada filter Laporan Izin")
    public void penggunaMengklikTombolResetPadaFilterLaporanIzin() {
        laporanIzinPage.clickResetButton();
        System.out.println("Tombol Reset diklik.");
    }

    @Then("Kolom input pencarian nama izin menjadi kosong kembali")
    public void kolomInputPencarianNamaIzinMenjadiKosongKembali() {
        boolean isEmpty = laporanIzinPage.waitForSearchInputEmpty();
        String currentVal = laporanIzinPage.getSearchInputValue();
        System.out.println("Nilai input pencarian setelah reset: '" + currentVal + "'");
        Assert.assertTrue(isEmpty || currentVal.isEmpty(),
                "Verifikasi Gagal: Kolom input pencarian tidak kosong setelah Reset. Nilai Aktual: " + currentVal);
    }
}
