@employee @auth
Feature: Hadir Employee - Autentikasi Pengguna
  Sebagai karyawan (Employee)
  Saya ingin login ke aplikasi Hadir Web
  Agar saya dapat mengakses fitur absensi dan pengajuan

  @employee @EMP-AUTH-01 @positive
  Scenario: Login Hadir Employee Berhasil Menggunakan Kredensial Valid dari ConfigReader
    Given Pengguna membuka halaman login Hadir Employee
    When Pengguna memasukkan email dan password valid dari konfigurasi
    And Pengguna mengklik tombol Masuk
    Then Pengguna berhasil dialihkan ke halaman Dashboard Employee
    And Dashboard menampilkan salam pengguna "Hai, Hadir SQA"

  @employee @EMP-AUTH-02 @security @negative
  Scenario: Proteksi Akses Route Protected Hadir Employee Tanpa Sesi Login
    Given Pengguna belum melakukan login ke Hadir Employee
    When Pengguna mencoba mengakses langsung URL route protected "/apps/absent"
    Then Sistem mengarahkan kembali pengguna ke halaman login Hadir Employee

  @employee @EMP-AUTH-03 @negative
  Scenario: Login Gagal Menggunakan Password Salah
    Given Pengguna membuka halaman login Hadir Employee
    When Pengguna memasukkan email valid dan password salah "SalahPassword123!"
    And Pengguna mengklik tombol Masuk
    Then Sistem menolak login dan pengguna tetap berada di halaman login Hadir Employee

  @employee @EMP-AUTH-04 @negative @boundary
  Scenario: Validasi Format Input Email Tanpa Karakter '@'
    Given Pengguna membuka halaman login Hadir Employee
    When Pengguna memasukkan format email tidak valid "userhadirsqatesting"
    And Pengguna memasukkan password "SQA@Hadir12345"
    And Pengguna mengklik tombol Masuk
    Then Field email menandai format input tidak valid sesuai spesifikasi browser

  @employee @EMP-AUTH-05 @negative
  Scenario: Validasi Pengosongan Field Email dan Password Saat Login
    Given Pengguna membuka halaman login Hadir Employee
    When Pengguna mengosongkan field email dan password
    And Pengguna mengklik tombol Masuk
    Then Form login tidak terkirim dan pengguna tetap berada di halaman login Hadir Employee
