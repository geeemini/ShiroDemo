package demo.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Before;
import org.junit.Test;

/**
 * spring boot 整合shiro  测试第三课
 * 通过 ini文件来  对Realm进行身份认证  权限认证等
 */
public class QuickStartTest5_22 {


    @Test
    public void testAuthentication(){

        // 通过shiro.ini 加载用户的认证信息  和 授权信息
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        // 通过SecurityManager 工厂拿到SecurityManager
        SecurityManager securityManager = factory.getInstance();

        //　用SecurityUtils把SecurityManager放进去
        SecurityUtils.setSecurityManager(securityManager);
        // 通过SecurityUtils拿到Subject
        Subject sub = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken("yw","456");

        sub.login(token);

        System.out.println("授权认证："+sub.isAuthenticated());

        System.out.println("root角色有吗？"+sub.hasRole("root"));

        System.out.println("完成认证的用户名："+sub.getPrincipal());

        System.out.println("当前用户--"+sub.getPrincipal()+"--是否拥有权限："+ sub.isPermitted("product:delete"));

        sub.logout();

        System.out.println("logout后授权认证："+sub.isAuthenticated());
    }

}
