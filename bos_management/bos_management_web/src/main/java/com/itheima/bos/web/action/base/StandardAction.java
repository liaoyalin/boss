package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.action.CommonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:41:24 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller//spring 的注解,控制层代码
@Scope("prototype")//spring 的注解,多例
public class StandardAction extends CommonAction<Standard> {

  
    public StandardAction() {
          
        super(Standard.class);  
        
    }
    @Autowired
    private StandardService standardService;
    @Action(value="standardAction_save",results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
    public String save(){
        standardService.save(getModel());
        return SUCCESS;
        
    }
   
    //Ajax请求，不需要跳转页面
    @Action(value="standardAction_pageQuery")
    public String pageQuery() throws IOException{
        // EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1
      Pageable pageable= new PageRequest(page-1, rows);
      Page<Standard> page=  standardService.findAll(pageable);
     page2Json(page, null);

        
        return NONE;
    }
    @Action(value="standard_findAll")
    public String findAll() throws IOException{
        Page<Standard> page = standardService.findAll(null);
        List<Standard> list = page.getContent();
        //把list集合转换成json对象
        String json = JSONArray.fromObject(list).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write(json);
       Thread thread = new Thread();
       thread.run();
        return NONE;
        
    }

}
  
