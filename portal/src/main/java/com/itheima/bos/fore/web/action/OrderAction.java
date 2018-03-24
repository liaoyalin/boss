package com.itheima.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.ctc.wstx.util.StringUtil;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:OrderAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午3:31:21 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
    private Order model=new Order();

    @Override
    public Order getModel() {
          
        return model;
    }
    //使用属性驱动获取发件和收件的区域信息
    private String sendAreaInfo;
    private String recAreaInfo;
   public void setRecAreaInfo(String recAreaInfo) {
    this.recAreaInfo = recAreaInfo;
}
    
    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }
    
    @Action(value="orderAction_add",results={@Result(name="success",location="/index.html",type="redirect")})
    public String saveOrder(){
        
     // 获取发件区域数据
        if(StringUtils.isNotEmpty(sendAreaInfo)){
            String[] split = sendAreaInfo.split("/");
            //去掉省市区
            String province = split[0];
            String city =split[1];
            String district = split[2];
            
            province = province.substring(0, province.length() - 1);
            city =city.substring(0, city.length()-1);
            district=district.substring(0, district.length()-1);
            // 封装Area
            Area area=new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);
            model.setSendArea(area);
        
        
    }
        if (StringUtils.isNotEmpty(recAreaInfo)) {
            String[] split = recAreaInfo.split("/");

            String province = split[0];
            String city = split[1];
            String district = split[2];

            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            Area area = new Area();
            area.setProvince(province);
            area.setCity(city);
            area.setDistrict(district);

            model.setRecArea(area);
        }
        WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveOrder")
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON)
        .post(model);
        

        return SUCCESS;
        
    }

}
  
