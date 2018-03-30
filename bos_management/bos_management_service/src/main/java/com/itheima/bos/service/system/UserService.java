package com.itheima.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 上午11:08:06 <br/>       
 */
public interface UserService {

    void save(User user, Long[] roleIds);

    Page<User> findAll(Pageable pageable);

}
  
