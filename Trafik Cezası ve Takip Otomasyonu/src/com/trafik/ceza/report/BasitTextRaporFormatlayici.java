package com.trafik.ceza.report;

import com.trafik.ceza.model.Ceza; // Örnek veri tipi olarak Ceza modelini kullanalım
import java.util.ArrayList;
import java.util.List;

public class BasitTextRaporFormatlayici extends SoyutRaporFormatlayici {

    @Override
    protected String baslikFormatla(String raporAdi) {
        return "===== " + raporAdi.toUpperCase() + " =====" +
               "\nRapor Oluşturma Tarihi: " + getTimestamp();
    }

    @Override
    protected List<String> icerikFormatla(List<Object> veriListesi) {
        List<String> formatlanmisSatirlar = new ArrayList<>();
        if (veriListesi == null || veriListesi.isEmpty()) {
            formatlanmisSatirlar.add("Raporlanacak veri bulunamadı.");
            return formatlanmisSatirlar;
        }

        for (Object veri : veriListesi) {
            if (veri instanceof Ceza) {
                Ceza ceza = (Ceza) veri;
                String satir = String.format("Ceza ID: %d, Plaka (Araç ID): %d, Tutar: %.2f TL, Tarih: %s, Durum: %s",
                                             ceza.getCezaId(),
                                             ceza.getAracId(),
                                             ceza.getKesilenTutar(),
                                             ceza.getCezaTarihi(),
                                             ceza.getOdemeDurumuStr());
                formatlanmisSatirlar.add(satir);
            } else {
                formatlanmisSatirlar.add(veri.toString());
            }
        }
        return formatlanmisSatirlar;
    }

    @Override
    protected String altBilgiFormatla(String ekBilgi) {
        return "Ek Bilgiler: " + (ekBilgi == null || ekBilgi.isEmpty() ? "Yok" : ekBilgi) +
               "\nBu rapor sistem tarafından otomatik olarak oluşturulmuştur.";
    }

    public static void main(String[] args) {
        BasitTextRaporFormatlayici formatlayici = new BasitTextRaporFormatlayici();
        List<Object> testVerisi = new ArrayList<>();
        
        Ceza ceza1 = new Ceza(1, 101, 201, "2024-05-01", "10:30", "Ankara Yolu", 150.75, 0, "Hız aşımı");
        Ceza ceza2 = new Ceza(2, 102, 202, "2024-05-02", "14:15", "İstanbul Cad.", 250.00, 1, "Kırmızı ışık");
        
        testVerisi.add(ceza1);
        testVerisi.add(ceza2);
        testVerisi.add("Bu bir test verisidir.");

        String rapor = formatlayici.raporOlustur("Aylık Ceza Raporu", testVerisi, "Mayıs 2024 dönemi");
        System.out.println(rapor);
    }
}