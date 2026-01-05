package com.hyungsuu.apigate.samaple.web;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyungsuu.apigate.samaple.kafka.KafkaProducer;
import com.hyungsuu.apigate.samaple.vo.UserReqVo;
import com.hyungsuu.apigate.samaple.vo.UserResVo;
import com.hyungsuu.common.exception.GlobalException;
import com.hyungsuu.common.util.CommonUtil;
import com.hyungsuu.common.vo.BaseResponseVo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaProducerController {
    
    private final KafkaProducer kafkaProducer;
    
    // kafka 메시지를 보내는 함수 
    @RequestMapping(value="/send", method=RequestMethod.POST, consumes = "application/json;charset=UTF-8", produces="application/json;charset=UTF-8")
    public ResponseEntity<?> send(@RequestBody @Valid UserReqVo userReqVo,BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) throws GlobalException {
     
		CommonUtil.checkBindingResult(bindingResult);
   	
    	
    	kafkaProducer.send("example-product-topic", userReqVo);
        

		BaseResponseVo baseResVo = new BaseResponseVo();
		baseResVo.setSuccess();

        return new ResponseEntity<BaseResponseVo>(baseResVo, HttpStatus.OK);
    }
}