package com.ternak.sapi.payload;

public class KelahiranRequest {
    private String idKejadian;
    private String tanggalLaporan;
    private String tanggalLahir;
    private String eartagAnak;
    private String jenisKelaminAnak;
    private String noKartuTernakAnak;
    private String spesies;
    private String urutanIB;
    private String lokasi;
    private String kategori;
    private String jumlah;
    private String idHewanAnak;

    // Petugas
    private String petugasId;
    private String nikPetugas;
    private String namaPetugas;
    private String noTelpPetugas;
    private String emailPetugas;
    private String job;
    private String wilayah;

    // Peternak
    private String idPeternak;
    private String nikPeternak;
    private String namaPeternak;
    private String alamatPeternak;
    private String noTelpPeternak;
    private String emailPeternak;
    private String jenisKelaminPeternak;
    private String idIsikhnas;
    private String tanggalPendaftaran;
    private String lokasiPeternak;
    private String provinsiPeternak;
    private String kabupatenPeternak;
    private String kecamatanPeternak;
    private String desaPeternak;
    private String dusunPeternak;
    private String tanggalLahirPeternak;

    // Kandang
    private String idKandang;
    private String kapasitas;
    private String jenisKandang;
    private String nilaiBangunan;
    private String alamatKandang;
    private String namaKandang;
    private String latitudeKandang;
    private String longitudeKandang;
    private String luas;

    // Hewan
    private String idHewan;
    private String kodeEartagNasional;
    private String noKartuTernak;
    private String sex;
    private String umur;
    private String tanggalLahirHewan;
    private String tempatLahir;
    private String identifikasiHewan;
    private String tanggalTerdaftar;
    private String latitude;
    private String longitude;

    // Jenis Hewan
    private String idJenisHewan;
    private String jenis;
    private String deskripsiJenis;

    // Rumpun Hewan
    private String idRumpunHewan;
    private String rumpun;
    private String deskripsiRumpun;

    // Inseminasi
    private String idInseminasi;
    private String tanggalIB;
    private String ib1;
    private String ib2;
    private String ib3;
    private String ibLain;
    private String idPejantan;
    private String idPembuatan;
    private String bangsaPejantan;
    private String produsen;
    private String lokasiInseminasi;

    public KelahiranRequest() {
    }

