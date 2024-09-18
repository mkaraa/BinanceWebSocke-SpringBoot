package com.mkara.BinanceWS;

import com.mkara.BinanceWS.domain.model.CryptoSymbolPairs;
import com.mkara.BinanceWS.websocket.BinanceWebSocketClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@EnableCaching
public class BinanceWsApplication {

    private final BinanceWebSocketClient webSocketClient;

    public BinanceWsApplication(BinanceWebSocketClient webSocketClient) {
        this.webSocketClient = webSocketClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(BinanceWsApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            List<String> symbols = CryptoSymbolPairs.getCryptoPairs();
            symbols.forEach(webSocketClient::connect);
        };
    }
}
