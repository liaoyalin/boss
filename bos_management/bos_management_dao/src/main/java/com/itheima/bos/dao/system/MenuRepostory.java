package com.itheima.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepostory <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:42:49 <br/>       
 */
public interface MenuRepostory extends JpaRepository<Menu, Long>{

    List<Menu> findByParentMenuIsNull();

}
  
