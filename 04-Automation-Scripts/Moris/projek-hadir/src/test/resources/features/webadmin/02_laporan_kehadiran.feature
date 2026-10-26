Feature: Modul Laporan Kehadiran Web Admin

  @LaporanKehadiran @Security
  Scenario: LH-01 Akses halaman Laporan Kehadiran tanpa login dialihkan ke halaman login
    Given Pengguna membuka browser
    When Pengguna mengakses halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Pengguna dialihkan ke halaman login "/authentication/login"
    And Judul halaman adalah "HADIR"
    And Form input email dan password login ditampilkan

  @LaporanKehadiran @Functional
  Scenario: LH-02 Verifikasi ketersediaan elemen utama halaman Laporan Kehadiran
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    And Tabel laporan kehadiran dan 10 header kolom ditampilkan
    And Kontrol pagination laporan kehadiran ditampilkan
    And Seluruh komponen filter dan tombol aksi laporan kehadiran ditampilkan

  @LaporanKehadiran @Functional
  Scenario: LH-03 Pengujian interaksi pencarian nama karyawan fiktif dan reset filter
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan nama "KaryawanFiktifXYZ999" ke kolom pencarian dan klik Search
    Then Halaman Laporan Kehadiran tetap stabil
    When Pengguna mengklik tombol Reset pada filter Laporan Kehadiran
    Then Kolom input pencarian nama menjadi kosong kembali
