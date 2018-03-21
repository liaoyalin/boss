package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:40:52 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller//spring 的注解,控制层代码
@Scope("prototype")//spring 的注解,多例
public class TakeTimeAction  extends CommonAction<TakeTime>{

    public TakeTimeAction() {
          
        super(TakeTime.class);  
        
    }
    @Autowired
    private TakeTimeService takeTimeService;
    @Action(value="takeTimeAction_listAjax")
    public String listAjax() throws IOException{
       List<TakeTime> list= takeTimeService.findAll();
       list2json(list, null);
  
        return NONE;
        
    }

}
  
