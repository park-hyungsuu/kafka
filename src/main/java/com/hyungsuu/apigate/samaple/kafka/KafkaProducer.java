package com.hyungsuu.apigate.samaple.kafka;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.apigate.samaple.vo.UserResVo;
import com.hyungsuu.common.exception.GlobalException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class KafkaProducer {

	
    // Kafka 메시지를 전송하기 위한 템플릿 (문자열 키, 값)
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    
	@Autowired
    private RSocketRequester rSocketRequester;

    // kafka에 메시지 전송
    public void send(String topic, UserReqVo userReqVo) throws GlobalException {

    	
	        // ObjectMapper를 통해 ProductRegistDto 객체를 JSON 문자열로 변환
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(userReqVo);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }

        // JSON 문자열을 해당 topic에 전달 
        kafkaTemplate.send(topic, jsonInString);
        
        Mono<String> monoString= rSocketRequester
        		.route("responder-request-response.{id}", 123)
//                .data("data to send")
                .retrieveMono(String.class);
        String result = monoString.block();
        UserResVo userResVo = new UserResVo();
        userResVo.setSuccess();
        userResVo.setData(result);
        log.info("Kafka Producer sent data from the product micro service " + userResVo);

    }

}