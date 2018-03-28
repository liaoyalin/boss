package com.itheima.bos.service.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.system.UserRepostory;
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

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
          
        return null;
    }

    //认证
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
  
