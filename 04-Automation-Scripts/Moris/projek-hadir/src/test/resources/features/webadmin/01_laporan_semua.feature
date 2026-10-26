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
