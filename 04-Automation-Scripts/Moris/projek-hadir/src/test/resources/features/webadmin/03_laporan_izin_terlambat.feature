Feature: Modul Laporan Izin Terlambat Web Admin

  @LaporanIzinTerlambat @Security
  Scenario: LIT-01 Akses halaman Laporan Izin Terlambat tanpa login dialihkan ke halaman login
    Given Pengguna membuka browser
    When Pengguna mengakses halaman Laporan Izin Terlambat di URL "/laporan/izin-terlambat"
    Then Pengguna dialihkan ke halaman login "/authentication/login"
    And Judul halaman adalah "HADIR"
    And Form input email dan password login ditampilkan

  @LaporanIzinTerlambat @Functional
  Scenario: LIT-02 Verifikasi ketersediaan elemen utama halaman Laporan Izin Terlambat
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Izin Terlambat di URL "/laporan/izin-terlambat"
    Then Halaman Laporan Izin Terlambat berhasil dimuat
    And Tabel laporan izin terlambat dan 7 header kolom ditampilkan
    And Kontrol pagination laporan izin terlambat ditampilkan
    And Seluruh komponen filter dan tombol aksi laporan izin terlambat ditampilkan

  @LaporanIzinTerlambat @Functional
  Scenario: LIT-03 Pengujian interaksi pencarian nama karyawan fiktif dan reset filter izin terlambat
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Izin Terlambat di URL "/laporan/izin-terlambat"
    Then Halaman Laporan Izin Terlambat berhasil dimuat
    When Pengguna memasukkan nama "KaryawanFiktifXYZ999" ke kolom pencarian izin terlambat dan klik Search
    Then Halaman Laporan Izin Terlambat tetap stabil
    When Pengguna mengklik tombol Reset pada filter Laporan Izin Terlambat
    Then Kolom input pencarian nama izin terlambat menjadi kosong kembali
