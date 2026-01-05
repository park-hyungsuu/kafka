package com.hyungsuu.apigate.samaple.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.hyungsuu.common.vo.BaseResponseVo;

import lombok.Data;



@SuppressWarnings("serial")
@Data
public class UserResVo extends BaseResponseVo implements Serializable {

	private Object data;

}
