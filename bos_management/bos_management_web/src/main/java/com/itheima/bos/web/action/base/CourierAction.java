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

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:19:26 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
    private Courier model=new Courier();

    @Override
    public Courier getModel() {
          
        return model;
    }
    @Autowired
    private CourierService courierService;
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        courierService.save(model);
        
        return SUCCESS;
        
    }
    private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable=new PageRequest(page-1, rows);
       Page<Courier> page= courierService.findAll(pageable);
       long total = page.getTotalElements();
       List<Courier> list = page.getContent();
      Map<String, Object> map=new HashMap<String, Object>(); 
      map.put("total", total);
      map.put("rows", list);
      
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
   // 在实际开发的时候,为了提高服务器的性能,把前台页面不需要的数据都应该忽略掉(解决no session问题)
      
                     //  发生懒加载异常时的解决方式:
          //a.属性上增加transient 关键字,回导致所有调用都无法生成该字段的值
         // b.注解中增加fetch=FetchType.EAGER 属性,将该字段的值加载出来
        //  c.使用JsonConfig灵活控制忽略字段(实际开发中用)

      String json = JSONObject.fromObject(map,jsonConfig).toString();
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setContentType("application/json; charset=utf-8");
      response.getWriter().write(json);
        
        
        return NONE;
        
    }

}
  