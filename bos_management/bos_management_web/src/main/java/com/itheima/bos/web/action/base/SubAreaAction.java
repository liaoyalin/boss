package com.itheima.bos.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:00:33 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller//spring 的注解,控制层代码
@Scope("prototype")//spring 的注解,多例
public class SubAreaAction extends ActionSupport implements ModelDriven<SubArea>{
    private SubArea model=new SubArea();

    @Override
    public SubArea getModel() {
          
        return model;
    }
    @Autowired
    private SubAreaService subAreaService;
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String save(){
        subAreaService.save(model);
        return SUCCESS;
        
    }
   

}
  
