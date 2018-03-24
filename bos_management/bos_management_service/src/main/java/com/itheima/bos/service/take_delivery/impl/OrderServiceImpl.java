package com.itheima.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.take_delivery.OrderRepostory;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.service.take_delivery.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午4:26:58 <br/>       
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepostory orderRepostory;
    
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveOrder(Order order) {
        //把瞬时态的Area转换成持久态的area
        Area sendArea = order.getSendArea();
        if(sendArea!=null){
            //持久态对象
            Area sendAreaDB = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(), sendArea.getCity(), sendArea.getDistrict());
       order.setSendArea(sendAreaDB);
        }
        
        Area recArea = order.getRecArea();
        if(recArea!=null){
            //持久态对象
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(), recArea.getCity(), recArea.getDistrict());
       order.setRecArea(recAreaDB);
        }
        //订单编号
        order.setOrderNum(UUID.randomUUID().toString().replaceAll("-", ""));
        //下单时间
        order.setOrderTime(new Date());
        orderRepostory.save(order);
        //自动分单
        String sendAddress = order.getSendAddress();
        
        if(StringUtils.isNotEmpty(sendAddress)){
            //1.根据发件地址完全匹配
            //思路：让crm系统根据发件地址查询定区id
        String fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedAreaByAddress")
        
        .type(MediaType.APPLICATION_JSON)
        .query("address", sendAddress)
        .accept(MediaType.APPLICATION_JSON)
        .get(String.class);
        System.out.println(fixedAreaId);
        }
       
        //根据定区id查询定区
        //查询快递员，安排快递员上门
        //
        //2.根据发件地址模糊匹配


    }

}
  
