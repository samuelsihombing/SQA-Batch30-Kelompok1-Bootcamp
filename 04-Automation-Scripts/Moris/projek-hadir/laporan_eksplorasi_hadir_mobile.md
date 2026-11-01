# Laporan Hasil Eksplorasi UI & Test Scenario Planning — Hadir Employee QA Automation

---

## 1. Informasi Environment dan URL Aplikasi

| Parameter | Detail |
| :--- | :--- |
| **Aplikasi / Target** | Hadir Employee Web (Aplikasi Karyawan) |
| **Tech Stack Aplikasi** | Next.js / React, Material-UI (MUI v5), Responsive / Mobile Viewport |
| **Login URL** | `https://magang.dikahadir.com/absen/login` |
| **Dashboard URL** | `https://magang.dikahadir.com/apps/absent` |
| **Route Koreksi Absen** | `https://magang.dikahadir.com/apps/absent/correction` |
| **Route Izin (Terlambat & Pulang Cepat)** | `https://magang.dikahadir.com/apps/absent/permit` |
| **Route Lembur** | `https://magang.dikahadir.com/apps/absent/overtime-client` |
| **Test Environment** | Demo / Training Staging (`magang.dikahadir.com`) |
| **Device Emulation** | Viewport: 375 x 812 px, Device Scale Factor: 1.0, Mobile Mode: Active |
| **Tech Stack Automation** | Java 21, Selenium WebDriver 4.28.0, TestNG 7.10.2, Maven, Chrome DevTools Protocol |

---

## 2. Hasil Konfigurasi Kredensial dan Penggunaan ConfigReader.java

Sesuai instruksi dan aturan keamanan kredensial, konfigurasi lokal dan mekanismenya telah diimplementasikan sebagai berikut:

1. **File Kredensial Lokal (`src/test/resources/config.properties`):**
   - Menyimpan parameter autentikasi lokal:
     ```properties
     login.url=https://magang.dikahadir.com/absen/login
     login.email=hadirsqa1@gmail.com
     login.password=SQA@Hadir12345
     ```
2. **File Template Repository (`src/test/resources/config.example.properties`):**
   - Berfungsi sebagai template aman untuk repository tanpa menyertakan password asli:
     ```properties
     login.url=https://magang.dikahadir.com/absen/login
     login.email=your_email@example.com
     login.password=your_password_here
     ```
