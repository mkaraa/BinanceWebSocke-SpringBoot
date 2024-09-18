package com.mkara.BinanceWS.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.Instant;

@RedisHash("prices")
public class Price implements Serializable {
    @Id
    private String symbol;
    private String price;
    private Instant timestamp;

    public Price(String price, String symbol) {
        this.price = price;
        this.symbol = symbol;
        this.timestamp = Instant.now();
    }

    public Price(String price, String symbol, Instant time) {
        this.price = price;
        this.symbol = symbol;
        this.timestamp = time;
    }

    public Price() {
        
    }

    public String getPrice() {
        return price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isExpired() {
        return timestamp.plusSeconds(5).isBefore(Instant.now());
    }
}
