package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Permission;

/**  
 * ClassName:PermissionRepostory <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:58:02 <br/>       
 */
public interface PermissionRepostory extends JpaRepository<Permission, Long>{

    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
    List<Permission> findbyUid(Long uid);

}
  
