package com.example.girl.exception;

import com.example.girl.Enum.ResultEnum;
import com.example.girl.VO.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SellException extends RuntimeException {
    private Integer code;

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handel(SellException sellException){
        ResultVO resultVO = new ResultVO(1,sellException.getMessage());
        return resultVO;
    }

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
