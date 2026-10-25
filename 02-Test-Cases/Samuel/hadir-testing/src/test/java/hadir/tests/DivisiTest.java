package hadir.tests;

import hadir.base.BaseTest;
import hadir.Pages.DivisiPage;
import hadir.Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DivisiTest extends BaseTest {

    private final List<String> dataUji = new ArrayList<>();

    private String namaUnik() {
        return "QA Selenium " + new SimpleDateFormat("MMdd-HHmmss").format(new Date());
    }

    @BeforeMethod
    public void loginDulu() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open(config.getProperty("admin.url"));
        loginPage.login(config.getProperty("admin.email"),
                        config.getProperty("admin.password"));
        Assert.assertTrue(loginPage.isLoginSuccess(),
                "Prasyarat: login harus berhasil dulu");
    }

    @Override
    protected void bersihkan() {
        for (String nama : dataUji) {
            try {
                DivisiPage divisi = new DivisiPage(driver);
                divisi.bukaLangsung(config.getProperty("divisi.url"));
                divisi.cariDivisi(nama);
                if (divisi.isDivisiDisplayed(nama)) {
                    divisi.hapusDivisi(nama);
                    System.out.println("Data uji dihapus: " + nama);
                }
            } catch (Exception e) {
                System.out.println("PERLU HAPUS MANUAL: " + nama + " (" + e.getMessage() + ")");
            }
        }
        dataUji.clear();
    }

    @Test(description = "TC-DIV-01: Halaman Divisi terbuka")
    public void halamanDivisiTerbuka() {
        DivisiPage divisi = new DivisiPage(driver);
        divisi.bukaMenuDivisi();
        System.out.println("URL halaman Divisi: " + driver.getCurrentUrl());
        Assert.assertTrue(divisi.isTambahkanDisplayed(),
                "Tombol Tambahkan harus terlihat di halaman Divisi");
    }

    @Test(description = "TC-DIV-02: Tambah divisi baru")
    public void tambahDivisi() {
        DivisiPage divisi = new DivisiPage(driver);
        divisi.bukaMenuDivisi();

        String nama = namaUnik();
        dataUji.add(nama);
        System.out.println("Nama divisi uji: " + nama);

        divisi.tambahDivisi(nama);
        divisi.cariDivisi(nama);
        Assert.assertTrue(divisi.isDivisiDisplayed(nama),
                "Divisi '" + nama + "' harus muncul setelah ditambahkan");
    }

    @Test(description = "TC-DIV-03: Edit nama divisi")
    public void editDivisi() {
        DivisiPage divisi = new DivisiPage(driver);
        divisi.bukaMenuDivisi();

        String lama = namaUnik();
        String baru = lama + " Edit";
        dataUji.add(lama);
        dataUji.add(baru);

        divisi.tambahDivisi(lama);
        divisi.cariDivisi(lama);
        Assert.assertTrue(divisi.isDivisiDisplayed(lama),
                "Prasyarat: divisi awal harus ada");

        divisi.editDivisi(lama, baru);

        divisi.cariDivisi(baru);
        Assert.assertTrue(divisi.isDivisiDisplayed(baru),
                "Nama baru '" + baru + "' harus muncul setelah diedit");
        Assert.assertTrue(divisi.isDivisiAbsent(lama),
                "Nama lama '" + lama + "' tidak boleh ada lagi");
    }

    @Test(description = "TC-DIV-04: Hapus divisi")
    public void hapusDivisi() {
        DivisiPage divisi = new DivisiPage(driver);
        divisi.bukaMenuDivisi();

        String nama = namaUnik();
        dataUji.add(nama);

        divisi.tambahDivisi(nama);
        divisi.cariDivisi(nama);
        Assert.assertTrue(divisi.isDivisiDisplayed(nama),
                "Prasyarat: divisi harus ada sebelum dihapus");

        divisi.hapusDivisi(nama);

        divisi.cariDivisi(nama);
        Assert.assertTrue(divisi.isDivisiAbsent(nama),
                "Divisi '" + nama + "' harus hilang setelah dihapus");
    }
}