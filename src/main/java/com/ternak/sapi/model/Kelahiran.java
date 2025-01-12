package com.ternak.sapi.model;

public class Kelahiran {
    private String idKejadian;
    private String tanggalLaporan;
    private String tanggalLahir;
    private Peternak peternak;
    private Hewan hewan;
    private Kandang kandang;
    private Petugas petugas;
    private Inseminasi inseminasi;
    private JenisHewan jenisHewan;
    private RumpunHewan rumpunHewan;
    private String lokasi;
    private String kategori;
    private String jumlah;
    private String idHewanAnak;
    private String eartagAnak;
    private String jenisKelaminAnak;
    private String spesies;
    private String urutanIB;

    public Kelahiran() {
    }

    public Kelahiran(String idKejadian, String tanggalLaporan, String tanggalLahir, Peternak peternak,
            Hewan hewan, Kandang kandang, Petugas petugas, Inseminasi inseminasi, String eartagAnak,
            String jenisKelaminAnak, String spesies, String urutanIB, JenisHewan jenisHewan, RumpunHewan rumpunHewan,
            String lokasi, String kategori, String jumlah, String idHewanAnak) {
        this.idKejadian = idKejadian;
        this.tanggalLaporan = tanggalLaporan;
        this.tanggalLahir = tanggalLahir;
        this.peternak = peternak;
        this.hewan = hewan;
        this.kandang = kandang;
        this.petugas = petugas;
        this.inseminasi = inseminasi;
        this.eartagAnak = eartagAnak;
        this.jenisKelaminAnak = jenisKelaminAnak;
        this.spesies = spesies;
        this.urutanIB = urutanIB;
        this.jenisHewan = jenisHewan;
        this.rumpunHewan = rumpunHewan;
        this.lokasi = lokasi;
        this.kategori = kategori;
        this.jumlah = jumlah;
        this.idHewanAnak = idHewanAnak;
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

    public Peternak getPeternak() {
        return peternak;
    }

    public void setPeternak(Peternak peternak) {
        this.peternak = peternak;
    }

    public Hewan getHewan() {
        return hewan;
    }

    public void setHewan(Hewan hewan) {
        this.hewan = hewan;
    }

    public Kandang getKandang() {
        return kandang;
    }

    public void setKandang(Kandang kandang) {
        this.kandang = kandang;
    }

    public Petugas getPetugas() {
        return petugas;
    }

    public void setPetugas(Petugas petugas) {
        this.petugas = petugas;
    }

    public Inseminasi getInseminasi() {
        return inseminasi;
    }

    public void setInseminasi(Inseminasi inseminasi) {
        this.inseminasi = inseminasi;
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

    public JenisHewan getJenisHewan() {
        return jenisHewan;
    }

    public void setJenisHewan(JenisHewan jenisHewan) {
        this.jenisHewan = jenisHewan;
    }

    public RumpunHewan getRumpunHewan() {
        return rumpunHewan;
    }

    public void setRumpunHewan(RumpunHewan rumpunHewan) {
        this.rumpunHewan = rumpunHewan;
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

    public boolean isValid() {
        return this.idKejadian != null &&
                this.peternak != null &&
                this.hewan != null &&
                this.kandang != null &&
                this.petugas != null;
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
