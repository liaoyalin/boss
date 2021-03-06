package com.itheima.bos.service.base.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午6:40:50 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    
    @Autowired
    private CourierRepository courierRepository;
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    
    @Autowired
    private SubAreaRepository subAreaRepository;

    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
        
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public void associationCourierToFixedArea(Long fixedAreaId, Long courierId, Long takeTimeId) {
          
        //代码执行成功后，快递员表发送update操作，快递员和定区中间表会发送insert操作
        
        //持久态对象
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
        //建立快递员和时间的关联
        courier.setTakeTime(takeTime);
        
        //建立快递员和定区的关联，(必须要用定区去关联快递员)
        //因为Courier实体中fixedAreas字段上方添加了mappedBy属性，代表了快递员放弃了对关系的维护
        fixedArea.getCouriers().add(courier);
        
    }
//关联分区到指定的定区
    @Override
    public void assignSubAreas2FixedArea(Long fixedAreaId, Long[] subAreaIds) {
          
        //先解绑，把当前定区上绑定的所有分区解绑
        FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
        //查找到fixedArea对象所绑定的分区
        Set<SubArea> subareas = fixedArea.getSubareas();
        for (SubArea subArea : subareas) {
            subArea.setFixedArea(null);
        }
        //再绑定
        for (Long subAreaId : subAreaIds) {
            SubArea subArea = subAreaRepository.findOne(subAreaId);
            subArea.setFixedArea(fixedArea);
        }
    }

}
  
