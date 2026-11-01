@employee @izin
Feature: Hadir Employee - Modul Izin (Terlambat & Pulang Cepat)
  Sebagai karyawan (Employee)
  Saya ingin mengakses form drawer Izin
  Agar saya dapat mengajukan izin terlambat atau izin pulang cepat

  @employee @EMP-IZN-01 @positive
  Scenario: Berpindah Antara Tab Izin Terlambat dan Pulang Cepat
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna memverifikasi tab default Terlambat aktif dengan tombol "Ajukan Izin Terlambat"
    And Pengguna mengklik tab "Pulang Cepat"
    Then Tab "Pulang Cepat" menjadi berstatus aktif
    And Halaman menampilkan judul riwayat "List Pulang Cepat" dan tombol "Ajukan Pulang Cepat"

  @employee @EMP-IZN-02 @positive
  Scenario: Menampilkan Drawer Form Pengajuan Izin Terlambat dan Validasi Field
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna berada pada tab Terlambat dan mengklik tombol "Ajukan Izin Terlambat"
    Then Drawer form pengajuan Izin Terlambat berhasil ditampilkan dari bawah
    And Form menampilkan field Tanggal, Jam, Keterangan, tombol Reset, dan tombol Ajukan

  @employee @EMP-IZN-03 @positive
  Scenario: Menampilkan Drawer Form Pengajuan Izin Pulang Cepat dan Validasi Field
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna beralih ke tab Pulang Cepat dan mengklik tombol "Ajukan Pulang Cepat"
    Then Drawer form pengajuan Izin Pulang Cepat berhasil ditampilkan dari bawah
    And Form menampilkan field Tanggal, Jam, Keterangan, tombol Reset, dan tombol Ajukan

  @employee @EMP-IZN-05 @boundary
  Scenario: Pengujian Boundary Value Input Keterangan Izin dengan String Panjang
    Given Pengguna membuka drawer form pengajuan izin Terlambat
    When Pengguna memasukkan teks keterangan panjang 255 karakter alfanumerik
    Then Field textarea Keterangan menerima seluruh teks tanpa terjadi error layout UI

  @employee @EMP-IZN-12 @positive @ui
  Scenario: Verifikasi Dinamika Teks Floating Action Button Saat Berpindah Tab Izin
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna berada pada tab default Terlambat
    Then Tombol floating pengajuan bertuliskan "Ajukan Izin Terlambat"
    When Pengguna mengklik tab "Pulang Cepat"
    Then Tombol floating pengajuan berganti teks menjadi "Ajukan Pulang Cepat"

  @employee @EMP-IZN-06 @positive @component
  Scenario: Memilih Tanggal Melalui Date Picker pada Form Izin Terlambat
    Given Pengguna membuka drawer form pengajuan izin Terlambat
    When Pengguna mengklik ikon picker tanggal pada form Izin Terlambat
    And Pengguna memilih tanggal aktif pada kalender izin
    Then Nilai field Tanggal terisi otomatis dengan tanggal yang dipilih

  @employee @EMP-IZN-07 @positive @ui
  Scenario: Menutup Drawer Form Izin Melalui Klik Area Backdrop
    Given Pengguna membuka drawer form pengajuan izin Terlambat
    When Pengguna mengklik area backdrop overlay di luar drawer Izin
    Then Drawer form Izin berhasil ditutup dari layar

  @employee @EMP-IZN-08 @positive @ui
  Scenario: Verifikasi Informasi Header Halaman Izin dan Total Counter pada Tab Terlambat
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna berada pada tab default Terlambat
    Then Header halaman Izin menampilkan "Halaman Izin" dan "List Izin Terlambat"
    And Halaman Izin menampilkan counter total yang valid

  @employee @EMP-IZN-09 @positive @ui
  Scenario: Verifikasi Informasi Header List dan Total Counter pada Tab Pulang Cepat
    Given Pengguna berhasil login ke Hadir Employee dan berada di halaman Izin
    When Pengguna mengklik tab "Pulang Cepat"
    Then Halaman menampilkan judul riwayat "List Pulang Cepat"
    And Halaman Izin menampilkan counter total yang valid

  @employee @EMP-IZN-10 @boundary
  Scenario: Pengujian Boundary Value Input Keterangan pada Form Izin Pulang Cepat dengan String Panjang
    Given Pengguna membuka drawer form pengajuan izin Pulang Cepat
    When Pengguna memasukkan teks keterangan panjang 255 karakter alfanumerik
    Then Field textarea Keterangan menerima seluruh teks tanpa terjadi error layout UI

  @employee @EMP-IZN-11 @positive @component
  Scenario: Membuka Time Picker pada Form Drawer Izin
    Given Pengguna membuka drawer form pengajuan izin Terlambat
    When Pengguna mengklik ikon time picker pada field Jam form Izin
    Then Popup pemilih waktu berhasil ditampilkan di layar


