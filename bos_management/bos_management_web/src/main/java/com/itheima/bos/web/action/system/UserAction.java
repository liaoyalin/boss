package com.itheima.bos.web.action.system;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.User;
import com.itheima.bos.web.action.CommonAction;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午9:17:40 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction  extends CommonAction<User>{

    public UserAction() {
        super(User.class);  
        
    }
    //用户输入的验证码
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value="userAction_login",results={@Result(name="success",location="/index.html",type="redirect"),
            @Result(name="login",location="/login.html",type="redirect")})
    public String login(){
        //获取session中的验证码
        String  serverCode = (String)ServletActionContext.getRequest().getSession().getAttribute("key");
        if(StringUtils.isNotEmpty(checkcode)&& StringUtils.isNotEmpty(serverCode)&& checkcode.equals(serverCode) ){
            //登录
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token=new UsernamePasswordToken(getModel().getUsername(),getModel().getPassword());
            subject.login(token);
            
        }
        return LOGIN;
       
        
    }

}
  
