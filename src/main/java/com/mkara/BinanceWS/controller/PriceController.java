package com.mkara.BinanceWS.controller;

import com.mkara.BinanceWS.domain.model.Price;
import com.mkara.BinanceWS.domain.response.PriceResponse;
import com.mkara.BinanceWS.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/{symbol}")
    public ResponseEntity<PriceResponse> getPrice(@PathVariable String symbol) {
        Price price = priceService.getPrice(symbol);
        if (price == null || price.isExpired()) {
            return ResponseEntity.noContent().build();
        }
        PriceResponse response = new PriceResponse(symbol.toUpperCase(), price.getPrice());
        return ResponseEntity.ok(response);
    }
}