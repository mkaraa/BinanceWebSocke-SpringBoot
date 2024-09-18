package com.mkara.BinanceWS.service;

public interface RedisCacheService {
    void cachePrice(String symbol, String price);

    String getCachedPrice(String symbol);

}
