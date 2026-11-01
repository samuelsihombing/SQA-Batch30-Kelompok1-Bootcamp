@employee @navigation
Feature: Hadir Employee - Navigasi Menu Dashboard
  Sebagai karyawan (Employee)
  Saya ingin menavigasi menu pada dashboard
  Agar saya dapat mengakses halaman pengajuan Koreksi Absen, Izin, dan Lembur

  @employee @EMP-NAV-01 @positive
  Scenario: Akses Modul Koreksi Absen Melalui Menu Grid Dashboard
    Given Pengguna berhasil login dan berada di Dashboard Employee
    When Pengguna mengklik menu "Koreksi Absen" pada grid menu
    Then Pengguna diarahkan ke halaman Koreksi Absen dengan route "/apps/absent/correction"
    And Halaman menampilkan judul "Halaman Koreksi" dan "List Koreksi"

  @employee @EMP-NAV-02 @positive
  Scenario: Akses Modul Izin Melalui Menu Grid Dashboard
    Given Pengguna berhasil login dan berada di Dashboard Employee
    When Pengguna mengklik menu "Izin" pada grid menu
    Then Pengguna diarahkan ke halaman Izin dengan route "/apps/absent/permit"
    And Halaman menampilkan judul "Halaman Izin" dan tab default "Terlambat" berstatus aktif

  @employee @EMP-NAV-03 @positive
  Scenario: Akses Modul Lembur Melalui Menu Grid Dashboard
    Given Pengguna berhasil login dan berada di Dashboard Employee
    When Pengguna mengklik menu "Lembur" pada grid menu
    Then Pengguna diarahkan ke halaman Lembur dengan route "/apps/absent/overtime-client"
    And Halaman menampilkan judul "Halaman Lembur" dan "List Overtime"

  @employee @EMP-NAV-04 @positive
  Scenario: Kembali ke Dashboard Menggunakan Tombol Kembali pada Header Modul
    Given Pengguna berada di halaman Koreksi Absen Employee
    When Pengguna mengklik tombol Kembali pada header
    Then Pengguna berhasil kembali ke halaman Dashboard Employee

  @employee @EMP-DASH-01 @positive @ui
  Scenario: Verifikasi Informasi Profil Karyawan dan Widget Periode Kalender di Dashboard
    Given Pengguna berhasil login dan berada di Dashboard Employee
    Then Header dashboard menampilkan nama profil pengguna "Hai, Hadir SQA" dan "Have a nice day !"
    And Dashboard menampilkan widget periode kehadiran "Kehadiranmu"

  @employee @EMP-DASH-02 @positive @ui
  Scenario: Verifikasi Tampilan dan Kelengkapan Summary Card Status Kehadiran
    Given Pengguna berhasil login dan berada di Dashboard Employee
    Then Dashboard menampilkan ringkasan kartu status kehadiran

  @employee @EMP-DASH-03 @positive @ui
  Scenario: Verifikasi Keberadaan dan Status Tombol Floating "Absen Masuk"
    Given Pengguna berhasil login dan berada di Dashboard Employee
    Then Tombol aksi utama floating "Absen Masuk" tampil di layar dan berstatus aktif

  @employee @EMP-NAV-05 @positive @navigation
  Scenario: Navigasi Kembali ke Dashboard Menggunakan Tombol Header pada Modul Lembur
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Lembur
    When Pengguna mengklik tombol Kembali pada header modul
    Then Pengguna berhasil kembali ke halaman Dashboard Employee

  @employee @EMP-NAV-06 @positive @navigation
  Scenario: Navigasi Kembali ke Dashboard Menggunakan Tombol Header pada Modul Izin
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna mengklik tombol Kembali pada header modul
    Then Pengguna berhasil kembali ke halaman Dashboard Employee
