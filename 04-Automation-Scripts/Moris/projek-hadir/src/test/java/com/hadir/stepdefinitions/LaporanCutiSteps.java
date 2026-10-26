package com.hadir.stepdefinitions;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.LaporanCutiPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Arrays;
import java.util.List;

public class LaporanCutiSteps {

    private WebDriver driver;
    private LaporanCutiPage laporanCutiPage;

    @When("Pengguna mengakses halaman Laporan Cuti di URL {string}")
    public void penggunaMengaksesHalamanLaporanCutiDiURL(String path) {
        driver = DriverSingleton.getDriver();
        // Menjamin kondisi unauthenticated bersih dari sesi sebelumnya
        driver.manage().deleteAllCookies();
        driver.get("https://magang.dikahadir.com" + path);
    }

    @When("Pengguna membuka halaman Laporan Cuti di URL {string}")
    public void penggunaMembukaHalamanLaporanCutiDiURL(String path) {
        driver = DriverSingleton.getDriver();
        driver.get("https://magang.dikahadir.com" + path);
        laporanCutiPage = new LaporanCutiPage(driver);
    }

    @Then("Halaman Laporan Cuti berhasil dimuat")
    public void halamanLaporanCutiBerhasilDimuat() {
        if (laporanCutiPage == null) {
            driver = DriverSingleton.getDriver();
            laporanCutiPage = new LaporanCutiPage(driver);
        }
        Assert.assertTrue(laporanCutiPage.isPageLoaded(),
                "Verifikasi Gagal: URL tidak mengarah ke /laporan/cuti. URL Aktual: " + driver.getCurrentUrl());
        System.out.println("Halaman Laporan Cuti berhasil dimuat pada URL: " + driver.getCurrentUrl());
    }

    @And("Tabel laporan cuti dan 11 header kolom ditampilkan")
    public void tabelLaporanCutiDan11HeaderKolomDitampilkan() {
        Assert.assertTrue(laporanCutiPage.isTableDisplayed(),
                "Verifikasi Gagal: Tabel laporan cuti tidak ditampilkan.");

        int headerCount = laporanCutiPage.getTableHeadersCount();
        List<String> headers = laporanCutiPage.getAllTableHeadersText();
        System.out.println("Jumlah header kolom terdeteksi: " + headerCount);
        System.out.println("Header kolom terdeteksi: " + headers);

        Assert.assertEquals(headerCount, 11,
                "Verifikasi Gagal: Jumlah header kolom tidak berjumlah 11. Ditemukan: " + headerCount);

        List<String> expectedHeaders = Arrays.asList(
                "", "NAMA KARYAWAN", "TANGGAL PENGAJUAN", "TANGGAL CUTI", "UPLINER",
                "UPLINER 2", "UPLINER 3", "TIPE CUTI", "NOTES", "STATUS", "AKSI"
        );
        for (int i = 0; i < expectedHeaders.size(); i++) {
            if (i < headers.size()) {
                Assert.assertEquals(headers.get(i), expectedHeaders.get(i),
                        "Verifikasi Gagal pada header indeks ke-" + i);
            }
        }
    }

