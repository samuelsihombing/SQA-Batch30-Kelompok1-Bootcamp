package hadir.tests;

import hadir.base.BaseTest;
import hadir.Pages.DashboardMenuPage;
import hadir.Pages.LoginPage;
import hadir.Pages.PendingPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DashboardTest extends BaseTest {

    private static final String[] JUDUL_KARTU = {
            "Total semua karyawan",
            "Total absen hari ini",
            "Karyawan yang sedang WFH hari ini",
            "Karyawan yang sedang cuti hari ini",
            "Karyawan yang sedang sakit hari ini",
            "Karyawan yang sedang Izin hari ini"
    };

    @BeforeMethod
    public void loginDulu() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(config.getProperty("admin.url"));
        loginPage.login(config.getProperty("admin.email"),
                        config.getProperty("admin.password"));
        Assert.assertTrue(loginPage.isLoginSuccess(),
                "Prasyarat: login harus berhasil dulu");
    }

    @Test(description = "TC-DASH-01: Setelah login, halaman Pending terbuka")
    public void pendingTerbukaSetelahLogin() {
        PendingPage pending = new PendingPage(driver);
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboards/pending"));
        Assert.assertTrue(pending.isValidatorDisplayed(1), "Validator 1 harus tampil");
        Assert.assertTrue(pending.isValidatorDisplayed(2), "Validator 2 harus tampil");
        Assert.assertTrue(pending.isValidatorDisplayed(3), "Validator 3 harus tampil");
    }

    @Test(description = "TC-DASH-02: Enam kartu di menu Dashboard tampil")
    public void enamKartuTampil() {
        DashboardMenuPage dashboard = new DashboardMenuPage(driver);
        dashboard.open(config.getProperty("dashboard.url"));
        for (String judul : JUDUL_KARTU) {
            Assert.assertTrue(dashboard.isCardDisplayed(judul),
                    "Kartu tidak tampil: " + judul);
        }
    }

    @Test(description = "TC-DASH-03: Nilai kartu berupa angka, total karyawan > 0")
    public void nilaiKartuBerupaAngka() {
        DashboardMenuPage dashboard = new DashboardMenuPage(driver);
        dashboard.open(config.getProperty("dashboard.url"));
        for (String judul : JUDUL_KARTU) {
            String nilai = dashboard.getCardValue(judul);
            Assert.assertTrue(nilai.matches("\\d+"),
                    "Nilai kartu '" + judul + "' bukan angka: " + nilai);
        }
        int total = Integer.parseInt(dashboard.getCardValue("Total semua karyawan"));
        Assert.assertTrue(total > 0, "Total karyawan harus lebih dari 0");
    }

    @Test(description = "TC-DASH-04: Klik Search di Pending, halaman tetap normal")
    public void searchPending() {
        PendingPage pending = new PendingPage(driver);
        pending.clickSearch();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboards/pending"));
        Assert.assertTrue(pending.isValidatorDisplayed(1), "Validator 1 harus tetap tampil");
    }

    @Test(description = "TC-DASH-05: Klik Reset di Pending, halaman tetap normal")
    public void resetPending() {
        PendingPage pending = new PendingPage(driver);
        pending.clickReset();
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboards/pending"));
        Assert.assertTrue(pending.isValidatorDisplayed(1), "Validator 1 harus tetap tampil");
    }
    @Test (description = "TC-DASH-06: Isi Start/End Date lalu Search")
   public void filterTanggal() {
    PendingPage pending = new PendingPage(driver);
    pending.setStartDate("2026-10-05");
    pending.setEndDate("2026-10-20");
    pending.clickSearch();

    Assert.assertEquals(pending.getStartDate(), "2026-10-05",
            "Start Date harus tetap sesuai input");
    Assert.assertEquals(pending.getEndDate(), "2026-10-20",
            "End Date harus tetap sesuai input");
    Assert.assertTrue(pending.isValidatorDisplayed(1),
            "Halaman harus tetap normal setelah Search");
}
@Test(description = "TC-DASH-07: Reset setelah mengubah tanggal")
public void resetSetelahUbahTanggal() {
    PendingPage pending = new PendingPage(driver);
    pending.setStartDate("2026-10-05");
    pending.clickReset();

    String sesudahReset = pending.getStartDate();
    System.out.println("Start Date setelah Reset: '" + sesudahReset + "'");
    Assert.assertNotEquals(sesudahReset, "2026-10-05",
            "Reset harus mengubah Start Date yang sudah diisi");
}

@Test(description = "TC-DASH-08: Buka dialog filter lalu Batal")
public void dialogFilterBukaTutup() {
    PendingPage pending = new PendingPage(driver);
    pending.openFilter();
    Assert.assertTrue(pending.isFilterDialogDisplayed(),
            "Dialog 'Filter By' harus tampil setelah tombol filter diklik");

    pending.clickBatal();
    Assert.assertTrue(pending.isFilterDialogClosed(),
            "Dialog harus tertutup setelah klik Batal");
}
@Test(description = "TC-DASH-09: Filter Unit lalu Terapkan")
public void filterUnit() {
    PendingPage pending = new PendingPage(driver);
    pending.openFilter();
    Assert.assertTrue(pending.isFilterDialogDisplayed(),
            "Prasyarat: dialog filter harus terbuka");

    pending.pilihUnit("Auto Test Unit No Tetap");
    Assert.assertEquals(pending.getUnitValue(), "Auto Test Unit No Tetap",
            "Kolom Unit harus berisi unit yang dipilih");

    pending.clickTerapkan();
    Assert.assertTrue(pending.isFilterDialogClosed(),
            "Dialog harus tertutup setelah Terapkan");
    Assert.assertTrue(pending.isValidatorDisplayed(1),
            "Halaman Pending harus tetap normal setelah filter");
}
}