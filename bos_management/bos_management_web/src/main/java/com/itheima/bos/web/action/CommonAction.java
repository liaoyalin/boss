package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.itheima.bos.domain.base.Area;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:45:19 <br/>       
 */
//抽取共性的代码
public class CommonAction<T> extends ActionSupport implements ModelDriven<T>{
    private T model;
    private Class<T> clazz;
   public CommonAction(Class<T> clazz){
       this.clazz=clazz;
   }

    @Override
    public T getModel() {
          try {
            model=clazz.newInstance();
        } catch (Exception e) {
              
            e.printStackTrace();  
        } 
        return null;
    }
    
    protected int page;//第几页
    protected int rows;//每页显示多少条数据
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public void page2Json(Page<T> page,JsonConfig jsonConfig) throws IOException{
        
        long total = page.getTotalElements();  // 总数据条数
        List<T> list = page.getContent(); // 当前页要实现的内容
        //封装数据
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", list);
        
     // 把对象转化为json字符串
        String json ;
        if(jsonConfig!=null){
            json= JSONObject.fromObject(map,jsonConfig).toString();
        }else{
            json= JSONObject.fromObject(map).toString();
            
        }
        //将json字符串写入到页面
        HttpServletResponse response = ServletActionContext.getResponse();
         response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);

    }
    
    public void list2json( List<T> list ,JsonConfig jsonConfig) throws IOException{
        String json;
        if(jsonConfig!=null){
            json= JSONArray.fromObject(list,jsonConfig).toString();
        }else{
            json= JSONArray.fromObject(list).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
       response.getWriter().write(json);
        
    }

}
  
