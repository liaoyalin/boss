package com.itheima.bos.web.action.system;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

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
            try {
                subject.login(token);
                //保存用户
                //此方法的返回值是由realm中的 doGetAuthenticationInfo方法的里面的SimpleAuthenticationInfo的第一个参数决定的
               User user= (User) subject.getPrincipal();
               ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            } catch (UnknownAccountException e) {
                  
                e.printStackTrace();  
                //用户名写错了
                System.out.println("用户名写错了");
                
            } catch (IncorrectCredentialsException e) {
                
              e.printStackTrace();  
              //密码写错了
              System.out.println("密码写错了");
              
          }catch (Exception e) {
              
            e.printStackTrace();  
            //密码写错了
            System.out.println("其他服务器错误");
            
        }
            
        }
        return LOGIN;
       
    }
    
    @Action(value="userAction_logout",
            results={
            @Result(name="success",location="/login.html",
                    type="redirect")})
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        
        return SUCCESS;
        
    }
    
    @Autowired
    private UserService userService;
    
    private Long[] roleIds;
    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
    @Action(value="userAction_save",
            results={
            @Result(name="success",location="/pages/system/userlist.html",
                    type="redirect")})
    public String save(){
        userService.save(getModel(),roleIds);
      
        return SUCCESS;
        
    }
    
    @Action(value="userAction_pageQuery")
    public String pageQuery() throws IOException{
     // Struts框架在封装数据的时候会优先封装给模型对象,会导致属性驱动中的page对象无法获取数据
      Pageable pageable= new PageRequest(page-1, rows);
      Page<User> page=  userService.findAll(pageable);
      JsonConfig jsonConfig=new JsonConfig();
      jsonConfig.setExcludes(new String[]{"roles"});
     page2Json(page, jsonConfig);

        
        return NONE;
    }

}
  
