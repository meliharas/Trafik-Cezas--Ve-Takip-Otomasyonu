package com.trafik.ceza.service;

import com.trafik.ceza.model.Arac;
import java.util.List;

public interface AracService extends KayitIslemleri<Arac, Integer>, AramaIslemleri<Arac, String> {
    
    List<Arac> sahibeGoreAracListele(int sahipId) throws Exception;

    List<Arac> yilaGoreAracListele(int yil) throws Exception;
}