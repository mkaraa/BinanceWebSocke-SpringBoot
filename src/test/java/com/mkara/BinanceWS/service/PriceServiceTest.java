package com.mkara.BinanceWS.service;

import com.mkara.BinanceWS.domain.model.Price;
import com.mkara.BinanceWS.service.impl.PriceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {
    private static final String SYMBOL = "BTCUSDT";
    private static final String PRICE_VALUE = "29000.54";

    @InjectMocks
    private PriceServiceImpl priceService;
    @Mock
    private RedisCacheService redisCacheService;

    @Test
    void testUpdateAndGetPrice() {
        when(redisCacheService.getCachedPrice(SYMBOL)).thenReturn(null);

        priceService.updatePrice(SYMBOL, PRICE_VALUE);
        Price actualPrice = priceService.getPrice(SYMBOL);

        verify(redisCacheService).cachePrice(SYMBOL, PRICE_VALUE);
        assertNotNull(actualPrice, "Price should not be null");
        assertEquals(PRICE_VALUE, actualPrice.getPrice(), "Price value should match");
        assertFalse(actualPrice.isExpired(), "Price should not be expired");
    }

    @Test
    void testGetPriceFromCache() {
        when(redisCacheService.getCachedPrice(SYMBOL)).thenReturn(PRICE_VALUE);

        Price actualPrice = priceService.getPrice(SYMBOL);

        verify(redisCacheService).getCachedPrice(SYMBOL);
        assertNotNull(actualPrice, "Price should not be null");
        assertEquals(PRICE_VALUE, actualPrice.getPrice(), "Price value should match");
    }

}
