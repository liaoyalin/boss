package com.itheima.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.WaybillRepostory;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;

/**  
 * ClassName:WayBillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午9:12:41 <br/>       
 */
@Service
@Transactional
public class WayBillServiceImpl implements WayBillService {
    @Autowired
    private WaybillRepostory waybillRepostory;

    @Override
    public void save(WayBill model) {
        waybillRepostory.save(model);
       
        
    }

}
  
