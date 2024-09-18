# Binance WebSocket Price Monitoring and Caching System

## Project Description

This project has been developed to fetch real-time price data from the Binance API and cache this data using Redis.
The project maintains price data both in Redis and in a `ConcurrentHashMap` structure on the JVM.
These structures enable fast access to price data and enhance system performance.

## Content

1. [Starter](#starter)
2. [Tech Stack](#tech-stack)
3. [Installation and Configuration](#installation-and-configuration)
4. [Usage Instructions](#usage-instructions)
5. [Swagger Usage](#swagger-usage)
6. [Author](#author)

## Starter

This project retrieves price data from the Binance WebSocket API and caches this data using Redis. Before you start
using the project, you need to perform the necessary installations and configurations.

### Requirements

- JDK 21
- Maven 3.8.x
- Docker ve Docker Compose
- Redis ve Jedis
- IDE *(ex: Intellij IDEA)*

## Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data Redis**
- **JUnit and Mockito**
- **Jedis**
- **WebSocket API**
- **Docker**
- **Docker Compose**

## Installation and Configuration

### 1. **Install Project Requirements**

Run this command.

```bash
mvn clean install
```

## Redis installation with Docker Compose

### 1.Docker Compose Configuration

Run Redis with `docker-compose.yml` file. Settings are already written in docker-compose.yml

### 2.Docker Compose Start

Please ensure that the same path with `docker-compose.yml` and then run this script in terminal.

```bash
docker-compose up -d
```

## Usage Instructions

### 1. Start Spring Boot application

```bash
mvn spring-boot:run
```

### 2. Binance WebSocket Connection

`BinanceWebSocketClient.java` this class establish connection with Binance WebSocket and then retrieve current price of
that currency.

### 3. Retrieve and Update Prices

`PriceServiceImpl.java` class provides to retrieve and update prices

- Price Update:

```bash
  priceService.updatePrice("ETHUSDT","2312.31");
  ```

- Price Retrieve:

 ```bash
  Price price = priceService.getPrice("ETHUSDT");
  ```

### 4. Available Crypto Currencies:

- `BTCUSDT`
- `ETHUSDT`
- `AVAXUSDT`
- `BNBUSDT`
- `SOLUSDT`
- `ADAUSDT`
- `DOGEUSDT`
- `LINKUSDT`
- `XLMUSDT`
- `XRPUSDT`

### 5. Run test cases with Maven

```bash
mvn clean test
```

## Swagger Usage

You can use Web UI to test application with Swagger which is available on:

- `http://localhost:8080/swagger-ui.html`

# Author

- [Metehan KARA](https://www.linkedin.com/in/metehankara)
