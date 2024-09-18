package com.mkara.BinanceWS.websocket;

import com.mkara.BinanceWS.service.PriceService;
import jakarta.websocket.*;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@Component
@ClientEndpoint
public class BinanceWebSocketClient {
    private static final String BINANCE_WS_URL = "wss://stream.binance.com:9443/ws/{symbol}@ticker";
    private static final int RECONNECT_DELAY = 5;
    private static final int MAX_RECONNECT_ATTEMPTS = 5;
    private final Logger logger = LoggerFactory.getLogger(BinanceWebSocketClient.class);
    private final ConcurrentMap<String, String> priceData = new ConcurrentHashMap<>();


    private final PriceService priceService;

    public BinanceWebSocketClient(PriceService priceService) {
        this.priceService = priceService;
    }

    @OnOpen
    public void onOpen(Session session) {
        logger.info("Connected to BinanceWebSocket: {}", session.getRequestURI());
    }

    @Async("taskExecutor")
    @OnMessage
    public void onMessage(String message) {
        logger.info("Received message: {}", message);

        JSONObject json = new JSONObject(message);
        String symbol = json.getString("s");
        String price = json.getString("c");

        priceService.updatePrice(symbol, price);

    }

    @OnClose
    public void onClose(Session session) {
        logger.info("BinanceWebSocket connection closed!");
        reconnect(session.getRequestURI());
    }

    private void reconnect(URI uri) {
        int attempts = 0;
        while (attempts < MAX_RECONNECT_ATTEMPTS) {
            try {
                TimeUnit.SECONDS.sleep(RECONNECT_DELAY * (attempts + 1));  // Her denemede süreyi artır
                WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                container.connectToServer(this, uri);
                logger.info("Reconnected to: {}", uri);
                return;  // Başarılı bağlantı sağlandıysa çıkış yap
            } catch (Exception e) {
                attempts++;
                logger.error("Reconnect attempt {} failed: {}", attempts, e.getMessage());
            }
        }
        logger.error("Failed to reconnect after {} attempts", MAX_RECONNECT_ATTEMPTS);
    }

    public void connect(String symbol) {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        URI uri = URI.create(BINANCE_WS_URL.replace("{symbol}", symbol.toLowerCase()));

        try {
            container.connectToServer(this, uri);
            logger.info("Connecting to: {}", uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPrice(String symbol) {
        return priceData.get(symbol);
    }

    public ConcurrentMap<String, String> getPriceData() {
        return priceData;
    }
}
