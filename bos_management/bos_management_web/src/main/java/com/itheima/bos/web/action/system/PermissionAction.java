package com.itheima.bos.web.action.system;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.CommonAction;
import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:54:50 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction  extends CommonAction<Permission>{
    @Autowired
    private PermissionService permissionService;

    public PermissionAction() {
          
        super(Permission.class);  
        
    }

    
    @Action(value="permissionAction_pageQuery")
    public String pageQuery() throws IOException{
     // Struts框架在封装数据的时候会优先封装给模型对象,会导致属性驱动中的page对象无法获取数据
      Pageable pageable= new PageRequest(page-1, rows);
      Page<Permission> page=  permissionService.findAll(pageable);
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"roles"});
     page2Json(page, jsonConfig);

        
        return NONE;
    }
    
    
    @Action(value="permissionAction_save",results={@Result(name="success",location="/pages/system/permission.html",type="redirect")})
    public String save(){
        permissionService.save(getModel());
        
        return SUCCESS;
        
    }
}
  
