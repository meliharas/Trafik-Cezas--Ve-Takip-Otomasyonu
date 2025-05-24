package com.trafik.ceza.service;

import java.util.List;

public interface KayitIslemleri<T, ID> {
    void ekle(T entity) throws Exception;
    void guncelle(T entity) throws Exception;
    void sil(ID id) throws Exception;
    T idIleBul(ID id) throws Exception;
    List<T> tumunuListele() throws Exception;
}