package com.itheima.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.crm.domain.Customer;
import com.itheima.utils.MailUtils;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月20日 下午6:38:52 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
    private Customer model=new Customer();

    @Override
    public Customer getModel() {
          
        return model;
    }
    @Action(value="customerAction_sendSMS")
    public String sendSMS(){
        //随机生成验证码
        String code = RandomStringUtils.randomNumeric(6);
        System.out.println(code);
        //存储验证码
        ServletActionContext.getRequest().getSession().setAttribute("serverCode", code);
        try {
            SmsUtils.sendSms(model.getTelephone(), code);
        } catch (ClientException e) {
              
            e.printStackTrace();  
            
        }
        return NONE;
        
    }
    //使用属性驱动获取页面的验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    //使用redis模板，存储激活码(因为激活码只有24个小时 就过期了)
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //注册
    @Action(value="customerAction_regist",results={
            @Result(name="success",location="/signup-success.html",type="redirect"),
            @Result(name="error",location="/signup-fail.html",type="redirect")
            })
    public String regist(){
        //校验验证码
        //生成的验证码serverCode
       String serverCode= (String) ServletActionContext.getRequest().getSession().getAttribute("serverCode");
       //判断用户输入的验证码checkcode和生成的验证码serverCode
       if(StringUtils.isNotEmpty(serverCode)&& StringUtils.isNotEmpty(checkcode)&& serverCode.equals(checkcode) ){
           
           //调用crm的注册方法
           WebClient.create("http://localhost:8180/crm/webService/customerService/save")
           .accept(MediaType.APPLICATION_JSON)
           .type(MediaType.APPLICATION_JSON).post(model);
           //生成的激活码
           String activeCode = RandomStringUtils.randomNumeric(23);
           //这里参数1必须要写用户的手机号，方便等下取出来
           redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1, TimeUnit.DAYS);
           String emailBody="感谢您注册本网站,请在24小时内点击<a href='http://localhost:8280/portal/customerAction_active.action?activeCode="+activeCode+"&telephone="+model.getTelephone()+"'>本链接</a>,激活您的账号";
        MailUtils.sendMail(model.getEmail(), "激活邮件", emailBody);
           return SUCCESS;
       }
        return ERROR;
        
    }
    //获取激活码
    private String activeCode;
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    //激活
    @Action(value="customerAction_active",results={
            @Result(name="success",location="/login.html",type="redirect"),
            @Result(name="error",location="/signup-fail.html",type="redirect")
            })
    public String active(){
        //对比激活码
        String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
        if(StringUtils.isNotEmpty(serverCode)&& StringUtils.isNotEmpty(activeCode)&& serverCode.equals(activeCode)){
            
            //激活
            WebClient.create("http://localhost:8180/crm/webService/customerService/active")
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())
            .put(null);
            return SUCCESS;
        }
        
        
        return ERROR;
        
        
    }
    
    
    @Action(value="customerAction_login",results={
            @Result(name="success",location="/index.html",type="redirect"),
            @Result(name="error",location="/login.html",type="redirect"),
            @Result(name="unactived",location="/login.html",type="redirect")
            }) 
    public String login(){
        //校验用户输入的验证码
        //获取服务器的验证码
        Object validateCode = ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        if(StringUtils.isNotEmpty("validateCode") && StringUtils.isNotEmpty(checkcode) && validateCode.equals(checkcode)){
            //校验用户是否激活
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/isActived")
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .query("telephone", model.getTelephone())
            .get(Customer.class);
            if(customer!=null && customer.getType()!=null){
                if(customer.getType()==1){
                    //用户才是激活状态
                    
                    //登录
                    Customer c = WebClient.create("http://localhost:8180/crm/webService/customerService/login")
                    .accept(MediaType.APPLICATION_JSON)
                    .type(MediaType.APPLICATION_JSON)
                    .query("telephone", model.getTelephone())
                    .query("password", model.getPassword())
                    .get(Customer.class);
                    ServletActionContext.getRequest().getSession().setAttribute("user", c);
                    return SUCCESS;
                }else{
                    //用户已经注册，但是没有激活
                    return "unactived";
                }
            }
            
        }
        return ERROR;
        
    }

}
  
