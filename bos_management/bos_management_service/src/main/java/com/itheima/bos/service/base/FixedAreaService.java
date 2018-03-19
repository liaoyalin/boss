package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午6:40:28 <br/>       
 */
public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> findAll(Pageable pageable);

}
  