    public KelahiranRequest(String idKejadian, String tanggalLaporan, String tanggalLahir, String idPeternak,
            String idHewan, String idKandang, String petugasId, String inseminasi_id, String eartagAnak,
            String jenisKelaminAnak, String noKartuTernakAnak,
            String spesies, String urutanIB, String lokasi, String kategori, String jumlah, String idHewanAnak,
            String nikPetugas, String namaPetugas, String noTelpPetugas, String emailPetugas,
            String job, String wilayah, String nikPeternak, String namaPeternak, String alamatPeternak,
            String noTelpPeternak, String emailPeternak, String jenisKelaminPeternak, String idIsikhnas,
            String tanggalPendaftaran, String lokasiPeternak, String provinsiPeternak, String kabupatenPeternak,
            String kecamatanPeternak, String desaPeternak, String dusunPeternak, String tanggalLahirPeternak,
            String kodeEartagNasional, String noKartuTernak, String sex, String umur, String tanggalLahirHewan,
            String tempatLahir, String identifikasiHewan, String tanggalTerdaftar, String latitude, String longitude,
            String idJenisHewan, String jenis, String deskripsiJenis, String idRumpunHewan, String rumpun,
            String deskripsiRumpun, String idInseminasi, String tanggalIB, String ib1, String ib2, String ib3,
            String ibLain, String idPejantan, String idPembuatan, String bangsaPejantan, String produsen,
            String lokasiInseminasi, String kapasitas, String jenisKandang, String nilaiBangunan, String alamatKandang,
            String namaKandang, String latitudeKandang, String longitudeKandang, String luas) {
        this.idKejadian = idKejadian;
        this.tanggalLaporan = tanggalLaporan;
        this.tanggalLahir = tanggalLahir;
        this.idPeternak = idPeternak;
        this.idHewan = idHewan;
        this.petugasId = petugasId;
        this.eartagAnak = eartagAnak;
        this.jenisKelaminAnak = jenisKelaminAnak;
        this.noKartuTernakAnak = noKartuTernakAnak;
        this.spesies = spesies;
        this.urutanIB = urutanIB;
        this.lokasi = lokasi;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.idHewanAnak = idHewanAnak;
        // Petugas
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.noTelpPetugas = noTelpPetugas;
        this.emailPetugas = emailPetugas;
        this.job = job;
        this.wilayah = wilayah;
        // Peternak
        this.nikPeternak = nikPeternak;
        this.namaPeternak = namaPeternak;
        this.alamatPeternak = alamatPeternak;
        this.noTelpPeternak = noTelpPeternak;
        this.emailPeternak = emailPeternak;
        this.jenisKelaminPeternak = jenisKelaminPeternak;
        this.idIsikhnas = idIsikhnas;
        this.tanggalPendaftaran = tanggalPendaftaran;
        this.lokasiPeternak = lokasiPeternak;
        this.provinsiPeternak = provinsiPeternak;
        this.kabupatenPeternak = kabupatenPeternak;
        this.kecamatanPeternak = kecamatanPeternak;
        this.desaPeternak = desaPeternak;
        this.dusunPeternak = dusunPeternak;
        this.tanggalLahirPeternak = tanggalLahirPeternak;
        // Kandang
        this.idKandang = idKandang;
        this.kapasitas = kapasitas;
        this.jenisKandang = jenisKandang;
        this.nilaiBangunan = nilaiBangunan;
        this.alamatKandang = alamatKandang;
        this.namaKandang = namaKandang;
        this.latitudeKandang = latitudeKandang;
        this.longitudeKandang = longitudeKandang;
        this.luas = luas;

        // Hewan
        this.kodeEartagNasional = kodeEartagNasional;
        this.noKartuTernak = noKartuTernak;
        this.sex = sex;
        this.umur = umur;
        this.tanggalLahirHewan = tanggalLahirHewan;
        this.tempatLahir = tempatLahir;
        this.identifikasiHewan = identifikasiHewan;
        this.tanggalTerdaftar = tanggalTerdaftar;
        this.latitude = latitude;
        this.longitude = longitude;
        // Jenis Hewan
        this.idJenisHewan = idJenisHewan;
        this.jenis = jenis;
        this.deskripsiJenis = deskripsiJenis;
        // Rumpun Hewan
        this.idRumpunHewan = idRumpunHewan;
        this.rumpun = rumpun;
        this.deskripsiRumpun = deskripsiRumpun;
        // Inseminasi
        this.idInseminasi = idInseminasi;
        this.tanggalIB = tanggalIB;
        this.ib1 = ib1;
        this.ib2 = ib2;
        this.ib3 = ib3;
        this.ibLain = ibLain;
        this.idPejantan = idPejantan;
        this.idPembuatan = idPembuatan;
        this.bangsaPejantan = bangsaPejantan;
        this.produsen = produsen;
        this.lokasiInseminasi = lokasiInseminasi;
    }

    public String getIdKejadian() {
        return idKejadian;
    }

    public void setIdKejadian(String idKejadian) {
        this.idKejadian = idKejadian;
    }

    public String getTanggalLaporan() {
        return tanggalLaporan;
    }

