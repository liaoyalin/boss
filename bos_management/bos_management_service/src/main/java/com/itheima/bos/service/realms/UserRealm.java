package com.itheima.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.system.PermissionRepostory;
import com.itheima.bos.dao.system.RoleRepostory;
import com.itheima.bos.dao.system.UserRepostory;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午9:46:05 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm{
    @Autowired
    private UserRepostory userRepostory;
    @Autowired
    private RoleRepostory roleRepostory;
    @Autowired
    private PermissionRepostory permissionRepostory;

    //授权的方法
    //每一次访问需要权限的资源时，都会调用授权的方法，太浪费资源(可以使用缓存技术改善)
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        // 需要根据当前的用户去查询对应的权限和角色
        Subject subject = SecurityUtils.getSubject();
        User user= (User) subject.getPrincipal();
        if("admin".equals(user.getUsername())){
            // 内置管理员的权限和角色是写死的
            List<Role> roles = roleRepostory.findAll();
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            
            List<Permission> permissions = permissionRepostory.findAll();
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        }else {
            List<Role> roles = roleRepostory.findbyUid(user.getId());
            for (Role role : roles) {
                info.addRole(role.getKeyword());
            }
            
            List<Permission> permissions = permissionRepostory.findbyUid(user.getId());
            for (Permission permission : permissions) {
                info.addStringPermission(permission.getKeyword());
            }
        }
        // 角色其实是权限的集合,并不是所有的权限都会包含在某一个角色中
        

        return info;
    }

    //认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken=(UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        //根据用户名查找用户
        User user = userRepostory.findByUsername(username);
        //找不到-》抛异常
        if(user!=null){
            //找到-》对比密码
            
            /**
             * @param principal 参数一
             *            当事人,主体.往往传递从数据库中查询出来的用户对象
             * @param credentials 参数2
             *            凭证,密码(是从数据库中查询出来的密码)
             * @param realmName  参数3
             * 
             */

            AuthenticationInfo info=
                    new SimpleAuthenticationInfo(user, user.getPassword(), getName());
            //对比成功，执行后续的逻辑
            //对比失败，抛异常
            return info;
        }
          
        return null;
    }

}
  
