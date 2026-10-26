Feature: Modul Laporan Izin Web Admin

  @LaporanIzin @Security
  Scenario: LIZ-01 Akses halaman Laporan Izin tanpa login dialihkan ke halaman login
    Given Pengguna membuka browser
    When Pengguna mengakses halaman Laporan Izin di URL "/laporan/izin"
    Then Pengguna dialihkan ke halaman login "/authentication/login"
    And Judul halaman adalah "HADIR"
    And Form input email dan password login ditampilkan

  @LaporanIzin @Functional
  Scenario: LIZ-02 Verifikasi ketersediaan elemen utama halaman Laporan Izin
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Izin di URL "/laporan/izin"
    Then Halaman Laporan Izin berhasil dimuat
    And Tabel laporan izin dan 7 header kolom ditampilkan
    And Kontrol pagination laporan izin ditampilkan
    And Seluruh komponen filter dan tombol aksi laporan izin ditampilkan

  @LaporanIzin @Functional
  Scenario: LIZ-03 Pengujian interaksi pencarian nama karyawan fiktif dan reset filter izin
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Izin di URL "/laporan/izin"
    Then Halaman Laporan Izin berhasil dimuat
    When Pengguna memasukkan nama "KaryawanFiktifXYZ999" ke kolom pencarian izin dan klik Search
    Then Halaman Laporan Izin tetap stabil
    When Pengguna mengklik tombol Reset pada filter Laporan Izin
    Then Kolom input pencarian nama izin menjadi kosong kembali