    public void setTanggalLaporan(String tanggalLaporan) {
        this.tanggalLaporan = tanggalLaporan;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getIdPeternak() {
        return idPeternak;
    }

    public void setIdPeternak(String idPeternak) {
        this.idPeternak = idPeternak;
    }

    public String getIdHewan() {
        return idHewan;
    }

    public void setIdHewan(String idHewan) {
        this.idHewan = idHewan;
    }

    public String getPetugasId() {
        return petugasId;
    }

    public void setPetugasId(String petugasId) {
        this.petugasId = petugasId;
    }

    public String getEartagAnak() {
        return eartagAnak;
    }

    public void setEartagAnak(String eartagAnak) {
        this.eartagAnak = eartagAnak;
    }

    public String getJenisKelaminAnak() {
        return jenisKelaminAnak;
    }

    public void setJenisKelaminAnak(String jenisKelaminAnak) {
        this.jenisKelaminAnak = jenisKelaminAnak;
    }

    public String getNoKartuTernakAnak() {
        return noKartuTernakAnak;
    }

    public void setNoKartuTernakAnak(String noKartuTernakAnak) {
        this.noKartuTernakAnak = noKartuTernakAnak;
    }

    public String getSpesies() {
        return spesies;
    }

    public void setSpesies(String spesies) {
        this.spesies = spesies;
    }

    public String getUrutanIB() {
        return urutanIB;
    }

    public void setUrutanIB(String urutanIB) {
        this.urutanIB = urutanIB;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getIdHewanAnak() {
        return idHewanAnak;
    }

    public void setIdHewanAnak(String idHewanAnak) {
        this.idHewanAnak = idHewanAnak;
    }

    // Petugas
    public String getNikPetugas() {
        return nikPetugas;
    }

    public void setNikPetugas(String nikPetugas) {
        this.nikPetugas = nikPetugas;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getNoTelpPetugas() {
        return noTelpPetugas;
    }

    public void setNoTelpPetugas(String noTelpPetugas) {
        this.noTelpPetugas = noTelpPetugas;
    }

    public String getEmailPetugas() {
        return emailPetugas;
    }

    public void setEmailPetugas(String emailPetugas) {
        this.emailPetugas = emailPetugas;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }

    // Peternak

    public String getNikPeternak() {
        return nikPeternak;
    }

    public void setNikPeternak(String nikPeternak) {
        this.nikPeternak = nikPeternak;
    }

    public String getNamaPeternak() {
        return namaPeternak;
    }

    public void setNamaPeternak(String namaPeternak) {
        this.namaPeternak = namaPeternak;
    }

    public String getAlamatPeternak() {
        return alamatPeternak;
    }

    public void setAlamatPeternak(String alamatPeternak) {
        this.alamatPeternak = alamatPeternak;
    }

    public String getNoTelpPeternak() {
        return noTelpPeternak;
    }

    public void setNoTelpPeternak(String noTelpPeternak) {
        this.noTelpPeternak = noTelpPeternak;
    }

    public String getEmailPeternak() {
        return emailPeternak;
    }

    public void setEmailPeternak(String emailPeternak) {
        this.emailPeternak = emailPeternak;
    }

    public String getJenisKelaminPeternak() {
        return jenisKelaminPeternak;
    }

    public void setJenisKelaminPeternak(String jenisKelaminPeternak) {
        this.jenisKelaminPeternak = jenisKelaminPeternak;
    }

    public String getIdIsikhnas() {
        return idIsikhnas;
    }

    public void setIdIsikhnas(String idIsikhnas) {
        this.idIsikhnas = idIsikhnas;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTanggalPendaftaran(String tanggalPendaftaran) {
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

    public String getLokasiPeternak() {
        return lokasiPeternak;
    }

    public void setLokasiPeternak(String lokasiPeternak) {
        this.lokasiPeternak = lokasiPeternak;
    }

    public String getProvinsiPeternak() {
        return provinsiPeternak;
    }

    public void setProvinsiPeternak(String provinsiPeternak) {
        this.provinsiPeternak = provinsiPeternak;
    }

    public String getKabupatenPeternak() {
        return kabupatenPeternak;
    }

    public void setKabupatenPeternak(String kabupatenPeternak) {
        this.kabupatenPeternak = kabupatenPeternak;
    }

    public String getKecamatanPeternak() {
        return kecamatanPeternak;
    }

    public void setKecamatanPeternak(String kecamatanPeternak) {
        this.kecamatanPeternak = kecamatanPeternak;
    }

    public String getDesaPeternak() {
        return desaPeternak;
    }

    public void setDesaPeternak(String desaPeternak) {
        this.desaPeternak = desaPeternak;
    }

    public String getDusunPeternak() {
        return dusunPeternak;
    }

    public void setDusunPeternak(String dusunPeternak) {
        this.dusunPeternak = dusunPeternak;
    }

    public String getTanggalLahirPeternak() {
        return tanggalLahirPeternak;
    }

    public void setTanggalLahirPeternak(String tanggalLahirPeternak) {
        this.tanggalLahirPeternak = tanggalLahirPeternak;
    }

    // Kandang
    public String getIdKandang() {
        return idKandang;
    }

    public void setIdKandang(String idKandang) {
        this.idKandang = idKandang;
    }

    public String getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(String kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getJenisKandang() {
        return jenisKandang;
    }

    public void setJenisKandang(String jenisKandang) {
        this.jenisKandang = jenisKandang;
    }

    public String getNilaiBangunan() {
        return nilaiBangunan;
    }

    public void setNilaiBangunan(String nilaiBangunan) {
        this.nilaiBangunan = nilaiBangunan;
    }

    public String getAlamatKandang() {
        return alamatKandang;
    }

    public void setAlamatKandang(String alamatKandang) {
        this.alamatKandang = alamatKandang;
    }

    public String getNamaKandang() {
        return namaKandang;
    }

    public void setNamaKandang(String namaKandang) {
        this.namaKandang = namaKandang;
    }

    public String getLatitudeKandang() {
        return latitudeKandang;
    }

    public void setLatitudeKandang(String latitudeKandang) {
        this.latitudeKandang = latitudeKandang;
    }

    public String getLongitudeKandang() {
        return longitudeKandang;
    }

    public void setLongitudeKandang(String longitudeKandang) {
        this.longitudeKandang = longitudeKandang;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    // Hewan

    public String getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public String getNoKartuTernak() {
        return noKartuTernak;
    }

    public void setNoKartuTernak(String noKartuTernak) {
        this.noKartuTernak = noKartuTernak;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getTanggalLahirHewan() {
        return tanggalLahirHewan;
    }

    public void setTanggalLahirHewan(String tanggalLahirHewan) {
        this.tanggalLahirHewan = tanggalLahirHewan;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getIdentifikasiHewan() {
        return identifikasiHewan;
    }

    public void setIdentifikasiHewan(String identifikasiHewan) {
        this.identifikasiHewan = identifikasiHewan;
    }

    public String getTanggalTerdaftar() {
        return tanggalTerdaftar;
    }

    public void setTanggalTerdaftar(String tanggalTerdaftar) {
        this.tanggalTerdaftar = tanggalTerdaftar;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    // Jenis Hewan

    public String getIdJenisHewan() {
        return idJenisHewan;
    }

    public void setIdJenisHewan(String idJenisHewan) {
        this.idJenisHewan = idJenisHewan;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getDeskripsiJenis() {
        return deskripsiJenis;
    }

    public void setDeskripsiJenis(String deskripsiJenis) {
        this.deskripsiJenis = deskripsiJenis;
    }

    // Rumpun Hewan

    public String getIdRumpunHewan() {
        return idRumpunHewan;
    }

    public void setIdRumpunHewan(String idRumpunHewan) {
        this.idRumpunHewan = idRumpunHewan;
    }

    public String getRumpun() {
        return rumpun;
    }

    public void setRumpun(String rumpun) {
        this.rumpun = rumpun;
    }

    public String getDeskripsiRumpun() {
        return deskripsiRumpun;
    }

    public void setDeskripsiRumpun(String deskripsiRumpun) {
        this.deskripsiRumpun = deskripsiRumpun;
    }

    // Inseminasi

    public String getIdInseminasi() {
        return idInseminasi;
    }

    public void setIdInseminasi(String idInseminasi) {
        this.idInseminasi = idInseminasi;
    }

    public String getTanggalIB() {
        return tanggalIB;
    }

    public void setTanggalIB(String tanggalIB) {
        this.tanggalIB = tanggalIB;
    }

    public String getIb1() {
        return ib1;
    }

    public void setIb1(String ib1) {
        this.ib1 = ib1;
    }

    public String getIb2() {
        return ib2;
    }

    public void setIb2(String ib2) {
        this.ib2 = ib2;
    }

    public String getIb3() {
        return ib3;
    }

    public void setIb3(String ib3) {
        this.ib3 = ib3;
    }

    public String getIbLain() {
        return ibLain;
    }

    public void setIbLain(String ibLain) {
        this.ibLain = ibLain;
    }

    public String getIdPejantan() {
        return idPejantan;
    }

    public void setIdPejantan(String idPejantan) {
        this.idPejantan = idPejantan;
    }

    public String getIdPembuatan() {
        return idPembuatan;
    }

    public void setIdPembuatan(String idPembuatan) {
        this.idPembuatan = idPembuatan;
    }

    public String getBangsaPejantan() {
        return bangsaPejantan;
    }

    public void setBangsaPejantan(String bangsaPejantan) {
        this.bangsaPejantan = bangsaPejantan;
    }

    public String getProdusen() {
        return produsen;
    }

    public void setProdusen(String produsen) {
        this.produsen = produsen;
    }

    public String getLokasiInseminasi() {
        return lokasiInseminasi;
    }

    public void setLokasiInseminasi(String lokasiInseminasi) {
        this.lokasiInseminasi = lokasiInseminasi;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idKejadian":
                this.idKejadian = value;
                break;
            case "tanggalLaporan":
                this.tanggalLaporan = value;
                break;
            case "tanggalLahir":
                this.tanggalLahir = value;
                break;
            case "idPeternak":
                this.idPeternak = value;
                break;
            case "idHewan":
                this.idHewan = value;
                break;
            case "idKandang":
                this.idKandang = value;
                break;
            case "petugasId":
                this.petugasId = value;
                break;
            case "idInseminasi":
                this.idInseminasi = value;
                break;
            case "eartagAnak":
                this.eartagAnak = value;
                break;
            case "jenisKelaminAnak":
                this.jenisKelaminAnak = value;
                break;
            case "spesies":
                this.spesies = value;
                break;
            case "urutanIB":
                this.urutanIB = value;
                break;
            case "lokasi":
                this.lokasi = value;
                break;
            case "kategori":
                this.kategori = value;
                break;
            case "jumlah":
                this.jumlah = value;
                break;
            case "idHewanAnak":
                this.idHewanAnak = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
