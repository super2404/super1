package com.example.girl.repository;

import com.example.girl.domain.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    public ProductCategoryRepository repository;

    @Test
    public void findOneTest(){
        ProductCategory one = repository.findOne(1);
        System.out.println(one);
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = repository.findOne(2);
        productCategory.setCategoryName("iii");
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByListTest(){
        List<Integer> list = Arrays.asList(1,2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());
    }
}