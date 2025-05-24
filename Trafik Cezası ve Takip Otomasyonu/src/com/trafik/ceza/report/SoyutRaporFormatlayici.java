package com.trafik.ceza.report;

import java.util.List;

public abstract class SoyutRaporFormatlayici {

    protected abstract String baslikFormatla(String raporAdi);
    protected abstract List<String> icerikFormatla(List<Object> veriListesi);
    protected abstract String altBilgiFormatla(String ekBilgi);

    public final String raporOlustur(String raporAdi, List<Object> veriListesi, String ekAltBilgi) {
        StringBuilder raporBuilder = new StringBuilder();

        raporBuilder.append(baslikFormatla(raporAdi));
        raporBuilder.append("\n------------------------------------\n");

        List<String> formatlanmisIcerik = icerikFormatla(veriListesi);
        for (String satir : formatlanmisIcerik) {
            raporBuilder.append(satir).append("\n");
        }

        raporBuilder.append("------------------------------------\n");
        raporBuilder.append(altBilgiFormatla(ekAltBilgi));
        raporBuilder.append("\n--- Rapor Sonu ---\n");

        return raporBuilder.toString();
    }

    public String getTimestamp() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new java.util.Date());
    }
}