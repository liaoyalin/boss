package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.system.Role;

/**  
 * ClassName:RoleRepostory <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:47:39 <br/>       
 */
public interface RoleRepostory extends JpaRepository<Role, Long>{

    @Query("select r from Role r inner join r.users u where u.id=?")
    List<Role> findbyUid(Long uid);

}
  
