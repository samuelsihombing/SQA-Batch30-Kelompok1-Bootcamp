@LaporanResponsiveMobile
Feature: Pengujian Responsive Layout dan Mobile Web Submenu Laporan Hadir Web HRD
  Sebagai HRD Administrator
  Saya ingin memastikan seluruh halaman laporan dapat diakses dan digunakan dengan baik
  pada berbagai ukuran viewport desktop, tablet, dan mobile web melalui navigasi drawer,
  filter yang adaptif, horizontal scrolling tabel, date picker, dan pagination.

  Background:
    Given Pengguna membuka browser
    And Pengguna login menggunakan kredensial akun testing yang valid

  # ============================================================================
  # 1. RESPONSIVE LAYOUT TESTING (RES-01 s/d RES-06)
  # ============================================================================

  @ResponsiveLayout @RES-01
  Scenario: RES-01 Tampilan halaman Laporan pada desktop 1366x768
    When Pengguna mengatur ukuran viewport desktop "1366" x "768"
    And Pengguna membuka halaman Laporan Semua di URL "/laporan/all"
    Then Halaman Laporan Semua berhasil dimuat
    And Sidebar navigasi desktop berstatus docked dan ditampilkan
    And Form filter pencarian tertata sejajar secara horizontal

  @ResponsiveLayout @RES-02
  Scenario: RES-02 Tampilan halaman Laporan pada tablet 1024x768
    When Pengguna mengatur ukuran viewport tablet "1024" x "768"
    And Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    And Halaman tidak mengalami broken horizontal overflow pada level window

  @ResponsiveLayout @RES-03
  Scenario: RES-03 Tampilan halaman Laporan pada mobile 375x812
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    And Elemen filter pencarian tertata secara responsif dan bertumpuk vertikal

  @ResponsiveLayout @RES-04
  Scenario: RES-04 Tampilan halaman Laporan pada mobile 412x915
    When Pengguna mengatur ukuran viewport mobile "412" x "915"
    And Pengguna membuka halaman Laporan Izin Terlambat di URL "/laporan/izin-terlambat"
    Then Halaman Laporan Izin Terlambat berhasil dimuat
    And Halaman tidak mengalami broken horizontal overflow pada level window

  @ResponsiveLayout @RES-05
  Scenario: RES-05 Memeriksa ketiadaan horizontal overflow pada halaman mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Izin Pulang Cepat di URL "/laporan/izin-pulang-cepat"
    Then Halaman Laporan Izin Pulang Cepat berhasil dimuat
    And Halaman tidak mengalami broken horizontal overflow pada level window

  @ResponsiveLayout @RES-06
  Scenario: RES-06 Memastikan field Search tanggal dan tombol aksi tetap ditampilkan pada mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Izin di URL "/laporan/izin"
    Then Halaman Laporan Izin berhasil dimuat
    And Seluruh komponen filter pencarian tanggal dan tombol aksi ditampilkan di mobile

  # ============================================================================
  # 2. MOBILE NAVIGATION & DRAWER TESTING (MOB-01 s/d MOB-05)
  # ============================================================================

  @MobileNavigation @MOB-01
  Scenario: MOB-01 Memeriksa perubahan navigasi dari sidebar desktop ke hamburger button di mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Semua di URL "/laporan/all"
    Then Halaman Laporan Semua berhasil dimuat
    And Sidebar desktop disembunyikan dan tombol hamburger navigasi ditampilkan di navbar

  @MobileNavigation @MOB-02
  Scenario: MOB-02 Membuka drawer navigation melalui klik tombol hamburger
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna mengklik tombol hamburger navigasi di header
    Then Drawer navigasi modal berhasil terbuka dan menampilkan menu aplikasi

  @MobileNavigation @MOB-03
  Scenario: MOB-03 Menutup drawer navigation melalui klik backdrop modal
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    When Pengguna mengklik tombol hamburger navigasi di header
    And Pengguna mengklik backdrop di luar drawer navigasi
    Then Drawer navigasi modal berhasil ditutup dari layar

  @MobileNavigation @MOB-04
  Scenario Outline: MOB-04 Membuka submenu Laporan melalui drawer pada mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Semua di URL "/laporan/all"
    Then Halaman Laporan Semua berhasil dimuat
    When Pengguna mengklik tombol hamburger navigasi di header
    And Pengguna mengklik submenu "<Submenu>" di dalam drawer
    Then Halaman diarahkan ke route "<ExpectedRoute>" secara tepat

    Examples:
      | Submenu           | ExpectedRoute              |
      | Semua             | /laporan/all               |
      | Kehadiran         | /laporan/activity          |
      | Izin Terlambat    | /laporan/izin-terlambat    |
      | Izin Pulang Cepat | /laporan/izin-pulang-cepat |
      | Izin              | /laporan/izin              |
      | Cuti              | /laporan/cuti              |

  @MobileNavigation @MOB-05
  Scenario: MOB-05 Memastikan active state submenu pada drawer sesuai halaman yang aktif
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    And Submenu "Cuti" dengan path "/laporan/cuti" berstatus aktif secara semantik di drawer

  # ============================================================================
  # 3. MOBILE FUNCTIONAL TESTING (MOB-06 s/d MOB-10)
  # ============================================================================

  @MobileFunctional @MOB-06
  Scenario: MOB-06 Fitur pencarian data Search dapat digunakan pada tampilan mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan nama pencarian "Testing" di mobile dan klik tombol Search
    Then Tabel laporan di mobile menampilkan hasil pencarian yang sesuai dengan kata kunci

  @MobileFunctional @MOB-07
  Scenario: MOB-07 Tombol Reset filter dapat digunakan pada tampilan mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna memasukkan nama pencarian "Testing" di mobile dan klik tombol Search
    And Pengguna mengklik tombol Reset pada filter di mobile
    Then Kolom pencarian di mobile kembali kosong dan data awal kembali dimuat

  @MobileFunctional @MOB-08
  Scenario: MOB-08 Date Picker popover dapat dibuka dan dipilih pada tampilan mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    When Pengguna membuka kalender Date Picker di mobile
    Then Popover Date Picker ditampilkan proporsional di dalam batas viewport mobile
    When Pengguna memilih rentang tanggal awal dan akhir di kalender mobile
    Then Field Start Date dan End Date di mobile berhasil terisi dengan tanggal yang dipilih

  @MobileFunctional @MOB-09
  Scenario: MOB-09 Tabel laporan dapat di-scroll secara horizontal pada tampilan mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Cuti di URL "/laporan/cuti"
    Then Halaman Laporan Cuti berhasil dimuat
    And Container tabel laporan mendukung penggeseran horizontal pada mobile
    When Pengguna menggeser tabel laporan secara horizontal sejauh 150 piksel di mobile
    Then Tabel laporan berhasil digeser secara horizontal tanpa merusak layout luar

  @MobileFunctional @MOB-10
  Scenario: MOB-10 Kontrol pagination dapat digunakan untuk berpindah halaman pada tampilan mobile
    When Pengguna mengatur ukuran viewport mobile "375" x "812"
    And Pengguna membuka halaman Laporan Kehadiran di URL "/laporan/activity"
    Then Halaman Laporan Kehadiran berhasil dimuat
    And Kontrol pagination ditampilkan pada bagian bawah tabel di mobile
    When Pengguna berpindah ke halaman berikutnya dan kembali ke halaman sebelumnya pada pagination mobile
    Then Teks rentang baris pagination di mobile diperbarui sesuai halaman yang aktif
