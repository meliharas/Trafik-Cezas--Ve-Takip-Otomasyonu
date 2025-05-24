package com.trafik.ceza.service;

import java.util.List;

public interface AramaIslemleri<T, K> {
    List<T> kritereGoreAra(K kriter) throws Exception;
}