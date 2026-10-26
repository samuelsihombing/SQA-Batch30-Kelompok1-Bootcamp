Feature: Modul Laporan Izin Pulang Cepat Web Admin

  @LaporanIzinPulangCepat @Security
  Scenario: LIPC-01 Akses halaman Laporan Izin Pulang Cepat tanpa login dialihkan ke halaman login
    Given Pengguna membuka browser
    When Pengguna mengakses halaman Laporan Izin Pulang Cepat di URL "/laporan/izin-pulang-cepat"
    Then Pengguna dialihkan ke halaman login "/authentication/login"
    And Judul halaman adalah "HADIR"
    And Form input email dan password login ditampilkan

  @LaporanIzinPulangCepat @Functional
  Scenario: LIPC-02 Verifikasi ketersediaan elemen utama halaman Laporan Izin Pulang Cepat
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Izin Pulang Cepat di URL "/laporan/izin-pulang-cepat"
    Then Halaman Laporan Izin Pulang Cepat berhasil dimuat
    And Tabel laporan izin pulang cepat dan 7 header kolom ditampilkan
    And Kontrol pagination laporan izin pulang cepat ditampilkan
    And Seluruh komponen filter dan tombol aksi laporan izin pulang cepat ditampilkan

  @LaporanIzinPulangCepat @Functional
  Scenario: LIPC-03 Pengujian interaksi pencarian nama karyawan fiktif dan reset filter izin pulang cepat
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Izin Pulang Cepat di URL "/laporan/izin-pulang-cepat"
    Then Halaman Laporan Izin Pulang Cepat berhasil dimuat
    When Pengguna memasukkan nama "KaryawanFiktifXYZ999" ke kolom pencarian izin pulang cepat dan klik Search
    Then Halaman Laporan Izin Pulang Cepat tetap stabil
    When Pengguna mengklik tombol Reset pada filter Laporan Izin Pulang Cepat
    Then Kolom input pencarian nama izin pulang cepat menjadi kosong kembali
