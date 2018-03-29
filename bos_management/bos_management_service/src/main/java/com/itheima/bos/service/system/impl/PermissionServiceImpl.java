package com.itheima.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.PermissionRepostory;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:57:29 <br/>       
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepostory permissionRepostory;

    @Override
    public Page<Permission> findAll(Pageable pageable) {
        
          
        return permissionRepostory.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
          
        permissionRepostory.save(model);
    }

}
  
