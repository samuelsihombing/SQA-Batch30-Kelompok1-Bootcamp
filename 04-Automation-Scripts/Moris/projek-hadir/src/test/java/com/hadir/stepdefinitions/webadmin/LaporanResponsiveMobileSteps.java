package com.hadir.stepdefinitions.webadmin;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.webadmin.MobileNavigationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LaporanResponsiveMobileSteps {
    private WebDriver driver;
    private MobileNavigationPage mobileNavPage;
    private String initialPaginationText = "";

    public LaporanResponsiveMobileSteps() {
        this.driver = DriverSingleton.getDriver();
        this.mobileNavPage = new MobileNavigationPage(driver);
    }

    // --- Pengaturan Viewport CDP ---
    @When("Pengguna mengatur ukuran viewport desktop {string} x {string}")
    public void penggunaMengaturUkuranViewportDesktop(String width, String height) {
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);
        mobileNavPage.setViewport(w, h);
        Assert.assertEquals(mobileNavPage.getInnerWidth(), w, "Verifikasi Gagal: Lebar viewport desktop tidak sesuai.");
        System.out.println("Viewport Desktop aktif: " + w + "x" + h);
    }

    @When("Pengguna mengatur ukuran viewport tablet {string} x {string}")
    public void penggunaMengaturUkuranViewportTablet(String width, String height) {
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);
        mobileNavPage.setViewport(w, h);
        Assert.assertEquals(mobileNavPage.getInnerWidth(), w, "Verifikasi Gagal: Lebar viewport tablet tidak sesuai.");
        System.out.println("Viewport Tablet aktif: " + w + "x" + h);
    }

    @When("Pengguna mengatur ukuran viewport mobile {string} x {string}")
    public void penggunaMengaturUkuranViewportMobile(String width, String height) {
        int w = Integer.parseInt(width);
        int h = Integer.parseInt(height);
        mobileNavPage.setViewport(w, h);
        Assert.assertEquals(mobileNavPage.getInnerWidth(), w, "Verifikasi Gagal: Lebar viewport mobile tidak sesuai.");
        System.out.println("Viewport Mobile aktif: " + w + "x" + h);
    }

    // --- Responsive Layout Assertions ---
    @And("Sidebar navigasi desktop berstatus docked dan ditampilkan")
    public void sidebarNavigasiDesktopBerstatusDockedDanDitampilkan() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isDocked = wait.until(d -> mobileNavPage.isSidebarDockedVisible());
        Assert.assertTrue(isDocked, "Verifikasi Gagal: Sidebar docked desktop tidak ditampilkan.");
        System.out.println("Verifikasi Berhasil: Sidebar docked desktop terlihat.");
    }

    @And("Form filter pencarian tertata sejajar secara horizontal")
    public void formFilterPencarianTertataSejajarSecaraHorizontal() {
        boolean isStacked = mobileNavPage.isFilterStackedVertically();
        Assert.assertFalse(isStacked, "Verifikasi Gagal: Filter tidak sejajar horizontal pada desktop.");
        System.out.println("Verifikasi Berhasil: Filter tertata sejajar secara horizontal.");
    }

    @And("Halaman tidak mengalami broken horizontal overflow pada level window")
    public void halamanTidakMengalamiBrokenHorizontalOverflowPadaLevelWindow() {
        boolean hasOverflow = mobileNavPage.hasPageHorizontalOverflow();
        Assert.assertFalse(hasOverflow, "Verifikasi Gagal: Terjadi horizontal overflow pada halaman.");
        System.out.println("Verifikasi Berhasil: Halaman tidak memiliki horizontal overflow.");
    }

    @And("Elemen filter pencarian tertata secara responsif dan bertumpuk vertikal")
    public void elemenFilterPencarianTertataSecaraResponsifDanBertumpukVertikal() {
        boolean isStacked = mobileNavPage.isFilterStackedVertically();
        Assert.assertTrue(isStacked, "Verifikasi Gagal: Filter tidak bertumpuk vertikal pada mobile.");
        System.out.println("Verifikasi Berhasil: Filter tertata responsif bertumpuk vertikal.");
    }

    @And("Seluruh komponen filter pencarian tanggal dan tombol aksi ditampilkan di mobile")
    public void seluruhKomponenFilterPencarianTanggalDanTombolAksiDitampilkanDiMobile() {
        Assert.assertTrue(mobileNavPage.areAllFilterControlsVisible(),
                "Verifikasi Gagal: Ada komponen filter yang tidak ditampilkan di mobile.");
        System.out.println("Verifikasi Berhasil: Seluruh 5 kontrol filter ditampilkan di mobile.");
    }

    // --- Mobile Navigation & Drawer Steps ---
    @And("Sidebar desktop disembunyikan dan tombol hamburger navigasi ditampilkan di navbar")
    public void sidebarDesktopDisembunyikanDanTombolHamburgerNavigasiDitampilkanDiNavbar() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean isHamburger = wait.until(d -> mobileNavPage.isHamburgerButtonDisplayed());
        boolean isDocked = mobileNavPage.isSidebarDockedVisible();

        Assert.assertFalse(isDocked,
                "Verifikasi Gagal: Sidebar desktop docked masih terlihat di mobile.");
        Assert.assertTrue(isHamburger,
                "Verifikasi Gagal: Tombol hamburger navigasi tidak ditampilkan di navbar.");
        System.out.println("Verifikasi Berhasil: Sidebar desktop disembunyikan dan hamburger menu aktif.");
    }

    @When("Pengguna mengklik tombol hamburger navigasi di header")
    public void penggunaMengklikTombolHamburgerNavigasiDiHeader() {
        mobileNavPage.clickHamburgerButton();
        System.out.println("Tombol hamburger diklik.");
    }

    @Then("Drawer navigasi modal berhasil terbuka dan menampilkan menu aplikasi")
    public void drawerNavigasiModalBerhasilTerbukaDanMenampilkanMenuAplikasi() {
        Assert.assertTrue(mobileNavPage.isDrawerOpen(),
                "Verifikasi Gagal: Drawer modal navigasi tidak terbuka.");
        System.out.println("Verifikasi Berhasil: Drawer modal navigasi berhasil terbuka.");
    }

    @And("Pengguna mengklik backdrop di luar drawer navigasi")
    public void penggunaMengklikBackdropDiLuarDrawerNavigasi() {
        mobileNavPage.closeDrawerViaBackdrop();
        System.out.println("Backdrop drawer diklik.");
    }

    @Then("Drawer navigasi modal berhasil ditutup dari layar")
    public void drawerNavigasiModalBerhasilDitutupDariLayar() {
        Assert.assertTrue(mobileNavPage.isDrawerClosed(),
                "Verifikasi Gagal: Drawer navigasi tidak tertutup setelah klik backdrop.");
        System.out.println("Verifikasi Berhasil: Drawer navigasi berhasil ditutup.");
    }

    @And("Pengguna mengklik submenu {string} di dalam drawer")
    public void penggunaMengklikSubmenuDiDalamDrawer(String submenuName) {
        mobileNavPage.clickSubmenuInDrawer(submenuName);
        System.out.println("Submenu '" + submenuName + "' diklik di dalam drawer.");
    }

    @Then("Halaman diarahkan ke route {string} secara tepat")
    public void halamanDiarahkanKeRouteSecaraTepat(String expectedRoute) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean urlReached = wait.until(ExpectedConditions.urlContains(expectedRoute));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(urlReached && currentUrl.contains(expectedRoute),
                "Verifikasi Gagal: URL tidak sesuai. Harusnya mengandung: " + expectedRoute + ", Aktual: " + currentUrl);
        System.out.println("Verifikasi Berhasil: Navigasi ke route " + expectedRoute + " terkonfirmasi.");
    }

    @And("Submenu {string} dengan path {string} berstatus aktif secara semantik di drawer")
    public void submenuDenganPathBerstatusAktifSecaraSemantikDiDrawer(String submenuName, String expectedPath) {
        boolean isActive = mobileNavPage.isSubmenuActiveInDrawer(submenuName, expectedPath);
        Assert.assertTrue(isActive,
                "Verifikasi Gagal: Submenu '" + submenuName + "' tidak berstatus aktif di drawer.");
        System.out.println("Verifikasi Berhasil: Submenu '" + submenuName + "' berstatus aktif.");
    }

    // --- Mobile Functional Steps ---
    @When("Pengguna memasukkan nama pencarian {string} di mobile dan klik tombol Search")
    public void penggunaMemasukkanNamaPencarianDiMobileDanKlikTombolSearch(String keyword) {
        mobileNavPage.enterSearchText(keyword);
        Assert.assertEquals(mobileNavPage.getSearchInputValue(), keyword,
                "Verifikasi Gagal: Input search tidak sesuai kata kunci.");
        mobileNavPage.clickSearchButton();
        System.out.println("Search '" + keyword + "' diklik pada mobile.");
    }

    @Then("Tabel laporan di mobile menampilkan hasil pencarian yang sesuai dengan kata kunci")
    public void tabelLaporanDiMobileMenampilkanHasilPencarianYangSesuaiDenganKataKunci() {
        String pag = mobileNavPage.getPaginationText();
        Assert.assertTrue(!pag.isEmpty() && !pag.contains("0-0 of 0"),
                "Verifikasi Gagal: Hasil pencarian kosong atau pagination tidak valid. Teks: " + pag);
        System.out.println("Verifikasi Berhasil: Hasil pencarian mobile tampil dengan pagination: " + pag);
    }

    @And("Pengguna mengklik tombol Reset pada filter di mobile")
    public void penggunaMengklikTombolResetPadaFilterDiMobile() {
        mobileNavPage.clickResetButton();
        System.out.println("Tombol Reset filter diklik pada mobile.");
    }

    @Then("Kolom pencarian di mobile kembali kosong dan data awal kembali dimuat")
    public void kolomPencarianDiMobileKembaliKosongDanDataAwalKembaliDimuat() {
        Assert.assertEquals(mobileNavPage.getSearchInputValue(), "",
                "Verifikasi Gagal: Input pencarian tidak kembali kosong setelah reset.");
        Assert.assertTrue(mobileNavPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Data awal tidak kembali dimuat.");
        System.out.println("Verifikasi Berhasil: Input reset menjadi kosong dan data kembali termuat.");
    }

    @When("Pengguna membuka kalender Date Picker di mobile")
    public void penggunaMembukaKalenderDatePickerDiMobile() {
        mobileNavPage.openDatePickerOnMobile();
        System.out.println("Date Picker popover dibuka pada mobile.");
    }

    @Then("Popover Date Picker ditampilkan proporsional di dalam batas viewport mobile")
    public void popoverDatePickerDitampilkanProporsionalDiDalamBatasViewportMobile() {
        Assert.assertTrue(mobileNavPage.isDatePickerPopoverWithinViewport(),
                "Verifikasi Gagal: Date Picker meluap keluar batas layar mobile.");
        System.out.println("Verifikasi Berhasil: Date Picker popover muat proporsional di layar mobile.");
    }

    @When("Pengguna memilih rentang tanggal awal dan akhir di kalender mobile")
    public void penggunaMemilihRentangTanggalAwalDanAkhirDiKalenderMobile() {
        mobileNavPage.selectDateRangeOnMobile(1, 3);
        System.out.println("Rentang tanggal dipilih pada kalender mobile.");
    }

    @Then("Field Start Date dan End Date di mobile berhasil terisi dengan tanggal yang dipilih")
    public void fieldStartDateDanEndDateDiMobileBerhasilTerisiDenganTanggalYangDipilih() {
        String start = mobileNavPage.getStartDateValue();
        String end = mobileNavPage.getEndDateValue();
        Assert.assertFalse(start.isEmpty(), "Verifikasi Gagal: Start Date belum terisi.");
        Assert.assertFalse(end.isEmpty(), "Verifikasi Gagal: End Date belum terisi.");
        System.out.println("Verifikasi Berhasil: Tanggal terisi -> Start: '" + start + "', End: '" + end + "'");
    }

    @And("Container tabel laporan mendukung penggeseran horizontal pada mobile")
    public void containerTabelLaporanMendukungPenggeseranHorizontalPadaMobile() {
        Assert.assertTrue(mobileNavPage.canTableScrollHorizontally(),
                "Verifikasi Gagal: Container tabel tidak mendukung scroll horizontal.");
        System.out.println("Verifikasi Berhasil: Container tabel memiliki scrollWidth > clientWidth.");
    }

    @When("Pengguna menggeser tabel laporan secara horizontal sejauh {int} piksel di mobile")
    public void penggunaMenggeserTabelLaporanSecaraHorizontalSejauhPikselDiMobile(int offset) {
        boolean scrolled = mobileNavPage.scrollTableHorizontally(offset);
        Assert.assertTrue(scrolled, "Verifikasi Gagal: Penggeseran horizontal tabel gagal.");
        System.out.println("Tabel digeser sejauh " + offset + "px.");
    }

    @Then("Tabel laporan berhasil digeser secara horizontal tanpa merusak layout luar")
    public void tabelLaporanBerhasilDigeserSecaraHorizontalTanpaMerusakLayoutLuar() {
        Assert.assertFalse(mobileNavPage.hasPageHorizontalOverflow(),
                "Verifikasi Gagal: Scroll tabel menyebabkan broken horizontal overflow pada halaman.");
        System.out.println("Verifikasi Berhasil: Geser tabel lancar tanpa horizontal overflow di halaman.");
    }

    @And("Kontrol pagination ditampilkan pada bagian bawah tabel di mobile")
    public void kontrolPaginationDitampilkanPadaBagianBawahTabelDiMobile() {
        Assert.assertTrue(mobileNavPage.isPaginationDisplayed(),
                "Verifikasi Gagal: Pagination tidak ditampilkan pada mobile.");
        initialPaginationText = mobileNavPage.getPaginationText();
        System.out.println("Pagination awal di mobile: " + initialPaginationText);
    }

    @When("Pengguna berpindah ke halaman berikutnya dan kembali ke halaman sebelumnya pada pagination mobile")
    public void penggunaBerpindahKeHalamanBerikutnyaDanKembaliKeHalamanSebelumnyaPadaPaginationMobile() {
        if (mobileNavPage.isNextPageButtonEnabled()) {
            mobileNavPage.clickNextPage();
            String page2Text = mobileNavPage.getPaginationText();
            System.out.println("Pagination setelah Next: " + page2Text);
            Assert.assertNotEquals(page2Text, initialPaginationText,
                    "Verifikasi Gagal: Halaman pagination tidak berpindah setelah klik Next.");

            mobileNavPage.clickPreviousPage();
            String backPageText = mobileNavPage.getPaginationText();
            System.out.println("Pagination setelah Prev kembali: " + backPageText);
            Assert.assertEquals(backPageText, initialPaginationText,
                    "Verifikasi Gagal: Halaman pagination tidak kembali setelah klik Prev.");
        } else {
            System.out.println("Next page button disabled karena data hanya 1 halaman.");
        }
    }

    @Then("Teks rentang baris pagination di mobile diperbarui sesuai halaman yang aktif")
    public void teksRentangBarisPaginationDiMobileDiperbaruiSesuaiHalamanYangAktif() {
        String currentPag = mobileNavPage.getPaginationText();
        Assert.assertFalse(currentPag.isEmpty(), "Verifikasi Gagal: Teks pagination kosong.");
        System.out.println("Verifikasi Berhasil: Teks pagination aktif terverifikasi: " + currentPag);
    }
}