3. **Perlindungan Git (`.gitignore`):**
   - File `src/test/resources/config.properties` telah didaftarkan ke [.gitignore](file:///d:/Juaracoding%20stack/projek-hadir/.gitignore) sehingga tidak akan ter-commit atau ter-push ke version control.
4. **Mekanisme Pembacaan Dinamis (`ConfigReader.java`):**
   - File Java utility [ConfigReader.java](file:///d:/Juaracoding%20stack/projek-hadir/src/main/java/com/hadir/utils/ConfigReader.java) membaca file `config.properties` menggunakan `java.util.Properties` dengan fallback membaca via ClassLoader.
   - Menyediakan getter: `ConfigReader.getLoginUrl()`, `ConfigReader.getLoginEmail()`, dan `ConfigReader.getLoginPassword()`.
   - Menghilangkan hardcode URL/kredensial pada step definition maupun Page Object.
   - Tidak mencetak kredensial/password ke konsol, log eksekusi, maupun screenshot evidence.

---

## 3. Struktur Navigasi Aplikasi

```
[Halaman Login: /absen/login]
        │
        ▼ (Submit Email & Password)
[Halaman Dashboard: /apps/absent]
        ├── Header Profil & Kalender Kehadiran
        ├── Kartu Status Kehadiran (WFO, Cuti, Sakit, WFH, Lembur, Terlambat, Pulang Cepat)
        ├── Menu Grid Navigasi:
        │     ├── [Koreksi Absen] ──► [/apps/absent/correction] ──► Drawer "Ajukan Koreksi Absen"
        │     ├── [Izin]          ──► [/apps/absent/permit]
        │     │                          ├── Tab Terlambat     ──► Drawer "Ajukan Izin Terlambat"
        │     │                          └── Tab Pulang Cepat  ──► Drawer "Ajukan Izin Pulang Cepat"
        │     └── [Lembur]        ──► [/apps/absent/overtime-client] ──► Drawer "Ajukan Lembur"
        └── Tombol Utama Floating: "Absen Masuk"
```

- **Mekanisme Navigasi Modul:**
  Item menu di dashboard merupakan tag `<a>` dengan class `user__menu__item` di dalam struktur `MuiGrid-item`. Navigasi dilakukan melalui interaksi klik pada elemen menu tersebut.
- **Tombol `< Kembali`:**
  Tersedia di bagian kiri header setiap halaman modul (`/correction`, `/permit`, `/overtime-client`). Saat diklik, browser diarahkan kembali ke Dashboard (`/apps/absent`).

---

## 4. Hasil Eksplorasi Modul: Koreksi Absen

- **URL:** `https://magang.dikahadir.com/apps/absent/correction`
- **Elemen Halaman List:**
  - Header: Tombol `< Kembali` dan Teks `Halaman Koreksi`.
  - Judul Sub: `List Koreksi` dengan counter `Total : 708`.
  - Kartu Riwayat Koreksi memuat informasi:
    - Nama Pengguna: `Hadir SQA Testing 1`
    - `Jam masuk` (misal: `-` atau `2026-09-30, 08:40`)
    - `Jam keluar` (misal: `2026-10-29, 00:00`)
    - `Tipe kerja` (misal: `Work From Office` atau `-`)
    - `status` (`Approved`, `Rejected`, atau `-`)
    - `Approved by` (misal: `Admin Hadir`)
    - `Notes`
    - `Dibuat` (tanggal dan jam pembuatan pengajuan)
  - Floating Action Button (FAB): `Ajukan Koreksi` di bagian bawah layar.
- **Drawer Form Modal ("Ajukan Koreksi Absen"):**
  - Dipicu dengan mengklik tombol `Ajukan Koreksi`.
  - Menggunakan komponen slide-up `MuiDrawer-paper`.
  - Judul Drawer: `Ajukan Koreksi Absen`.
  - Field yang tersedia:
    1. **Jam masuk:**
       - Label: `Jam masuk`
       - Input: `placeholder="dd mm yyyy, hh:mm"`, atribut `readonly=""`
       - Tombol Icon Jam/Kalender: `aria-label="Choose date"`
    2. **Jam keluar:**
       - Label: `Jam keluar`
       - Input: `placeholder="dd mm yyyy, hh:mm"`, atribut `readonly=""`
       - Tombol Icon Jam/Kalender: `aria-label="Choose date"`
  - Tombol Tindakan:
    - `Reset` (`type="button"`, class `MuiButton-text`)
    - `Ajukan` (`type="submit"`, class `MuiButton-contained`)

---

## 5. Hasil Eksplorasi Modul: Izin (Terlambat & Pulang Cepat)

- **URL:** `https://magang.dikahadir.com/apps/absent/permit`
- **Header:** Tombol `< Kembali` dan Teks `Halaman Izin`.
- **Tab Navigasi:**
  - Tab 1: `Terlambat` (`button[role='tab']`, default `aria-selected="true"`)
  - Tab 2: `Pulang Cepat` (`button[role='tab']`, `aria-selected="false"`)

### A. Sub-Modul Izin Terlambat
- **Tampilan List:**
  - Judul: `List Izin Terlambat` dengan counter `Total : 21`.
  - Kartu riwayat memuat: Jam (tanggal & jam), Terlambat (`Menunggu Approval`), Status, Catatan (`notes`), Alasan, Ditolak, Tanggal Dibuat.
  - Floating Button: `Ajukan Izin Terlambat`.
- **Drawer Form Modal ("Ajukan Izin Terlambat"):**
  - Judul Drawer: `Ajukan Izin Terlambat`.
  - Field:
    1. **Tanggal:** Input `placeholder="mm/dd/yyyy"`, icon picker `aria-label="Choose date"` (`CalendarIcon`).
    2. **Jam:** Input `placeholder="14:00"`, icon picker `aria-label="Choose time"` (`AccessAlarmIcon`).
    3. **Keterangan:** Textarea `id="notes"`, `name="notes"` (rows=4).
  - Tombol: `Reset` dan `Ajukan` (`type="submit"`).

### B. Sub-Modul Izin Pulang Cepat
- **Tampilan List:**
  - Saat tab `Pulang Cepat` diklik, `aria-selected` berubah menjadi `true`.
  - Judul berganti menjadi: `List Pulang Cepat` dengan counter `Total : 78`.
  - Floating Button berganti menjadi: `Ajukan Pulang Cepat`.
- **Drawer Form Modal ("Ajukan Izin Pulang Cepat"):**
  - Judul Drawer: `Ajukan Izin Pulang Cepat`.
  - Field:
    1. **Tanggal:** Input `placeholder="mm/dd/yyyy"`, icon picker tanggal.
    2. **Jam:** Input `placeholder="14:00"`, icon picker jam.
    3. **Keterangan:** Textarea `id="notes"`, `name="notes"`.
  - Tombol: `Reset` dan `Ajukan` (`type="submit"`).

---

## 6. Hasil Eksplorasi Modul: Lembur

- **URL:** `https://magang.dikahadir.com/apps/absent/overtime-client`
- **Header:** Tombol `< Kembali` dan Teks `Halaman Lembur`.
- **Tampilan List:**
  - Judul: `List Overtime` dengan counter `Total : 119`.
  - Kartu riwayat memuat: Jam Masuk (tanggal & jam), Jam Keluar (tanggal & jam), Status (`Menunggu Approval` / `Approved`), Notes.
  - Floating Action Button: `Ajukan Lembur`.
- **Drawer Form Modal ("Ajukan Lembur"):**
  - Judul Drawer: `Ajukan Lembur`.
  - Field yang tersedia:
    1. **Jam masuk:** Input `placeholder="dd mm yyyy, hh:mm"`, icon picker kalender/jam.
    2. **Jam keluar:** Input `placeholder="dd mm yyyy, hh:mm"`, icon picker kalender/jam.
    3. **Catatan:** Textarea `id="notes"`, `name="notes"`.
  - Tombol Tindakan:
    - `Reset` (`type="button"`)
    - `Ajukan` (`type="submit"`)

---

## 7. Detail Komponen UI: Field, Tombol, Drawer, Date & Time Picker

### A. Bottom Sheet Drawer & Backdrop
- Kontainer form muncul sebagai komponen slide-up `div.MuiDrawer-paper.MuiDrawer-paperAnchorBottom`.
- Dilengkapi overlay latar belakang `div.MuiBackdrop-root`.
- **Observasi Interaksi Backdrop:** Mengklik area `MuiBackdrop-root` berhasil menutup drawer tanpa memicu submit (teruji pada seluruh modal).

### B. Date & Time Picker (MUI Pickers Popper)
- **Aktivasi:** Diklik melalui tombol ikon `aria-label="Choose date"` atau `aria-label="Choose time"`.
- **Struktur Dialog Kalender:**
  - Kontainer dialog: `div.base-Popper-root.MuiPickersPopper-root`.
  - Navigasi bulan: Tombol panah `<` (`previous month`) dan `>` (`next month`).
  - Grid tanggal: Sel tanggal `button.MuiPickersDay-root` (angka 1 s/d 30/31).
- **Struktur Dialog Jam (Clock Face):**
  - Pada field Koreksi Absen, setelah tanggal diklik, dialog bertransisi ke tampilan dial jam (`div.MuiClock-root`).
  - Menampilkan angka jam 1-12 dan 13-24 (`span.MuiClockNumber-root`).
  - Setelah jam dipilih, nilai otomatis terformat ke field input: `02 11 2026, 00:00`.

### C. Textarea Keterangan / Catatan
- Digunakan pada Izin (`Keterangan`) dan Lembur (`Catatan`).
- Selector pasti: `textarea#notes` atau `textarea[name='notes']`.
- Mendukung input teks alfanumerik serta multiline.
- **Pemeriksaan Batas:** Pada kode HTML DOM, tidak ditemukan atribut `maxlength` bawaan.

---

## 8. Temuan Potential Defect

### DEF-01: Tombol Reset pada Form Koreksi Absen Tidak Mengosongkan Nilai Input Tanggal/Jam
- **Modul / Lokasi:** Form Drawer "Ajukan Koreksi Absen" (`https://magang.dikahadir.com/apps/absent/correction`).
- **Elemen:** `<button type="button" class="... MuiButton-text ...">Reset</button>`.
- **Kondisi Pengujian:**
  1. Pengguna membuka drawer "Ajukan Koreksi Absen".
  2. Pengguna memilih tanggal dan jam pada field "Jam masuk" melalui popup MUI Picker (input terisi nilai `02 11 2026, 00:00`).
  3. Pengguna mengklik tombol `Reset`.
  4. Nilai atribut `value` pada input diperiksa kembali.
- **Hasil Aktual:** Nilai input "Jam masuk" tetap bernilai `02 11 2026, 00:00` (tidak terhapus / tidak kembali kosong).
- **Hasil yang Diharapkan:** Mengklik tombol `Reset` seharusnya mengosongkan nilai input tanggal/jam kembali ke nilai default (string kosong dengan placeholder `dd mm yyyy, hh:mm`).
- **Penyebab Teknis:** Elemen input berstatus `readonly` dan nilainya dikendalikan oleh React state. Tombol `Reset` bertipe `type="button"` (bukan HTML `type="reset"`) dan komponen tidak mengikat event handler `onClick` untuk mengatur ulang state nilai field ke `null` atau `""`.
- **Severity / Dampak:** Medium (Fungsionalitas reset tidak bekerja sesuai harapan pengguna, pengguna harus menghapus atau memilih ulang secara manual jika ingin membatalkan pilihan).

---

## 9. Evaluasi Pengujian Negative & Boundary Testing

### A. Pengujian Field Kosong (Negative Testing)
- **Kondisi Observasi DOM:**
  - Input "Jam masuk" & "Jam keluar" pada Koreksi Absen: Atribut `required` bernilai `null` (tidak ada HTML5 required validation).
  - Input "Tanggal" & "Jam" pada Izin: Atribut `required` bernilai `null`.
  - Textarea "Keterangan" pada Izin: Atribut `required` bernilai `null`.
  - Input "Jam masuk", "Jam keluar", dan textarea "Catatan" pada Lembur: Atribut `required` bernilai `null`.
  - Tidak ditemukan pesan validasi lokal yang muncul secara instan saat field dikosongkan lalu kehilangan fokus (*onBlur*).
- **Batasan Pengujian:**
  Validasi field kosong pada ketiga modul ini baru dipicu saat form dikirim melalui tombol `Ajukan` (`type="submit"`). Namun, mengklik tombol `Ajukan` tanpa data memiliki risiko memicu request API mutasi data ke server backend demo. Sesuai aturan STOP CONDITION dan larangan mutasi data, tindakan submit tidak dilakukan.
- **Status Evaluasi:** Skenario validasi submit field kosong ditandai sebagai **Not Verified** dengan penjelasan teknis lengkap.

### B. Pengujian Input Teks Panjang (Boundary Testing)
- **Evaluasi Form Izin (Keterangan):**
  Textarea `Keterangan` (`textarea#notes`) pada form Izin (baik Terlambat maupun Pulang Cepat) memiliki struktur dan karakteristik yang identik dengan textarea `Catatan` pada form Lembur.
- **Kesesuaian:** Textarea `Keterangan` Izin **sangat layak** mendapatkan pengujian boundary input teks panjang (misalnya 255 karakter alfanumerik) untuk memastikan antarmuka mobile mampu menampung string panjang tanpa merusak tata letak drawer (*no layout breaking*).
- **Aturan Batas Karakter:** Tidak ditemukan atribut `maxlength` pada tag textarea di UI, sehingga pengujian menggunakan string representatif tanpa mengarang batas maksimal yang tidak ada di UI.

---

## 10. Daftar Lengkap 20 Kandidat Test Scenario

Berikut adalah spesifikasi lengkap 20 kandidat skenario pengujian hasil penyempurnaan:

---

### Skenario 1: EMP-AUTH-01 — Login Hadir Mobile dengan Kredensial Valid
- **Test Case ID:** EMP-AUTH-01
- **Nama Skenario:** Login Hadir Mobile Berhasil Menggunakan Kredensial Valid dari ConfigReader
- **Preconditions:** Browser terbuka pada viewport mobile (375x812), user belum terautentikasi.
- **Test Steps:**
  1. Buka URL login dari konfigurasi (`ConfigReader.getLoginUrl()`).
  2. Masukkan email dari konfigurasi (`ConfigReader.getLoginEmail()`) ke field `input#email`.
  3. Masukkan password dari konfigurasi (`ConfigReader.getLoginPassword()`) ke field `input#password`.
  4. Klik tombol "Masuk" (`button[type='submit']`).
  5. Tunggu hingga proses redirect selesai.
- **Expected Result:** URL beralih ke `https://magang.dikahadir.com/apps/absent` dan dashboard menampilkan profil pengguna ("Hai, Hadir SQA Testing 1").
- **Actual Result:** Lolos eksplorasi aktual (URL sukses redirect ke `/apps/absent` dan halaman termuat sempurna).
- **Test Type:** Positive Testing
- **Priority:** Critical (P1)
- **Feasibility:** Automatable

---

### Skenario 2: EMP-AUTH-02 — Akses Halaman Protected Tanpa Autentikasi
- **Test Case ID:** EMP-AUTH-02
- **Nama Skenario:** Proteksi Akses Route Protected Hadir Mobile Tanpa Sesi Login
- **Preconditions:** Browser dalam keadaan sesi bersih (tanpa cookie/token sesi login).
- **Test Steps:**
  1. Buka langsung URL route protected: `https://magang.dikahadir.com/apps/absent`.
  2. Tunggu proses redirect sistem.
- **Expected Result:** Sistem menolak akses dan mengarahkan browser kembali ke `https://magang.dikahadir.com/absen/login`.
- **Actual Result:** Lolos eksplorasi aktual (unauthenticated request diarahkan ke halaman login).
- **Test Type:** Security / Negative Testing
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 3: EMP-NAV-01 — Navigasi Dashboard ke Modul Koreksi Absen
- **Test Case ID:** EMP-NAV-01
- **Nama Skenario:** Akses Modul Koreksi Absen Melalui Menu Grid Dashboard
- **Preconditions:** User sudah login dan berada di halaman Dashboard (`/apps/absent`).
- **Test Steps:**
  1. Cari elemen menu "Koreksi Absen" pada grid menu.
  2. Klik link/menu "Koreksi Absen" (`//p[text()='Koreksi Absen']/ancestor::a`).
  3. Tunggu navigasi halaman selesai.
- **Expected Result:** URL berpindah ke `https://magang.dikahadir.com/apps/absent/correction`, header menampilkan "Halaman Koreksi" dan subheader memuat "List Koreksi".
- **Actual Result:** Lolos eksplorasi aktual (navigasi berjalan dan halaman riwayat koreksi tampil).
- **Test Type:** Positive Testing / Navigation
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 4: EMP-NAV-02 — Navigasi Dashboard ke Modul Izin
- **Test Case ID:** EMP-NAV-02
- **Nama Skenario:** Akses Modul Izin Melalui Menu Grid Dashboard
- **Preconditions:** User sudah login dan berada di halaman Dashboard (`/apps/absent`).
- **Test Steps:**
  1. Cari elemen menu "Izin" pada grid menu dashboard.
  2. Klik link/menu "Izin" (`//div[contains(@class,'MuiGrid-item')]//p[text()='Izin']/ancestor::a`).
  3. Tunggu navigasi halaman selesai.
- **Expected Result:** URL berpindah ke `https://magang.dikahadir.com/apps/absent/permit`, header menampilkan "Halaman Izin" dan tab default "Terlambat" aktif.
- **Actual Result:** Lolos eksplorasi aktual (navigasi sukses dan halaman izin tampil).
- **Test Type:** Positive Testing / Navigation
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 5: EMP-NAV-03 — Navigasi Dashboard ke Modul Lembur
- **Test Case ID:** EMP-NAV-03
- **Nama Skenario:** Akses Modul Lembur Melalui Menu Grid Dashboard
- **Preconditions:** User sudah login dan berada di halaman Dashboard (`/apps/absent`).
- **Test Steps:**
  1. Cari elemen menu "Lembur" pada grid menu dashboard.
  2. Klik link/menu "Lembur" (`//div[contains(@class,'MuiGrid-item')]//p[text()='Lembur']/ancestor::a`).
  3. Tunggu navigasi halaman selesai.
- **Expected Result:** URL berpindah ke `https://magang.dikahadir.com/apps/absent/overtime-client`, header menampilkan "Halaman Lembur" dan memuat "List Overtime".
- **Actual Result:** Lolos eksplorasi aktual (navigasi sukses dan halaman lembur tampil).
- **Test Type:** Positive Testing / Navigation
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 6: EMP-NAV-04 — Navigasi Tombol Kembali ke Dashboard
- **Test Case ID:** EMP-NAV-04
- **Nama Skenario:** Kembali ke Dashboard Menggunakan Tombol Kembali pada Header Modul
- **Preconditions:** User berada pada salah satu halaman riwayat modul (`/correction`, `/permit`, atau `/overtime-client`).
- **Test Steps:**
  1. Klik tombol `< Kembali` pada bagian kiri header.
  2. Tunggu URL halaman berubah.
- **Expected Result:** URL kembali ke `https://magang.dikahadir.com/apps/absent` dan dashboard aktif kembali.
- **Actual Result:** Lolos eksplorasi aktual (URL sukses kembali ke `/apps/absent`).
- **Test Type:** Positive Testing / Navigation
- **Priority:** Medium (P3)
- **Feasibility:** Automatable

---

### Skenario 7: EMP-KOR-01 — Verifikasi Tampilan Form Drawer Ajukan Koreksi Absen
- **Test Case ID:** EMP-KOR-01
- **Nama Skenario:** Menampilkan Drawer Form Pengajuan Koreksi Absen dan Memvalidasi Elemen Input
- **Preconditions:** User berada di halaman `https://magang.dikahadir.com/apps/absent/correction`.
- **Test Steps:**
  1. Klik tombol floating "Ajukan Koreksi".
  2. Tunggu drawer muncul.
  3. Verifikasi judul drawer, field "Jam masuk", field "Jam keluar", tombol "Reset", dan tombol "Ajukan".
- **Expected Result:** Drawer form `Ajukan Koreksi Absen` tampil dari bawah, memuat kedua input jam (placeholder `dd mm yyyy, hh:mm`), tombol Reset, dan tombol Ajukan.
- **Actual Result:** Lolos eksplorasi aktual (seluruh elemen terverifikasi ada dan terlihat).
- **Test Type:** Positive Testing / UI Verification
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 8: EMP-KOR-02 — Interaksi Date & Time Picker pada Koreksi Absen
- **Test Case ID:** EMP-KOR-02
- **Nama Skenario:** Memilih Tanggal dan Jam Masuk Menggunakan Popup MUI Picker
- **Preconditions:** Form drawer "Ajukan Koreksi Absen" terbuka.
- **Test Steps:**
  1. Klik icon picker pada field "Jam masuk" (`button[@aria-label='Choose date']`).
  2. Verifikasi dialog kalender muncul.
  3. Klik tanggal aktif (misal tanggal 2).
  4. Verifikasi dial jam (clock face) muncul.
  5. Periksa nilai yang terisi pada input "Jam masuk".
- **Expected Result:** Nilai input "Jam masuk" terisi otomatis dengan format tanggal dan waktu yang valid (`02 11 2026, 00:00`).
- **Actual Result:** Lolos eksplorasi aktual (input berhasil terisi string `02 11 2026, 00:00`).
- **Test Type:** Positive Testing / Component Interaction
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 9: EMP-KOR-03 — Menutup Drawer Koreksi Absen via Klik Backdrop
- **Test Case ID:** EMP-KOR-03
- **Nama Skenario:** Menutup Drawer Form Koreksi Absen Melalui Klik Area Backdrop
- **Preconditions:** Form drawer "Ajukan Koreksi Absen" terbuka.
- **Test Steps:**
  1. Klik area overlay `div.MuiBackdrop-root` di luar drawer.
  2. Tunggu animasi penutupan drawer.
- **Expected Result:** Drawer tertutup dan elemen input form tidak lagi terlihat di layar.
- **Actual Result:** Lolos eksplorasi aktual (drawer berhasil tertutup, query element drawer bernilai false).
- **Test Type:** Positive Testing / UI Interaction
- **Priority:** Medium (P3)
- **Feasibility:** Automatable

---

### Skenario 10: EMP-KOR-04 — Verifikasi Fungsi Reset pada Form Koreksi Absen (Potential Defect)
- **Test Case ID:** EMP-KOR-04
- **Nama Skenario:** Verifikasi Efek Tombol Reset Terhadap Field Jam Masuk dan Jam Keluar pada Koreksi Absen
- **Preconditions:** Form drawer "Ajukan Koreksi Absen" terbuka.
- **Test Steps:**
  1. Buka picker dan pilih tanggal serta jam untuk field "Jam masuk" (misal terisi `02 11 2026, 00:00`).
  2. Buka picker dan pilih tanggal serta jam untuk field "Jam keluar" (misal terisi `02 11 2026, 00:00`).
  3. Klik tombol "Reset" (`//div[contains(@class,'MuiDrawer')]//button[text()='Reset']`).
  4. Periksa kondisi aktual nilai atribut `value` pada field Jam Masuk dan Jam Keluar.
  5. Jangan klik tombol "Ajukan".
- **Expected Result:** Nilai kedua field dikosongkan kembali ke kondisi awal (string kosong / placeholder `dd mm yyyy, hh:mm`).
- **Actual Result:** **FAIL (Potential Defect DEF-01)** — Nilai kedua input tetap terisi `02 11 2026, 00:00` dan tidak ter-reset.
- **Test Type:** Functional / Potential Defect Verification
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 11: EMP-KOR-05 — Validasi Field Kosong pada Form Koreksi Absen (Negative Testing)
- **Test Case ID:** EMP-KOR-05
- **Nama Skenario:** Pemeriksaan Validasi Pengosongan Field pada Form Koreksi Absen Tanpa Submit
- **Preconditions:** Form drawer "Ajukan Koreksi Absen" terbuka.
- **Test Steps:**
  1. Biarkan field "Jam masuk" dan "Jam keluar" dalam keadaan kosong.
  2. Periksa apakah terdapat atribut HTML5 `required` pada elemen input.
  3. Klik field lalu pindahkan fokus ke luar field (*blur*).
  4. Periksa apakah muncul pesan peringatan lokal tanpa submit form.
  5. Jangan klik tombol "Ajukan" untuk menghindari pengiriman transaksi ke backend.
- **Expected Result:** Sistem memberikan feedback validasi lokal bahwa field wajib diisi.
- **Actual Result:** **Not Verified (Unverified - Requires Submit)** — Atribut HTML5 `required` tidak ditemukan pada tag input (`required=null`), dan tidak ada pesan validasi instan pada event blur. Validasi hanya dapat dipicu saat submit, yang dilarang dieksekusi demi keamanan data.
- **Test Type:** Negative Testing
- **Priority:** Medium (P3)
- **Feasibility:** Not Automatable (Memerlukan aksi submit yang dilarang)

---

### Skenario 12: EMP-IZN-01 — Switch Tab Izin Terlambat dan Pulang Cepat
- **Test Case ID:** EMP-IZN-01
- **Nama Skenario:** Berpindah Antara Tab Izin Terlambat dan Pulang Cepat
- **Preconditions:** User berada di halaman `https://magang.dikahadir.com/apps/absent/permit`.
- **Test Steps:**
  1. Verifikasi tab default "Terlambat" memiliki atribut `aria-selected="true"`.
  2. Verifikasi tombol floating bertuliskan "Ajukan Izin Terlambat".
  3. Klik tab "Pulang Cepat" (`//button[@role='tab' and contains(.,'Pulang Cepat')]`).
  4. Verifikasi tab "Pulang Cepat" menjadi `aria-selected="true"`.
  5. Verifikasi judul riwayat berganti menjadi "List Pulang Cepat" dan tombol floating berganti menjadi "Ajukan Pulang Cepat".
- **Expected Result:** Tab dan konten riwayat berhasil beralih secara dinamis tanpa reload halaman penuh.
- **Actual Result:** Lolos eksplorasi aktual (tab switch, text heading, dan text tombol floating berubah sesuai tab).
- **Test Type:** Positive Testing / Tab Interaction
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 13: EMP-IZN-02 — Verifikasi Form Drawer Ajukan Izin Terlambat
- **Test Case ID:** EMP-IZN-02
- **Nama Skenario:** Menampilkan Drawer Form Pengajuan Izin Terlambat dan Validasi Field
- **Preconditions:** User berada di tab "Terlambat" halaman `/permit`.
- **Test Steps:**
  1. Klik tombol "Ajukan Izin Terlambat".
  2. Tunggu drawer muncul.
  3. Verifikasi judul "Ajukan Izin Terlambat", input Tanggal (`mm/dd/yyyy`), input Jam (`14:00`), textarea Keterangan (`#notes`), tombol Reset, dan tombol Ajukan.
- **Expected Result:** Seluruh komponen form pengajuan izin terlambat tampil lengkap di dalam drawer.
- **Actual Result:** Lolos eksplorasi aktual (semua field dan tombol terverifikasi).
- **Test Type:** Positive Testing / UI Verification
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 14: EMP-IZN-03 — Verifikasi Form Drawer Ajukan Izin Pulang Cepat
- **Test Case ID:** EMP-IZN-03
- **Nama Skenario:** Menampilkan Drawer Form Pengajuan Izin Pulang Cepat dan Validasi Field
- **Preconditions:** User berada di tab "Pulang Cepat" halaman `/permit`.
- **Test Steps:**
  1. Klik tombol "Ajukan Pulang Cepat".
  2. Tunggu drawer muncul.
  3. Verifikasi judul "Ajukan Izin Pulang Cepat", input Tanggal, input Jam, textarea Keterangan, tombol Reset, dan tombol Ajukan.
- **Expected Result:** Seluruh komponen form pengajuan izin pulang cepat tampil lengkap di dalam drawer.
- **Actual Result:** Lolos eksplorasi aktual (semua field dan tombol terverifikasi).
- **Test Type:** Positive Testing / UI Verification
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 15: EMP-IZN-04 — Validasi Field Kosong pada Form Izin (Negative Testing)
- **Test Case ID:** EMP-IZN-04
- **Nama Skenario:** Pemeriksaan Validasi Pengosongan Field Form Izin Tanpa Submit
- **Preconditions:** Form drawer "Ajukan Izin Terlambat" atau "Ajukan Izin Pulang Cepat" terbuka.
- **Test Steps:**
  1. Biarkan field Tanggal, Jam, dan Keterangan dalam keadaan kosong.
  2. Periksa apakah terdapat atribut HTML5 `required` pada field form.
  3. Lakukan interaksi fokus lalu keluar fokus (*blur*).
  4. Amati apakah muncul pesan error inline sebelum pengiriman.
  5. Jangan klik tombol "Ajukan".
- **Expected Result:** Sistem memberikan indikator validasi bahwa field wajib diisi sebelum form dapat dikirim.
- **Actual Result:** **Not Verified (Unverified - Requires Submit)** — Atribut HTML5 `required` tidak ada pada elemen form (`required=null`). Validasi keterisian field hanya dijalankan pada saat event form submission, yang dilarang dieksekusi demi menjaga integritas data demo.
- **Test Type:** Negative Testing
- **Priority:** Medium (P3)
- **Feasibility:** Not Automatable (Memerlukan submit yang dilarang)

---

### Skenario 16: EMP-IZN-05 — Input Batas Teks pada Field Keterangan Izin (Boundary Testing)
- **Test Case ID:** EMP-IZN-05
- **Nama Skenario:** Pengujian Boundary Value Input Keterangan Izin dengan String Panjang
- **Preconditions:** Form drawer pengajuan izin terbuka.
- **Test Steps:**
  1. Siapkan string alfanumerik panjang (misal 255 karakter).
  2. Ketikkan string tersebut ke dalam textarea Keterangan (`textarea#notes`).
  3. Ambil nilai atribut `value` dari textarea.
  4. Periksa apakah teks diterima secara penuh dan apakah tata letak modal tetap rapi.
  5. Jangan klik tombol "Ajukan".
- **Expected Result:** Textarea Keterangan menerima seluruh karakter teks panjang tanpa terjadi pemotongan (*truncation*) tak terduga dan tanpa layout breaking pada drawer mobile.
- **Actual Result:** Lolos eksplorasi aktual (textarea menerima string panjang secara utuh tanpa merusak UI).
- **Test Type:** Boundary Value Testing
- **Priority:** Medium (P3)
- **Feasibility:** Automatable

---

### Skenario 17: EMP-LMB-01 — Verifikasi Form Drawer Ajukan Lembur
- **Test Case ID:** EMP-LMB-01
- **Nama Skenario:** Menampilkan Drawer Form Pengajuan Lembur dan Validasi Field
- **Preconditions:** User berada di halaman `https://magang.dikahadir.com/apps/absent/overtime-client`.
- **Test Steps:**
  1. Klik tombol floating "Ajukan Lembur".
  2. Tunggu drawer muncul.
  3. Verifikasi judul "Ajukan Lembur", field "Jam masuk", field "Jam keluar", textarea "Catatan", tombol Reset, dan tombol Ajukan.
- **Expected Result:** Drawer form pengajuan lembur tampil lengkap dengan seluruh field yang dipersyaratkan.
- **Actual Result:** Lolos eksplorasi aktual (seluruh elemen terverifikasi lengkap).
- **Test Type:** Positive Testing / UI Verification
- **Priority:** High (P2)
- **Feasibility:** Automatable

---

### Skenario 18: EMP-LMB-02 — Input Batas Teks pada Field Catatan Lembur (Boundary Test)
- **Test Case ID:** EMP-LMB-02
- **Nama Skenario:** Pengujian Boundary Value Input Catatan Lembur dengan String Panjang
- **Preconditions:** Form drawer "Ajukan Lembur" terbuka.
- **Test Steps:**
  1. Siapkan string panjang (misal 255 karakter alfanumerik).
  2. Ketik string tersebut ke dalam textarea Catatan (`textarea#notes`).
  3. Ambil nilai atribut `value` dari textarea.
  4. Verifikasi bahwa nilai yang dimasukkan diterima tanpa error UI.
  5. Jangan klik tombol "Ajukan".
- **Expected Result:** Textarea Catatan dapat menerima input teks panjang tanpa truncate atau layout breaking.
- **Actual Result:** Lolos eksplorasi aktual (textarea menerima string teks secara presisi).
- **Test Type:** Boundary Value Testing
- **Priority:** Medium (P3)
- **Feasibility:** Automatable

---

### Skenario 19: EMP-LMB-03 — Validasi Field Kosong pada Form Lembur (Negative Testing)
- **Test Case ID:** EMP-LMB-03
- **Nama Skenario:** Pemeriksaan Validasi Pengosongan Field Form Lembur Tanpa Submit
- **Preconditions:** Form drawer "Ajukan Lembur" terbuka.
- **Test Steps:**
  1. Biarkan field "Jam masuk", "Jam keluar", dan "Catatan" dalam keadaan kosong.
  2. Periksa apakah terdapat atribut HTML5 `required` pada field input dan textarea.
  3. Lakukan interaksi fokus lalu keluar fokus (*blur*).
  4. Periksa apakah ada pesan peringatan visual sebelum submit.
  5. Jangan klik tombol "Ajukan".
- **Expected Result:** Sistem menampilkan peringatan validasi lokal bahwa field belum terisi.
- **Actual Result:** **Not Verified (Unverified - Requires Submit)** — Atribut HTML5 `required` tidak ditemukan pada tag form (`required=null`). Pesan validasi hanya dipicu saat event form submission, yang tidak dapat dijalankan demi mencegah mutasi data backend demo.
- **Test Type:** Negative Testing
- **Priority:** Medium (P3)
- **Feasibility:** Not Automatable (Memerlukan submit yang dilarang)

---

### Skenario 20: EMP-LMB-04 — Menutup Drawer Form Lembur via Klik Backdrop
- **Test Case ID:** EMP-LMB-04
- **Nama Skenario:** Menutup Drawer Form Pengajuan Lembur Melalui Klik Area Backdrop
- **Preconditions:** Form drawer "Ajukan Lembur" terbuka.
- **Test Steps:**
  1. Klik area overlay `div.MuiBackdrop-root` di luar drawer lembur.
  2. Tunggu animasi penutupan drawer selesai.
- **Expected Result:** Drawer form pengajuan lembur tertutup dan elemen input form tidak lagi tampil pada DOM terlihat.
- **Actual Result:** Lolos eksplorasi aktual (drawer form lembur berhasil tertutup secara mulus).
- **Test Type:** Positive Testing / UI Interaction
- **Priority:** Medium (P3)
- **Feasibility:** Automatable

---

## 11. Skenario yang Belum Dapat Diverifikasi (Not Verified) dan Alasannya

Terdapat 3 skenario yang ditandai sebagai **Not Verified** dengan rincian sebagai berikut:

| Test Case ID | Modul | Skenario | Alasan Belum Dapat Diverifikasi |
| :--- | :--- | :--- | :--- |
| **EMP-KOR-05** | Koreksi Absen | Validasi Field Kosong | Elemen input tidak memiliki atribut HTML5 `required`. Validasi field kosong hanya terpicu saat form disubmit via tombol `Ajukan` (`type="submit"`). Tombol `Ajukan` tidak diklik karena aturan ketat STOP CONDITION melarang tindakan yang berpotensi memutasi data backend atau membuat transaksi demo. |
| **EMP-IZN-04** | Izin | Validasi Field Kosong | Tidak ada atribut HTML5 `required` maupun pesan error inline pada event `onBlur`. Validasi hanya dipicu saat submit pengajuan, yang tidak dieksekusi demi keamanan data. |
| **EMP-LMB-03** | Lembur | Validasi Field Kosong | Seluruh input dan textarea tidak memiliki atribut `required`. Validasi hanya terjadi saat form submission, yang dilarang dieksekusi untuk mencegah pembuatan transaksi lembur fiktif pada sistem demo. |

---

## 12. Batasan Keamanan yang Telah Dipatuhi

Selama seluruh proses eksplorasi dan penyempurnaan perencanaan skenario berlangsung, batasan berikut telah dipatuhi secara ketat:
- **Kerahasiaan Kredensial:** Password akun demo tidak pernah dicetak ke terminal konsol, log eksekusi, screenshot, maupun kode automation.
- **Pengecualian Version Control:** File kredensial [config.properties](file:///d:/Juaracoding%20stack/projek-hadir/src/test/resources/config.properties) dilindungi oleh [.gitignore](file:///d:/Juaracoding%20stack/projek-hadir/.gitignore).
- **Integritas Data Demo:** Tidak ada data absensi, koreksi, izin, atau lembur yang dimutasi atau dibuat baru di database backend (zero mutation).
- **Zero Commit / Push:** Tidak ada instruksi Git commit atau Git push yang dijalankan.
- **Integritas Workspace:** Tidak ada file Page Object, Feature File, atau Step Definition baru yang dibuat sebelum mendapat persetujuan eksplisit dari User.
