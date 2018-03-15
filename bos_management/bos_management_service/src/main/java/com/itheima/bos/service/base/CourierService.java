package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:18:16 <br/>       
 */
public interface CourierService {

    Page<Courier> findAll(Pageable pageable);

    void save(Courier courier);


}
  