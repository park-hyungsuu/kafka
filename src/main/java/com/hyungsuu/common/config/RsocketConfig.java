package com.hyungsuu.common.config;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;


import reactor.util.retry.Retry;
@Configuration
public class RsocketConfig {
//    @Bean
//    public RSocketRequester getRSocketRequester(RSocketStrategies rSocketStrategies) {
//        return RSocketRequester.builder()
//                .rsocketConnector(connector -> connector.reconnect(Retry.backoff(10, Duration.ofMillis(500))))
//                .rsocketStrategies(rSocketStrategies)
//                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
//                .tcp("localhost", 7575);
//    }
	


    @Bean
    RSocketRequester rSocketRequester(RSocketRequester.Builder builder) {
        return builder.tcp("localhost", 7575);
 //       return builder.websocket(URI.create("ws://localhost:8080/rsocket"));
    }

}

