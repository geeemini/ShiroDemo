package demo.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * spring boot 整合shiro  测试第三课
 * 通过 ini文件来  对Realm进行身份认证  权限认证等
 */
public class QuickStartTest5_23 {


    @Test
    public void testAuthentication(){

        // 通过shiro.ini 加载用户的认证信息  和 授权信息
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:jdbcrealm.ini");
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

        // 检查角色
        String role = "root";
        System.out.println(role+"角色有吗？"+sub.hasRole(role));

        System.out.println("完成认证的用户名："+sub.getPrincipal());

        // 检查权限
        String permitted = "video:find";
        System.out.println("当前用户--"+sub.getPrincipal()+"--是否拥有"+permitted+"权限："+ sub.isPermitted(permitted));

        sub.logout();

        System.out.println("logout后授权认证："+sub.isAuthenticated());
    }

    /**
     * jdbc  通过JDBCREALM 连接
     */
    @Test
    public void test1(){
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://47.114.39.98:3306/shiro_demo?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false");
        ds.setUsername("root");
        ds.setPassword("Yw123456.");

        JdbcRealm realm = new JdbcRealm();
        // jdbcRealm 配置数据源
        realm.setDataSource(ds);
        // jdbcRealm 设置自动关联权限 为true
        realm.setPermissionsLookupEnabled(true);

        securityManager.setRealm(realm);

        SecurityUtils.setSecurityManager(securityManager);

        // 通过SecurityUtils拿到Subject
        Subject sub = SecurityUtils.getSubject();
        UsernamePasswordToken token =
                new UsernamePasswordToken("lxx","123");

        sub.login(token);

        System.err.println("授权认证："+sub.isAuthenticated());

        // 检查角色
        String role = "root";
        System.err.println(role+"角色有吗？"+sub.hasRole(role));

        System.err.println("完成认证的用户名："+sub.getPrincipal());

        // 检查权限
        String permitted = "video:delete";
        System.err.println("当前用户--"+sub.getPrincipal()+"--是否拥有"+permitted+"权限："+ sub.isPermitted(permitted));

        sub.logout();

        System.err.println("logout后授权认证："+sub.isAuthenticated());



    }

}
