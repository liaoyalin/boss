package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.action.CommonAction;
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
public class CourierAction extends CommonAction<Courier>{
    public CourierAction() {
          
        super(Courier.class);  
        
    }
   
    @Autowired
    private CourierService courierService;
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        courierService.save(getModel());
        
        return SUCCESS;
        
    }
    
    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException{
        
        Specification<Courier> specification=new Specification<Courier>() {
            //创建一个查询的where语句
            //root：根对象，可以简单的认为就是泛型对象
            //cb：构建查询的条件
            //
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String courierNum = getModel().getCourierNum();
                String company = getModel().getCompany();
                Standard standard = getModel().getStandard();
                String type = getModel().getType();
                List<Predicate>list=new ArrayList<>();
                if(StringUtils.isNotEmpty(courierNum)){
                    //如果工号不为空，就构建一个等值查询
                    //参数2：具体要比较的值
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if(StringUtils.isNotEmpty(company)){
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotEmpty(type)){
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                if(standard!=null){
                    String name = standard.getName();
                    if(StringUtils.isNoneEmpty(name)){
                        //连表查询
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                    }
                }
                //用户没有输入查询条件
                if(list.size()==0){
                    return null;
                }
                //用户输入了查询条件
                Predicate[] arr=new Predicate[list.size()];
                list.toArray(arr);
                //用户输入了多少个条件，就让条件都满足
                Predicate predicate = cb.and(arr);
                return predicate;
            }
        };
        
        
        
        
        Pageable pageable=new PageRequest(page-1, rows);
       Page<Courier> page= courierService.findAll(specification,pageable);
       /*long total = page.getTotalElements();
       List<Courier> list = page.getContent();
      Map<String, Object> map=new HashMap<String, Object>(); 
      map.put("total", total);
      map.put("rows", list);*/
      
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
   // 在实际开发的时候,为了提高服务器的性能,把前台页面不需要的数据都应该忽略掉(解决no session问题)
      
                     //  发生懒加载异常时的解决方式:
          //a.属性上增加transient 关键字,回导致所有调用都无法生成该字段的值
         // b.注解中增加fetch=FetchType.EAGER 属性,将该字段的值加载出来
        //  c.使用JsonConfig灵活控制忽略字段(实际开发中用)

      
        page2Json(page, jsonConfig);
        return NONE;
        
    }
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    //作废快递员
    @Action(value="courierAction_batchDel",results={@Result(name="success",
            location="/pages/base/courier.html",type="redirect")})
    public String batchDel(){
        courierService.batchDel(ids);
        return SUCCESS;
        
    }
    
    //还原快递员 
    @Action(value="courierAction_restore",results={@Result(name="success",
            location="/pages/base/courier.html",type="redirect")})
    public String restore(){
        courierService.restore(ids);
        return SUCCESS;
        
    }
    //查询所有在职的快递员
    @Action(value="courierAction_listajax")
    public String listajax() throws IOException{
        Specification<Courier> specification=new Specification<Courier>() {
            
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //判断deltag字段是否为空，如果为空则表示在职，为1则表示不在职
                Predicate predicate = cb.isNull(root.get("deltag").as(Character.class));
                return predicate;
            }
        };
        Page<Courier> p = courierService.findAll(specification, null);
        List<Courier> list = p.getContent();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        list2json(list, jsonConfig);
        return NONE;
}
}
  
