package com.example.girl.controller;

import com.example.girl.VO.ProductInfoVO;
import com.example.girl.VO.ProductVO;
import com.example.girl.VO.ResultVO;
import com.example.girl.domain.ProductCategory;
import com.example.girl.domain.ProductInfo;
import com.example.girl.service.impl.CategoryServiceImpl;
import com.example.girl.service.impl.ProductServiceImpl;
import com.example.girl.utils.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class ProductBuyerController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CategoryServiceImpl categoryService;
    /**
     * 1.查询全部商品
     * 2.查询分类
     * 3.数据拼装
     * @return
     */
    @GetMapping("/list")
    public ResultVO buyTest(){
        List<ProductInfo> productInfos = productService.findUpAll();

        List<Integer> categoryTypeList = new ArrayList<>();
        for (ProductInfo productInfo : productInfos) {
            categoryTypeList.add(productInfo.getCategoryType());
        }
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeList);


        List<ProductVO> productVOS = new ArrayList<>();
        for (ProductCategory productCategory : categoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            List<ProductInfoVO> productInfoVOs = new ArrayList<>();
            for (ProductInfo productInfo : productInfos) {
                if (productInfo.getCategoryType().equals(productVO.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOs.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOs);
            productVOS.add(productVO);
        }

        return ResultUtil.success(productVOS);
    }
}
