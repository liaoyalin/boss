package com.itheima.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:48:30 <br/>       
 */
public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    void save(Role model, String menuIds, Long[] permissionIds);



}
  
