@employee @koreksi
Feature: Hadir Employee - Modul Koreksi Absen
  Sebagai karyawan (Employee)
  Saya ingin mengakses form drawer Koreksi Absen
  Agar saya dapat mengajukan koreksi kehadiran

  @employee @EMP-KOR-01 @positive
  Scenario: Menampilkan Drawer Form Pengajuan Koreksi Absen dan Validasi Elemen Input
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Koreksi Absen
    When Pengguna mengklik tombol floating Ajukan Koreksi
    Then Drawer form Ajukan Koreksi Absen berhasil ditampilkan dari bawah
    And Form menampilkan field "Jam masuk", "Jam keluar", tombol "Reset", dan tombol "Ajukan"

  @employee @EMP-KOR-02 @positive
  Scenario: Memilih Tanggal dan Jam Masuk Menggunakan Popup MUI Picker
    Given Pengguna membuka drawer form Ajukan Koreksi Absen
    When Pengguna mengklik ikon picker pada field Jam masuk
    And Pengguna memilih tanggal aktif pada kalender picker
    Then Tampilan jam picker ditampilkan dan nilai Jam masuk terisi otomatis pada form

  @employee @EMP-KOR-03 @positive
  Scenario: Menutup Drawer Form Koreksi Absen Melalui Klik Area Backdrop
    Given Pengguna membuka drawer form Ajukan Koreksi Absen
    When Pengguna mengklik area backdrop overlay di luar drawer Koreksi
    Then Drawer form Koreksi Absen berhasil ditutup dari layar

  @employee @EMP-KOR-04 @potential_defect
  Scenario: Verifikasi Efek Tombol Reset Terhadap Field Jam Masuk dan Jam Keluar pada Koreksi Absen
    Given Pengguna membuka drawer form Ajukan Koreksi Absen
    When Pengguna memilih tanggal dan jam pada field Jam masuk
    And Pengguna memilih tanggal dan jam pada field Jam keluar
    And Pengguna mengklik tombol Reset pada form Koreksi
    Then Nilai kedua field Jam masuk dan Jam keluar harus kembali kosong

  @employee @EMP-KOR-06 @positive @component
  Scenario: Memilih Tanggal dan Jam Keluar Menggunakan Popup MUI Picker
    Given Pengguna membuka drawer form Ajukan Koreksi Absen
    When Pengguna mengklik ikon picker pada field Jam keluar
    And Pengguna memilih tanggal aktif pada kalender picker
    Then Tampilan jam picker ditampilkan dan nilai Jam keluar terisi otomatis pada form

  @employee @EMP-KOR-07 @positive @ui
  Scenario: Verifikasi Atribut Readonly pada Input Field Jam Masuk dan Jam Keluar
    Given Pengguna membuka drawer form Ajukan Koreksi Absen
    Then Field input "Jam masuk" dan "Jam keluar" berstatus readonly

  @employee @EMP-KOR-08 @positive @ui
  Scenario: Verifikasi Informasi Header Halaman Koreksi dan Total Counter Koreksi
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Koreksi Absen
    Then Header halaman menampilkan "Halaman Koreksi" dan "List Koreksi"
    And Halaman menampilkan counter total koreksi yang valid

  @employee @EMP-KOR-09 @positive @ui
  Scenario: Verifikasi Tampilan dan Kelengkapan Struktur Informasi Kartu Riwayat Koreksi Absen
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Koreksi Absen
    Then Daftar riwayat menampilkan kartu koreksi dengan detail jam kerja dan status

  @employee @EMP-KOR-10 @positive @safety
  Scenario: Verifikasi Tombol Ajukan Berstatus Aktif dan Bertipe Submit Tanpa Melakukan Submission
    Given Pengguna membuka drawer form Ajukan Koreksi Absen
    Then Tombol "Ajukan" berstatus aktif dan bertipe submit tanpa diklik

