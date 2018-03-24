package com.itheima.portal.test;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**  
 * ClassName:RedisTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午3:56:24 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class RedisTest {
    @Autowired
    private RedisTemplate<String , String> redisTemplate;
    @Test
    public void test(){
      //  redisTemplate.opsForValue().set("name", "zhangsan");
        //可以设置数据的有效期，定时任务
        //参数3：时间
        //参数4：时间单位
        redisTemplate.opsForValue().set("liu", "yalin", 10, TimeUnit.SECONDS);
    }

}
  
