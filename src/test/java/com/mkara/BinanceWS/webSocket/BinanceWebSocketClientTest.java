package com.mkara.BinanceWS.webSocket;

import com.mkara.BinanceWS.service.PriceService;
import com.mkara.BinanceWS.websocket.BinanceWebSocketClient;
import jakarta.websocket.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BinanceWebSocketClientTest {

    @Mock
    private Session sessionMock;
    @Mock
    private PriceService priceServiceMock;
    @Mock
    private Logger loggerMock;
    @InjectMocks
    private BinanceWebSocketClient client;

    @Test
    public void testOnMessage_processesMessageCorrectly() {
        String message = "{\"s\": \"BTCUSDT\", \"c\": \"50000\"}";

        client.onMessage(message);

        verify(priceServiceMock).updatePrice("BTCUSDT", "50000");
    }

    @Test
    public void testOnClose_logsClosedMessageAndRetries() {
        client.onClose(sessionMock);

        verify(sessionMock).getRequestURI();
    }

    @Test
    public void testGetPrice_returnsPriceFromMap() {
        String symbol = "BNBUSDT";
        String price = "400";
        client.getPriceData().put(symbol, price);

        String returnedPrice = client.getPrice(symbol);

        assertEquals(price, returnedPrice);
    }

    @Test
    public void testGetPrice_returnsNullForMissingSymbol() {
        String symbol = "LTCUSDT";

        String returnedPrice = client.getPrice(symbol);

        assertNull(returnedPrice);
    }
}