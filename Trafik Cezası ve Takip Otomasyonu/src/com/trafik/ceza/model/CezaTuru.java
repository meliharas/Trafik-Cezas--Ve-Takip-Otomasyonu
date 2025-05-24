package com.trafik.ceza.model;

public class CezaTuru {
    private int cezaTuruId;
    private String aciklama;
    private double varsayilanTutar;

    public CezaTuru(int cezaTuruId, String aciklama, double varsayilanTutar) {
        this.cezaTuruId = cezaTuruId;
        this.aciklama = aciklama;
        this.varsayilanTutar = varsayilanTutar;
    }

    public CezaTuru(String aciklama, double varsayilanTutar) {
        this.aciklama = aciklama;
        this.varsayilanTutar = varsayilanTutar;
    }

    public CezaTuru() {
    }

    public int getCezaTuruId() {
        return cezaTuruId;
    }

    public void setCezaTuruId(int cezaTuruId) {
        this.cezaTuruId = cezaTuruId;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public double getVarsayilanTutar() {
        return varsayilanTutar;
    }

    public void setVarsayilanTutar(double varsayilanTutar) {
        this.varsayilanTutar = varsayilanTutar;
    }

    @Override
    public String toString() {
        return aciklama;
    }

    public String getDetayliString() {
        return "Ceza Türü ID: " + cezaTuruId +
               "\nAçıklama: " + aciklama +
               "\nVarsayılan Tutar: " + String.format("%.2f TL", varsayilanTutar);
    }
}