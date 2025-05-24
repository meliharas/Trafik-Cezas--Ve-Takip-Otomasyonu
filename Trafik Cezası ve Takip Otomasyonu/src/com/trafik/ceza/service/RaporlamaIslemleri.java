package com.trafik.ceza.service;

public interface RaporlamaIslemleri<R, P> {
    R raporOlustur(P parametreler) throws Exception;
}