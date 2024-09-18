package com.mkara.BinanceWS.service;

import com.mkara.BinanceWS.domain.model.Price;

public interface PriceService {
    public void updatePrice(String symbol, String price);

    public Price getPrice(String symbol);
}
