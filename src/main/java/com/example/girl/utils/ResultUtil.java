package com.example.girl.utils;

import com.example.girl.VO.ProductVO;
import com.example.girl.VO.ResultVO;

import java.util.List;

public class ResultUtil {
    public static ResultVO success(List<ProductVO> productVOS){
        ResultVO resultVO = new ResultVO();
         resultVO.setCode(0);
         resultVO.setMsg("成功");
         resultVO.setData(productVOS);
         return resultVO;
    }
}
