package com.hyungsuu.apigate.samaple.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyungsuu.apigate.samaple.service.UserService;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor // 생성자 자동 생성
public class KafkaConsumer {
	
	@Autowired
    private UserService userService;

	
	@Autowired
    private RSocketRequester rSocketRequester;
	
    // 등록해놓은 카프카 토픽에 데이터가 들어오면 실행되는 로직
    @KafkaListener(topics = "example-product-topic")
    public void updatePrice(String kafkaMessage) {
        log.info("Kafka Message : -> " + kafkaMessage);

        UserReqVo userReqVo = new UserReqVo();

        // ObjectMapper를 통해 카프카로부터 받은 메시지를 Object로 변환합니다.
        ObjectMapper mapper = new ObjectMapper();
        try {
        	
        	userReqVo = mapper.readValue(kafkaMessage, UserReqVo.class);
        	
			int retVal = userService.insertUser(userReqVo);

       } catch (Exception ex) {
            ex.printStackTrace();
        }
        Mono<String> monoString= rSocketRequester
        		.route("responder-request-response.{id}", 123)
                .data("data to send")
                .retrieveMono(String.class);
        log.info("결과 확인" + " " + kafkaMessage);

    }

}