package com.hadir.stepdefinitions.webadmin;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.webadmin.LaporanCutiPage;
import com.hadir.pages.webadmin.LaporanKehadiranPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LaporanPositiveSearchFilterSteps {

    private WebDriver driver;
    private LaporanCutiPage laporanCutiPage;
    private LaporanKehadiranPage laporanKehadiranPage;

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

    // --- POSITIVE SEARCH SCENARIOS (POS-01, POS-02, POS-03) ---

    @When("Pengguna mencari nama karyawan {string} pada Laporan Cuti")
    public void penggunaMencariNamaKaryawanPadaLaporanCuti(String name) {
        LaporanCutiPage page = getLaporanCutiPage();
        page.enterSearchName(name);
        page.clickSearchButton();
        boolean loaded = page.waitForSearchResultsLoaded(name);
        Assert.assertTrue(loaded, "Verifikasi Gagal: Hasil pencarian '" + name + "' tidak termuat dalam waktu yang ditentukan.");
    }

    @Then("Tabel Laporan Cuti menampilkan data yang seluruh barisnya mengandung {string}")
    public void tabelLaporanCutiMenampilkanDataYangSeluruhBarisnyaMengandung(String expectedText) {
        LaporanCutiPage page = getLaporanCutiPage();
        int rowCount = page.getTableRowsCount();
        Assert.assertTrue(rowCount > 0, "Verifikasi Gagal: Jumlah baris hasil pencarian harus lebih dari 0, aktual: " + rowCount);

        boolean allContain = page.areAllRowsContainingText(expectedText);
        Assert.assertTrue(allContain, "Verifikasi Gagal: Terdapat baris tabel yang tidak mengandung teks yang dicari: " + expectedText);
        System.out.println("Verifikasi Berhasil: Seluruh " + rowCount + " baris data cuti terbukti memuat '" + expectedText + "'");
    }

    // --- DATE FILTER SCENARIOS (FIL-01, FIL-02, FIL-03, FIL-04) ---

    @When("Pengguna memasukkan Start Date {string} dan End Date {string} pada Laporan Kehadiran")
    public void penggunaMemasukkanStartDateDanEndDatePadaLaporanKehadiran(String startDate, String endDate) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        page.enterStartDate(startDate);
        page.enterEndDate(endDate);
    }

    @And("Pengguna mengklik tombol Search filter kehadiran")
    public void penggunaMengklikTombolSearchFilterKehadiran() {
        getLaporanKehadiranPage().clickSearchButton();
    }

    @Then("Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya sesuai tanggal {string}")
    public void tabelLaporanKehadiranMenampilkanDataYangSeluruhBarisnyaSesuaiTanggal(String expectedDate) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        boolean loaded = page.waitForSearchResultsLoaded(expectedDate);
        Assert.assertTrue(loaded, "Verifikasi Gagal: Hasil filter tanggal '" + expectedDate + "' tidak termuat.");

        int rowCount = page.getTableRowsCount();
        Assert.assertTrue(rowCount > 0, "Verifikasi Gagal: Jumlah baris hasil filter tanggal harus lebih dari 0, aktual: " + rowCount);

        boolean allContain = page.areAllRowsContainingText(expectedDate);
        Assert.assertTrue(allContain, "Verifikasi Gagal: Baris tabel kehadiran tidak sesuai dengan tanggal: " + expectedDate);
        System.out.println("Verifikasi Berhasil: Seluruh " + rowCount + " baris kehadiran terbukti sesuai tanggal '" + expectedDate + "'");
    }

    @When("Pengguna memasukkan hanya Start Date {string} pada Laporan Kehadiran")
    public void penggunaMemasukkanHanyaStartDatePadaLaporanKehadiran(String startDate) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        page.enterStartDate(startDate);
    }

    @When("Pengguna memasukkan hanya End Date {string} pada Laporan Kehadiran")
    public void penggunaMemasukkanHanyaEndDatePadaLaporanKehadiran(String endDate) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        page.enterEndDate(endDate);
    }

    // --- COMBINED FILTER SCENARIOS (FIL-05, FIL-06) ---

    @When("Pengguna memasukkan nama {string} dan tanggal {string} sampai {string} pada Laporan Kehadiran")
    public void penggunaMemasukkanNamaDanTanggalSampaiPadaLaporanKehadiran(String name, String startDate, String endDate) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        page.enterSearchName(name);
        page.enterStartDate(startDate);
        page.enterEndDate(endDate);
    }

    @Then("Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya mengandung nama {string} dan tanggal {string}")
    public void tabelLaporanKehadiranMenampilkanDataYangSeluruhBarisnyaMengandungNamaDanTanggal(String name, String date) {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        boolean loaded = page.waitForSearchResultsLoaded(name);
        Assert.assertTrue(loaded, "Verifikasi Gagal: Hasil kombinasi filter untuk nama '" + name + "' tidak termuat.");

        int rowCount = page.getTableRowsCount();
        Assert.assertTrue(rowCount > 0, "Verifikasi Gagal: Jumlah baris kombinasi filter harus lebih dari 0, aktual: " + rowCount);

        boolean nameMatch = page.areAllRowsContainingText(name);
        Assert.assertTrue(nameMatch, "Verifikasi Gagal: Baris tabel kehadiran tidak memuat nama: " + name);

        boolean dateMatch = page.areAllRowsContainingText(date);
        Assert.assertTrue(dateMatch, "Verifikasi Gagal: Baris tabel kehadiran tidak memuat tanggal: " + date);
        System.out.println("Verifikasi Berhasil: Seluruh baris kehadiran terbukti memuat nama '" + name + "' dan tanggal '" + date + "'");
    }

    // --- RESET FILTER SCENARIOS (FIL-07, FIL-08) ---
    // Note: Step @When("Pengguna mengklik tombol Reset pada filter Laporan Kehadiran") 
    // direuse langsung dari LaporanKehadiranSteps.java untuk menghindari duplikasi.

    @Then("Seluruh input filter pencarian dan tanggal kehadiran kembali kosong")
    public void seluruhInputFilterPencarianDanTanggalKehadiranKembaliKosong() {

        LaporanKehadiranPage page = getLaporanKehadiranPage();
        Assert.assertTrue(page.waitForSearchInputEmpty(), "Verifikasi Gagal: Input search tidak kembali kosong setelah Reset.");
        Assert.assertTrue(page.waitForStartDateInputEmpty(), "Verifikasi Gagal: Input Start Date tidak kembali kosong setelah Reset.");
        Assert.assertTrue(page.waitForEndDateInputEmpty(), "Verifikasi Gagal: Input End Date tidak kembali kosong setelah Reset.");
        System.out.println("Verifikasi Berhasil: Seluruh field input search, Start Date, dan End Date kembali kosong.");
    }

    @And("Data tabel laporan kehadiran kembali termuat ke kondisi default")
    public void dataTabelLaporanKehadiranKembaliTermuatKeKondisiDefault() {
        LaporanKehadiranPage page = getLaporanKehadiranPage();
        Assert.assertTrue(page.waitForTableRowsLoaded(), "Verifikasi Gagal: Baris data tabel kehadiran tidak kembali termuat.");
        Assert.assertTrue(page.getTableRowsCount() > 0, "Verifikasi Gagal: Jumlah baris data setelah reset harus lebih dari 0.");
        System.out.println("Verifikasi Berhasil: Data tabel Laporan Kehadiran kembali termuat ke kondisi awal.");
    }
}
