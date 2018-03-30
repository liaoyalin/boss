package com.itheima.bos.service.system.impl;

import javax.mail.Folder;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepostory;
import com.itheima.bos.dao.system.PermissionRepostory;
import com.itheima.bos.dao.system.RoleRepostory;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:49:07 <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepostory roleRepostory;
    @Autowired
    private MenuRepostory menuRepostory;
    @Autowired
    private PermissionRepostory permissionRepostory;

    @Override
    public Page<Role> findAll(Pageable pageable) {
          
        return roleRepostory.findAll(pageable);
    }

   

    @Override
    public void save(Role role, String menuIds, Long[] permissionIds) {
          roleRepostory.save(role);
        if(StringUtils.isNotEmpty(menuIds)){
            String[] split = menuIds.split(",");
            for (String menuId : split) {
                Menu menu = menuRepostory.findOne(Long.parseLong(menuId));
                role.getMenus().add(menu);
            }
        }
        if(permissionIds!=null && permissionIds.length>0){
            for (Long permissionId : permissionIds) {
                Permission permission = permissionRepostory.findOne(permissionId);
                role.getPermissions().add(permission);
            }
        }
    }
    
 
}
  
