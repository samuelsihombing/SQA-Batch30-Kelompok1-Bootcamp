package hadir.tests;
import hadir.Pages.DashboardPage;
import hadir.base.BaseTest;
import hadir.Pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage LoginPage;

    @BeforeMethod
    public void openLoginPage() {
        LoginPage = new LoginPage(driver);
        LoginPage.open(config.getProperty("admin.url"));
    }

    @Test(description = "TC-LOGIN-01: Login dengan email & password valid")
    public void loginValid() {
        LoginPage.login(config.getProperty("admin.email"),
                        config.getProperty("admin.password"));
        Assert.assertTrue(LoginPage.isLoginSuccess(),
                "Setelah login valid, harus pindah dari halaman login");
    }

    @Test(description = "TC-LOGIN-02: Email tidak terdaftar")
    public void loginEmailSalah() {
        LoginPage.login("tidakada@hadir.com", "Apapun123");
        Assert.assertTrue(LoginPage.isTextDisplayed("Akun tidak ditemukan"),
                "Pesan 'Akun tidak ditemukan' harus tampil");
    }

    @Test(description = "TC-LOGIN-03: Password salah")
    public void loginPasswordSalah() {
        LoginPage.login(config.getProperty("admin.email"), "PasswordSalah999");
        Assert.assertTrue(LoginPage.isTextDisplayed("Email atau password salah"),
                "Pesan 'Email atau password salah' harus tampil");
    }

    @Test(description = "TC-LOGIN-04: Email dan password kosong")
    public void loginKosong() {
        LoginPage.login("", "");
        Assert.assertFalse(LoginPage.isLoginSuccess(),
                "Login dengan data kosong tidak boleh berhasil");
    }
    @Test(description = "TC-LOGOUT-01: Logout setelah login berhasil")
public void logoutBerhasil() {
    LoginPage.login(config.getProperty("admin.email"),
                    config.getProperty("admin.password"));
    Assert.assertTrue(LoginPage.isLoginSuccess(),
            "Prasyarat: login harus berhasil dulu");

    DashboardPage dashboard = new DashboardPage(driver);
    dashboard.logout();

    Assert.assertTrue(dashboard.isLoggedOut(),
            "Setelah logout, harus kembali ke halaman login");
}
}