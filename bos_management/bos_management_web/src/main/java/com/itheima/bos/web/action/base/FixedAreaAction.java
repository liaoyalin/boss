package com.itheima.bos.web.action.base;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.crm.domain.Customer;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午5:58:41 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller//spring 的注解,控制层代码
@Scope("prototype")//spring 的注解,多例
public class FixedAreaAction extends ActionSupport implements ModelDriven<FixedArea> {

    private FixedArea model=new FixedArea();
    @Override
    public FixedArea getModel() {
          
        return model;
    }
    @Autowired
    private FixedAreaService fixedAreaService;
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save(){
        fixedAreaService.save(model);
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
    @Action(value="fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException{
      Pageable pageable =new PageRequest(page-1, rows);
     Page<FixedArea> page=fixedAreaService.findAll(pageable);
     long total = page.getTotalElements();
     List<FixedArea> content = page.getContent();
     Map<String, Object> map=new HashMap<>();
     map.put("total", total);
     map.put("rows", content);
     JsonConfig jsonConfig=new JsonConfig();
     jsonConfig.setExcludes(new String[]{"subareas","couriers"});
     
     String json = JSONObject.fromObject(map,jsonConfig).toString();
     HttpServletResponse response = ServletActionContext.getResponse();
     response.setContentType("application/json;charset=utf-8");
     response.getWriter().write(json);
        return NONE;
        
    }
    
    
    //向crm系统发起请求，查询未关联定区的客户
    @Action(value="fixedAreaAction_findUnAssociatedCustomers")
    public String findUnAssociatedCustomers() throws IOException{
      List<Customer> list= (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssocaited")
              .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
      JsonConfig jsonConfig=new JsonConfig();
      String json;
      if(jsonConfig!=null){
          json= JSONArray.fromObject(list,jsonConfig).toString();
      }else{
          json= JSONArray.fromObject(list).toString();
      }
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setContentType("application/json;charset=UTF-8");
     response.getWriter().write(json);
        return NONE;
        
    }
    
  //向crm系统发起请求，查询已关联定区的客户
    @Action(value="fixedAreaAction_findAssociatedCustomers")
    public String findAssociatedCustomers() throws IOException{
        System.out.println("~~~~~~~~~~~~~~~"+getModel().getId());
      List<Customer> list= (List<Customer>) WebClient.create("http://localhost:8180/crm/webService/customerService/findCustomersAssocaited")
              .query("fixedAreaId", getModel().getId())
              .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
      JsonConfig jsonConfig=new JsonConfig();
      String json;
      if(jsonConfig!=null){
          json= JSONArray.fromObject(list,jsonConfig).toString();
      }else{
          json= JSONArray.fromObject(list).toString();
      }
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setContentType("application/json;charset=UTF-8");
     response.getWriter().write(json);
        return NONE;
        
    }
    //使用属性驱动获取到要关联到指定定区的客户id
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    //向crm发送请求，关联客户
    @Action(value="fixedAreaAction_assignCustomers2FixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignCustomers2FixedArea(){
        WebClient.create("http://localhost:8180/crm/webService/customerService//assignCustomers2FixedArea")
        .query("fixedAreaId", getModel().getId())
        .query("customerIds", customerIds)
        .type(MediaType.APPLICATION_JSON)
  .accept(MediaType.APPLICATION_JSON).put(null);
        
        return SUCCESS;
        
    }
    
    private Long courierId;
    private Long takeTimeId;
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    //关联快递员
    @Action(value=" fixedAreaAction_associationCourierToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String associationCourierToFixedArea(){
       
        fixedAreaService.associationCourierToFixedArea(getModel().getId(),courierId,takeTimeId);
        return SUCCESS;
        
    }
    
    //获取分区的id
    private Long[] subAreaIds;
    public void setSubAreaIds(Long[] subAreaIds) {
        this.subAreaIds = subAreaIds;
    }
    //关联分区
    @Action(value="fixedAreaAction_assignSubAreas2FixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String assignSubAreas2FixedArea(){
        fixedAreaService.assignSubAreas2FixedArea(getModel().getId(),subAreaIds);
        
        return SUCCESS;
        
    }
    

}
  
