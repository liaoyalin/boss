package com.itheima.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserRepostory <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午9:52:07 <br/>       
 */
public interface UserRepostory extends JpaRepository<User, Long>{
    User findByUsername(String username);

}
  
