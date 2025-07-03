# 🎙️ Suaraku - Aplikasi Text-to-Speech Indonesia

**Suaraku** adalah aplikasi Android yang memanfaatkan Text-to-Speech (TTS) untuk membaca teks berbahasa Indonesia. Pengguna dapat memilih jenis suara (Laki-laki / Perempuan), mengatur pitch (Tinggi / Normal / Rendah), serta menyimpan teks ke dalam daftar bookmark.

---

## 🧩 Fitur Utama

- ✅ **TTS Bahasa Indonesia (`in_ID`)**
- 🧔‍♂️🧕 **Pilihan Suara Laki-laki & Perempuan**
- 🎚️ **Pengaturan Pitch (Nada Suara)**
- 💾 **Bookmark untuk Simpan Teks**
- 📋 **Deteksi dan Filter Suara Tersedia di Perangkat**

---

## 📸 Screenshot

### Home
<img src="https://github.com/user-attachments/assets/b1cc8d29-05bf-412c-998e-a37fbf5de4d6" width="300" />

### Bookmark
<img src="https://github.com/user-attachments/assets/93df4edf-7521-4781-b2ba-a33f416912e5" width="300" />

### Info
<img src="https://github.com/user-attachments/assets/b968c8c8-a7ee-4a0d-a196-0fece945dccd" width="300" />


---

## 🚀 Cara Install & Jalankan

1. Clone repositori:
   ```bash
   git clone https://github.com/namamu/suaraku.git

2. Buka di Android Studio:
   - Buka Android Studio
   - Pilih **"Open an Existing Project"**
   - Arahkan ke folder `suaraku` yang telah kamu clone

3. Build & Jalankan Aplikasi:
   - Pastikan kamu menggunakan emulator atau device fisik (Android)
   - Klik tombol ▶️ (Run) di Android Studio
   - Tunggu proses **Gradle Build** selesai
   - Aplikasi akan otomatis berjalan di perangkatmu

---

## 🛠️ Teknologi yang Digunakan

- Kotlin
- Android SDK
- TextToSpeech API
- MVVM (Model-View-ViewModel)
- Room Database
- ViewBinding
- Dagger Hilt

---

## 📂 Struktur Direktori
suaraku/
├── app/
│   ├── manifests/
│   │   └── AndroidManifest.xml
│   ├── kotlin+java/
│   │   └── com.example.suaraku/
│   │       ├── adapter/           # Adapter untuk Spinner/RecyclerView
│   │       ├── data/              # Model dan Database
│   │       ├── di/                # Dependency Injection (Hilt Modules)
│   │       ├── repository/        # (Opsional) Layer Repository
│   │       ├── view/              # UI (Fragment, Activity)
│   │       ├── viewmodel/         # ViewModel
│   │       └── MyApp.kt           # Kelas Application
│   └── res/
│       ├── drawable/              # Gambar ikon dan aset grafis lainnya
│       ├── layout/                # File XML layout
│       ├── menu/                  # Menu XML
│       ├── mipmap/                # Ikon launcher
│       ├── values/                # Strings, Styles, Colors, dll.
│       └── xml/                   # Konfigurasi tambahan
├── build.gradle                   # Konfigurasi Gradle
└── ...                            # File dan folder lain

---


## MIT License

Copyright (c) 2025 Revi Faturahman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...


