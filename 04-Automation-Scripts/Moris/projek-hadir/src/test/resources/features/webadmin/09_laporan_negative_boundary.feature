Feature: Negative dan Boundary Value Testing Modul Laporan Web Admin

  Background:
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid

  @NegativeTesting @NEG-01
  Scenario: NEG-01 Pencarian nama karyawan yang tidak terdaftar menghasilkan empty state
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan kata kunci negatif "KaryawanFiktifXYZ999" ke pencarian cuti dan klik Search
    Then Tabel Laporan Cuti menampilkan empty state dengan pagination nol

  @NegativeTesting @NEG-02
  Scenario: NEG-02 Pencarian menggunakan karakter khusus menghasilkan empty state tanpa crash
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan kata kunci negatif "@#$%^&*()" ke pencarian cuti dan klik Search
    Then Tabel Laporan Cuti menampilkan empty state dengan pagination nol

  @NegativeTesting @NEG-03
  Scenario: NEG-03 Pencarian angka fiktif yang tidak cocok menghasilkan empty state
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan kata kunci negatif "999888777" ke pencarian cuti dan klik Search
    Then Tabel Laporan Cuti menampilkan empty state dengan pagination nol

  @NegativeTesting @NEG-04
  Scenario: NEG-04 Filter rentang tanggal di luar periode data aktual menghasilkan empty state
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memilih rentang tanggal di luar data cuti via kalender dan klik Search
    Then Tabel Laporan Cuti menampilkan empty state dengan pagination nol

  @NegativeTesting @NEG-05
  Scenario: NEG-05 Filter kombinasi nama fiktif dan tanggal valid menghasilkan empty state
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan nama fiktif "KaryawanFiktifXYZ999" dan tanggal valid "01 Nov 2026" pada Laporan Kehadiran
    And Pengguna mengklik tombol Search filter kehadiran
    Then Tabel Laporan Kehadiran menampilkan empty state dengan pagination nol

  @BoundaryTesting @BVT-01
  Scenario: BVT-01 Pencarian batas minimum satu karakter
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan 1 karakter "S" ke pencarian kehadiran dan klik Search
    Then Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya mengandung nama "Saleh"

  @BoundaryTesting @BVT-02
  Scenario: BVT-02 Pencarian teks batas panjang ekstrem 100 karakter
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan teks panjang 100 karakter ke pencarian cuti dan klik Search
    Then Kolom input pencarian cuti menerima teks 100 karakter dan tabel menampilkan empty state

  @BoundaryTesting @BVT-05
  Scenario: BVT-05 Pencarian dengan leading dan trailing spaces dievaluasi secara literal
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan teks pencarian dengan spasi "  Testing 1  " ke pencarian cuti dan klik Search
    Then Tabel Laporan Cuti menampilkan empty state dengan pagination nol

  @BoundaryTesting @BVT-06
  Scenario: BVT-06 Klik tombol Search tanpa kriteria filter mempertahankan kondisi baseline
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna mencatat baseline data cuti lalu klik Search tanpa filter
    Then Tabel Laporan Cuti mempertahankan jumlah dan kondisi data sesuai baseline awal
