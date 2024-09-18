package com.mkara.BinanceWS.domain.response;

public class PriceResponse {
    private String symbol;
    private String price;

    public PriceResponse(String symbol, String price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPrice() {
        return price;
    }
}
