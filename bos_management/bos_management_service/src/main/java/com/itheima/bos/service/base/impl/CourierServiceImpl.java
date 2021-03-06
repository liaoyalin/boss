package com.itheima.bos.service.base.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:27:39 <br/>       
 */
@Transactional
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;

    @Override
    public void save(Courier courier) {
        courierRepository.save(courier);

    }

    @Override
    public Page<Courier> findAll(Pageable pageable) {
           
        return courierRepository.findAll(pageable);
    }
//在调用方法前，框架就会检查当前用户是否有对应的权限，如果有就放行，没有就抛异常
    @RequiresPermissions("batchDel")
    @Override
    public void batchDel(String ids) {
        //真实开发中，只有逻辑删除
        if( StringUtils.isNoneEmpty(ids)){
            String[] split = ids.split(",");
            for (String id : split) {
                
                courierRepository.updateDelTagById(Long.parseLong(id));
            }
        }
       
        
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
          
        return courierRepository.findAll(specification, pageable);
    }

    @Override
    public void restore(String ids) {
          
        if( StringUtils.isNoneEmpty(ids)){
            String[] split = ids.split(",");
            for (String id : split) {
                
                courierRepository.updateRestoreById(Long.parseLong(id));
            }
        }  
        
    }

}
  
