package com.example.girl.VO;

import lombok.Data;

import java.util.List;

@Data
public class ResultVO<T> {
    public Integer code;
    public String msg;
    public List<T> data;

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO() {
    }
}
