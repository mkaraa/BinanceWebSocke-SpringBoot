package com.mkara.BinanceWS.service;

import com.mkara.BinanceWS.service.impl.RedisCacheServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RedisCacheServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private RedisCacheServiceImpl redisCacheService;

    @BeforeEach
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testCachePrice_Success() {
        String symbol = "BTCUSDT";
        String price = "29000.54";

        redisCacheService.cachePrice(symbol, price);

        verify(valueOperations, times(1)).set(symbol.toUpperCase(), price, 5, TimeUnit.SECONDS);
    }

    @Test
    void testCachePrice_NullSymbol() {
        String symbol = null;
        String price = "29000.54";

        assertThrows(NullPointerException.class, () -> redisCacheService.cachePrice(symbol, price));
    }

    @Test
    void testGetCachedPrice_Success() {
        // Arrange
        String symbol = "BTCUSDT";
        String price = "29000.54";

        when(valueOperations.get(symbol.toUpperCase())).thenReturn(price);

        // Act
        String cachedPrice = redisCacheService.getCachedPrice(symbol);

        // Assert
        assertEquals(price, cachedPrice);
        verify(valueOperations, times(1)).get(symbol.toUpperCase());
    }

    @Test
    void testGetCachedPrice_NullSymbol() {
        // Arrange
        String symbol = null;

        // Act & Assert
        assertThrows(NullPointerException.class, () -> redisCacheService.getCachedPrice(symbol));
    }

    @Test
    void testGetCachedPrice_NotFound() {
        // Arrange
        String symbol = "ETHUSDT";

        when(valueOperations.get(symbol.toUpperCase())).thenReturn(null);

        // Act
        String cachedPrice = redisCacheService.getCachedPrice(symbol);

        // Assert
        assertNull(cachedPrice);
        verify(valueOperations, times(1)).get(symbol.toUpperCase());
    }
}