    @And("Kontrol pagination laporan cuti ditampilkan dan baris data cuti tersedia")
    public void kontrolPaginationLaporanCutiDitampilkanDanBarisDataCutiTersedia() {
        Assert.assertTrue(laporanCutiPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Kontrol pagination tidak ditampilkan.");
        
        laporanCutiPage.waitForTableRowsLoaded();
        int rows = laporanCutiPage.getTableRowsCount();
        String paginationText = laporanCutiPage.getPaginationText();
        System.out.println("Kontrol pagination terverifikasi: " + paginationText.replace("\n", " | "));
        System.out.println("Jumlah baris data cuti pada halaman aktif: " + rows);

        Assert.assertTrue(rows > 0,
                "Verifikasi Peringatan: Tidak ada baris data cuti yang terdeteksi di lingkungan testing saat ini.");
    }

    @And("Seluruh komponen filter dan tombol aksi laporan cuti ditampilkan")
    public void seluruhKomponenFilterDanTombolAksiLaporanCutiDitampilkan() {
        boolean searchInput = laporanCutiPage.isSearchInputDisplayed();
        boolean startDate = laporanCutiPage.isStartDateInputDisplayed();
        boolean endDate = laporanCutiPage.isEndDateInputDisplayed();
        boolean searchBtn = laporanCutiPage.isSearchButtonDisplayed();
        boolean resetBtn = laporanCutiPage.isResetButtonDisplayed();

        System.out.println("Status Komponen Filter dan Tombol Aksi Laporan Cuti:");
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

    @When("Pengguna memasukkan nama {string} ke kolom pencarian cuti dan klik Search")
    public void penggunaMemasukkanNamaKeKolomPencarianCutiDanKlikSearch(String nama) {
        laporanCutiPage.enterSearchName(nama);
        String enteredVal = laporanCutiPage.getSearchInputValue();
        Assert.assertEquals(enteredVal, nama, "Verifikasi Gagal: Nilai input pencarian tidak sesuai yang diketik.");
        System.out.println("Memasukkan nama pencarian: " + nama);

        laporanCutiPage.clickSearchButton();
        System.out.println("Tombol Search diklik.");
    }

    @Then("Halaman Laporan Cuti menampilkan hasil pencarian kosong")
    public void halamanLaporanCutiMenampilkanHasilPencarianKosong() {
        boolean isEmpty = laporanCutiPage.waitForEmptySearchResult();
        int rows = laporanCutiPage.getTableRowsCount();
        String pagText = laporanCutiPage.getPaginationText();
        System.out.println("Hasil pencarian fiktif - Baris data: " + rows + ", Pagination: " + pagText.replace("\n", " | "));

        Assert.assertTrue(isEmpty || (rows == 0 && pagText.contains("0-0 of 0")),
                "Verifikasi Gagal: Hasil pencarian untuk data fiktif tidak kosong. Baris: " + rows + ", Pagination: " + pagText);

        // Verifikasi integritas URL dan 11 header tetap terjaga
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/laporan/cuti"),
                "Verifikasi Gagal: URL berpindah setelah pencarian. URL Aktual: " + currentUrl);
        Assert.assertEquals(laporanCutiPage.getTableHeadersCount(), 11,
                "Verifikasi Gagal: Jumlah header tabel berubah setelah pencarian.");
    }

    @When("Pengguna mengklik tombol Reset pada filter Laporan Cuti")
    public void penggunaMengklikTombolResetPadaFilterLaporanCuti() {
        laporanCutiPage.clickResetButton();
        System.out.println("Tombol Reset diklik.");
    }

    @Then("Kolom input pencarian nama cuti menjadi kosong dan data tabel cuti kembali termuat")
    public void kolomInputPencarianNamaCutiMenjadiKosongDanDataTabelCutiKembaliTermuat() {
        boolean isInputEmpty = laporanCutiPage.waitForSearchInputEmpty();
        String currentVal = laporanCutiPage.getSearchInputValue();
        System.out.println("Nilai input pencarian setelah reset: '" + currentVal + "'");
        Assert.assertTrue(isInputEmpty || currentVal.isEmpty(),
                "Verifikasi Gagal: Kolom input pencarian tidak kosong setelah Reset. Nilai: " + currentVal);

        boolean isReloaded = laporanCutiPage.waitForDataReloaded();
        int rowsAfterReset = laporanCutiPage.getTableRowsCount();
        String pagTextAfterReset = laporanCutiPage.getPaginationText();
        System.out.println("Kondisi tabel setelah Reset - Baris: " + rowsAfterReset + ", Pagination: " + pagTextAfterReset.replace("\n", " | "));

        Assert.assertTrue(isReloaded || rowsAfterReset > 0,
                "Verifikasi Gagal: Data tabel cuti tidak kembali termuat setelah Reset.");
    }
}
