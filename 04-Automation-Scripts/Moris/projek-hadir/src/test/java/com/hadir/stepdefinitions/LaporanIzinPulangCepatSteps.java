package com.hadir.stepdefinitions;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.LaporanIzinPulangCepatPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class LaporanIzinPulangCepatSteps {

    private WebDriver driver;
    private LaporanIzinPulangCepatPage laporanIzinPulangCepatPage;

    @When("Pengguna mengakses halaman Laporan Izin Pulang Cepat di URL {string}")
    public void penggunaMengaksesHalamanLaporanIzinPulangCepatDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
    }

    @When("Pengguna membuka halaman Laporan Izin Pulang Cepat di URL {string}")
    public void penggunaMembukaHalamanLaporanIzinPulangCepatDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
        laporanIzinPulangCepatPage = new LaporanIzinPulangCepatPage(driver);
    }

    @Then("Halaman Laporan Izin Pulang Cepat berhasil dimuat")
    public void halamanLaporanIzinPulangCepatBerhasilDimuat() {
        if (laporanIzinPulangCepatPage == null) {
            driver = DriverSingleton.getDriver();
            laporanIzinPulangCepatPage = new LaporanIzinPulangCepatPage(driver);
        }
        Assert.assertTrue(laporanIzinPulangCepatPage.isPageLoaded(),
                "Verifikasi Gagal: URL tidak mengarah ke /laporan/izin-pulang-cepat. URL Aktual: " + driver.getCurrentUrl());
        System.out.println("Halaman Laporan Izin Pulang Cepat berhasil dimuat pada URL: " + driver.getCurrentUrl());
    }

    @And("Tabel laporan izin pulang cepat dan 7 header kolom ditampilkan")
    public void tabelLaporanIzinPulangCepatDan7HeaderKolomDitampilkan() {
        Assert.assertTrue(laporanIzinPulangCepatPage.isTableDisplayed(),
                "Verifikasi Gagal: Tabel laporan izin pulang cepat tidak ditampilkan.");

        int headerCount = laporanIzinPulangCepatPage.getTableHeadersCount();
        List<String> headers = laporanIzinPulangCepatPage.getAllTableHeadersText();
        System.out.println("Jumlah header kolom terdeteksi: " + headerCount);
        System.out.println("Header kolom terdeteksi: " + headers);

        Assert.assertEquals(headerCount, 7,
                "Verifikasi Gagal: Jumlah header kolom tidak berjumlah 7. Ditemukan: " + headerCount);

        List<String> expectedHeaders = Arrays.asList(
                "", "NAMA USER", "NAMA UPLINER", "UNIT", "TANGGAL IZIN PULANG CEPAT", "NOTES", "STATUS"
        );
        for (int i = 0; i < expectedHeaders.size(); i++) {
            if (i < headers.size()) {
                Assert.assertEquals(headers.get(i), expectedHeaders.get(i),
                        "Verifikasi Gagal pada header indeks ke-" + i);
            }
        }

        // Catat jumlah baris yang tampil (tidak mensyaratkan jumlah baris tertentu)
        int rows = laporanIzinPulangCepatPage.getTableRowsCount();
        System.out.println("Jumlah baris data tabel saat ini: " + rows);
    }

    @And("Kontrol pagination laporan izin pulang cepat ditampilkan")
    public void kontrolPaginationLaporanIzinPulangCepatDitampilkan() {
        Assert.assertTrue(laporanIzinPulangCepatPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Kontrol pagination tidak ditampilkan.");
        System.out.println("Kontrol pagination berhasil diverifikasi.");
    }

    @And("Seluruh komponen filter dan tombol aksi laporan izin pulang cepat ditampilkan")
    public void seluruhKomponenFilterDanTombolAksiLaporanIzinPulangCepatDitampilkan() {
        boolean searchInput = laporanIzinPulangCepatPage.isSearchInputDisplayed();
        boolean startDate = laporanIzinPulangCepatPage.isStartDateInputDisplayed();
        boolean endDate = laporanIzinPulangCepatPage.isEndDateInputDisplayed();
        boolean searchBtn = laporanIzinPulangCepatPage.isSearchButtonDisplayed();
        boolean resetBtn = laporanIzinPulangCepatPage.isResetButtonDisplayed();

        System.out.println("Status Komponen Filter dan Tombol Aksi Laporan Izin Pulang Cepat:");
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

    @When("Pengguna memasukkan nama {string} ke kolom pencarian izin pulang cepat dan klik Search")
    public void penggunaMemasukkanNamaKeKolomPencarianIzinPulangCepatDanKlikSearch(String nama) {
        laporanIzinPulangCepatPage.enterSearchName(nama);
        String enteredVal = laporanIzinPulangCepatPage.getSearchInputValue();
        Assert.assertEquals(enteredVal, nama, "Verifikasi Gagal: Nilai input pencarian tidak sesuai yang diketik.");
        System.out.println("Memasukkan nama pencarian: " + nama);

        laporanIzinPulangCepatPage.clickSearchButton();
        System.out.println("Tombol Search diklik.");
    }

    @Then("Halaman Laporan Izin Pulang Cepat tetap stabil")
    public void halamanLaporanIzinPulangCepatTetapStabil() {
        boolean isStable = laporanIzinPulangCepatPage.waitForTableOrPageStable();
        Assert.assertTrue(isStable, "Verifikasi Gagal: Halaman atau tabel tidak stabil setelah pencarian.");

        // Verifikasi URL tetap berada di halaman target
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/laporan/izin-pulang-cepat"),
                "Verifikasi Gagal: URL berpindah setelah pencarian. URL Aktual: " + currentUrl);

        // Verifikasi struktur header tabel tetap 7
        int headerCount = laporanIzinPulangCepatPage.getTableHeadersCount();
        Assert.assertEquals(headerCount, 7,
                "Verifikasi Gagal: Struktur header tabel berubah setelah pencarian. Ditemukan: " + headerCount);

        int rowCount = laporanIzinPulangCepatPage.getTableRowsCount();
        System.out.println("Kestabilan halaman terverifikasi. Jumlah baris tabel setelah search: " + rowCount);
    }

    @When("Pengguna mengklik tombol Reset pada filter Laporan Izin Pulang Cepat")
    public void penggunaMengklikTombolResetPadaFilterLaporanIzinPulangCepat() {
        laporanIzinPulangCepatPage.clickResetButton();
        System.out.println("Tombol Reset diklik.");
    }

    @Then("Kolom input pencarian nama izin pulang cepat menjadi kosong kembali")
    public void kolomInputPencarianNamaIzinPulangCepatMenjadiKosongKembali() {
        boolean isEmpty = laporanIzinPulangCepatPage.waitForSearchInputEmpty();
        String currentVal = laporanIzinPulangCepatPage.getSearchInputValue();
        System.out.println("Nilai input pencarian setelah reset: '" + currentVal + "'");
        Assert.assertTrue(isEmpty || currentVal.isEmpty(),
                "Verifikasi Gagal: Kolom input pencarian tidak kosong setelah Reset. Nilai Aktual: " + currentVal);
    }
}
