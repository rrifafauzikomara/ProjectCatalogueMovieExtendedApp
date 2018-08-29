# Project Catalogue Movie Extended
Aplikasi katalog movie untuk submission terakhir beasiswa MADE (Menjadi Android Developer Expert) dari Dicoding https://www.dicoding.com/academies/14/.

### Screenshots Aplikasi Pertama

<pre>
<img src="Screenshot/Screenshot_20180829-163432_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163457_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163511_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163526_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163542_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163546_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163551_Settings.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163607_Catalogue Film.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-163610_Catalogue Film.jpg" width="250" height="444">
</pre>

### Screenshots Aplikasi Kedua (***New Module***)
<pre>
<img src="Screenshot/Screenshot_20180829-164313_favoritemovie.jpg" width="250" height="444">         <img src="Screenshot/Screenshot_20180829-164321_favoritemovie.jpg" width="250" height="444">
</pre>

### Screenshots Widget Setelah Menambahkan Favorit Movie
<pre>
<img src="Screenshot/Screenshot_20180829-163706_Samsung Experience Home.jpg" width="250" height="444">
</pre>

### Persyaratan aplikasi

* [x] Tombol pada detail untuk menambahkan film favorit.
* [x] Halaman untuk menampilkan list movie favorit.
* [x] Menggunakan **contentprovider**.
* [x] Membuat aplikasi baru yaitu aplikasi favorit (boleh dengan menggunakan module baru) untuk mengakses list favorit.


### Petunjuk menjalankan source code aplikasi

Untuk menjalankan source code aplikasi ini, anda perlu registrasi API KEY dari www.themoviedb.org
kemudian memasukkan API KEY yang telah didapat ke dalam file ***gradle.properties***

```
MovieDbApiKey="Masukan API KEY anda disini"
```

Kemudian tambah baris berikut pada file ***build.gradle*** dibawah ***buildTypes***

```
buildTypes.each {
        it.buildConfigField 'String', 'MOVIE_DB_API_KEY', MovieDbApiKey
    }
```

## Author

* **R Rifa Fauzi Komara**

Jangan lupa untuk follow dan ★
