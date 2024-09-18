package com.mkara.BinanceWS.service.impl;

import com.mkara.BinanceWS.domain.model.Price;
import com.mkara.BinanceWS.service.PriceService;
import com.mkara.BinanceWS.service.RedisCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PriceServiceImpl implements PriceService {

    private final Logger logger = LoggerFactory.getLogger(PriceServiceImpl.class);

    private final ConcurrentHashMap<String, Price> priceMap = new ConcurrentHashMap<>();

    private final RedisCacheService redisCacheService;

    public PriceServiceImpl(RedisCacheService redisCacheService) {
        this.redisCacheService = redisCacheService;
    }

    @Override
    public void updatePrice(String symbol, String price) {
        Price priceObj = new Price(price, symbol.toUpperCase());
        priceMap.put(symbol.toUpperCase(), priceObj);
        redisCacheService.cachePrice(symbol, price);
        logger.info(" {} Price is updated: {} ", symbol.toUpperCase(), price);
    }

    @Override
    public Price getPrice(String symbol) {
        // Check Redis cache
        String cachedPrice = redisCacheService.getCachedPrice(symbol);
        if (cachedPrice != null) {
            logger.info(" {} Price fetched from Redis: {}", symbol.toUpperCase(), cachedPrice);
            Price priceFromCache = new Price(cachedPrice, symbol.toUpperCase());
            if (!priceFromCache.isExpired()) {
                return priceFromCache;
            } else {
                logger.info("{} price from Redis has expired and is removed.", symbol);
            }
        }

        // Check in ConcurrentHashMap
        Price price = priceMap.get(symbol.toUpperCase());
        if (price != null && !price.isExpired()) {
            logger.info(" {} Price fetched from ConcurrentHashMap: {}", symbol.toUpperCase(), price);
            return price;
        }

        // Return null if no valid price is found
        return null;
    }

}