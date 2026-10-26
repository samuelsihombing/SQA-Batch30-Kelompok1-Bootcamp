Feature: Modul Laporan Cuti Web Admin

  @LaporanCuti @Security
  Scenario: LCU-01 Akses halaman Laporan Cuti tanpa login dialihkan ke halaman login
    Given Pengguna membuka browser
    When Pengguna mengakses halaman Laporan Cuti di URL "/laporan/cuti"
    Then Pengguna dialihkan ke halaman login "/authentication/login"
    And Judul halaman adalah "HADIR"
    And Form input email dan password login ditampilkan

  @LaporanCuti @Functional
  Scenario: LCU-02 Verifikasi ketersediaan elemen utama halaman Laporan Cuti
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    And Tabel laporan cuti dan 11 header kolom ditampilkan
    And Kontrol pagination laporan cuti ditampilkan dan baris data cuti tersedia
    And Seluruh komponen filter dan tombol aksi laporan cuti ditampilkan

  @LaporanCuti @Functional
  Scenario: LCU-03 Pengujian interaksi pencarian nama karyawan fiktif dan reset filter cuti
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan nama "KaryawanFiktifXYZ999" ke kolom pencarian cuti dan klik Search
    Then Halaman Laporan Cuti menampilkan hasil pencarian kosong
    When Pengguna mengklik tombol Reset pada filter Laporan Cuti
    Then Kolom input pencarian nama cuti menjadi kosong dan data tabel cuti kembali termuat
