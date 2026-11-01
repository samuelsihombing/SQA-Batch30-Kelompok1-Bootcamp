package com.hadir.stepdefinitions.webadmin;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.webadmin.LaporanCutiPage;
import com.hadir.pages.webadmin.LaporanKehadiranPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LaporanNegativeBoundarySteps {

    private WebDriver driver;
    private LaporanCutiPage laporanCutiPage;
    private LaporanKehadiranPage laporanKehadiranPage;

    private int baselineRowCount;
    private String baselinePagination;

    private LaporanCutiPage getLaporanCutiPage() {
        if (laporanCutiPage == null) {
            driver = DriverSingleton.getDriver();
            laporanCutiPage = new LaporanCutiPage(driver);
        }
        return laporanCutiPage;
    }

    private LaporanKehadiranPage getLaporanKehadiranPage() {
        if (laporanKehadiranPage == null) {
            driver = DriverSingleton.getDriver();
            laporanKehadiranPage = new LaporanKehadiranPage(driver);
        }
        return laporanKehadiranPage;
    }

    // --- NEGATIVE TESTING STEPS (NEG-01, NEG-02, NEG-03, NEG-04, NEG-05) ---

    @When("Pengguna memasukkan kata kunci negatif {string} ke pencarian cuti dan klik Search")
    public void penggunaMemasukkanKataKunciNegatifKePencarianCutiDanKlikSearch(String keyword) {
        LaporanCutiPage page = getLaporanCutiPage();
        page.enterSearchName(keyword);
        page.clickSearchButton();
    }

    @Then("Tabel Laporan Cuti menampilkan empty state dengan pagination nol")
    public void tabelLaporanCutiMenampilkanEmptyStateDenganPaginationNol() {
        LaporanCutiPage page = getLaporanCutiPage();
        boolean isEmpty = page.waitForEmptySearchResult();
        Assert.assertTrue(isEmpty, "Verifikasi Gagal: Tabel cuti tidak beralih ke empty state (0-0 of 0).");
        Assert.assertEquals(page.getTableRowsCount(), 0, "Verifikasi Gagal: Jumlah baris data cuti harus 0.");
        System.out.println("Verifikasi Berhasil: Tabel Laporan Cuti menampilkan Empty State (0-0 of 0) secara aman.");
    }

    @When("Pengguna memilih rentang tanggal di luar data cuti via kalender dan klik Search")
    public void penggunaMemilihRentangTanggalDiLuarDataCutiViaKalenderDanKlikSearch() {
        LaporanCutiPage page = getLaporanCutiPage();
        page.selectDateRangeViaCalendar(0, 1);
        page.clickSearchButton();
    }

    @When("Pengguna memasukkan nama fiktif {string} dan tanggal valid {string} pada Laporan Kehadiran")
    public void penggunaMemasukkanNamaFiktifDanTanggalValidPadaLaporanKehadiran(String name, String date) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        page.enterSearchName(name);
        page.enterStartDate(date);
        page.enterEndDate(date);
    }

    @Then("Tabel Laporan Kehadiran menampilkan empty state dengan pagination nol")
    public void tabelLaporanKehadiranMenampilkanEmptyStateDenganPaginationNol() {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        boolean isEmpty = page.waitForEmptySearchResult();
        Assert.assertTrue(isEmpty, "Verifikasi Gagal: Tabel kehadiran tidak menampilkan empty state (0-0 of 0).");
        Assert.assertEquals(page.getTableRowsCount(), 0, "Verifikasi Gagal: Jumlah baris data kehadiran harus 0.");
        System.out.println("Verifikasi Berhasil: Tabel Laporan Kehadiran menampilkan Empty State (0-0 of 0).");
    }

    // --- BOUNDARY VALUE TESTING STEPS (BVT-01, BVT-02, BVT-05, BVT-06) ---

    @When("Pengguna memasukkan 1 karakter {string} ke pencarian kehadiran dan klik Search")
    public void penggunaMemasukkan1KarakterKePencarianKehadiranDanKlikSearch(String charText) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        page.enterSearchName(charText);
        page.clickSearchButton();
    }

    @Then("Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya mengandung nama {string}")
    public void tabelLaporanKehadiranMenampilkanDataYangSeluruhBarisnyaMengandungNama(String expectedName) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        boolean loaded = page.waitForSearchResultsLoaded(expectedName);
        Assert.assertTrue(loaded, "Verifikasi Gagal: Data dengan nama '" + expectedName + "' tidak termuat.");
        Assert.assertTrue(page.getTableRowsCount() > 0, "Verifikasi Gagal: Jumlah baris data harus > 0.");
        Assert.assertTrue(page.areAllRowsContainingText(expectedName), "Verifikasi Gagal: Baris data tidak mengandung nama: " + expectedName);
        System.out.println("Verifikasi Berhasil: Seluruh baris data kehadiran memuat nama: '" + expectedName + "'");
    }


    @When("Pengguna memasukkan teks panjang 100 karakter ke pencarian cuti dan klik Search")
    public void penggunaMemasukkanTeksPanjang100KarakterKePencarianCutiDanKlikSearch() {
        LaporanCutiPage page = getLaporanCutiPage();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("A");
        }
        String longText = sb.toString();
        page.enterSearchName(longText);

        String entered = page.getSearchInputValue();
        Assert.assertEquals(entered.length(), 100, "Verifikasi Gagal: Kolom input harus menerima 100 karakter penuh.");
        System.out.println("Verifikasi Berhasil: Input search menerima 100 karakter dengan aman.");

        page.clickSearchButton();
    }

    @Then("Kolom input pencarian cuti menerima teks 100 karakter dan tabel menampilkan empty state")
    public void kolomInputPencarianCutiMenerimaTeks100KarakterDanTabelMenampilkanEmptyState() {
        LaporanCutiPage page = getLaporanCutiPage();
        Assert.assertEquals(page.getSearchInputValue().length(), 100, "Verifikasi Gagal: Panjang teks input tidak 100 karakter.");
        Assert.assertTrue(page.waitForEmptySearchResult(), "Verifikasi Gagal: Tabel harus menampilkan empty state setelah input 100 karakter.");
        Assert.assertEquals(page.getTableRowsCount(), 0, "Verifikasi Gagal: Jumlah baris data harus 0.");
        System.out.println("Verifikasi Berhasil: Input 100 karakter berhasil dievaluasi dan menghasilkan Empty State tanpa crash.");
    }

    @When("Pengguna memasukkan teks pencarian dengan spasi {string} ke pencarian cuti dan klik Search")
    public void penggunaMemasukkanTeksPencarianDenganSpasiKePencarianCutiDanKlikSearch(String textWithSpaces) {
        LaporanCutiPage page = getLaporanCutiPage();
        page.enterSearchName(textWithSpaces);
        page.clickSearchButton();
    }

    @When("Pengguna mencatat baseline data cuti lalu klik Search tanpa filter")
    public void penggunaMencatatBaselineDataCutiLaluKlikSearchTanpaFilter() {
        LaporanCutiPage page = getLaporanCutiPage();
        Assert.assertTrue(page.waitForTableRowsLoaded(), "Verifikasi Gagal: Data awal tabel cuti belum termuat.");
        baselineRowCount = page.getTableRowsCount();
        baselinePagination = page.getPaginationText();

        Assert.assertTrue(baselineRowCount > 0, "Verifikasi Gagal: Baseline baris data cuti harus > 0, aktual: " + baselineRowCount);
        System.out.println("Baseline tercatat: " + baselineRowCount + " baris, Pagination: '" + baselinePagination + "'");

        page.clickSearchButton();
    }

    @Then("Tabel Laporan Cuti mempertahankan jumlah dan kondisi data sesuai baseline awal")
    public void tabelLaporanCutiMempertahankanJumlahDanKondisiDataSesuaiBaselineAwal() {
        LaporanCutiPage page = getLaporanCutiPage();
        Assert.assertTrue(page.waitForTableOrPageStable(), "Verifikasi Gagal: Tabel tidak stabil setelah klik search tanpa filter.");

        int currentRowCount = page.getTableRowsCount();
        String currentPagination = page.getPaginationText();

        Assert.assertEquals(currentRowCount, baselineRowCount,
                "Verifikasi Gagal: Jumlah baris data berubah setelah search tanpa filter. Baseline: " + baselineRowCount + ", Aktual: " + currentRowCount);
        Assert.assertEquals(currentPagination, baselinePagination,
                "Verifikasi Gagal: Teks pagination berubah setelah search tanpa filter. Baseline: '" + baselinePagination + "', Aktual: '" + currentPagination + "'");

        System.out.println("Verifikasi Berhasil: Klik Search tanpa filter mempertahankan kondisi baseline secara akurat (" + currentRowCount + " baris).");
    }
}
