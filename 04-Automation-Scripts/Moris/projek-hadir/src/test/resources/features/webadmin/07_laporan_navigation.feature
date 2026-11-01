Feature: Navigasi Sidebar Modul Laporan Web Admin

  Background:
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid

  @Navigation
  Scenario: NAV-01 Membuka menu Laporan melalui sidebar
    When Pengguna mengklik menu Laporan pada sidebar
    Then Menu Laporan terbuka dan menampilkan submenu laporan

  @Navigation
  Scenario: NAV-02 Membuka submenu Laporan Semua
    When Pengguna mengklik submenu "Semua" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"
    And Submenu "Semua" berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-03 Membuka submenu Laporan Kehadiran
    When Pengguna mengklik submenu "Kehadiran" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
    And Submenu "Kehadiran" berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-04 Membuka submenu Laporan Izin Terlambat
    When Pengguna mengklik submenu "Izin Terlambat" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/izin-terlambat"
    And Submenu "Izin Terlambat" berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-05 Membuka submenu Laporan Izin Pulang Cepat
    When Pengguna mengklik submenu "Izin Pulang Cepat" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/izin-pulang-cepat"
    And Submenu "Izin Pulang Cepat" berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-06 Membuka submenu Laporan Izin
    When Pengguna mengklik submenu "Izin" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/izin"
    And Submenu "Izin" berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-07 Membuka submenu Laporan Cuti
    When Pengguna mengklik submenu "Cuti" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/cuti"
    And Submenu "Cuti" berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-08 Berpindah dari Laporan Semua ke Laporan Kehadiran
    When Pengguna mengklik submenu "Semua" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"
    When Pengguna mengklik submenu "Kehadiran" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
    And Submenu "Kehadiran" berstatus aktif pada sidebar
    And Submenu "Semua" tidak berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-09 Berpindah antar enam submenu secara berurutan
    When Pengguna mengklik submenu "Semua" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"
    When Pengguna mengklik submenu "Kehadiran" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
    When Pengguna mengklik submenu "Izin Terlambat" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/izin-terlambat"
    When Pengguna mengklik submenu "Izin Pulang Cepat" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/izin-pulang-cepat"
    When Pengguna mengklik submenu "Izin" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/izin"
    When Pengguna mengklik submenu "Cuti" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/cuti"

  @Navigation
  Scenario: NAV-10 Memastikan active state sidebar sesuai halaman yang sedang dibuka
    When Pengguna mengklik submenu "Semua" pada sidebar
    Then Submenu "Semua" berstatus aktif pada sidebar
    When Pengguna mengklik submenu "Cuti" pada sidebar
    Then Submenu "Cuti" berstatus aktif pada sidebar
    And Submenu "Semua" tidak berstatus aktif pada sidebar

  @Navigation
  Scenario: NAV-11 Menggunakan browser Back untuk kembali ke halaman laporan sebelumnya
    When Pengguna mengklik submenu "Semua" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"
    When Pengguna mengklik submenu "Kehadiran" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
    When Pengguna menavigasi browser Back
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"

  @Navigation
  Scenario: NAV-12 Menggunakan browser Forward untuk kembali ke halaman berikutnya
    When Pengguna mengklik submenu "Semua" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"
    When Pengguna mengklik submenu "Kehadiran" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
    When Pengguna menavigasi browser Back
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/all"
    When Pengguna menavigasi browser Forward
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"

  @Navigation
  Scenario: NAV-13 Melakukan refresh ketika berada di halaman laporan
    When Pengguna mengklik submenu "Kehadiran" pada sidebar
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
    When Pengguna melakukan refresh halaman
    Then Pengguna diarahkan ke halaman dengan URL "/laporan/activity"
