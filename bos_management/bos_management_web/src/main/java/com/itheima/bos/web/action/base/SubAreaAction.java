package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

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
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:00:33 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller//spring 的注解,控制层代码
@Scope("prototype")//spring 的注解,多例
public class SubAreaAction extends CommonAction<SubArea>{

    public SubAreaAction() {
          
        super(SubArea.class);  
        
    }
   //保存
    @Autowired
    private SubAreaService subAreaService;
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String save(){
        subAreaService.save(getModel());
        return SUCCESS;
        
    }
    
    //分页
    
    //Ajax请求，不需要跳转页面
    @Action(value="subareaAction_pageQuery")
    public String pageQuery() throws IOException{
        // EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
      Pageable pageable= new PageRequest(page-1, rows);
      Page<SubArea> page=  subAreaService.findAll(pageable);
      page2Json(page, jsonConfig);
   
        return NONE;
    }
    
  //查询未关联的分区
    @Action(value="subAreaAction_findUnAssociatedSubAreas")
    public String findUnAssociatedSubAreas() throws IOException{
       List<SubArea> list=subAreaService.findUnAssociatedSubAreas();
       JsonConfig jsonConfig=new JsonConfig();
       jsonConfig.setExcludes(new String[]{"subareas"});
        list2json(list, jsonConfig);
        return NONE;
        
    }
    
    
    //查询已关联的分区
    @Action(value="subAreaAction_findAssociatedSubAreas")
    public String findAssociatedSubAreas() throws IOException{
        List<SubArea> list=subAreaService.findAssociatedSubAreas(getModel().getId());
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","couriers"});
        list2json(list, jsonConfig);
        return NONE;
        
    }

}
  
