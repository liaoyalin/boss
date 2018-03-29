package com.itheima.bos.web.action.system;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午4:51:48 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends CommonAction<Role>{

    public RoleAction() {
          
        super(Role.class);  
        
    }
    @Autowired
    private RoleService roleService;
    
    @Action(value="roleAction_pageQuery")
    public String pageQuery() throws IOException{
     // Struts框架在封装数据的时候会优先封装给模型对象,会导致属性驱动中的page对象无法获取数据
      Pageable pageable= new PageRequest(page-1, rows);
      Page<Role> page=  roleService.findAll(pageable);
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"users","permissions","menus"});
     page2Json(page, jsonConfig);

        
        return NONE;
    }

}
  
