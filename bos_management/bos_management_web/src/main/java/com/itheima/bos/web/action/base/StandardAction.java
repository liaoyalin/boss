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
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    private Standard model=new Standard();

    @Override
    public Standard getModel() {
          
        return model;
    }
    @Autowired
    private StandardService standardService;
    @Action(value="standardAction_save",results={@Result(name="success",location="/pages/base/standard.html",type="redirect")})
    public String save(){
        standardService.save(model);
        return SUCCESS;
        
    }
    //使用属性驱动获取数据
    private int page;//第几页
    private int rows;//每页显示多少条数据
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    //Ajax请求，不需要跳转页面
    @Action(value="standardAction_pageQuery")
    public String pageQuery() throws IOException{
        // EasyUI的页码是从1开始的
        // SPringDataJPA的页码是从0开始的
        // 所以要-1
      Pageable pageable= new PageRequest(page-1, rows);
      Page<Standard> page=  standardService.findAll(pageable);
      long total = page.getTotalElements();  // 总数据条数
      List<Standard> list = page.getContent(); // 当前页要实现的内容
      //封装数据
      Map<String, Object> map=new HashMap<String, Object>();
      map.put("total", total);
      map.put("rows", list);
      // 方式一：JSONObject : 封装对象或map集合
      // 方式二：JSONArray : 数组,list集合
   // 把对象转化为json字符串
      String json = JSONObject.fromObject(map).toString();
      //将json字符串写入到页面
      HttpServletResponse response = ServletActionContext.getResponse();
       response.setContentType("application/json;charset=UTF-8");
      response.getWriter().write(json);

        
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
        
        return NONE;
        
    }

}
  
