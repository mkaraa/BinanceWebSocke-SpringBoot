package com.mkara.BinanceWS.service.impl;

import com.mkara.BinanceWS.service.RedisCacheService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisCacheServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void cachePrice(String symbol, String price) {
        long ttlPeriod = 5;
        redisTemplate.opsForValue().set(symbol.toUpperCase(), String.valueOf(price), ttlPeriod, TimeUnit.SECONDS);
    }

    @Override
    public String getCachedPrice(String symbol) {
        return redisTemplate.opsForValue().get(symbol.toUpperCase());
    }

}
