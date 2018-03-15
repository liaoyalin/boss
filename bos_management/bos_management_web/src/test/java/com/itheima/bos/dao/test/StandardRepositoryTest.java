package com.itheima.bos.dao.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午9:07:58 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    @Autowired
    private StandardRepository standardRepository;

    @Test
    public void test() {
        List<Standard> list = standardRepository.findAll();
        for (Standard standard : list) {
            System.err.println(standard);
        }
    }
    
    @Test
    public void test02() {
     Standard standard=new Standard();
     standard.setName("1亚索");
     standard.setMaxWeight(100);
     standardRepository.save(standard);
    }

    @Test
    public void test03() {
    Standard standard=standardRepository.findOne(2L);
    System.out.println(standard);
    }
    
    @Test
    public void test04() {
    Standard standard=standardRepository.findOne(2L);
    System.out.println(standard);
    }
    
    @Test
    public void test05() {
    //根据名字找
        Standard findByName = standardRepository.findByName("亚索");
        System.out.println(findByName);
    }
    @Test
    public void test06() {
    //根据名字找
       List<Standard> list = standardRepository.findByNameAndMaxWeight111("托儿索", 200);
       for (Standard standard : list) {
        
           System.out.println(standard);
    }
    }
       @Test
       public void test07() {
       //使用原生sql语句
          List<Standard> list = standardRepository.findByNameAndMaxWeight33342343("托儿索", 200);
          for (Standard standard : list) {
           
              System.out.println(standard);
       }
    }
     // @Transactional//在测试用例中，使用事务注解，方法执行以后，事务回滚了
       @Test
       public void test08() {
       standardRepository.updateWeightByName(500, "托儿索");
    }


}
  
