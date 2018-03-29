package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepostory;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:44:49 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepostory menuRepostory;

    @Override
    public List<Menu> findLevelOne() {
          
        
        return menuRepostory.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu menu) {
        //导致异常的原因是parentMenu字段是一个瞬时态对象
     // 判断用户是否要添加一级菜单,父菜单是否id为null
        if(menu.getParentMenu()!=null && menu.getParentMenu().getId()==null){
            //说明用户想要添加一级菜单
            menu.setParentMenu(null);
        }
        menuRepostory.save(menu);
          
        
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
          
        return menuRepostory.findAll(pageable);
    }

}
  
