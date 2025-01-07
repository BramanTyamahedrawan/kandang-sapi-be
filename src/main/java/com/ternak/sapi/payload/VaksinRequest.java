package com.ternak.sapi.payload;

public class VaksinRequest {
    private String idVaksin;
    private String peternak_id;
    private String hewan_id;
    private String petugas_id;
    private String namaVaksin;
    private String jenisVaksin;
    private String tglVaksin;
    private String batchVaksin;
    private String vaksinKe;
    private String tglPendataan;
    private String nikPetugas;
    private String namaPetugas;
    private String nikPeternak;
    private String namaPeternak;
    private String noTelp;
    private String kodeEartagNasional;

    public VaksinRequest() {
    }

    public VaksinRequest(String idVaksin, String peternak_id, String hewan_id, String petugas_id,
            String namaVaksin, String jenisVaksin, String tglVaksin,
            String tglPendataan, String nikPetugas, String namaPetugas, String nikPeternak, String namaPeternak,
            String noTelp, String kodeEartagNasional) {
        this.idVaksin = idVaksin;
        this.peternak_id = peternak_id;
        this.hewan_id = hewan_id;
        this.petugas_id = petugas_id;
        this.namaVaksin = namaVaksin;
        this.jenisVaksin = jenisVaksin;
        this.tglVaksin = tglVaksin;
        this.batchVaksin = "";
        this.vaksinKe = "";
        this.tglPendataan = tglPendataan;
        this.nikPetugas = nikPetugas;
        this.namaPetugas = namaPetugas;
        this.nikPeternak = nikPeternak;
        this.namaPeternak = namaPeternak;
        this.noTelp = noTelp;
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public String getIdVaksin() {
        return idVaksin;
    }

    public void setIdVaksin(String idVaksin) {
        this.idVaksin = idVaksin;
    }

    public String getPeternak_id() {
        return peternak_id;
    }

    public void setPeternak_id(String peternak_id) {
        this.peternak_id = peternak_id;
    }

    public String getHewan_id() {
        return hewan_id;
    }

    public void setHewan_id(String hewan_id) {
        this.hewan_id = hewan_id;
    }

    public String getPetugas_id() {
        return petugas_id;
    }

    public void setPetugas_id(String petugas_id) {
        this.petugas_id = petugas_id;
    }

    public String getNamaVaksin() {
        return namaVaksin;
    }

    public void setNamaVaksin(String namaVaksin) {
        this.namaVaksin = namaVaksin;
    }

    public String getJenisVaksin() {
        return jenisVaksin;
    }

    public void setJenisVaksin(String jenisVaksin) {
        this.jenisVaksin = jenisVaksin;
    }

    public String getTglVaksin() {
        return tglVaksin;
    }

    public void setTglVaksin(String tglVaksin) {
        this.tglVaksin = tglVaksin;
    }

    public String getBatchVaksin() {
        return batchVaksin;
    }

    public void setBatchVaksin(String batchVaksin) {
        this.batchVaksin = batchVaksin;
    }

    public String getVaksinKe() {
        return vaksinKe;
    }

    public void setVaksinKe(String vaksinKe) {
        this.vaksinKe = vaksinKe;
    }

    public String getTglPendataan() {
        return tglPendataan;
    }

    public void setTglPendataan(String tglPendataan) {
        this.tglPendataan = tglPendataan;
    }

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

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public String getKodeEartagNasional() {
        return kodeEartagNasional;
    }

    public void setKodeEartagNasional(String kodeEartagNasional) {
        this.kodeEartagNasional = kodeEartagNasional;
    }

    public void set(String fieldName, String value) {
        switch (fieldName) {
            case "idVaksin":
                this.idVaksin = value;
                break;
            case "peternak_id":
                this.peternak_id = value;
                break;
            case "hewan_id":
                this.hewan_id = value;
                break;
            case "petugas_id":
                this.petugas_id = value;
                break;
            case "namaVaksin":
                this.namaVaksin = value;
                break;
            case "jenisVaksin":
                this.jenisVaksin = value;
                break;
            case "tglVaksin":
                this.tglVaksin = value;
                break;
            case "batchVaksin":
                this.batchVaksin = value;
                break;
            case "vaksinKe":
                this.vaksinKe = value;
                break;
            case "tglPendataan":
                this.tglPendataan = value;
                break;
            case "nikPetugas":
                this.nikPetugas = value;
                break;
            case "namaPetugas":
                this.namaPetugas = value;
                break;
            case "nikPeternak":
                this.nikPeternak = value;
                break;
            case "namaPeternak":
                this.namaPeternak = value;
                break;
            case "noTelp":
                this.noTelp = value;
                break;
            case "kodeEartagNasional":
                this.kodeEartagNasional = value;
                break;
            default:
                throw new IllegalArgumentException("Invalid field name: " + fieldName);
        }
    }
}
