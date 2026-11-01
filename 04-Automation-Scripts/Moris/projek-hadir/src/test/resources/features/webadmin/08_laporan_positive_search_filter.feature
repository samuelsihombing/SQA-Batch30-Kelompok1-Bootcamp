Feature: Positive Search dan Date Filter Modul Laporan Web Admin

  Background:
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid

  @PositiveSearch @POS-01 @POS-02
  Scenario: POS-01 dan POS-02 Pencarian nama karyawan riil pada tabel dan verifikasi hasil pencarian
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna mencari nama karyawan "Hadir SQA Testing 1" pada Laporan Cuti
    Then Tabel Laporan Cuti menampilkan data yang seluruh barisnya mengandung "Hadir SQA Testing 1"

  @PositiveSearch @POS-03
  Scenario: POS-03 Pencarian parsial sebagian teks nama karyawan
    When Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna mencari nama karyawan "Testing 1" pada Laporan Cuti
    Then Tabel Laporan Cuti menampilkan data yang seluruh barisnya mengandung "Hadir SQA Testing 1"

  @DateFilter @FIL-01 @FIL-04
  Scenario: FIL-01 dan FIL-04 Filter rentang tanggal valid Start Date dan End Date
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan Start Date "01 Nov 2026" dan End Date "01 Nov 2026" pada Laporan Kehadiran
    And Pengguna mengklik tombol Search filter kehadiran
    Then Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya sesuai tanggal "01 Nov 2026"

  @DateFilter @FIL-02
  Scenario: FIL-02 Filter hanya menggunakan Start Date valid
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan hanya Start Date "01 Nov 2026" pada Laporan Kehadiran
    And Pengguna mengklik tombol Search filter kehadiran
    Then Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya sesuai tanggal "01 Nov 2026"

  @DateFilter @FIL-03
  Scenario: FIL-03 Filter hanya menggunakan End Date valid
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan hanya End Date "01 Nov 2026" pada Laporan Kehadiran
    And Pengguna mengklik tombol Search filter kehadiran
    Then Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya sesuai tanggal "01 Nov 2026"

  @CombinedFilter @FIL-05 @FIL-06
  Scenario: FIL-05 dan FIL-06 Filter kombinasi nama karyawan dan tanggal valid
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan nama "Saleh" dan tanggal "01 Nov 2026" sampai "01 Nov 2026" pada Laporan Kehadiran
    And Pengguna mengklik tombol Search filter kehadiran
    Then Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya mengandung nama "Saleh" dan tanggal "01 Nov 2026"

  @ResetFilter @FIL-07 @FIL-08
  Scenario: FIL-07 dan FIL-08 Reset filter setelah pencarian dan filter tanggal diterapkan
    When Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna memasukkan nama "Saleh" dan tanggal "01 Nov 2026" sampai "01 Nov 2026" pada Laporan Kehadiran
    And Pengguna mengklik tombol Search filter kehadiran
    Then Tabel Laporan Kehadiran menampilkan data yang seluruh barisnya mengandung nama "Saleh" dan tanggal "01 Nov 2026"
    When Pengguna mengklik tombol Reset pada filter Laporan Kehadiran
    Then Seluruh input filter pencarian dan tanggal kehadiran kembali kosong
    And Data tabel laporan kehadiran kembali termuat ke kondisi default
