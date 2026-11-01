@employee @lembur
Feature: Hadir Employee - Modul Lembur
  Sebagai karyawan (Employee)
  Saya ingin mengakses form drawer Lembur
  Agar saya dapat mengajukan jam kerja lembur

  @employee @EMP-LMB-01 @positive
  Scenario: Menampilkan Drawer Form Pengajuan Lembur dan Validasi Field
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Lembur
    When Pengguna mengklik tombol floating Ajukan Lembur
    Then Drawer form Ajukan Lembur berhasil ditampilkan dari bawah
    And Form menampilkan field Jam masuk, Jam keluar, Catatan, tombol Reset, dan tombol Ajukan

  @employee @EMP-LMB-02 @boundary
  Scenario: Pengujian Boundary Value Input Catatan Lembur dengan String Panjang
    Given Pengguna membuka drawer form Ajukan Lembur
    When Pengguna memasukkan teks catatan lembur panjang 255 karakter alfanumerik
    Then Field textarea Catatan lembur menerima seluruh teks tanpa terjadi error layout UI

  @employee @EMP-LMB-04 @positive
  Scenario: Menutup Drawer Form Pengajuan Lembur Melalui Klik Area Backdrop
    Given Pengguna membuka drawer form Ajukan Lembur
    When Pengguna mengklik area backdrop overlay di luar drawer Lembur
    Then Drawer form Lembur berhasil ditutup dari layar

  @employee @EMP-LMB-05 @positive @component
  Scenario: Memilih Tanggal dan Jam Masuk Menggunakan Popup MUI Picker pada Lembur
    Given Pengguna membuka drawer form Ajukan Lembur
    When Pengguna mengklik ikon picker pada field Jam masuk lembur
    And Pengguna memilih tanggal aktif pada kalender picker lembur
    Then Tampilan jam picker lembur ditampilkan dan nilai Jam masuk lembur terisi otomatis pada form

  @employee @EMP-LMB-06 @positive @component
  Scenario: Memilih Tanggal dan Jam Keluar Menggunakan Popup MUI Picker pada Lembur
    Given Pengguna membuka drawer form Ajukan Lembur
    When Pengguna mengklik ikon picker pada field Jam keluar lembur
    And Pengguna memilih tanggal aktif pada kalender picker lembur
    Then Tampilan jam picker lembur ditampilkan dan nilai Jam keluar lembur terisi otomatis pada form

  @employee @EMP-LMB-07 @positive @ui
  Scenario: Verifikasi Informasi Header Halaman Lembur dan Total Counter Overtime
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Lembur
    Then Header halaman lembur menampilkan "Halaman Lembur" dan "List Overtime"
    And Halaman lembur menampilkan counter total lembur yang valid

  @employee @EMP-LMB-08 @positive @ui
  Scenario: Verifikasi Tampilan dan Kelengkapan Struktur Informasi Kartu Riwayat Lembur
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Lembur
    Then Daftar riwayat lembur menampilkan kartu dengan detail jam kerja dan status

  @employee @EMP-LMB-09 @positive @component
  Scenario: Verifikasi Fungsionalitas Input Manual Jam Masuk Lembur Melalui Keyboard Numerik
    Given Pengguna membuka drawer form Ajukan Lembur
    When Pengguna memasukkan teks jam masuk lembur secara manual "02 11 2026, 09:00"
    Then Field input Jam masuk lembur berhasil menerima dan menyimpan nilai yang dimasukkan


