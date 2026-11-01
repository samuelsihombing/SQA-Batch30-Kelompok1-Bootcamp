package com.hadir.stepdefinitions.webadmin;

import com.hadir.drivers.DriverSingleton;
import com.hadir.pages.webadmin.SidebarNavigationPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LaporanNavigationSteps {

    private WebDriver driver;
    private SidebarNavigationPage sidebarNavigationPage;

    private SidebarNavigationPage getSidebarNavigationPage() {
        if (sidebarNavigationPage == null) {
            driver = DriverSingleton.getDriver();
            sidebarNavigationPage = new SidebarNavigationPage(driver);
        }
        return sidebarNavigationPage;
    }

    @When("Pengguna mengklik menu Laporan pada sidebar")
    public void penggunaMengklikMenuLaporanPadaSidebar() {
        getSidebarNavigationPage().clickMenuLaporan();
        System.out.println("Menu Laporan pada sidebar diklik.");
    }

    @Then("Menu Laporan terbuka dan menampilkan submenu laporan")
    public void menuLaporanTerbukaDanMenampilkanSubmenuLaporan() {
        boolean isSemuaDisplayed = getSidebarNavigationPage().waitForSubmenuDisplayed("Semua");
        System.out.println("Submenu 'Semua' ditampilkan: " + isSemuaDisplayed);
        Assert.assertTrue(isSemuaDisplayed, "Verifikasi Gagal: Submenu laporan tidak terbuka setelah klik menu Laporan.");
    }

    @When("Pengguna mengklik submenu {string} pada sidebar")
    public void penggunaMengklikSubmenuPadaSidebar(String submenuName) {
        getSidebarNavigationPage().clickSubmenu(submenuName);
        System.out.println("Submenu '" + submenuName + "' diklik pada sidebar.");
    }

    @Then("Pengguna diarahkan ke halaman dengan URL {string}")
    public void penggunaDiarahkanKeHalamanDenganURL(String expectedPath) {
        boolean urlMatched = getSidebarNavigationPage().waitForUrlContains(expectedPath);
        String currentUrl = DriverSingleton.getDriver().getCurrentUrl();
        System.out.println("URL Aktual saat ini: " + currentUrl + " (Diharapkan memuat: " + expectedPath + ")");
        Assert.assertTrue(urlMatched && currentUrl.contains(expectedPath),
                "Verifikasi Gagal: URL tidak mengarah ke '" + expectedPath + "'. URL Aktual: " + currentUrl);
    }

    @And("Submenu {string} berstatus aktif pada sidebar")
    public void submenuBerstatusAktifPadaSidebar(String submenuName) {
        boolean isActive = getSidebarNavigationPage().waitForSubmenuActive(submenuName);
        System.out.println("Status aktif submenu '" + submenuName + "': " + isActive);
        Assert.assertTrue(isActive, "Verifikasi Gagal: Submenu '" + submenuName + "' tidak berstatus aktif pada sidebar.");
    }

    @And("Submenu {string} tidak berstatus aktif pada sidebar")
    public void submenuTidakBerstatusAktifPadaSidebar(String submenuName) {
        boolean isInactive = getSidebarNavigationPage().waitForSubmenuInactive(submenuName);
        System.out.println("Status tidak aktif submenu '" + submenuName + "': " + isInactive);
        Assert.assertTrue(isInactive, "Verifikasi Gagal: Submenu '" + submenuName + "' masih berstatus aktif padahal sudah berpindah.");
    }

    @When("Pengguna menavigasi browser Back")
    public void penggunaMenavigasiBrowserBack() {
        getSidebarNavigationPage().navigateBack();
        System.out.println("Navigasi browser Back dijalankan.");
    }

    @When("Pengguna menavigasi browser Forward")
    public void penggunaMenavigasiBrowserForward() {
        getSidebarNavigationPage().navigateForward();
        System.out.println("Navigasi browser Forward dijalankan.");
    }

    @When("Pengguna melakukan refresh halaman")
    public void penggunaMelakukanRefreshHalaman() {
        getSidebarNavigationPage().refreshPage();
        System.out.println("Refresh browser dijalankan.");
    }
}
