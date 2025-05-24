package com.trafik.ceza.model;

public class Arac {
    private int aracId;
    private String plaka;
    private String marka;
    private String model;
    private int yil;
    private int sahipId;

    public Arac(int aracId, String plaka, String marka, String model, int yil, int sahipId) {
        this.aracId = aracId;
        this.plaka = plaka;
        this.marka = marka;
        this.model = model;
        this.yil = yil;
        this.sahipId = sahipId;
    }

    public Arac(String plaka, String marka, String model, int yil, int sahipId) {
        this.plaka = plaka;
        this.marka = marka;
        this.model = model;
        this.yil = yil;
        this.sahipId = sahipId;
    }

    public Arac() {
    }

    public int getAracId() {
        return aracId;
    }

    public void setAracId(int aracId) {
        this.aracId = aracId;
    }

    public String getPlaka() {
        return plaka;
    }

    public void setPlaka(String plaka) {
        this.plaka = plaka;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public int getSahipId() {
        return sahipId;
    }

    public void setSahipId(int sahipId) {
        this.sahipId = sahipId;
    }

    @Override
    public String toString() {
        return "Araç ID: " + aracId +
               "\nPlaka: " + plaka +
               "\nMarka: " + marka +
               "\nModel: " + model +
               "\nYıl: " + yil +
               "\nSahip ID: " + sahipId;
    }
}