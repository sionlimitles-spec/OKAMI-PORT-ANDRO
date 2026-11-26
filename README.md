# Okami Recomp - Manager Shell (UI mockup)

Ini adalah **shell antarmuka (UI shell)** untuk proyek konsep "Okami on Android via static recompilation". Repo ini dibuat dari sesi brainstorming desain, bukan hasil riset recompilation yang sudah selesai.

## Status sejujurnya

- ✅ Aplikasi Android asli (Kotlin + WebView), bisa di-build jadi APK dan diinstall.
- ✅ Tampilan: kartu info game/perangkat/status, animasi micro-interaction, bottom nav.
- ❌ **Belum bisa membaca atau memainkan ISO apapun.** Tombol "pilih ISO" masih dummy.
- ❌ Belum ada mesin emulasi/recompilation Okami di dalamnya.

## Kenapa belum bisa main?

Membuat Okami benar-benar berjalan di Android lewat *static recompilation* (ala [Black-Android](https://github.com/RanggaCasper/Black-Android)) adalah proyek riset reverse-engineering multi-bulan: toolchain intinya, [PS2Recomp](https://github.com/ran-j/PS2Recomp), sampai saat ini belum punya satupun judul yang benar-benar selesai, dan renderer cel-shading Okami (lewat VU0/VU1) termasuk salah satu yang paling kompleks untuk PS2.

**Jalan pintas yang jujur dan nyata hari ini:** kalau tujuannya cuma "main Okami di HP", emulator PS2 open-source yang sudah matang seperti [Play!](https://github.com/jpd002/Play--Emulator) sudah mendukung banyak judul PS2 termasuk Okami, dan sudah punya build Android resmi. Static recompilation (seperti repo ini) baru masuk akal kalau tujuannya preservasi/performa native, bukan "ingin main sekarang juga".

## Build APK sendiri

### Lewat GitHub Actions (tanpa install apapun di HP/PC)
Setiap push ke `main` otomatis membangun APK lewat GitHub Actions. Ambil hasilnya di tab **Actions** → pilih run terakhir → unduh artifact `OkamiRecomp-debug-apk`.

### Lewat Android Studio
1. Clone repo ini.
2. Buka dengan Android Studio, tunggu Gradle sync.
3. Klik **Run** ▶, atau ambil APK dari `app/build/outputs/apk/debug/`.

## Struktur

- `app/src/main/assets/index.html` - UI (HTML/CSS/JS murni, di-render lewat WebView)
- `app/src/main/java/.../MainActivity.kt` - activity utama
