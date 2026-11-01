Feature: Modul Laporan Semua Web Admin

  @LaporanSemua @Security
  Scenario: Mengakses halaman Laporan Semua mengarahkan pengguna yang belum login ke halaman login
    Given Pengguna membuka browser
    When Pengguna mengakses halaman Laporan Semua di URL "/laporan/all"
    Then Pengguna dialihkan ke halaman login "/authentication/login"
    And Judul halaman adalah "HADIR"
    And Form input email dan password login ditampilkan

  @LaporanSemua @Functional
  Scenario: Pengguna yang telah login dapat mengakses halaman Laporan Semua dan melihat komponen utama
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Semua di URL "/laporan/all"
    Then Halaman Laporan Semua berhasil dimuat
    And Tabel laporan dan kontrol pagination ditampilkan
    And Komponen filter tanggal mulai dan tanggal akhir ditampilkan

  @LaporanSemua @Functional
  Scenario: Pengujian interaksi pencarian nama karyawan yang tidak ditemukan dan reset filter laporan semua
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid
    When Pengguna membuka halaman Laporan Semua di URL "/laporan/all"
    Then Halaman Laporan Semua berhasil dimuat
    When Pengguna memasukkan nama "KaryawanFiktifXYZ999" ke kolom pencarian laporan semua dan klik Search
    Then Halaman Laporan Semua menampilkan hasil pencarian kosong
    When Pengguna mengklik tombol Reset pada filter Laporan Semua
    Then Kolom input pencarian nama laporan semua menjadi kosong dan data tabel kembali termuat

