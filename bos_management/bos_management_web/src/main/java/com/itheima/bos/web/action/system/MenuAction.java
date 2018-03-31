package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:39:27 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction  extends CommonAction<Menu>{

    public MenuAction() {
          
        super(Menu.class);  
        
    }
    @Autowired
    private MenuService menuService;
    
    @Action(value="menuAction_findLevelOne")
    public String findAll() throws IOException{
        List<Menu> list=menuService.findLevelOne();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","childrenMenus","parentMenu"});
        list2json(list, jsonConfig);
        return NONE;
        
    }
    
    @Action(value="menuAction_save",results={@Result(name="success",location="/pages/system/menu.html",type="redirect")})
    public String save(){
        menuService.save(getModel());
        
        return SUCCESS;
        
    }
    
    
    @Action(value="menuAction_pageQuery")
    public String pageQuery() throws IOException{
     // Struts框架在封装数据的时候会优先封装给模型对象,会导致属性驱动中的page对象无法获取数据
      Pageable pageable= new PageRequest(Integer.parseInt(getModel().getPage())-1, rows);
      Page<Menu> page=  menuService.findAll(pageable);
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"roles","childrenMenus","parentMenu"});
     page2Json(page, jsonConfig);

        
        return NONE;
    }
    
    //根据用户查找菜单(动态加载菜单)
    @Action(value="menuAction_findbyUser")
    public String findbyUser() throws IOException{
        // 获取当前用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
      List<Menu> list =menuService.findbyUser(user);
      JsonConfig jsonConfig = new JsonConfig();
      jsonConfig.setExcludes(
              new String[] {"roles", "childrenMenus", "parentMenu","children"});
      list2json(list, jsonConfig);

        return NONE;
    }

}
  
