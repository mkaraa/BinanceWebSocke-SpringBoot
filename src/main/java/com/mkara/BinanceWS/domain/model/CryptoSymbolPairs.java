package com.mkara.BinanceWS.domain.model;

import java.util.Arrays;
import java.util.List;

public class CryptoSymbolPairs {
    private static final List<String> CRYPTO_PAIRS = Arrays.asList(
            "btcusdt", "ethusdt", "avaxusdt",
            "bnbusdt", "solusdt", "adausdt",
            "dogeusdt", "linkusdt", "maticusdt",
            "lunausdt", "xlmusdt", "xrpusdt"
    );

    public static List<String> getCryptoPairs() {
        return CRYPTO_PAIRS;
    }
}
