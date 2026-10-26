package com.hadir.stepdefinitions;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.LaporanIzinTerlambatPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class LaporanIzinTerlambatSteps {

    private WebDriver driver;
    private LaporanIzinTerlambatPage laporanIzinTerlambatPage;

    @When("Pengguna mengakses halaman Laporan Izin Terlambat di URL {string}")
    public void penggunaMengaksesHalamanLaporanIzinTerlambatDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
    }

    @When("Pengguna membuka halaman Laporan Izin Terlambat di URL {string}")
    public void penggunaMembukaHalamanLaporanIzinTerlambatDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
        laporanIzinTerlambatPage = new LaporanIzinTerlambatPage(driver);
    }

    @Then("Halaman Laporan Izin Terlambat berhasil dimuat")
    public void halamanLaporanIzinTerlambatBerhasilDimuat() {
        if (laporanIzinTerlambatPage == null) {
            driver = DriverSingleton.getDriver();
            laporanIzinTerlambatPage = new LaporanIzinTerlambatPage(driver);
        }
        Assert.assertTrue(laporanIzinTerlambatPage.isPageLoaded(),
                "Verifikasi Gagal: URL tidak mengarah ke /laporan/izin-terlambat. URL Aktual: " + driver.getCurrentUrl());
        System.out.println("Halaman Laporan Izin Terlambat berhasil dimuat pada URL: " + driver.getCurrentUrl());
    }

    @And("Tabel laporan izin terlambat dan 7 header kolom ditampilkan")
    public void tabelLaporanIzinTerlambatDan7HeaderKolomDitampilkan() {
        Assert.assertTrue(laporanIzinTerlambatPage.isTableDisplayed(),
                "Verifikasi Gagal: Tabel laporan izin terlambat tidak ditampilkan.");

        int headerCount = laporanIzinTerlambatPage.getTableHeadersCount();
        List<String> headers = laporanIzinTerlambatPage.getAllTableHeadersText();
        System.out.println("Jumlah header kolom terdeteksi: " + headerCount);
        System.out.println("Header kolom terdeteksi: " + headers);

        Assert.assertEquals(headerCount, 7,
                "Verifikasi Gagal: Jumlah header kolom tidak berjumlah 7. Ditemukan: " + headerCount);

        List<String> expectedHeaders = Arrays.asList(
                "", "NAMA USER", "NAMA UPLINER", "UNIT", "TANGGAL IZIN TERLAMBAT", "NOTES", "STATUS"
        );
        for (int i = 0; i < expectedHeaders.size(); i++) {
            if (i < headers.size()) {
                Assert.assertEquals(headers.get(i), expectedHeaders.get(i),
                        "Verifikasi Gagal pada header indeks ke-" + i);
            }
        }

        // Catat jumlah baris jika ada (tidak mengharuskan tabel memiliki data)
        int rows = laporanIzinTerlambatPage.getTableRowsCount();
        System.out.println("Jumlah baris data tabel saat ini: " + rows);
    }

    @And("Kontrol pagination laporan izin terlambat ditampilkan")
    public void kontrolPaginationLaporanIzinTerlambatDitampilkan() {
        Assert.assertTrue(laporanIzinTerlambatPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Kontrol pagination tidak ditampilkan.");
        System.out.println("Kontrol pagination berhasil diverifikasi.");
    }

    @And("Seluruh komponen filter dan tombol aksi laporan izin terlambat ditampilkan")
    public void seluruhKomponenFilterDanTombolAksiLaporanIzinTerlambatDitampilkan() {
        boolean searchInput = laporanIzinTerlambatPage.isSearchInputDisplayed();
        boolean startDate = laporanIzinTerlambatPage.isStartDateInputDisplayed();
        boolean endDate = laporanIzinTerlambatPage.isEndDateInputDisplayed();
        boolean searchBtn = laporanIzinTerlambatPage.isSearchButtonDisplayed();
        boolean resetBtn = laporanIzinTerlambatPage.isResetButtonDisplayed();

        System.out.println("Status Komponen Filter dan Tombol Aksi Laporan Izin Terlambat:");
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

    @When("Pengguna memasukkan nama {string} ke kolom pencarian izin terlambat dan klik Search")
    public void penggunaMemasukkanNamaKeKolomPencarianIzinTerlambatDanKlikSearch(String nama) {
        laporanIzinTerlambatPage.enterSearchName(nama);
        String enteredVal = laporanIzinTerlambatPage.getSearchInputValue();
        Assert.assertEquals(enteredVal, nama, "Verifikasi Gagal: Nilai input pencarian tidak sesuai yang diketik.");
        System.out.println("Memasukkan nama pencarian: " + nama);

        laporanIzinTerlambatPage.clickSearchButton();
        System.out.println("Tombol Search diklik.");
    }

    @Then("Halaman Laporan Izin Terlambat tetap stabil")
    public void halamanLaporanIzinTerlambatTetapStabil() {
        boolean isStable = laporanIzinTerlambatPage.waitForTableOrPageStable();
        Assert.assertTrue(isStable, "Verifikasi Gagal: Halaman atau tabel tidak stabil setelah pencarian.");

        int rowCount = laporanIzinTerlambatPage.getTableRowsCount();
        System.out.println("Kestabilan halaman terverifikasi. Jumlah baris tabel setelah search: " + rowCount);
    }

    @When("Pengguna mengklik tombol Reset pada filter Laporan Izin Terlambat")
    public void penggunaMengklikTombolResetPadaFilterLaporanIzinTerlambat() {
        laporanIzinTerlambatPage.clickResetButton();
        System.out.println("Tombol Reset diklik.");
    }

    @Then("Kolom input pencarian nama izin terlambat menjadi kosong kembali")
    public void kolomInputPencarianNamaIzinTerlambatMenjadiKosongKembali() {
        boolean isEmpty = laporanIzinTerlambatPage.waitForSearchInputEmpty();
        String currentVal = laporanIzinTerlambatPage.getSearchInputValue();
        System.out.println("Nilai input pencarian setelah reset: '" + currentVal + "'");
        Assert.assertTrue(isEmpty || currentVal.isEmpty(),
                "Verifikasi Gagal: Kolom input pencarian tidak kosong setelah Reset. Nilai Aktual: " + currentVal);
    }
}
